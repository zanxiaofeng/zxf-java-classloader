package zxf.common;

import java.util.Map;

public class Common {
    private String code;
    private String message;
    private Map<String, Object> properties;

    public Common(){
        System.out.println("Common in same-class loaded by " + this.getClass().getClassLoader());
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

    static {
        System.out.println("Static init of Common in same-class loaded by " + Common.class.getClassLoader());
    }
}
