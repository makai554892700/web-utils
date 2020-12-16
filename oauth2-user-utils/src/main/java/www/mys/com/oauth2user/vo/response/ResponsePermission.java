package www.mys.com.oauth2user.vo.response;

import java.io.Serializable;

public class ResponsePermission implements Serializable {

    private Integer id;
    private String permissionMark;//权限标识符	String(unique)
    private String permissionName;//权限名称

    public ResponsePermission() {
    }

    public ResponsePermission(Integer id, String permissionMark, String permissionName) {
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
        return "ResponsePermission{" +
                "id=" + id +
                ", permissionMark='" + permissionMark + '\'' +
                ", permissionName='" + permissionName + '\'' +
                '}';
    }
}
