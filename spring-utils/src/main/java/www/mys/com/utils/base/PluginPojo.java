package www.mys.com.utils.base;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class PluginPojo extends BasePojo {
    @Column(length = 64, unique = true)
    private String uniKey;                                  // 唯一键
    @Column(length = 128)
    private String index1;                                  // 索引字段1
    @Column(length = 128)
    private String index2;                                  // 索引字段2
    @Column(length = 128)
    private String index3;                                  // 索引字段3
    private String normal1;                                 // 备用字段1
    private String normal2;                                 // 备用字段2
    private String normal3;                                 // 备用字段3
    @Type(type = "text")
    private String text1;                                   // 超长字段1
    @Type(type = "text")
    private String text2;                                   // 超长字段2
    @Type(type = "text")
    private String text3;                                   // 超长字段3
}
