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

    public String getUniKey() {
        return uniKey;
    }

    public void setUniKey(String uniKey) {
        this.uniKey = uniKey;
    }

    public String getIndex1() {
        return index1;
    }

    public void setIndex1(String index1) {
        this.index1 = index1;
    }

    public String getIndex2() {
        return index2;
    }

    public void setIndex2(String index2) {
        this.index2 = index2;
    }

    public String getIndex3() {
        return index3;
    }

    public void setIndex3(String index3) {
        this.index3 = index3;
    }

    public String getNormal1() {
        return normal1;
    }

    public void setNormal1(String normal1) {
        this.normal1 = normal1;
    }

    public String getNormal2() {
        return normal2;
    }

    public void setNormal2(String normal2) {
        this.normal2 = normal2;
    }

    public String getNormal3() {
        return normal3;
    }

    public void setNormal3(String normal3) {
        this.normal3 = normal3;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public String getText3() {
        return text3;
    }

    public void setText3(String text3) {
        this.text3 = text3;
    }

}
