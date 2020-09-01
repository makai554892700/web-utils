package www.mys.com.utils;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class RequestData<T> implements Serializable {

    private int page;
    private int count;
    @Valid
    @NotNull(message = "data不能为空")
    private T data;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RequestData{" +
                "page=" + page +
                ", count=" + count +
                ", data=" + data +
                '}';
    }
}
