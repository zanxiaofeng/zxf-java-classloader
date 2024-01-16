package zxf.framework.userjar;

import zxf.framework.classloader.ChildFirstClassLoader;
import zxf.framework.classloader.ParentFirstClassLoader;

import java.lang.reflect.Method;
import java.net.URL;


public class UserJarStarter {
    public static void start(String startClass, Boolean parentFirst) throws Exception {
        ClassLoader classLoader = createClassLoader(null, parentFirst);
        startWithClassloader(startClass, classLoader);
        Class.forName()
    }

    private static void startWithClassloader(String startClass, ClassLoader userCodeClassLoader) throws Exception {
        ClassLoader originClassloader = Thread.currentThread().getContextClassLoader();

        try {
            Thread.currentThread().setContextClassLoader(userCodeClassLoader);
            Class klass = userCodeClassLoader.loadClass(startClass);
            Method start = klass.getMethod("start", Boolean.class);
            if (start != null) {
                start.invoke(null, Boolean.TRUE);
            }
        } finally {
            Thread.currentThread().setContextClassLoader(originClassloader);
        }
    }

    private static ClassLoader createClassLoader(URL[] urls, Boolean parentFirst) {
        if (parentFirst) {
            return new ParentFirstClassLoader(urls, Thread.currentThread().getContextClassLoader(), (ex) -> {
                System.out.println("Error when load class from parent first classloader: " + ex);
            });
        }

        String[] parentFirstPackages = new String[]{};
        return new ChildFirstClassLoader(urls, Thread.currentThread().getContextClassLoader(), parentFirstPackages, (ex) -> {
            System.out.println("Error when load class from child first classloader: " + ex);
        });
    }
}
