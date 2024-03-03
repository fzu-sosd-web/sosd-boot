package org.sosd.client.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.sosd.client.domain.Comment;
import org.sosd.client.service.CommentService;
import org.sosd.client.mapper.CommentMapper;
import org.springframework.stereotype.Service;

/**
* @author 86187
* @description 针对表【comment】的数据库操作Service实现
* @createDate 2024-02-19 13:30:25
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService {

}




