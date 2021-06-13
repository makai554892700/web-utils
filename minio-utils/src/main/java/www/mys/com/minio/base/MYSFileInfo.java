package www.mys.com.minio.base;

import java.io.InputStream;
import java.util.Date;

public interface MYSFileInfo {

    public InputStream getStream();

    public String getId();

    public String getFileName();

    public String getContentType();

    public long getLength();

    public Date getCreatedAt();

    public Date getUpdatedAt();


}
