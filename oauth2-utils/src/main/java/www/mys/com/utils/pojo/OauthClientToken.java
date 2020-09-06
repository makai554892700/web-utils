package www.mys.com.utils.pojo;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.util.Arrays;

@Entity
public class OauthClientToken {

    @Id
    @Column(nullable = false, length = 128)
    private String authenticationId;
    private String tokenId;
    @Lob
    @Type(type="org.hibernate.type.ImageType")
    private byte[] token;
    private String userName;
    private String clientId;

    public String getAuthenticationId() {
        return authenticationId;
    }

    public void setAuthenticationId(String authenticationId) {
        this.authenticationId = authenticationId;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public byte[] getToken() {
        return token;
    }

    public void setToken(byte[] token) {
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return "OauthClientToken{" +
                "authenticationId='" + authenticationId + '\'' +
                ", tokenId='" + tokenId + '\'' +
                ", token=" + Arrays.toString(token) +
                ", userName='" + userName + '\'' +
                ", clientId='" + clientId + '\'' +
                '}';
    }
}
