package www.mys.com.oauth2.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import www.mys.com.oauth2.pojo.OauthClientDetails;

@Repository("oauthClientDetailsMapper")
public interface OauthClientDetailsMapper extends JpaRepository<OauthClientDetails, String> {

    public OauthClientDetails getByClientId(String clientId);

}
