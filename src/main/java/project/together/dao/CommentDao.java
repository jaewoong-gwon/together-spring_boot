package project.together.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import project.together.dto.Comment;

import java.util.List;

@Mapper
public interface CommentDao {
    @Select("SELECT * FROM Comment")
    List<Comment> findAll();

}
