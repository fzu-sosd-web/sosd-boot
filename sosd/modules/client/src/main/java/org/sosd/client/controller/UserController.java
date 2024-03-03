package org.sosd.client.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.sosd.api.IdCardAuth;
import org.sosd.client.domain.AuthIdCard;
import org.sosd.client.domain.Event;
import org.sosd.client.domain.User;
import org.sosd.client.domain.vo.EventVo;
import org.sosd.client.domain.vo.UserVo;
import org.sosd.client.service.AuthIdCardService;
import org.sosd.client.service.EventService;
import org.sosd.client.service.UserService;
import org.sosd.core.domain.R;
import org.sosd.core.domain.model.user.AuthIdCardBody;
import org.sosd.core.util.ValidatorUtils;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@SaIgnore
@RequestMapping("/user")
// TODO @SaIgnore要删去
public class UserController {

    private final AuthIdCardService authIdCardService;
    private final UserService userService;
    private final EventService eventService;

    /**
     * 用户进行实名认证
     * @return String 提示信息
     */

    @PostMapping("/authIdCard")
    public R<String> authIdCard(@Validated @RequestBody AuthIdCardBody authBody){
        ValidatorUtils.validate(authBody);
        String realName = authBody.getRealName();
        String idNumber = authBody.getIdNumber();
        String responseString = IdCardAuth.AuthIdCard(idNumber, realName);
        // 使用Fastjson解析JSON响应
        JSONObject jsonObj = JSON.parseObject(responseString);

        // 提取 'isok' 和 'birthday' 字段
        boolean isOk = jsonObj.getJSONObject("result").getBoolean("isok");
        AuthIdCard authIdCard = BeanUtil.copyProperties(authBody, AuthIdCard.class);
        if (isOk){
            // TODO 给用户的信息加个出生日期字段
            String birthDateStr = idNumber.substring(6, 14); // 提取出生年月日部分
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            try {
                Date birthDate = dateFormat.parse(birthDateStr);
                System.out.println(birthDate);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            authIdCardService.save(authIdCard);
            userService.update(new LambdaUpdateWrapper<User>()
                    .eq(User::getId, StpUtil.getLoginIdAsLong())
                    .set(User::getIsRealName,isOk));
        }
        return isOk ? R.ok("实名认证成功") : R.fail("实名认证失败");
    }


    /**
     * 主页获取用户信息
     * @return UserVo 用户可显信息
     */
    @GetMapping("/index/{userId}")
    public R<UserVo> getUserMsg(@NotNull(message = "主键不能为空") @PathVariable Long userId){
        User one = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getId, userId));
        UserVo userVo = BeanUtil.copyProperties(one, UserVo.class);
        log.info("user:{}",one);
        return R.ok(userVo);
    }

    /**
     * 用户查看自己发布的需求
     * @return eventvos 发布的需求
     */
    @GetMapping("/publishevents")
    public R<List<EventVo>> getpublisheventsByuser(@RequestParam Long userId){
        List<Event> events = eventService.list(new LambdaQueryWrapper<Event>()
                .eq(Event::getCreateBy, userId));
        List<EventVo> eventVos = events.stream()
                .map(event -> {
                    EventVo eventVo = new EventVo();
                    BeanUtil.copyProperties(event, eventVo);
                    return eventVo;
                })
                .toList();
        return R.ok(eventVos);
    }

}
