package com.ixiangliu.modules.spider.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ixiangliu.common.utils.PageUtils;
import com.ixiangliu.common.utils.Query;
import com.ixiangliu.modules.spider.dao.SpiderResultDao;
import com.ixiangliu.modules.spider.entity.SpiderResult;
import com.ixiangliu.modules.spider.service.ISpiderResultService;
import com.ixiangliu.modules.sys.entity.Config;
import com.ixiangliu.modules.sys.service.IConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service("spiderResultService")
public class SpiderResultServiceImpl extends ServiceImpl<SpiderResultDao, SpiderResult> implements ISpiderResultService {

    @Autowired
    private IConfigService iConfigService;

    @Value("${appId}")
    private String appId;

    @Value("${appSecret}")
    private String appSecret;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String title = (String)params.get("title");
        String order = (String)params.get("order");
        String sidx = (String)params.get("sidx");

        IPage<SpiderResult> page = this.page(
            new Query<SpiderResult>().getPage(params),
            new QueryWrapper<SpiderResult>().like(StringUtils.isNotBlank(title),"title", title).orderByAsc(StringUtils.isBlank(sidx),"param_one desc,order_num desc,param_three"));

        return new PageUtils(page);
    }

    @Override
    public boolean updateBatchId(List<SpiderResult> list) {
        return baseMapper.updateBatchId(list);
    }

    @Override
    public boolean updateOrder(String keyword) {
        Config config = iConfigService.getOne(new QueryWrapper<Config>().eq("param_key", "GUI_GAO_SU"));
        String guiUrl = config.getParamValue();
        HttpGet httpGet = new HttpGet(guiUrl + "/vshop_MyOrders.aspx?NoCopyRight=1&type=ajax&Action=GetList&PageNo=1&k2=&status=");
        // 响应
        httpGet.setHeader("cookie", keyword);
        httpGet.addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_4) AppleWebKit/605.1.15 (KHTML, like Gecko) MicroMessenger/2.3.31(0x12031f10) MacWechat NetType/WIFI WindowsWechat");
        //设置代理IP，设置连接超时时间 、 设置 请求读取数据的超时时间 、 设置从connect Manager获取Connection超时时间、
//		HttpHost proxy = new HttpHost("123.57.232.137",16818);
//		RequestConfig requestConfig = RequestConfig.custom()
//				.setProxy(proxy)
//				.setConnectTimeout(10000)
//				.setSocketTimeout(10000)
//				.setConnectionRequestTimeout(3000)
//				.build();
//		httpGet.setConfig(requestConfig);
//        CredentialsProvider credsProvider = new BasicCredentialsProvider();
//        Config ipProxy = iConfigService.getOne(new QueryWrapper<Config>().eq("param_key", "ip_proxy"));
//        String ipProxyValue = ipProxy.getParamValue();
//        JSONObject ipProxyJson = JSONObject.parseObject(ipProxyValue);
//        credsProvider.setCredentials(
//                new AuthScope(ipProxyJson.getString("host"), ipProxyJson.getInteger("port")),
//                new UsernamePasswordCredentials(ipProxyJson.getString("username"), ipProxyJson.getString("password")));
//        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            // 响应体
            HttpEntity responseEntity = response.getEntity();
            String resBody = EntityUtils.toString(responseEntity);
            JSONObject body = JSONObject.parseObject(resBody);
            JSONArray list =  JSONObject.parseArray(body.getString("Orders"));
            List<SpiderResult> spiderResults = new ArrayList<>();
            list.forEach(element -> {
                JSONObject order = (JSONObject)element;
                if (!"交易取消".equals(order.get("result").toString())) {
                    SpiderResult spiderResult = getOne(new QueryWrapper<SpiderResult>().eq("param_id", order.get("OrderID").toString()).eq("type", "guigaosu_order"));
                    if (spiderResult == null) {
                        spiderResult = new SpiderResult();
                    }
                    spiderResult.setDetail(order.toString());
                    spiderResult.setType("guigaosu_order");
                    spiderResult.setTitle(order.get("productname").toString());
                    spiderResult.setParamOne("-" + order.get("OrderNo").toString());
                    spiderResult.setParamTwo(order.get("result").toString() + "："+order.getString("OrderTime"));
                    spiderResult.setParamThree(order.get("TotalPrice").toString());
                    spiderResult.setParamId(order.get("OrderID").toString());
                    spiderResults.add(spiderResult);
                }
            });
            if (CollectionUtils.isNotEmpty(spiderResults)) {
                saveOrUpdateBatch(spiderResults);
            }
            return true;
        } catch (Exception e) {
            log.error("订单数据保存失败",e);
            return false;
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

}
