package zxf.framework.classloader;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.function.Consumer;

@Slf4j
public final class ChildFirstClassLoader extends MyClassloader {
    private final String[] alwaysParentFirstPatterns;

    public ChildFirstClassLoader(URL[] urls, ClassLoader parent, String[] alwaysParentFirstPatterns, Consumer<Throwable> classLoadingExceptionHandler) {
        super(urls, parent, classLoadingExceptionHandler);
        this.alwaysParentFirstPatterns = alwaysParentFirstPatterns;
        log.info("::ctor, urls={}, parent={}, alwaysParentFirstPatterns={}", urls, parent, alwaysParentFirstPatterns);
    }

    @Override
    protected synchronized Class<?> loadClassWithoutExceptionHandling(String name, boolean resolve) throws ClassNotFoundException {
        log.debug("::loadClassWithoutExceptionHandling, name={}, resolve={}", name, resolve);
        Class<?> klass = findLoadedClass(name);
        if (klass != null) {
            log.debug("::loadClassWithoutExceptionHandling, the class had been loaded in current class loader");
            return klass;
        }

        for (String alwaysParentFirstPattern : alwaysParentFirstPatterns) {
            if (name.startsWith(alwaysParentFirstPattern)) {
                log.debug("::loadClassWithoutExceptionHandling, this class should be loaded by parent-first");
                return super.loadClassWithoutExceptionHandling(name, resolve);
            }
        }

        try {
            log.info("::loadClassWithoutExceptionHandling, finding the class in current class loader(child-first)");
            klass = findClass(name);
            if (resolve) {
                resolveClass(klass);
            }
        } catch (ClassNotFoundException e) {
            log.debug("::loadClassWithoutExceptionHandling, can not find the class in current class loader, then find it in parent");
            klass = super.loadClassWithoutExceptionHandling(name, resolve);
        }

        return klass;
    }

    @Override
    public URL getResource(String name) {
        log.info("::getResource, first, find resource in current class loader");
        URL urlClassLoaderResource = findResource(name);
        if (urlClassLoaderResource != null) {
            return urlClassLoaderResource;
        }
        log.info("::getResource, then, find resource in parent");
        return super.getResource(name);
    }

    @Override
    public Enumeration<URL> getResources(String name) throws IOException {
        log.info("::getResource, first, find resources in current class loader");
        Enumeration<URL> urlClassLoaderResources = findResources(name);
        final List<URL> result = Collections.list(urlClassLoaderResources);

        log.info("::getResource, then, find resources in parent");
        Enumeration<URL> parentResources = getParent().getResources(name);
        result.addAll(Collections.list(parentResources));

        return Collections.enumeration(result);
    }
}
