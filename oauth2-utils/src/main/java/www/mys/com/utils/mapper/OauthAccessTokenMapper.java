package www.mys.com.utils.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import www.mys.com.utils.pojo.OauthAccessToken;

@Repository("oauthAccessTokenMapper")
public interface OauthAccessTokenMapper extends JpaRepository<OauthAccessToken, String> {
}
