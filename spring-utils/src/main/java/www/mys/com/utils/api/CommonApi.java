package www.mys.com.utils.api;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import www.mys.com.utils.vo.response.ResponsePage;

public interface CommonApi<Req, Res, V> {

    public ResponseEntity<Res> uploadData(Req data, BindingResult bindingResult) throws Exception;

    public ResponseEntity<Res> getData(V id);

    public ResponseEntity<ResponsePage<Res>> getDatas(Integer page, Integer count);

    public ResponseEntity<Res> updateData(V id, Req data);

    public ResponseEntity<V> deleteData(V id);

}
