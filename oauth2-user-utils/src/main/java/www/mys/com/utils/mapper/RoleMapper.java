package www.mys.com.utils.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import www.mys.com.utils.pojo.Role;

@Repository("roleMapper")
public interface RoleMapper extends JpaRepository<Role, Integer> {

    public Role getRoleByRoleName(String roleName);

}
