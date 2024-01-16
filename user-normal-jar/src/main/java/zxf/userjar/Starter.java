package zxf.userjar;

import lombok.extern.slf4j.Slf4j;
import zxf.common.Common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class Starter {
    public static List<String> messages = new ArrayList<>();

    public static Boolean start(String message) {
        log.error("::start BEGIN {}, classloader={}, Common.message={}", message, Starter.class.getClassLoader(), Common.message);
        messages.add(message);
        log.error("::start END {} ,Common.message={}", messages, Common.message);
        return true;
    }

    static {
        log.info("::main, Starter.class.getClassLoader={}", Starter.class.getClassLoader());
        log.info("::main, Thread.currentThread().getContextClassLoader={}", Thread.currentThread().getContextClassLoader());
    }
}
