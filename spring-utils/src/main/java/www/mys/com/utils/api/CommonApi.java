package www.mys.com.utils.api;

import org.springframework.validation.BindingResult;
import www.mys.com.utils.Result;
import www.mys.com.utils.vo.response.ResponsePage;

public interface CommonApi<Req, Res, V> {

    public Result<Res> uploadData(Req data, BindingResult bindingResult) throws Exception;

    public Result<Res> getData(V id);

    public Result<ResponsePage<Res>> getDatas(Integer page, Integer count);

    public Result<Res> updateData(V id, Req data);

    public Result<V> deleteData(V id);

}
