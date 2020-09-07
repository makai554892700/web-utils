package www.mys.com.oauth2user.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import www.mys.com.oauth2user.pojo.Permission;

@Repository("permissionMapper")
public interface PermissionMapper extends JpaRepository<Permission, Integer> {

    public Permission getPermissionByPermissionName(String permissionName);

}
