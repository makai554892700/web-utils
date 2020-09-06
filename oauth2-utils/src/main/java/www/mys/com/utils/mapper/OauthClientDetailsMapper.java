package www.mys.com.utils.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import www.mys.com.utils.pojo.OauthClientDetails;

@Repository("oauthClientDetailsMapper")
public interface OauthClientDetailsMapper extends JpaRepository<OauthClientDetails, String> {

    public OauthClientDetails getByClientId(String clientId);

}
