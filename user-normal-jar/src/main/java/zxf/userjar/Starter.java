package zxf.userjar;

import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import zxf.common.Common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class Starter {
    public static List<String> messages = new ArrayList<>();

    public static Boolean start(String message) {
        log.info("::start BEGIN {}, classloader={}, Common.message={}", message, Starter.class.getClassLoader(), Common.message);
        // Guava 19.0 do not support this method
        log.info("::start Preconditions.class.getClassLoader={}", Preconditions.class.getClassLoader());
        Preconditions.checkArgument(message != null, "Argument at %s can not be null", 1);
        messages.add(message);
        log.info("::start END {} ,Common.message={}", messages, Common.message);
        return true;
    }

    static {
        log.info("::main, Starter.class.getClassLoader={}", Starter.class.getClassLoader());
        log.info("::main, Thread.currentThread().getContextClassLoader={}", Thread.currentThread().getContextClassLoader());
    }
}
