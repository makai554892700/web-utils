package www.mys.com.oauth2.pojo;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Arrays;

@Entity
public class OauthRefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String tokenId;
    @Lob
    @Type(type="org.hibernate.type.ImageType")
    private byte[] token;
    @Lob
    @Type(type="org.hibernate.type.ImageType")
    private byte[] authentication;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public byte[] getAuthentication() {
        return authentication;
    }

    public void setAuthentication(byte[] authentication) {
        this.authentication = authentication;
    }

    @Override
    public String toString() {
        return "OauthRefreshToken{" +
                "id=" + id +
                ", tokenId='" + tokenId + '\'' +
                ", token=" + Arrays.toString(token) +
                ", authentication=" + Arrays.toString(authentication) +
                '}';
    }
}
