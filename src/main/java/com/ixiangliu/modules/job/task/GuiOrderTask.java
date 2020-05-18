package com.ixiangliu.modules.job.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ixiangliu.modules.spider.entity.SpiderResult;
import com.ixiangliu.modules.spider.service.ISpiderResultService;
import com.ixiangliu.modules.sys.entity.Config;
import com.ixiangliu.modules.sys.service.IConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 测试定时任务(演示Demo，可删除)
 *
 * testTask为spring bean的名称
 */
@Slf4j
@Component("guiOrder")
public class GuiOrderTask implements ITask {

	@Autowired
	ISpiderResultService iSpiderResultService;

	@Autowired
	private IConfigService iConfigService;

//	@Override
	public void run(String params){
		Random random = new Random();
		int randNum = random.nextInt(10);
		log.info(randNum + "");
		try {
			Thread.sleep(randNum*100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		log.info("执行定时任务时间: " + LocalDateTime.now());
		http();
	}

	public void http () {
		String cookieStr="XpShopMemberAuth=A0E69E67F2FEB64C7225AE65B62C67836EF7E053D5351D0AF9026360BC90491A70DBCF9DA23BBCAA88194826623555062336E1C053F70BA1CF93B7550438AE532D9454952B29EF046FA5DA0E089525FB7E2A8009D93CBFD04B2B2232293CC676AE7EA307BEDF4461D7B47342658AA94E176651969506CC6AFDB23B6997C2E7735F57A6B62E3AB6C19CAB4DCC756195CA; userMemberID=1351982; SERVERID=83b2529bbd206d231645c1629db5f12f|1589778474|1589778429; ssxmod_itna=QqfxyD2DgDRWG=YGHD8IY4D5p2m5KND0l0Y=ACOD0vweGzDAxn407DUo=Tpoz/n0Dhaee=i443We6bhYIrKjS2qEtOSueGIDeF=Do1qD1D3qDkblPUjODADi3DEDDmXDmqi8DIL=Dfnb5DiOKDcEyCDCm1CxDC2GH=5/0bLj9Oc1T=DB+eQirdWmFvY64N5i2DESTb4o4qT/GxYcDx8m25L3fN+eY4+C2eQ0GDD=; ssxmod_itna2=QqfxyD2DgDRWG=YGHD8IY4D5p2m5KND0l0Y=AGDnIfqqxDseNNDLG7YzTuGDLqAPecRDT76wPqtWO=1PNIl5bITs8CAW1SW769qX1yzTePX7ItevH42Kk2LqwWAmh3wXNKDzlq4QQY68euEkK0LD5k=1K8DKGOY18MqYdjdcfo24xquIK3ilKSRkYfRNPjAh0dabO1c3s6u35AWl+6K8MR9ba=T75eK8E=0zd3n8LoWs0enmVRWzdmcT5kWrLW5=uFi4X3Rb5w7b5pdxgnbW2KCEg9dFchXFqMjBchxu4yLxtCQKY2QGiLwGDsDe3ipvxekCe0DeqGiA7vLBiY8eP0tGC2o+w34eKG5NF3NbLoRDkjr4Wq3QqaI40lrvPAwAwxY+5PbKailj6itm77igxnCOmW+5qA4DQ9DYBhYB=l5rboKbPljq4nu=iGq8Siz0PX360GYGmm0Sap3xGwTvr7Zu=Y0pRo8uaK4xmWqKzk7o07rGCskrTrf0d8pf4+Mk6wVt7oGM1TCR3u=4xDFqD+=9RAG9CHP053Q4YggsixrjQV+XSCE5AeWlYQlYTLaYgvsG+I8G7TxtCy8/qeivMK8T58IfeemxrYD=; u_asec=099%23KAFEtYEKExYEh7TLEEEEEpEQz0yFD603DrJYa6f3DuioW6DhSXBqV6AXE7EFD67EEFMTEEyP%2F9iSlllUE7Tx7h8VD5AoiDvkLmyV0He6mSuIp9N4k4VBiGGvzaSSLFSc1nZW2h5IwqsM2e04IEWcvHNlUJ6%2F1HvkamyUINj7DQYRyUQnBFt0DbDkl%2BKI8AAfJXQTETyP%2FcyNYyB6CM3B8%2FB0Ponp%2BqWJsO8DBEFE1cZdtMX5bpGNsEFEpcZdtn3lluZdsyaZ4lllsC%2FP%2F3HjlllrccZddmJllusVsyaSt3llsyahE7TuDpQEEqa3U61vv0XB%2BD1aGppq3w%2BcaR%2FRuz4nNnn6rtCtSAoSaGZQgsBEcO96Ly32rUXMFSMRU6k8CpUVa7JAGuJiqsFRi2wHzUYScYn6dzhsnspX%2FOsNnXXmKR%2Fu1s%2FrUb7MKdsH4rBNPL4%2BAIlNnXXmKposLewTUqkX8wVulYFETIZ4gb35E7EFsyaDdE%3D%3D; CNZZDATA1278193973=1331833566-1587718480-%7C1589774993; ASP.NET_SessionId=1hd5vbqkqovgt3ak2yajtx4q; aliyungf_tc=AQAAALRb/VF4lwQA9mCjPbh9/fbJX0kj; MEIQIA_TRACK_ID=1aymcMhHCEUh2iINFQimR6UG5c6; MEIQIA_VISIT_ID=1c3wEojmzc3LK8YXWsQbWeEEMFf; cn=6; XpShop_CartID=f6cae2cf-f728-49b4-94ee-427607df8010; UM_distinctid=171ab7ff92975e-0f8be0ac73a815-36593843-13c680-171ab7ff92a6d8; _uab_collina=158771626648024991073878; acw_tc=2f6a1faa15877162758653889e35b4d5adba972b2c207284ac89d65d791cb0";
		CloseableHttpClient httpClient = HttpClients.createDefault();
		Config config = iConfigService.getOne(new QueryWrapper<Config>().eq("param_key", "GUI_GAO_SU"));
		String guiUrl = config.getParamValue();
		String url = guiUrl + "/vshop_MyOrders.aspx?NoCopyRight=1&type=ajax&Action=GetList&PageNo=1&k2=&status=";
		HttpGet httpGet = new HttpGet(guiUrl + "/vshop_MyOrders.aspx?NoCopyRight=1&type=ajax&Action=GetList&PageNo=1&k2=&status=");
		// 响应
		Header[] headers = httpGet.getAllHeaders();
		httpGet.setHeader("cookie", cookieStr);
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
				SpiderResult spiderResult = iSpiderResultService.getOne(new QueryWrapper<SpiderResult>().eq("param_id", order.get("OrderID").toString()).eq("type", "guigaosu_order"));
				if (spiderResult == null) {
					spiderResult = new SpiderResult();
				}
				spiderResult.setDetail(order.toString());
				spiderResult.setType("guigaosu_order");
				spiderResult.setTitle(order.get("productname").toString());
				spiderResult.setRemark(order.get("result").toString() + "："+order.getString("OrderTime"));
				spiderResult.setParamThree(order.get("TotalPrice").toString());
				spiderResult.setParamId(order.get("OrderID").toString());
				spiderResults.add(spiderResult);
			});
			iSpiderResultService.saveOrUpdateBatch(spiderResults);
			log.info("订单数据保存成功");
		} catch (Exception e) {
			log.error("订单数据保存失败",e);
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