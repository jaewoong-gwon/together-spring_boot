package project.together.repository;

import org.apache.ibatis.annotations.Mapper;
import project.together.vo.Notice;

@Mapper
public interface NoticeMapper {

    Notice findAllNotice();
}
