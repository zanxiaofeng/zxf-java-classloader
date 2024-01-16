package zxf.framework.classloader;

import java.net.URL;
import java.util.function.Consumer;

public class ParentFirstClassLoader extends MyClassloader {
    public ParentFirstClassLoader(URL[] urls, ClassLoader parent, Consumer<Throwable> classLoadingExceptionHandler) {
        super(urls, parent, classLoadingExceptionHandler);
    }
}
