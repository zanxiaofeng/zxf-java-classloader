package zxf.framework.classloader;

import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.function.Consumer;

@Slf4j
public abstract class MyClassloader extends URLClassLoader {
    public static final Consumer<Throwable> NOOP_EXCEPTION_HANDLER = classLoadingException -> {
    };

    private final Consumer<Throwable> classLoadingExceptionHandler;

    protected MyClassloader(URL[] urls, ClassLoader parent) {
        this(urls, parent, NOOP_EXCEPTION_HANDLER);
    }

    protected MyClassloader(URL[] urls, ClassLoader parent, Consumer<Throwable> classLoadingExceptionHandler) {
        super(urls, parent);
        this.classLoadingExceptionHandler = classLoadingExceptionHandler;
        log.info("::ctor, urls={}, parent={}", urls, parent);
    }

    @Override
    protected final Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        log.debug("::loadClass, name={}, resolve={}", name, resolve);
        try {
            return loadClassWithoutExceptionHandling(name, resolve);
        } catch (Throwable classLoadingException) {
            classLoadingExceptionHandler.accept(classLoadingException);
            throw classLoadingException;
        }
    }

    protected Class<?> loadClassWithoutExceptionHandling(String name, boolean resolve) throws ClassNotFoundException {
        log.debug("::loadClassWithoutExceptionHandling, name={}, resolve={}", name, resolve);
        Class<?> klass = super.loadClass(name, resolve);
        if (resolve) {
            resolveClass(klass);
        }
        return klass;
    }
}
