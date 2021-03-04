package www.mys.com.utils.vo.response;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResponsePage<T> implements Serializable {

    private List<T> content;
    private ResponsePageable pageable;
    private long totalElements;
    private boolean last;
    private int totalPages;
    private int number;
    private long size;
    private ResponseSort sort;
    private int numberOfElements;
    private boolean first;
    private boolean empty;

    public ResponsePage() {
    }

    public ResponsePage(List<T> datas, Pageable pageable) {
        boolean isEmpty = datas == null || datas.isEmpty();
        int length = isEmpty ? 0 : datas.size();
        int totalPages = isEmpty ? 0 : length / pageable.getPageSize();
        int number = isEmpty ? 0 : pageable.getPageNumber();
        int size = isEmpty ? 0 : pageable.getPageSize();
        int numberOfElements = number;
        boolean isFirst = number == 0;
        boolean isLast = totalElements <= size;
        List<T> content = new ArrayList<>();
        if (!isEmpty) {
            for (int i = number * size; i < (number + 1) * size; i++) {
                if (i < datas.size()) {
                    content.add(datas.get(i));
                }
            }
        }
        setContent(content);
        Sort sort = pageable.getSort();
        ResponsePage.ResponseSort responseSort = new ResponsePage.ResponseSort();
        responseSort.setSorted(sort.isSorted());
        responseSort.setEmpty(sort.isEmpty());
        responseSort.setUnsorted(sort.isUnsorted());
        setSort(responseSort);
        ResponsePage.ResponsePageable responsePageable = new ResponsePage.ResponsePageable();
        responsePageable.setSort(responseSort);
        responsePageable.setOffset(pageable.getOffset());
        responsePageable.setPageSize(pageable.getPageSize());
        responsePageable.setPageNumber(pageable.getPageNumber());
        responsePageable.setPaged(pageable.isPaged());
        responsePageable.setUnpaged(pageable.isUnpaged());
        setPageable(responsePageable);
        setTotalElements(length);
        setLast(isLast);
        setTotalPages(totalPages);
        setNumber(number);
        setSize(size);
        setNumberOfElements(numberOfElements);
        setFirst(isFirst);
        setEmpty(isEmpty);
    }

    public ResponsePage(Page<T> page) {
        setContent(page.getContent());
        Sort sort = page.getSort();
        ResponsePage.ResponseSort responseSort = new ResponsePage.ResponseSort();
        responseSort.setSorted(sort.isSorted());
        responseSort.setEmpty(sort.isEmpty());
        responseSort.setUnsorted(sort.isUnsorted());
        setSort(responseSort);
        Pageable pageable = page.getPageable();
        ResponsePage.ResponsePageable responsePageable = new ResponsePage.ResponsePageable();
        responsePageable.setSort(responseSort);
        responsePageable.setOffset(pageable.getOffset());
        responsePageable.setPageSize(pageable.getPageSize());
        responsePageable.setPageNumber(pageable.getPageNumber());
        responsePageable.setPaged(pageable.isPaged());
        responsePageable.setUnpaged(pageable.isUnpaged());
        setPageable(responsePageable);
        setTotalElements(page.getTotalElements());
        setLast(page.isLast());
        setTotalPages(page.getTotalPages());
        setNumber(page.getNumber());
        setSize(page.getSize());
        setNumberOfElements(page.getNumberOfElements());
        setFirst(page.isFirst());
        setEmpty(page.isEmpty());
    }

    public ResponsePage(List<T> content, ResponsePageable pageable, long totalElements, boolean last
            , int totalPages, int number, long size, ResponseSort sort, int numberOfElements
            , boolean first, boolean empty) {
        this.content = content;
        this.pageable = pageable;
        this.totalElements = totalElements;
        this.last = last;
        this.totalPages = totalPages;
        this.number = number;
        this.size = size;
        this.sort = sort;
        this.numberOfElements = numberOfElements;
        this.first = first;
        this.empty = empty;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public ResponsePageable getPageable() {
        return pageable;
    }

    public void setPageable(ResponsePageable pageable) {
        this.pageable = pageable;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public ResponseSort getSort() {
        return sort;
    }

    public void setSort(ResponseSort sort) {
        this.sort = sort;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    @Override
    public String toString() {
        return "ResponsePage{" +
                "content=" + content +
                ", pageable=" + pageable +
                ", totalElements=" + totalElements +
                ", last=" + last +
                ", totalPages=" + totalPages +
                ", number=" + number +
                ", size=" + size +
                ", sort=" + sort +
                ", numberOfElements=" + numberOfElements +
                ", first=" + first +
                ", empty=" + empty +
                '}';
    }

    public static class ResponsePageable {
        private ResponseSort sort;
        private long offset;
        private long pageSize;
        private int pageNumber;
        private boolean paged;
        private boolean unpaged;

        public ResponsePageable() {
        }

        public ResponsePageable(ResponseSort sort, long offset, long pageSize, int pageNumber
                , boolean paged, boolean unpaged) {
            this.sort = sort;
            this.offset = offset;
            this.pageSize = pageSize;
            this.pageNumber = pageNumber;
            this.paged = paged;
            this.unpaged = unpaged;
        }

        public ResponseSort getSort() {
            return sort;
        }

        public void setSort(ResponseSort sort) {
            this.sort = sort;
        }

        public long getOffset() {
            return offset;
        }

        public void setOffset(long offset) {
            this.offset = offset;
        }

        public long getPageSize() {
            return pageSize;
        }

        public void setPageSize(long pageSize) {
            this.pageSize = pageSize;
        }

        public int getPageNumber() {
            return pageNumber;
        }

        public void setPageNumber(int pageNumber) {
            this.pageNumber = pageNumber;
        }

        public boolean isPaged() {
            return paged;
        }

        public void setPaged(boolean paged) {
            this.paged = paged;
        }

        public boolean isUnpaged() {
            return unpaged;
        }

        public void setUnpaged(boolean unpaged) {
            this.unpaged = unpaged;
        }

        @Override
        public String toString() {
            return "ResponsePageable{" +
                    "sort=" + sort +
                    ", offset=" + offset +
                    ", pageSize=" + pageSize +
                    ", pageNumber=" + pageNumber +
                    ", paged=" + paged +
                    ", unpaged=" + unpaged +
                    '}';
        }
    }

    public static class ResponseSort {
        private boolean sorted;
        private boolean unsorted;
        private boolean empty;

        public ResponseSort() {
        }

        public ResponseSort(boolean sorted, boolean unsorted, boolean empty) {
            this.sorted = sorted;
            this.unsorted = unsorted;
            this.empty = empty;
        }

        public boolean isSorted() {
            return sorted;
        }

        public void setSorted(boolean sorted) {
            this.sorted = sorted;
        }

        public boolean isUnsorted() {
            return unsorted;
        }

        public void setUnsorted(boolean unsorted) {
            this.unsorted = unsorted;
        }

        public boolean isEmpty() {
            return empty;
        }

        public void setEmpty(boolean empty) {
            this.empty = empty;
        }

        @Override
        public String toString() {
            return "ResponseSort{" +
                    "sorted=" + sorted +
                    ", unsorted=" + unsorted +
                    ", empty=" + empty +
                    '}';
        }
    }

}
