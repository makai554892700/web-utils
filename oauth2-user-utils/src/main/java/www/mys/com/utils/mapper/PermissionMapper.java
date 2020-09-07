package www.mys.com.utils.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import www.mys.com.utils.pojo.Permission;

@Repository("permissionMapper")
public interface PermissionMapper extends JpaRepository<Permission, Integer> {

    public Permission getPermissionByPermissionName(String permissionName);

}
