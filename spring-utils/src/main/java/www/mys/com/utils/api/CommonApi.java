package www.mys.com.utils.api;

import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import www.mys.com.utils.Result;

public interface CommonApi<Req, Res, V> {

    public Result<Res> uploadData(Req data, BindingResult bindingResult) throws Exception;

    public Result<Res> getData(V id);

    public Result<Page<Res>> getDatas(Integer page, Integer count);

    public Result<Res> updateData(V id, Req data);

    public Result<V> deleteData(V id);

}
