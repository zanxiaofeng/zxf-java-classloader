package zxf.framework;

import lombok.extern.slf4j.Slf4j;
import zxf.common.Common;
import zxf.framework.userjar.UserJarStarter;

@Slf4j
public class FrameworkStart {
    public static void main(String[] args) throws Exception {
        log.info("::main, args={}", args);
        log.info("::main, FrameworkStart.class.getClassLoader={}", FrameworkStart.class.getClassLoader());
        log.info("::main, Thread.currentThread().getContextClassLoader={}", Thread.currentThread().getContextClassLoader());
        log.info("::main, Before Start, Common.message={}", Common.message);
        UserJarStarter.start(args[0], args[1], Boolean.parseBoolean(args[2]), args[3]);
        log.info("::main, After Start, Common.message={}", Common.message);
    }
}
