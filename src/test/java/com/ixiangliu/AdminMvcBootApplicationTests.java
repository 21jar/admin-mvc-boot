package com.ixiangliu;

import com.ixiangliu.modules.wechat.dto.BaseResult;
import com.ixiangliu.modules.wechat.dto.TemplateMessage;
import com.ixiangliu.modules.wechat.service.IWechatService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminMvcBootApplicationTests {

    @Autowired
    IWechatService iWechatService;

    @Value("${appId}")
    private String appId;

    @Value("${appSecret}")
    private String appSecret;

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

}
