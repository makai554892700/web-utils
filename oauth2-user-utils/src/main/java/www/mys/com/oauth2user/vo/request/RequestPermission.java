package www.mys.com.oauth2user.vo.request;

import java.io.Serializable;

public class RequestPermission implements Serializable {

    private Integer id;
    private String permissionMark;//权限标识符	String(unique)
    private String permissionName;//权限名称

    public RequestPermission() {
    }

    public RequestPermission(Integer id, String permissionMark, String permissionName) {
        this.id = id;
        this.permissionMark = permissionMark;
        this.permissionName = permissionName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPermissionMark() {
        return permissionMark;
    }

    public void setPermissionMark(String permissionMark) {
        this.permissionMark = permissionMark;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    @Override
    public String toString() {
        return "RequestPermission{" +
                "id=" + id +
                ", permissionMark='" + permissionMark + '\'' +
                ", permissionName='" + permissionName + '\'' +
                '}';
    }
}
