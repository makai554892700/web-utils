package www.mys.com.oauth2user.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import www.mys.com.oauth2user.pojo.PersistentLogins;

@Repository("persistentLoginsMapper")
public interface PersistentLoginsMapper extends JpaRepository<PersistentLogins, Integer> {

    public PersistentLogins getBySeries(String series);

    @Transactional
    public void deleteByUsername(String username);

}
