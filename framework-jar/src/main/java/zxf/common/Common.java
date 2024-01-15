package zxf.common;

import java.io.Serializable;
import java.util.Map;

public class Common {
    private String code;
    private String message;
    private Map<String, Object> properties;

    public Common(){
        System.out.println("Common in test-same-class");
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }
}
