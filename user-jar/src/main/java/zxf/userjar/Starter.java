package zxf.userjar;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class Starter {
    public static Boolean start() {
        System.out.println("Static init of Common in same-class loaded by " + Starter.class.getClassLoader());
        return true;
    }
}
