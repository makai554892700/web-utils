package www.mys.com.utils.vo.response;

import java.io.Serializable;
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
