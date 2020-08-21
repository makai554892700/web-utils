package www.mys.com.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class XMLUtils {

    public static <T> T getObject(String data, Class<T> clzz) {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
        try {
            return xmlMapper.readValue(data, clzz);
        } catch (Exception e) {
            System.out.println("e=" + e);
            return null;
        }
    }

    public static String getMapToXML(Map<String, String> param) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (Map.Entry<String, String> entry : param.entrySet()) {
            sb.append("<").append(entry.getKey()).append(">");
            sb.append(entry.getValue());
            sb.append("<").append(entry.getKey()).append(">");
        }
        sb.append("</xml>");
        return sb.toString();
    }

    public static Map<String, String> doXMLParse(String strXml) throws JDOMException, IOException {
        if (null == strXml || "".equals(strXml)) {
            return null;
        }
        strXml = strXml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");
        Map<String, String> m = new HashMap<>();
        InputStream in = new ByteArrayInputStream(strXml.getBytes(StandardCharsets.UTF_8));
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(in);
        Element root = doc.getRootElement();
        List<Element> list = root.getChildren();
        for (Element e : list) {
            String k = e.getName();
            String v;
            List<Element> children = e.getChildren();
            if (children.isEmpty()) {
                v = e.getTextNormalize();
            } else {
                v = getChildrenText(children);
            }
            m.put(k, v);
        }
        in.close();
        return m;
    }

    public static String getChildrenText(List<Element> children) {
        StringBuilder sb = new StringBuilder();
        if (!children.isEmpty()) {
            for (Element e : children) {
                String name = e.getName();
                String value = e.getTextNormalize();
                List<Element> list = e.getChildren();
                sb.append("<").append(name).append(">");
                if (!list.isEmpty()) {
                    sb.append(getChildrenText(list));
                }
                sb.append(value);
                sb.append("<").append(name).append(">");
            }
        }
        return sb.toString();
    }


}
