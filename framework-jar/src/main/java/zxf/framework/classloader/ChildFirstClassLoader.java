package zxf.framework.classloader;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.function.Consumer;

public final class ChildFirstClassLoader extends MyClassloader {
    private final String[] alwaysParentFirstPatterns;

    public ChildFirstClassLoader(URL[] urls, ClassLoader parent, String[] alwaysParentFirstPatterns, Consumer<Throwable> classLoadingExceptionHandler) {
        super(urls, parent, classLoadingExceptionHandler);
        this.alwaysParentFirstPatterns = alwaysParentFirstPatterns;
    }

    @Override
    protected synchronized Class<?> loadClassWithoutExceptionHandling(String name, boolean resolve) throws ClassNotFoundException {
        // Check if the class has already been loaded
        Class<?> klass = findLoadedClass(name);
        if (klass != null) {
            return klass;
        }

        // Check whether the class should go parent-first
        for (String alwaysParentFirstPattern : alwaysParentFirstPatterns) {
            if (name.startsWith(alwaysParentFirstPattern)) {
                return super.loadClassWithoutExceptionHandling(name, resolve);
            }
        }

        // Child first
        try {
            klass = findClass(name);
            if (resolve) {
                resolveClass(klass);
            }
        } catch (ClassNotFoundException e) {
            // Then parent
            klass = super.loadClassWithoutExceptionHandling(name, resolve);
        }

        return klass;
    }

    @Override
    public URL getResource(String name) {
        // Child first
        URL urlClassLoaderResource = findResource(name);
        if (urlClassLoaderResource != null) {
            return urlClassLoaderResource;
        }
        // Then parent
        return super.getResource(name);
    }

    @Override
    public Enumeration<URL> getResources(String name) throws IOException {
        // Child first
        Enumeration<URL> urlClassLoaderResources = findResources(name);
        final List<URL> result = Collections.list(urlClassLoaderResources);

        // Then parent
        Enumeration<URL> parentResources = getParent().getResources(name);
        result.addAll(Collections.list(parentResources));

        return Collections.enumeration(result);
    }
}
