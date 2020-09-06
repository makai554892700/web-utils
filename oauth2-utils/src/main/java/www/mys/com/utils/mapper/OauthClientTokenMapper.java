package www.mys.com.utils.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import www.mys.com.utils.pojo.OauthClientToken;

@Repository("oauthClientTokenMapper")
public interface OauthClientTokenMapper extends JpaRepository<OauthClientToken, String> {

}
