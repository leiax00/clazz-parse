package classParse.unzip;

import classParse.ThreadConst;
import lombok.Data;

import java.io.File;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Data
public class UnzipEntrance {
    public static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(ThreadConst.CORE_THREAD_SIZE);

    public void process() {
        String scanDir = ThreadConst.config.getScanDir();
        if (!"".equals(scanDir)) {
            ThreadConst.dirs.add(scanDir);
        }
        consumeDirs();

    }

    private void consumeDirs() {
        long start = System.currentTimeMillis();
        int emptyCount = 0;
        while (true) {
            System.out.println(ThreadConst.dirs.size());
            System.out.println(executorService.toString());

            if (!ThreadConst.dirs.isEmpty()) {
                executorService.execute(new UnzipThread());
                this.waitTime(1);
            } else {
                if (emptyCount >= 100) {
                    break;
                }
                emptyCount++;
                this.waitTime(1);
            }
        }

        executorService.shutdown();
        while (true) {
            if (executorService.isTerminated()) {
                System.out.println(ThreadConst.clazzs.size());
                System.out.println("unzip total time: " + (System.currentTimeMillis() - start) / 1000);
                break;
            }
            System.out.println(executorService.toString());
            this.waitTime(1);
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
