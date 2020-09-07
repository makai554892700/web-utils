package www.mys.com.utils.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import www.mys.com.utils.pojo.User;

import java.util.List;

@Repository("userMapper")
public interface UserMapper extends JpaRepository<User, Integer> {

    public User getUserById(Integer id);

    public User getUserByUserName(String userName);

    @Transactional
    public void deleteByIdIn(List<Integer> ids);

}
