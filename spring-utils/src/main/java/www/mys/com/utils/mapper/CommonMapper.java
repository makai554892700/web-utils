package www.mys.com.utils.mapper;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommonMapper<T,V> extends JpaRepository<T, V> {

    public T getById(V id);

}
