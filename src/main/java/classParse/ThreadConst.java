package classParse;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ThreadConst {
    public static String clazzTemp = "";

    public static String scanPath = "";

    public static String srcTemp = "";

    public static final int CORE_THREAD_SIZE = 10;

    public static final int THREAD_START_DELAY = 2;

    public static final int THREAD_START_CYCLE = 5;

    public static final int CHECK_CYCLE = 5;

    public static final int MILLIS_OF_ONE_SECOND = 1000;

    public static final int CLAZZ_QUEUE_MAX_LENGTH = 100;

    public static final int CLAZZ_QUEUE_MIN_LENGTH = 2000;

    /**
     * 在class队列中路径存储格式： jar绝对路径 + 该分隔符 + class绝对路径
     */
    public static final String CLAZZ_PATH_SEPARATOR = "->";

    public static final String REGEX_TARGET = ".*\\.sun\\..*";

    public volatile static ConcurrentLinkedQueue<String> dirs = new ConcurrentLinkedQueue();

    public volatile static ConcurrentLinkedQueue<String> clazzs = new ConcurrentLinkedQueue();

}
