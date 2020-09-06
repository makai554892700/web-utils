package www.mys.com.utils.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import www.mys.com.utils.pojo.OauthRefreshToken;

@Repository("oauthRefreshTokenMapper")
public interface OauthRefreshTokenMapper extends JpaRepository<OauthRefreshToken, Integer> {


}
