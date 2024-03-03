package org.sosd.client.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.sosd.client.domain.Event;
import org.sosd.client.domain.Participations;
import org.sosd.client.domain.vo.UserVo;
import org.sosd.client.service.EventService;
import org.sosd.client.service.ParticipationsService;
import org.sosd.core.domain.R;
import org.sosd.core.domain.model.event.AuditBody;
import org.sosd.core.enums.EventStatus;
import org.sosd.core.exception.EventException;
import org.sosd.core.util.ValidatorUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@SaIgnore
@RequestMapping("/eventmanage")
public class EventManageController {

    private final EventService eventService;
    private final ParticipationsService participationsService;

    /**
     * 返回所有需求列表 审核和未审核的一并交给前端分开展示
     * @return 审核的列表
     */
    // TODO 分页查询
    @GetMapping("/eventlist")
    public R<List<Event>> getEventList(){
        List<Event> list = eventService.list();
        return R.ok(list);
    }


    /**
     *  根据需求id对需求进行审核
     * @return 提示信息就好了
     */
    @PostMapping("/auditevent")
    public R<String> auditEvent(@Validated @RequestBody AuditBody auditBody) {
        ValidatorUtils.validate(auditBody);
        String auditManager = auditBody.getAuditManager();
        Long eventId = auditBody.getId();
        Boolean ifPass = auditBody.getIfPass();
        // 通过则直接改变状态
        if (ifPass) {
            try {
                eventService.update(new LambdaUpdateWrapper<Event>()
                        .eq(Event::getId, eventId)
                        .set(Event::getStatus, EventStatus.PASS.getCode()));
                return R.ok("操作成功");
            } catch (Exception e) {
                throw new EventException("更新错误");
            }
        }
        // 未通过则放入错误信息
        try {
            String failReason = auditBody.getFailReason();
            eventService.update(new LambdaUpdateWrapper<Event>()
                    .eq(Event::getId, eventId)
                    .set(Event::getStatus, EventStatus.UNPASS.getCode())
                    .set(Event::getDefaultReason,failReason));
            return R.ok("操作成功");
        }catch (Exception e) {
            throw new EventException("更新错误");
        }
    }

    /**
     * 查看参加的人
     * @return 返回申请者的列表
     */
    // TODO 查看参与者的信息需要哪些不确定
    @GetMapping("/participationbyevent")
    public R<List<Participations>> getparticipationByeventId(@RequestParam Long eventid){
        participationsService.list(new LambdaQueryWrapper<Participations>()
                .eq(Participations::getEventId,eventid));
        return null;
    }


    /**
     * 对参与者进行审核
     * @return 提示信息
     */
    @PostMapping("/auditparticipation")
    public R<String> auditparticipation(@RequestBody List<UserVo> user){
        return R.ok();
    }
}
