package com.ixiangliu.modules.job.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ixiangliu.common.utils.SpringContextUtils;
import com.ixiangliu.modules.spider.entity.SpiderResult;
import com.ixiangliu.modules.spider.service.ISpiderResultService;
import com.ixiangliu.modules.wechat.dto.BaseResult;
import com.ixiangliu.modules.wechat.dto.TemplateMessage;
import com.ixiangliu.modules.wechat.service.IWechatService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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

	@Value("${appId}")
	private String appId;

	@Value("${appSecret}")
	private String appSecret;

	@Override
	public void run(String params){
		Random random = new Random();
		int randNum = random.nextInt(10);
		log.info(randNum + "");
		try {
			Thread.sleep(randNum*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		log.info("执行定时任务时间: " + LocalDateTime.now());
		http();
	}

	public void http () {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		// 请求
		Long longtimeNew=System.currentTimeMillis();
		HttpGet httpGet = new HttpGet("https://gzgsv.xpshop.cn/vshop_List.aspx?type=ajax&Action=GetProductList&BrandID=0&sid=-1&subcat=0&lp=0&hp=0&k=keyword&OrderSort=1&p=&page=1&Size=1000&_="+longtimeNew);
		// 响应
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpGet);
			// 响应体
			HttpEntity responseEntity = response.getEntity();
			log.info("响应状态：" + response.getStatusLine());
			log.info("响应体编码方式：" + responseEntity.getContentEncoding());
			log.info("响应体类型：" + responseEntity.getContentType());
			String resBody = EntityUtils.toString(responseEntity);
			JSONObject body = JSONObject.parseObject(resBody);
			JSONArray list =  JSONObject.parseArray(body.getString("Product"));
			list.forEach(element -> {
				JSONObject product = (JSONObject)element;
				SpiderResult spiderResult = iSpiderResultService.getOne(new QueryWrapper<SpiderResult>().eq("param_id", product.get("productid").toString()).eq("type", "guigaosu_maotai"));
				if (spiderResult == null) {
					spiderResult = new SpiderResult();
				}
				spiderResult.setDetail(product.toString());
				spiderResult.setType("guigaosu_maotai");
				spiderResult.setTitle(product.get("productname").toString());
				spiderResult.setParamOne(product.get("Storage").toString());
				spiderResult.setParamTwo("https://gzgsv.xpshop.cn/products/product-"+product.get("productid").toString()+".html");
				spiderResult.setParamThree(product.get("marketprice").toString());
				spiderResult.setParamId(product.get("productid").toString());
				iSpiderResultService.saveOrUpdate(spiderResult);
				if (spiderResult.getOrderNum() > 0 && spiderResult.getParamOne().equals("1")){
					send(spiderResult.getParamTwo(),spiderResult.getTitle());
				}
			});
			log.info("数据保存成功");
		} catch (Exception e) {
			log.error("数据保存失败");
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

	public void send (String url,String title) {
		IWechatService iWechatService = (IWechatService) SpringContextUtils.getBean("iWechatService");
		TemplateMessage msg = new TemplateMessage();
		msg.setTemplateId("EFVQ26kdPex2Lx8O8azydddSzHtyUG47gUS3YFqsk24");
		msg.setTopcolor("#000033");
		msg.setTouser("oa6jXwXYH6VLR_b2UIxMQK9obCtQ");
		msg.setUrl(url);
		Map<String, TemplateMessage.KeyWord> map = new HashMap<>();
		map.put("title",new TemplateMessage.KeyWord(title));
		map.put("url",new TemplateMessage.KeyWord(url));
		msg.setData(map);
		BaseResult flag = iWechatService.sendTemplateMsg(msg, appId, appSecret);
		System.out.println(flag);
	}
}