package classParse;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ThreadConst {
    public static String clazzTemp = "";

    public static String scanPath = "";

    public static String resultPath = "";

    public static final int CORE_THREAD_SIZE = 10;

    public static final int MILLIS_OF_ONE_SECOND = 1000;

    /**
     * 在class队列中路径存储格式： jar绝对路径 + 该分隔符 + class绝对路径
     */
    public static final String CLAZZ_PATH_SEPARATOR = "->";

    public static final String REGEX_TARGET = ".*\\.sun\\..*";

    public volatile static ConcurrentLinkedQueue<String> dirs = new ConcurrentLinkedQueue();

    public volatile static ConcurrentLinkedQueue<String> clazzs = new ConcurrentLinkedQueue();

    public volatile static ConcurrentLinkedQueue<String> result = new ConcurrentLinkedQueue();

}
