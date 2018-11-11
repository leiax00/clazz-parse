package classParse;


import classParse.decompile.DeCompileEntrance;
import classParse.decompile.DeCompiler;
import classParse.print.ResultBuilder;
import classParse.unzip.UnzipEntrance;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class DeCompilerApp {
    public static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);

    /**
     * @param args 传入两个参数： class的path路径，和转成java文件后的保存路径
     */
    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        setEnteringParams(args);
//        ThreadConst.scanPath = "D:\\SDK\\jdk8";
//        ThreadConst.clazzTemp = "F:\\work_space\\clazz-parse\\temp";
        UnzipEntrance unzipEntrance = new UnzipEntrance();
        DeCompileEntrance deCompileEntrance = new DeCompileEntrance();
        executorService.execute(()->unzipEntrance.process());
        executorService.execute(()->deCompileEntrance.process());
        executorService.shutdown();
        while (true) {
            if (executorService.isTerminated()) {
                System.out.println("All execute time: " + (System.currentTimeMillis() - start)/1000);
                break;
            }
        }
        new Thread(new ResultBuilder()).start();
    }

    private static void setEnteringParams(String[] args) {
        switch (args.length) {
            case 0:
                throw new RuntimeException("请给定扫描路径！");
            case 1:
                ThreadConst.scanPath = args[0];
                String parent = getJarPath();
                ThreadConst.clazzTemp = parent + File.separator + "temp";
                ThreadConst.resultPath = parent + File.separator + "result.txt";
                break;
            case 2:
                ThreadConst.scanPath = args[0];
                ThreadConst.resultPath = args[1];
                ThreadConst.clazzTemp = getJarPath() + File.separator + "temp";
            case 3:
                ThreadConst.scanPath = args[0];
                ThreadConst.resultPath = args[1];
                ThreadConst.clazzTemp = args[2];
            default:
                throw new RuntimeException("错误的入参数量！");
        }
    }

    private static String getJarPath() {
        String jarPath = DeCompilerApp.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        return new File(jarPath).getParent();
    }

}
