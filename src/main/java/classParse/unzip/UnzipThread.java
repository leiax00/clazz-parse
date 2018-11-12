package classParse.unzip;

import classParse.ThreadConst;

import java.io.File;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class UnzipThread implements Runnable {
    @Override
    public void run() {
        while (!ThreadConst.dirs.isEmpty()) {
            try {
                String dirPath = ThreadConst.dirs.remove();
                File[] files = new File(dirPath).listFiles();
                fileAssort(files);
            } catch (NoSuchElementException e) {
            }
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
            FileOperateUtil.unZipJar4Queue(file, ThreadConst.config.getClazzTemp());
        }
    }
}
