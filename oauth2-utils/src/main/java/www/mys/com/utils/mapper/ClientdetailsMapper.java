package www.mys.com.utils.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import www.mys.com.utils.pojo.Clientdetails;

@Repository("ClientdetailsMapper")
public interface ClientdetailsMapper extends JpaRepository<Clientdetails, String> {

}
