package project.together.repository;

import org.apache.ibatis.annotations.Mapper;
import project.together.vo.Notice;

import java.util.List;

@Mapper
public interface NoticeMapper {

    List<Notice> findAllNotice();

    Notice findNoticeById(Integer notId);

    Integer createNotice(Notice notice);

    Integer updateNotice(Notice notice);

    Integer deleteNoticeById(Integer notId);
}
