package www.mys.com.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import www.mys.com.utils.vo.response.ResponsePage;

import java.util.ArrayList;
import java.util.List;

public class PageUtils {

    public static <Req, Res, Real> ResponsePage<Res> transferPage(Page<Real> page
            , DataTransfer<Req, Res, Real> dataTransfer) {
        return commonTransfer(new ResponsePage<>(page), dataTransfer);
    }

    public static <Req, Res, Real> ResponsePage<Res> transferPage(List<Real> datas
            , DataTransfer<Req, Res, Real> dataTransfer, Pageable pageable) {
        return commonTransfer(new ResponsePage<>(datas, pageable), dataTransfer);
    }

    private static <Req, Res, Real> ResponsePage<Res> commonTransfer(ResponsePage<Real> result
            , DataTransfer<Req, Res, Real> dataTransfer) {
        List<Res> resList = new ArrayList<>();
        if (result.getSize() > 0) {
            for (Real real : result.getContent()) {
                resList.add(dataTransfer.real2Res(real));
            }
        }
        return new ResponsePage<>(resList
                , result.getPageable(), result.getTotalElements()
                , result.isLast(), result.getTotalPages(), result.getNumber()
                , result.getSize(), result.getSort(), result.getNumberOfElements()
                , result.isFirst(), result.isEmpty()
        );
    }

}
