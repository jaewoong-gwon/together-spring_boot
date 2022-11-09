package project.together.repository;

import org.apache.ibatis.annotations.Mapper;
import project.together.vo.Organization;

import java.util.List;

@Mapper
public interface OrganizationMapper {
    Organization findOrganizationById(String orgId);

    List<Organization> findAllOrganization();

    int createOrganization(Organization organization);

    int deleteOrganizationById(String ordId);
}

 