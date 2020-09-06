package www.mys.com.utils.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import www.mys.com.utils.pojo.OauthCode;

@Repository( "oauthCodeMapper")
public interface OauthCodeMapper extends JpaRepository<OauthCode, Integer> {


}
