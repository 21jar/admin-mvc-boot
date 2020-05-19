package com.ixiangliu.modules.spider.controller;

import com.ixiangliu.common.utils.PageUtils;
import com.ixiangliu.common.utils.Result;
import com.ixiangliu.common.validator.ValidatorUtils;
import com.ixiangliu.modules.spider.entity.SpiderResult;
import com.ixiangliu.modules.spider.service.ISpiderResultService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@Slf4j
@RestController
@RequestMapping("spider/spiderResult")
public class SpiderResultController {

    @Autowired
    private ISpiderResultService iSpiderResultService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("spider:spiderResult:list")
    public Result list(@RequestParam Map<String, Object> params){
        PageUtils page = iSpiderResultService.queryPage(params);
        return Result.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("spider:spiderResult:info")
    public Result info(@PathVariable("id") Long id){
        SpiderResult spiderResult = iSpiderResultService.getById(id);
        return Result.ok().put("spiderResult", spiderResult);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("spider:spiderResult:update")
    public Result update(@RequestBody SpiderResult spiderResult){
        //校验类型
        ValidatorUtils.validateEntity(spiderResult);
        iSpiderResultService.updateById(spiderResult);
        return Result.ok();
    }
    @RequestMapping("/updateOrder")
    public Result updateOrder(@RequestBody Map<String, Object> params){
        String keyword = (String) params.get("keyword");
        if (StringUtils.isBlank(keyword)){
            keyword="XpShopMemberAuth=A0E69E67F2FEB64C7225AE65B62C67836EF7E053D5351D0AF9026360BC90491A70DBCF9DA23BBCAA88194826623555062336E1C053F70BA1CF93B7550438AE532D9454952B29EF046FA5DA0E089525FB7E2A8009D93CBFD04B2B2232293CC676AE7EA307BEDF4461D7B47342658AA94E176651969506CC6AFDB23B6997C2E7735F57A6B62E3AB6C19CAB4DCC756195CA; userMemberID=1351982; SERVERID=83b2529bbd206d231645c1629db5f12f|1589778474|1589778429; ssxmod_itna=QqfxyD2DgDRWG=YGHD8IY4D5p2m5KND0l0Y=ACOD0vweGzDAxn407DUo=Tpoz/n0Dhaee=i443We6bhYIrKjS2qEtOSueGIDeF=Do1qD1D3qDkblPUjODADi3DEDDmXDmqi8DIL=Dfnb5DiOKDcEyCDCm1CxDC2GH=5/0bLj9Oc1T=DB+eQirdWmFvY64N5i2DESTb4o4qT/GxYcDx8m25L3fN+eY4+C2eQ0GDD=; ssxmod_itna2=QqfxyD2DgDRWG=YGHD8IY4D5p2m5KND0l0Y=AGDnIfqqxDseNNDLG7YzTuGDLqAPecRDT76wPqtWO=1PNIl5bITs8CAW1SW769qX1yzTePX7ItevH42Kk2LqwWAmh3wXNKDzlq4QQY68euEkK0LD5k=1K8DKGOY18MqYdjdcfo24xquIK3ilKSRkYfRNPjAh0dabO1c3s6u35AWl+6K8MR9ba=T75eK8E=0zd3n8LoWs0enmVRWzdmcT5kWrLW5=uFi4X3Rb5w7b5pdxgnbW2KCEg9dFchXFqMjBchxu4yLxtCQKY2QGiLwGDsDe3ipvxekCe0DeqGiA7vLBiY8eP0tGC2o+w34eKG5NF3NbLoRDkjr4Wq3QqaI40lrvPAwAwxY+5PbKailj6itm77igxnCOmW+5qA4DQ9DYBhYB=l5rboKbPljq4nu=iGq8Siz0PX360GYGmm0Sap3xGwTvr7Zu=Y0pRo8uaK4xmWqKzk7o07rGCskrTrf0d8pf4+Mk6wVt7oGM1TCR3u=4xDFqD+=9RAG9CHP053Q4YggsixrjQV+XSCE5AeWlYQlYTLaYgvsG+I8G7TxtCy8/qeivMK8T58IfeemxrYD=; u_asec=099%23KAFEtYEKExYEh7TLEEEEEpEQz0yFD603DrJYa6f3DuioW6DhSXBqV6AXE7EFD67EEFMTEEyP%2F9iSlllUE7Tx7h8VD5AoiDvkLmyV0He6mSuIp9N4k4VBiGGvzaSSLFSc1nZW2h5IwqsM2e04IEWcvHNlUJ6%2F1HvkamyUINj7DQYRyUQnBFt0DbDkl%2BKI8AAfJXQTETyP%2FcyNYyB6CM3B8%2FB0Ponp%2BqWJsO8DBEFE1cZdtMX5bpGNsEFEpcZdtn3lluZdsyaZ4lllsC%2FP%2F3HjlllrccZddmJllusVsyaSt3llsyahE7TuDpQEEqa3U61vv0XB%2BD1aGppq3w%2BcaR%2FRuz4nNnn6rtCtSAoSaGZQgsBEcO96Ly32rUXMFSMRU6k8CpUVa7JAGuJiqsFRi2wHzUYScYn6dzhsnspX%2FOsNnXXmKR%2Fu1s%2FrUb7MKdsH4rBNPL4%2BAIlNnXXmKposLewTUqkX8wVulYFETIZ4gb35E7EFsyaDdE%3D%3D; CNZZDATA1278193973=1331833566-1587718480-%7C1589774993; ASP.NET_SessionId=1hd5vbqkqovgt3ak2yajtx4q; aliyungf_tc=AQAAALRb/VF4lwQA9mCjPbh9/fbJX0kj; MEIQIA_TRACK_ID=1aymcMhHCEUh2iINFQimR6UG5c6; MEIQIA_VISIT_ID=1c3wEojmzc3LK8YXWsQbWeEEMFf; cn=6; XpShop_CartID=f6cae2cf-f728-49b4-94ee-427607df8010; UM_distinctid=171ab7ff92975e-0f8be0ac73a815-36593843-13c680-171ab7ff92a6d8; _uab_collina=158771626648024991073878; acw_tc=2f6a1faa15877162758653889e35b4d5adba972b2c207284ac89d65d791cb0";
        }
        boolean flag = iSpiderResultService.updateOrder(keyword);
        if (flag) {
            return Result.ok("订单数据保存成功");
        }else {
            return Result.error("订单数据保存失败");
        }
    }

}
