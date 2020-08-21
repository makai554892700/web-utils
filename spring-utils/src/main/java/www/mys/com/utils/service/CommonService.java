package www.mys.com.utils.service;

import org.springframework.data.domain.Page;

public interface CommonService<Req, Res, V> {

    public Res uploadData(Req data);

    public Res getData(V id);

    public Page<Res> getDatas(Integer page, Integer count);

    public Res updateData(V id, Req data);

    public V deleteData(V id);

}
