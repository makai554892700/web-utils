package www.mys.com.utils.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import www.mys.com.utils.BaseException;
import www.mys.com.utils.DataTransfer;
import www.mys.com.utils.mapper.CommonMapper;
import www.mys.com.utils.service.CommonService;

public abstract class CommonServiceImpl<Req, Res, Real, V> extends DataTransfer<Req, Res, Real>
        implements CommonService<Req, Res, V> {

    public abstract CommonMapper<Real, V> getCommonMapper();

    public Res uploadData(Req data) {
        return real2Res(getCommonMapper().save(req2Real(data)));
    }

    public Res getData(V id) {
        return real2Res(getCommonMapper().getById(id));
    }

    @Override
    public Page<Res> getDatas(Integer page, Integer count) {
        return real2Res(getCommonMapper().findAll(PageRequest.of(page, count)));
    }

    public Res updateData(V id, Req data) {
        Real real = getCommonMapper().getById(id);
        if (real == null) {
            throw new BaseException("no such data.", -10086);
        }
        updateReal(data, real);
        getCommonMapper().save(real);
        return real2Res(real);
    }

    public V deleteData(V id) {
        getCommonMapper().deleteById(id);
        return id;
    }

}
