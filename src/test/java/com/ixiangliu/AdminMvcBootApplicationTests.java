package com.ixiangliu;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ixiangliu.modules.job.task.GuiOrderTask;
import com.ixiangliu.modules.sys.entity.Config;
import com.ixiangliu.modules.sys.service.IConfigService;
import com.ixiangliu.modules.wechat.dto.BaseResult;
import com.ixiangliu.modules.wechat.dto.TemplateMessage;
import com.ixiangliu.modules.wechat.service.IWechatService;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
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
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminMvcBootApplicationTests {

    @Autowired
    IWechatService iWechatService;

    @Autowired
    GuiOrderTask guiOrderTask;

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
        map.put("title",new TemplateMessage.KeyWord("茅台"));
        map.put("url",new TemplateMessage.KeyWord("www.21jar.com"));
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
            /**
             *  EntityUtils.toString()方法会将响应体的输入流关闭，相当于消耗了响应体，
             *  此时连接会回到httpclient中的连接管理器的连接池中，如果下次访问的路由
             *  是一样的（如第一次访问https://www.jianshu.com/,第二次访问
             *  https://www.jianshu.com/search?q=java&page=1&type=note)，
             *  则此连接可以被复用。
             */

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
        guiOrderTask.http();
    }

}
