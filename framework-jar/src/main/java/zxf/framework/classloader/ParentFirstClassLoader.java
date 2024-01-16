package zxf.framework.classloader;

import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.function.Consumer;

@Slf4j
public class ParentFirstClassLoader extends MyClassloader {
    public ParentFirstClassLoader(URL[] urls, ClassLoader parent, Consumer<Throwable> classLoadingExceptionHandler) {
        super(urls, parent, classLoadingExceptionHandler);
        log.info("::ctor, urls={}, parent={}, alwaysParentFirstPatterns={}", urls, parent);
    }
}
