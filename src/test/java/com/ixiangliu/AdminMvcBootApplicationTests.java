package com.ixiangliu;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ixiangliu.modules.spider.service.ISpiderResultService;
import com.ixiangliu.modules.sys.entity.Config;
import com.ixiangliu.modules.sys.service.IConfigService;
import com.ixiangliu.modules.wechat.dto.BaseResult;
import com.ixiangliu.modules.wechat.dto.TemplateMessage;
import com.ixiangliu.modules.wechat.service.IWechatService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminMvcBootApplicationTests {

    @Autowired
    IWechatService iWechatService;

    @Autowired
    private ISpiderResultService iSpiderResultService;

    @Value("${appId}")
    private String appId;

    @Value("${appSecret}")
    private String appSecret;

    @Autowired
    private IConfigService iConfigService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void test2() throws Exception {
        TemplateMessage msg = new TemplateMessage();
        msg.setTemplateId("EFVQ26kdPex2Lx8O8azydddSzHtyUG47gUS3YFqsk24");
        msg.setTopcolor("#000033");
        msg.setTouser("oa6jXwXYH6VLR_b2UIxMQK9obCtQ");
        msg.setUrl("www.baidu.com");
        Map<String, TemplateMessage.KeyWord> map = new HashMap<>();
        map.put("title", new TemplateMessage.KeyWord("茅台"));
        map.put("url", new TemplateMessage.KeyWord("www.21jar.com"));
        msg.setData(map);
//        String msg = "{\"touser\": \"oyzG31DoJG4h2l8GXGf_NjJz_IZI\",\"url\": \"http://rxwnuu.natappfree.cc/wechat/wxReceive\",\"topcolor\": \"#000033\",\"data\": {\"first\": {\"value\": \"这里是标题\"},\"delivername\": {\"value\": \"顺风\"},\"ordername\": {\"value\": \"3432432\"},\"productName\": {\"value\": \"小白兔\"},\"productCount\": {\"value\": \"100件\"},\"remark\": {\"value\": \"这里是备注\"}},\"template_id\": \"jghn8MLQ59QfDyatxJTW_fYyZZrM6qrkVbzXf74HJZ8\"}";
        BaseResult flag = iWechatService.sendTemplateMsg(msg, appId, appSecret);
        System.out.println(flag);
    }

    @Test
    public void test3() {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 请求
        Config config = iConfigService.getOne(new QueryWrapper<Config>().eq("param_key", "GUIGAOSU"));
        String guiUrl = config.getParamValue();
        HttpGet httpGet = new HttpGet(guiUrl + "/products/product-1002563.html");
        // 响应
        CloseableHttpResponse response = null;
        try {
            // execute()执行成功会返回HttpResponse响应
            response = httpClient.execute(httpGet);
            // 响应体
            HttpEntity responseEntity = response.getEntity();
            System.out.println("响应状态：" + response.getStatusLine());
            // gzip,deflate,compress
            System.out.println("响应体编码方式：" + responseEntity.getContentEncoding());
            // 响应类型如text/html charset也有可能在ContentType中
            System.out.println("响应体类型：" + responseEntity.getContentType());
            System.out.println("响应体内容：" + EntityUtils.toString(responseEntity));
            // 如果关闭了httpEntity的inputStream，httpEntity长度应该为0，而且再次请求相同路由的连接可以共用一个连接。
            // 可以通过设置连接管理器最大连接为1来验证。
            response = httpClient.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
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

    @Test
    public void test4() {
        String keyword = "XpShopMemberAuth=" + "11144DCB971CBB1F9DC796978390AAE1857C2593F0150071298D654A8DA6267B620575DF6D5E429D5A35D973D035F3CCC2A2C0E10E2273C5FC2D92D820CAFE043CC3361D53BF7BA4EB5E3CE011B22A34B62CF54F30F41F39006A8E916B387E2634C5103F270432125BB49A4C7CCE3077F0D694EE4D7D5C326FF747EF5D52C85AF5098F469446E98A64C573D00B76AB82";
        boolean flag = iSpiderResultService.updateOrder(keyword);
        System.out.println(flag);
    }

    @Test
    public void test5() throws Exception {
        String pageUrl = "https://www.baidu.com"; //要访问的目标网页
        String proxyIp = "123.57.232.137"; //代理服务器IP
        int proxyPort = 16818; //代理服务器IP
        String username = "slt680"; //用户名
        String password = "xivufs33"; //密码
        //确保使用用户名密码鉴权正常运行
        System.setProperty("jdk.http.auth.tunneling.disabledSchemes", "");
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope(proxyIp, proxyPort),
                new UsernamePasswordCredentials(username, password));
        CloseableHttpClient httpclient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();
        try {
            URL url = new URL(pageUrl);
            HttpHost target = new HttpHost(url.getHost(), url.getDefaultPort(), url.getProtocol());
            HttpHost proxy = new HttpHost(proxyIp, proxyPort);
            RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
            HttpGet httpget = new HttpGet(url.getPath());
            httpget.setConfig(config);
            httpget.addHeader("Accept-Encoding", "gzip"); //使用gzip压缩传输数据让访问更快
            CloseableHttpResponse response = httpclient.execute(target, httpget);
            try {
                HttpEntity responseEntity = response.getEntity();
                String resBody = EntityUtils.toString(responseEntity);
                JSONObject body = JSONObject.parseObject(resBody);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }

}
