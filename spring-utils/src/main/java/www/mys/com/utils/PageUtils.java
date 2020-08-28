package www.mys.com.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public class PageUtils {

    public static <Req, Res, Real> Page<Res> transferPage(Page<Real> page
            , DataTransfer<Req, Res, Real> dataTransfer) {
        return new Page<Res>() {
            @Override
            public int getTotalPages() {
                return page.getTotalPages();
            }

            @Override
            public long getTotalElements() {
                return page.getTotalElements();
            }

            @Override
            public <U> Page<U> map(Function<? super Res, ? extends U> converter) {
                Page<Res> result = page.map(dataTransfer::real2Res);
                return result.map(converter);
            }

            @Override
            public int getNumber() {
                return page.getNumber();
            }

            @Override
            public int getSize() {
                return page.getSize();
            }

            @Override
            public int getNumberOfElements() {
                return page.getNumberOfElements();
            }

            @Override
            public List<Res> getContent() {
                List<Real> datas = page.getContent();
                List<Res> result = new ArrayList<>();
                if (!datas.isEmpty()) {
                    datas.forEach(data -> {
                        result.add(dataTransfer.real2Res(data));
                    });
                }
                return result;
            }

            @Override
            public boolean hasContent() {
                return page.hasContent();
            }

            @Override
            public Sort getSort() {
                return page.getSort();
            }

            @Override
            public boolean isFirst() {
                return page.isFirst();
            }

            @Override
            public boolean isLast() {
                return page.isLast();
            }

            @Override
            public boolean hasNext() {
                return page.hasNext();
            }

            @Override
            public boolean hasPrevious() {
                return page.hasPrevious();
            }

            @Override
            public Pageable nextPageable() {
                return page.nextPageable();
            }

            @Override
            public Pageable previousPageable() {
                return page.previousPageable();
            }

            @Override
            public Iterator<Res> iterator() {
                Iterator<Real> iterator = page.iterator();
                return new Iterator<Res>() {
                    @Override
                    public boolean hasNext() {
                        return page.hasNext();
                    }

                    @Override
                    public Res next() {
                        return dataTransfer.real2Res(iterator.next());
                    }
                };
            }
        };
    }

}
