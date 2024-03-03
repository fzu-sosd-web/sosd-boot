package org.sosd.client.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.sosd.client.domain.Event;
import org.sosd.client.domain.Follows;
import org.sosd.client.domain.User;
import org.sosd.client.domain.vo.EventVo;
import org.sosd.client.service.CommentService;
import org.sosd.client.service.EventService;
import org.sosd.client.service.FollowsService;
import org.sosd.client.service.UserService;
import org.sosd.core.domain.R;
import org.sosd.core.domain.model.event.CommentBody;
import org.sosd.core.domain.model.event.EditEventBody;
import org.sosd.core.domain.model.event.PulishEventBody;
import org.sosd.core.util.ValidatorUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/event")
@SaIgnore
public class EventController {

    private final EventService eventService;
    private final UserService userService;
    private final CommentService commentService;
    private final FollowsService followsService;


    /**
     * 返回需求列表，应该是放在主页访问
     * @return 返回所有需求列表
     */
    // TODO 分页查询
    @GetMapping("/index")
    public R<IPage<EventVo>> eventlist(@RequestParam(defaultValue = "1") Long current,
                                       @RequestParam(defaultValue = "8") Long size) {
        Page<Event> page = new Page<>(current, size);
        IPage<Event> eventPage = eventService.page(page, new LambdaQueryWrapper<Event>()
                .eq(Event::getStatus, "3")
                .eq(Event::getDelFlag, "0")
        );
        IPage<EventVo> eventVoPage = eventPage.convert(event -> {
            EventVo eventVo = new EventVo();
            BeanUtil.copyProperties(event, eventVo);
            User user = userService.getById(event.getCreateBy());
            eventVo.setUserName(user.getUserName());
            return eventVo;
        });
        return R.ok(eventVoPage);
    }

    /**
     * 搜索功能：根据描述进行模糊查询
     * @return 查询后的vo列表
     */
    @GetMapping("/search")
    public R<List<EventVo>> searchlist(@RequestParam String keyWord){
        List<Event> events = eventService.list(new LambdaQueryWrapper<Event>()
                .like(Event::getDescription, keyWord)
                .or()
                .like(Event::getCreateBy,keyWord));
        // 进行俩次查询，就不用去动mapper.xml避开重新写方法的麻烦
        List<EventVo> eventVos = events.stream()
                .map(event -> {
                    EventVo eventVo = new EventVo();
                    BeanUtil.copyProperties(event, eventVo);
                    User user = userService.getById(event.getCreateBy());
                    eventVo.setUserName(user.getUserName());
                    return eventVo;
                })
                .toList();
        return R.ok(eventVos);
    }

    /**
     * 根据eventId查看需求的详细信息
     * @return 需求的具体信息
     */
    @GetMapping("/detail")
    public R<EventVo> getEventDetail(@RequestParam Long eventId){
        Event one = eventService.getOne(new LambdaQueryWrapper<Event>()
                .eq(Event::getId, eventId));
        EventVo eventVo = BeanUtil.copyProperties(one, EventVo.class);
        return R.ok(eventVo);
    }

    /**
     * 发布留言
     * @return 提示信息
     */
    /*@PostMapping("/detail/addcomment")
    public R<String> comment(@Validated @RequestBody CommentBody commentBody){
        ValidatorUtils.validate(commentBody);
        Comment comment = BeanUtil.copyProperties(commentBody, Comment.class);
        comment.setCreateBy((Integer) StpUtil.getLoginId());
        boolean save = commentService.save(comment);
        if (save){
            return R.ok("评论成功");
        }
        return R.fail("评论失败");
    }*/

    /**
     * 查看留言留言
     * @return 返回留言列表
     * 暂定要不要额外界面，还是返回在EventVo里
     */
    /*@PostMapping("/commentlist")
    public R<List<Comment>> getcommentlist(Long eventId){

    }*/

    /*@PostMapping("/replycomment")
    public R<String> reply(@RequestParam Long commentId,
                           @RequestParam Long eventId,
                           @RequestParam String reply){
        boolean update = commentService.update(new LambdaUpdateWrapper<Comment>()
                .eq(Comment::getEventId, commentId)
                .eq(Comment::getEventId, eventId)
                .set(Comment::getReply, reply));
        if (update){
            return R.ok("回复成功");
        }
        return R.fail("回复失败");
    }*/

    /**
     * 上传需求（应该要看是什么角色然后才能上传）
     * @return String提示信息
     */
    @PostMapping("/publish")
    public R<String> publish(@Validated @RequestBody PulishEventBody pulishEventBody) {
            ValidatorUtils.validate(pulishEventBody);
            Event event = BeanUtil.copyProperties(pulishEventBody, Event.class);
        boolean save = eventService.save(event);
        if (save)
            return R.ok("上传成功");
        return R.fail("上传失败: 系统异常");
    }

    /**
     * 编辑需求（应该要看是什么角色然后才能上传）
     * @return String提示信息
     */
    @PostMapping("/editevent")
    public R<String> edit(@Validated @RequestBody EditEventBody editEventBody) {
        /*try {
            ValidatorUtils.validate(editEventBody);
            Long eventId = editEventBody.getEventId();
            eventService.update(new LambdaUpdateWrapper<Event>()
                    .eq(Event::getId,eventId)
                    .set(Event::getName,editEventBody.getName())
                    .set(Event::getDescription,editEventBody.getDescription())
                    .set(Event::getNeedNum,editEventBody.getNeedNum())
                    .set(Event::getReward,editEventBody.getReward())
                    .set(Event::getExecutionTime,editEventBody.getExecutionTime()));
            return R.ok("上传成功");
        }catch (Exception e) {
            log.info(e.getMessage());
            return R.fail("上传失败: 系统异常");
        }*/
            ValidatorUtils.validate(editEventBody);
            Long eventId = editEventBody.getEventId();

            Event eventToUpdate = eventService.getById(eventId);
            if (eventToUpdate == null) {
                return R.fail("上传失败: 需求不存在");
            }

            // 使用BeanUtils复制属性，排除不需要更新的字段
            String[] ignoreProperties = {"eventId", "createTime", "status", "delFlag", "createBy", "curentNum", "defaultReason"};
            BeanUtils.copyProperties(editEventBody, eventToUpdate, ignoreProperties);

            eventService.updateById(eventToUpdate);
            return R.ok("上传成功");
    }

    /**
     * 删除需求
     * @return 提示信息
     */
    @PostMapping("/deletevent")
    public R<String> delete(@RequestParam Long eventId){
            boolean update = eventService.update(new LambdaUpdateWrapper<Event>()
                    .eq(Event::getId, eventId)
                    .set(Event::getDelFlag, 1));
            if (update) {
                return R.ok("删除成功");
            }else {
                return R.fail("删除失败");
            }
    }

    /**
     * 收藏功能
     * @return 提示信息
     */
    @PostMapping("/detail/follow")
    @Transactional
    @SaCheckLogin
    public R<String> followevent(@RequestParam Long eventId){
        LambdaQueryWrapper<Follows> eq = new LambdaQueryWrapper<Follows>()
                .eq(Follows::getEventId, eventId)
                .eq(Follows::getUserId, StpUtil.getLoginIdAsLong());
        boolean exists = followsService.exists(eq);
        if (exists){
            followsService.remove(eq);
            return R.ok("取消收藏");
        }else {
            Follows follows = new Follows();
            follows.setEventId(eventId);
            follows.setUserId(StpUtil.getLoginIdAsLong());
            boolean save = followsService.save(follows);
            if (save) {
                return R.ok("收藏成功");
            } else return R.fail("收藏失败");
        }
    }
}
