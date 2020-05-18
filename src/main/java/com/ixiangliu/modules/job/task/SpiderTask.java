package com.ixiangliu.modules.job.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ixiangliu.common.exception.BizException;
import com.ixiangliu.common.utils.DateUtil;
import com.ixiangliu.common.utils.SpringContextUtils;
import com.ixiangliu.modules.spider.entity.SpiderResult;
import com.ixiangliu.modules.spider.service.ISpiderResultService;
import com.ixiangliu.modules.sys.entity.Config;
import com.ixiangliu.modules.sys.service.IConfigService;
import com.ixiangliu.modules.wechat.dto.BaseResult;
import com.ixiangliu.modules.wechat.dto.TemplateMessage;
import com.ixiangliu.modules.wechat.service.IWechatService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 测试定时任务(演示Demo，可删除)
 *
 * testTask为spring bean的名称
 */
@Slf4j
@Component("spiderTask")
public class SpiderTask implements ITask {

	@Autowired
	ISpiderResultService iSpiderResultService;

	@Autowired
	private IConfigService iConfigService;

	@Value("${appId}")
	private String appId;

	@Value("${appSecret}")
	private String appSecret;

	@Override
	public void run(String params){
		Random random = new Random();
		int randNum = random.nextInt(10);
		try {
			Thread.sleep(randNum*1000);
			log.info("---------------------------执行定时任务开始: " + LocalDateTime.now());
			http();
			log.info("---------------------------执行定时任务结束: " + LocalDateTime.now());
		} catch (Exception e) {
			log.error("执行定时任务失败",e);
			throw new BizException("执行定时任务失败",e);
		}

	}

	public void http () {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		// 请求
		Long longtimeNew=System.currentTimeMillis();
		Config config = iConfigService.getOne(new QueryWrapper<Config>().eq("param_key", "GUI_GAO_SU"));
		String guiUrl = config.getParamValue();
		HttpGet httpGet = new HttpGet(guiUrl + "/vshop_List.aspx?type=ajax&Action=GetProductList&BrandID=0&sid=-1&subcat=0&lp=0&hp=0&k=keyword&OrderSort=1&p=&page=1&Size=1000&_="+longtimeNew);
		// 响应
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpGet);
			// 响应体
			HttpEntity responseEntity = response.getEntity();
			String resBody = EntityUtils.toString(responseEntity);
			JSONObject body = JSONObject.parseObject(resBody);
			JSONArray jsonArray =  JSONObject.parseArray(body.getString("Product"));
			List<SpiderResult> insertList = new ArrayList<>();
			List<SpiderResult> updateList = new ArrayList<>();
			List<SpiderResult> dbList = iSpiderResultService.list(new QueryWrapper<SpiderResult>().eq("type", "guigaosu_maotai"));
			List<String> paramIdList = dbList.stream().map(SpiderResult::getParamId).collect(Collectors.toList());
			jsonArray.forEach(element -> {
				JSONObject product = (JSONObject)element;
				SpiderResult spiderResult;
				if (paramIdList.contains(product.get("productid").toString())){
					spiderResult = dbList.stream().filter(item -> item.getParamId().equals(product.get("productid").toString())).findFirst().get();
					updateList.add(spiderResult);
				} else {
					spiderResult = new SpiderResult();
					insertList.add(spiderResult);
				}
				spiderResult.setDetail(product.toString());
				spiderResult.setType("guigaosu_maotai");
				spiderResult.setTitle(product.get("productname").toString());
				spiderResult.setParamOne(product.get("Storage").toString());
				spiderResult.setParamTwo(guiUrl + "/products/product-"+product.get("productid").toString()+".html");
				spiderResult.setParamThree(product.get("memberprice").toString());
				spiderResult.setParamId(product.get("productid").toString());
				iSpiderResultService.saveOrUpdate(spiderResult);
				if (spiderResult.getOrderNum() > 0 && spiderResult.getParamOne().equals("1")){
					send(spiderResult);
				}
			});
			iSpiderResultService.saveBatch(insertList);
			iSpiderResultService.updateBatchById(updateList);
			log.info("数据保存成功");
		} catch (Exception e) {
			log.error("数据保存失败",e);
		} finally {
			try {
				if (response != null) {
					// 关闭连接，则此次连接被丢弃
					response.close();
				}
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void send (SpiderResult spiderResult) {
		IWechatService iWechatService = (IWechatService) SpringContextUtils.getBean("iWechatService");
		TemplateMessage msg = new TemplateMessage();
		msg.setTemplateId("6QytVD6ZiDD5kFcVBUtQh1WvNJwIVOMREYozcyZST_M");
		msg.setTopcolor("#000033");
		msg.setTouser("oa6jXwXYH6VLR_b2UIxMQK9obCtQ");
		msg.setUrl(spiderResult.getParamTwo());
		Map<String, TemplateMessage.KeyWord> map = new HashMap<>();
		map.put("title",new TemplateMessage.KeyWord(spiderResult.getTitle()));
		map.put("price",new TemplateMessage.KeyWord(spiderResult.getParamThree()));
		map.put("order",new TemplateMessage.KeyWord(spiderResult.getOrderNum()+""));
		map.put("time",new TemplateMessage.KeyWord(DateUtil.formatDate(new Date(),DateUtil.YYYY_MM_DD_HH_MM_SS)));
		msg.setData(map);
		BaseResult flag = iWechatService.sendTemplateMsg(msg, appId, appSecret);
		log.info(flag.toString());
		TemplateMessage msg2 = new TemplateMessage();
		msg2.setTemplateId("6QytVD6ZiDD5kFcVBUtQh1WvNJwIVOMREYozcyZST_M");
		msg2.setTopcolor("#000033");
		msg2.setTouser("oa6jXwdlydfk4Kbz2jj4LmLMEWS8");
		msg2.setUrl(spiderResult.getParamTwo());
		Map<String, TemplateMessage.KeyWord> map2 = new HashMap<>();
		map2.put("title",new TemplateMessage.KeyWord(spiderResult.getTitle()));
		map2.put("price",new TemplateMessage.KeyWord(spiderResult.getParamThree()));
		map2.put("order",new TemplateMessage.KeyWord(spiderResult.getOrderNum()+""));
		map2.put("time",new TemplateMessage.KeyWord(DateUtil.formatDate(new Date(),DateUtil.YYYY_MM_DD_HH_MM_SS)));
		msg2.setData(map2);
		BaseResult flag2 = iWechatService.sendTemplateMsg(msg2, appId, appSecret);
		log.info(flag2.toString());
		TemplateMessage msg3 = new TemplateMessage();
		msg3.setTemplateId("6QytVD6ZiDD5kFcVBUtQh1WvNJwIVOMREYozcyZST_M");
		msg3.setTopcolor("#000033");
		msg3.setTouser("oa6jXwXk7bGdHIfeBVzX6rG3V9ns");
		msg3.setUrl(spiderResult.getParamTwo());
		Map<String, TemplateMessage.KeyWord> map3 = new HashMap<>();
		map3.put("title",new TemplateMessage.KeyWord(spiderResult.getTitle()));
		map3.put("price",new TemplateMessage.KeyWord(spiderResult.getParamThree()));
		map3.put("order",new TemplateMessage.KeyWord(spiderResult.getOrderNum()+""));
		map3.put("time",new TemplateMessage.KeyWord(DateUtil.formatDate(new Date(),DateUtil.YYYY_MM_DD_HH_MM_SS)));
		msg3.setData(map3);
		BaseResult flag3 = iWechatService.sendTemplateMsg(msg3, appId, appSecret);
		log.info(flag3.toString());
	}

	public static void main(String[] args) throws IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet get=new HttpGet("/cart.html");
		HttpClientContext context = HttpClientContext.create();
		try {
			CloseableHttpResponse response = httpClient.execute(get, context);
			try{
				System.out.println(">>>>>>headers:");
				Arrays.stream(response.getAllHeaders()).forEach(System.out::println);
				System.out.println(">>>>>>cookies:");
				context.getCookieStore().getCookies().forEach(System.out::println);
			}
			finally {
				response.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}