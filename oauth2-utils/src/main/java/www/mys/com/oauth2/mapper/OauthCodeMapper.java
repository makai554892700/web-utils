package www.mys.com.oauth2.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import www.mys.com.oauth2.pojo.OauthCode;

@Repository( "oauthCodeMapper")
public interface OauthCodeMapper extends JpaRepository<OauthCode, Integer> {


}
