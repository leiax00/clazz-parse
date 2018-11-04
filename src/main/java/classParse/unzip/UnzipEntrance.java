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
        if (!"".equals(ThreadConst.scanPath)) {
            ThreadConst.dirs.add(ThreadConst.scanPath);
        }
        consumeDirs();

    }

    private void consumeDirs() {
        long start = System.currentTimeMillis();
        while (!ThreadConst.dirs.isEmpty()) {
            executorService.scheduleAtFixedRate(() -> {
                try {
                    String dirPath = ThreadConst.dirs.remove();
                    File[] files = new File(dirPath).listFiles();
                    fileAssort(files);
                } catch (NoSuchElementException e) {
                    System.out.println(ThreadConst.clazzs.size());
                    System.out.println("ThreadConst.dirs has bean empty");
                    ;
                }
            }, ThreadConst.THREAD_START_DELAY, ThreadConst.THREAD_START_CYCLE, TimeUnit.SECONDS);
            this.waitTime(ThreadConst.CHECK_CYCLE);
            System.out.println("UnzipEntrance:" + executorService);
        }
        executorService.shutdown();
        while (true) {
            if (executorService.isTerminated()) {
                System.out.println(ThreadConst.clazzs.size());
                System.out.println("unzip total time: " + (System.currentTimeMillis() - start) / 1000);
                break;
            }
            System.out.println(executorService.toString());
            this.waitTime(ThreadConst.CHECK_CYCLE);
        }
    }


    private void fileAssort(File[] files) {
        Arrays.asList(files).forEach(file -> {
            if (file.isDirectory()) {
                ThreadConst.dirs.add(file.getPath());
            } else {
                dealFile(file);
            }
        });
    }

    /**
     * 对于class,直接添加到clazz队列中，等待反编译进程进行解析判断
     * 对于jar包，解压到临时目录，临时目录中clazz路径添加到clazz队列中，再通过反编译进程进行解析判断
     *
     * @param file 扫描路径下的文件
     */
    private void dealFile(File file) {
        if (FileOperateUtil.isClazz(file)) {
            ThreadConst.clazzs.add(file.getAbsolutePath());
        } else if (FileOperateUtil.isJar(file)) {
            FileOperateUtil.unZipJar4Queue(file, ThreadConst.clazzTemp);
        }
    }

    private void waitTime(int time) {
        try {
            Thread.sleep(time * ThreadConst.MILLIS_OF_ONE_SECOND);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ThreadConst.clazzTemp = "F:\\work_space\\clazz-parse\\temp";
        ThreadConst.scanPath = "F:\\work_space\\clazz-parse\\lib";
        UnzipEntrance entrance = new UnzipEntrance();
        entrance.process();
    }
}
