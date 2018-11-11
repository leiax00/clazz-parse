package classParse;


import classParse.decompile.DeCompileEntrance;
import classParse.decompile.DeCompiler;
import classParse.unzip.UnzipEntrance;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class DeCompilerApp {
    public static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);

    /**
     * @param args 传入两个参数： class的path路径，和转成java文件后的保存路径
     */
    public static void main(String[] args) throws InterruptedException {
//        ThreadConst.scanPath = args[0];
//        ThreadConst.clazzTemp = args[1];
        long start = System.currentTimeMillis();
        ThreadConst.scanPath = "D:\\SDK\\jdk8";
        ThreadConst.clazzTemp = "F:\\work_space\\clazz-parse\\temp";
        ThreadConst.srcTemp = "F:\\work_space\\clazz-parse\\temp\\src";
        UnzipEntrance unzipEntrance = new UnzipEntrance();
        DeCompileEntrance deCompileEntrance = new DeCompileEntrance();
        executorService.execute(()->unzipEntrance.process());
        executorService.execute(()->deCompileEntrance.process());
        executorService.shutdown();
        while (true) {
            if (executorService.isTerminated()) {
                System.out.println("All execute time: " + (System.currentTimeMillis() - start));
                break;
            }
        }

    }

}
