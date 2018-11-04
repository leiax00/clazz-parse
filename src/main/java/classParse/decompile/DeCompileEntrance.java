package classParse.decompile;

import classParse.ThreadConst;
import classParse.unzip.UnzipEntrance;

import java.io.File;
import java.util.NoSuchElementException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DeCompileEntrance {
    public static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(ThreadConst.CORE_THREAD_SIZE);
    private DeCompiler compiler = new DeCompiler();
    public void process() {
        long start = System.currentTimeMillis();
        while (true) {
            // todo decompile逻辑
            while (!ThreadConst.clazzs.isEmpty()) {
                executorService.scheduleAtFixedRate(()->{
                    try {
                        String clazzPath = ThreadConst.clazzs.remove();
                        compiler.deCompile(clazzPath, ThreadConst.srcTemp);
                    } catch (NoSuchElementException e) {
                        System.out.println("ThreadConst.clazzs has bean empty");
//                        if (ThreadConst.dirs.size() != 0) {
//                            UnzipEntrance.executorService.notifyAll();
//                        }
                    }
                },1,3, TimeUnit.SECONDS);
                waitTime(2);
                System.out.println("DeCompileEntrance:" + executorService);
            }

            // 每5秒检测一次clazz队列
            waitTime(5);
            if (ThreadConst.dirs.size() == 0 && ThreadConst.clazzs.size() == 0) {
                break;
            }
        }
        executorService.shutdown();
        while (true) {
            if (executorService.isTerminated()) {
                System.out.println("Deal finish.....: " + (System.currentTimeMillis() - start) / 1000);
                break;
            }
            waitTime(5);
        }
    }

    private void waitTime(int time) {
        try {
            Thread.sleep(time * ThreadConst.MILLIS_OF_ONE_SECOND);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
