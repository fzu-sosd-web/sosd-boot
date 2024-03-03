package org.sosd.client.mapper;

import org.sosd.client.domain.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 86187
* @description 针对表【comment】的数据库操作Mapper
* @createDate 2024-02-19 13:30:25
* @Entity com.example.client.domain.Comment
*/
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}




