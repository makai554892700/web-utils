package www.mys.com.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import www.mys.com.utils.vo.response.ResponsePage;

import java.util.ArrayList;
import java.util.List;

public class PageUtils {

    public static <Req, Res, Real> ResponsePage<Res> transferPage(Page<Real> page
            , DataTransfer<Req, Res, Real> dataTransfer) {
        ResponsePage<Res> result = new ResponsePage<Res>();
        List<Real> realContent = page.getContent();
        List<Res> resContent = new ArrayList<>();
        if (!realContent.isEmpty()) {
            realContent.forEach(data -> {
                resContent.add(dataTransfer.real2Res(data));
            });
        }
        result.setContent(resContent);
        Sort sort = page.getSort();
        ResponsePage.ResponseSort responseSort = new ResponsePage.ResponseSort();
        responseSort.setSorted(sort.isSorted());
        responseSort.setEmpty(sort.isEmpty());
        responseSort.setUnsorted(sort.isUnsorted());
        result.setSort(responseSort);
        Pageable pageable = page.getPageable();
        ResponsePage.ResponsePageable responsePageable = new ResponsePage.ResponsePageable();
        responsePageable.setSort(responseSort);
        responsePageable.setOffset(pageable.getOffset());
        responsePageable.setPageSize(pageable.getPageSize());
        responsePageable.setPageNumber(pageable.getPageNumber());
        responsePageable.setPaged(pageable.isPaged());
        responsePageable.setUnpaged(pageable.isUnpaged());
        result.setPageable(responsePageable);
        result.setTotalElements(page.getTotalElements());
        result.setLast(page.isLast());
        result.setTotalPages(page.getTotalPages());
        result.setNumber(page.getNumber());
        result.setSize(page.getSize());
        result.setNumberOfElements(page.getNumberOfElements());
        result.setFirst(page.isFirst());
        result.setEmpty(page.isEmpty());
        return result;
    }

}
