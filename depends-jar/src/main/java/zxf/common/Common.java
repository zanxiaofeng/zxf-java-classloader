package zxf.common;

public class Common {
    public static String message;

    static {
        Common.message = String.format("Common.class.getClassLoader=%s, Thread.currentThread().getContextClassLoader=%s",
                Common.class.getClassLoader().toString(), Thread.currentThread().getContextClassLoader().toString());
    }
}
