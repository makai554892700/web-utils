package www.mys.com.oauth2user.pojo;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(indexes = {@Index(columnList = "username,series")})
public class PersistentLogins {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, length = 50)
    private String username;
    @Column(nullable = false, length = 50)
    private String series;
    @Column(nullable = false)
    private String tokenValue;
    @Column(columnDefinition = "TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Date date;

    public PersistentLogins() {
    }

    public PersistentLogins(String username, String series, String tokenValue, Date date) {
        this.username = username;
        this.series = series;
        this.tokenValue = tokenValue;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "PersistentLogins{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", series='" + series + '\'' +
                ", tokenValue='" + tokenValue + '\'' +
                ", date=" + date +
                '}';
    }
}
