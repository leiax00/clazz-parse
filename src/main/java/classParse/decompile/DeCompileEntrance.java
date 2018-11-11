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
        int emptyCount = 0;
        while (true) {
            // todo decompile逻辑
            while (!ThreadConst.clazzs.isEmpty()) {
                executorService.execute(new DecompileThread());
                waitTime(1);
                System.out.println("DeCompileEntrance:" + executorService);
            }

            if (ThreadConst.dirs.size() == 0 && ThreadConst.clazzs.size() == 0) {
                waitTime(2);
                emptyCount++;
            }
            if (emptyCount >= 10) {
                break;
            }
        }
        executorService.shutdown();
        while (true) {
            if (executorService.isTerminated()) {
                System.out.println("Deal finish.....: " + (System.currentTimeMillis() - start) / 1000);
                break;
            }
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
