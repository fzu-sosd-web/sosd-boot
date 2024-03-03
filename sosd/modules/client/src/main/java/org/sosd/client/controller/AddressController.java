package org.sosd.client.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import org.sosd.client.util.GeoUtil;
import org.sosd.core.domain.R;

import org.sosd.core.domain.model.AddressBody;
import org.sosd.core.util.ValidatorUtils;
import org.sosd.redis.util.RedisUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@Validated
@RestController
@SaIgnore
@RequestMapping("/address")
public class AddressController {
    // 签到进行计时，把key和member记到缓存中
    @GetMapping("/signIn")
    public R<String> saveaddress(@Validated @RequestBody AddressBody addressBody){
        ValidatorUtils.validate(addressBody);
        Double eventLon = addressBody.getEventLon();
        Double eventLat = addressBody.getEventLat();
        Double userLat = addressBody.getUserLat();
        Double userLon = addressBody.getUserLon();
        boolean withinRadius = GeoUtil.isWithinRadius(eventLat, eventLon, userLat, userLon, 1000);
        if (withinRadius){
            RedisUtils.recordSignIn((Long) StpUtil.getLoginId(),addressBody.getEventId());
            return R.ok("计时开始");
        }else {
            return R.fail("请到指定位置开始服务");
        }
    }

}
