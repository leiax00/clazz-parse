package classParse;


import classParse.decompile.DeCompileEntrance;
import classParse.decompile.DeCompiler;
import classParse.print.ResultBuilder;
import classParse.unzip.UnzipEntrance;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static classParse.ThreadConst.config;
import static classParse.ThreadConst.error;
import static classParse.ThreadConst.result;

public class DeCompilerApp {
    public static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);

    /**
     * @param args 传入两个参数： class的path路径，和转成java文件后的保存路径
     */
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        setEnteringParams();
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
        new Thread(new ResultBuilder(result, config.getResultFile())).start();
        new Thread(new ResultBuilder(error, config.getErrorFile())).start();
    }

    private static void setEnteringParams() {
        File file = new File(getJarPath(), "clazz-parse.yml");
        try {
            ThreadConst.config = new Yaml().loadAs(new FileInputStream(file), ClazzParseConfig.class);
            System.out.println("ThreadConst.config:" + ThreadConst.config);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("找不到配置文件！");
        }
    }

    private static String getJarPath() {
        String jarPath = DeCompilerApp.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        return new File(jarPath).getParent();
    }
}
