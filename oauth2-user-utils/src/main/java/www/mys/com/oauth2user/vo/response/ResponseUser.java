package www.mys.com.oauth2user.vo.response;

import java.io.Serializable;
import java.util.Date;

public class ResponseUser implements Serializable {

    private Integer id;
    private String userName;            //账户	String(unique)
    private String realName;            //真实姓名	String
    private Date lastLoginTime;         //最后登录时间 date
    private boolean accountNonExpired;                              //是否没过期
    private boolean accountNonLocked;                               //是否没被锁定
    private boolean credentialsNonExpired;                          //是否没过期
    private boolean enabled;                                        //账号是否可用

    public ResponseUser() {
    }

    public ResponseUser(Integer id, String userName, String realName, Date lastLoginTime
            , boolean accountNonExpired, boolean accountNonLocked
            , boolean credentialsNonExpired, boolean enabled) {
        this.id = id;
        this.userName = userName;
        this.realName = realName;
        this.lastLoginTime = lastLoginTime;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", realName='" + realName + '\'' +
                ", lastLoginTime=" + lastLoginTime +
                ", accountNonExpired=" + accountNonExpired +
                ", accountNonLocked=" + accountNonLocked +
                ", credentialsNonExpired=" + credentialsNonExpired +
                ", enabled=" + enabled +
                '}';
    }
}
