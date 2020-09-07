package www.mys.com.oauth2user.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import www.mys.com.oauth2user.pojo.Role;

@Repository("roleMapper")
public interface RoleMapper extends JpaRepository<Role, Integer> {

    public Role getRoleByRoleName(String roleName);

}
