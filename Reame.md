# Flink和Spark等框架的通用依赖问题
## 问题定义
- JVM的ClassLoader在加载类的时候，由同一个Classloader加载的类，如果出现重复，只有第一个会被加载，后面的重复类会被忽略。
- 对于Flink和Spark等框架来说，其上运行的代码可以分为用户代码和框架代码，用户代码有自身依赖的Ｊar包，框架代码也有自身依赖的Ｊar包，如果用户代码和框架代码使用了同一个Ｊar包的两个不兼容版本，在运行时如果不能将框架代码（包括依赖）和用户代码（包括依赖）相互隔离将导致用户或框架代码运行时使用的Ｊar包版本不是其本来需要的版本，从而导致执行出错。
## 解决方案
- 方案一：使用maven-shade-plugin插件，shade化第三方Ｊar包，以避免Ｊar包版本冲突；例如将项目中以com.google.common.*开头的GuavaＪar包中的Class以及代码中的使用点全部改名为my.guava.common.*，然后打包在一起，这样便可以避免冲突．
- 方案二：
- 
# Classloader
- Bootstrap class loader，bootstrap class loader是native code写的。它是所有ClassLoader的祖先，它是顶级ClassLoader。它负责加载JDK的内部类型，一般来说就是位于$JAVA_HOME/jre/lib下的核心库和rt.jar。
- Extension class loader，即Extension class loader，负责加载Java核心类的扩展，加载$JAVA_HOME/lib/ext目录和System Property java.ext.dirs所指定目录下的类。
- System class loader，又称Application class loader。它的parent class loader是extension class loader，负责加载CLASSPATH环境变量、-classpath/-cp启动参数指定路径下的类。


# Class的唯一性
- 
- 如果一个类被一个ClassLoader加载两次，那么两次的结果应该是一致的，并且这个加载过程是线程安全的.
- 一个Class的唯一性不仅仅是其全限定名（Fully-qualified-name），而是由【加载其的ClassLoader + 其全限定名】联合保证唯一
- 若同個.class檔案由不同ClassLoader載入, 將視為不同的Class<?>, 意即若將ClassLoader(A)載入之test.class實例派給ClassLoader(B)的ClassType將會拋出java.lang.ClassCastException

# Delegate Model
- The ClassLoader class uses a delegation model to search for classes and resources. Each instance of ClassLoader has an associated parent class loader. When requested to find a class or resource, a ClassLoader instance will delegate the search for the class or resource to its parent class loader before attempting to find the class or resource itself. The virtual machine’s built-in class loader, called the “bootstrap class loader”, does not itself have a parent but may serve as the parent of a ClassLoader instance.

# Context Classloader


# How to get classloader
- Class::getClassLoader()[jvm provided]
- ClassLoader.getSystemClassloader()[jvm provided]
- ClassLoader.getPlatformClassLoader()[jvm provided, after jdk9]
- Thread::getContextClassLoader()[jvm provided]
- Customized Classloader[user customized]

# How to load a class
- ClassLoader::loadClass(String name)
- Class.forName(String name) - (By CallerClass' classloader)
- Class.forName(String name, boolean initial, ClassLoader classloader)

# How to get a resource
- By Classloader
- By Class(By class's classloader)