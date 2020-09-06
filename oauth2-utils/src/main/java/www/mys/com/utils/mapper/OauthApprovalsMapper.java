package www.mys.com.utils.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import www.mys.com.utils.pojo.OauthApprovals;

@Repository("oauthApprovalsMapper")
public interface OauthApprovalsMapper extends JpaRepository<OauthApprovals, Integer> {

}
