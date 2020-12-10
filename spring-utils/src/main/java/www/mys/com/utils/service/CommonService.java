package www.mys.com.utils.service;

import www.mys.com.utils.vo.response.ResponsePage;

public interface CommonService<Req, Res, V> {

    public Res uploadData(Req data);

    public Res getData(V id);

    public ResponsePage<Res> getDatas(Integer page, Integer count);

    public Res updateData(V id, Req data);

    public V deleteData(V id);

}
