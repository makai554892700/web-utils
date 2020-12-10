package www.mys.com.utils;

import org.springframework.data.domain.Page;
import www.mys.com.utils.vo.response.ResponsePage;

public abstract class DataTransfer<Req, Res, Real> {

    public abstract Real req2Real(Req req);

    public abstract Res real2Res(Real real);

    public ResponsePage<Res> real2Res(Page<Real> reals) {
        return PageUtils.transferPage(reals, this);
    }

    public abstract void updateReal(Req req, Real real);

}
