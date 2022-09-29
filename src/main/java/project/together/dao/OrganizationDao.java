package project.together.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import project.together.dto.Organization;

import java.util.List;

@Mapper
public interface OrganizationDao {

    @Select("SELECT * FROM Organization")
    List<Organization> findAll();

    @Select("SELECT org_id FROM Organization WHERE org_id = #{orgId}")
    void findById(String orgId);

    @Insert("INSERT INTO Organization org_id,org_man_name,org_man_birth,org_man_gender,org_man_mobile,org_man_email " +
            "VALUES #{orgId},#{orgManName},#{orgManBirth},#{orgManGender},#{orgManMobile},#{orgManEmail}")
    boolean createOrganization(Organization organization);
}

