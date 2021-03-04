package www.mys.com.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class SortUtils {

    public static class SortType {
        public static final int TIME_DESC = 0;//时间顺序
        public static final int TIME_ASC = 1;//时间逆序
        public static final int USER_DESC = 2;//名字顺序
        public static final int USER_ASC = 3;//名字逆序
    }

    public static Pageable getPageable(Integer sortType, Integer page, Integer count) {
        return getPageable(getModelSort(sortType), page, count);
    }

    public static Pageable getPageable(Sort sort, Integer page, Integer count) {
        if (page == null) {
            page = 0;
        }
        if (count == null) {
            count = Integer.MAX_VALUE;
        }
        return PageRequest.of(page, count, sort);
    }

    public static Sort getModelSort(Integer sortType) {
        if (sortType != null) {
            switch (sortType) {
                case SortType.TIME_ASC:
                    return Sort.by(Sort.Direction.ASC, "updatedAt");
                case SortType.USER_DESC:
                    return Sort.by(Sort.Direction.DESC, "name");
                case SortType.USER_ASC:
                    return Sort.by(Sort.Direction.ASC, "name");
                default:
                case SortType.TIME_DESC:
                    return Sort.by(Sort.Direction.DESC, "updatedAt");
            }
        }
        return Sort.by(Sort.Direction.DESC, "updatedAt");
    }
}
