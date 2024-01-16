package zxf.framework.userjar;

import lombok.extern.slf4j.Slf4j;
import zxf.framework.classloader.ChildFirstClassLoader;
import zxf.framework.classloader.ParentFirstClassLoader;

import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Paths;

@Slf4j
public class UserJarStarter {
    public static void start(String userJar, String startClass, Boolean parentFirst, String message) throws Exception {
        log.info("::start, userJar={}, startClass={}, parentFirst={}, message={}", userJar, startClass, parentFirst, message);
        URL[] appJarUrls = new URL[]{
                new URL("file:" + Paths.get("./libraries", userJar).toAbsolutePath())
        };
        ClassLoader classLoader = createClassLoader(appJarUrls, parentFirst);
        startWithClassloader(startClass, classLoader, message);
    }

    private static void startWithClassloader(String startClass, ClassLoader userCodeClassLoader, String message) throws Exception {
        log.info("::startWithClassloader, startClass={}, userCodeClassLoader={}, message={}", startClass, userCodeClassLoader, message);
        ClassLoader originClassloader = Thread.currentThread().getContextClassLoader();

        try {
            Thread.currentThread().setContextClassLoader(userCodeClassLoader);
            Class klass = userCodeClassLoader.loadClass(startClass);
            Method start = klass.getMethod("start", String.class);
            if (start != null) {
                start.invoke(null, message);
            }
        } finally {
            Thread.currentThread().setContextClassLoader(originClassloader);
        }
    }

    private static ClassLoader createClassLoader(URL[] urls, Boolean parentFirst) {
        log.info("::createClassLoader, urls={}, parentFirst={}", urls, parentFirst);
        if (parentFirst) {
            return new ParentFirstClassLoader(urls, Thread.currentThread().getContextClassLoader(), (ex) -> {
                log.error("Error when load class from parent first classloader.", ex);
            });
        }

        String[] parentFirstPackages = new String[]{"java", "javax", "org.slf4j", "ch.qos.logback"};
        return new ChildFirstClassLoader(urls, Thread.currentThread().getContextClassLoader(), parentFirstPackages, (ex) -> {
            log.error("Error when load class from child first classloader.", ex);
        });
    }
}
