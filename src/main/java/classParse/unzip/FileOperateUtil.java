package classParse.unzip;

import classParse.ThreadConst;

import java.io.*;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;

public class FileOperateUtil {

    public static boolean isClazz(File file) {
        return isClazz(file.getPath());
    }

    public static boolean isClazz(String path) {
        return Pattern.matches("(?i).*\\.class$", path);
    }

    public static boolean isJar(File file) {
        return isJar(file.getPath());
    }

    public static boolean isJar(String path) {
        return Pattern.matches("(?i).*\\.jar$", path);
    }

    public static void unZipJar4Queue(File file, String unZipPath) {
        unZipJar(file, unZipPath, true);
    }

    public static void unZipJar(File file, String unZipPath) {
        unZipJar(file, unZipPath, false);
    }

    /**
     * 该方法为解压jar包，并仅保存class到指定目录
     * @param file jar包
     * @param unZipPath 解压路径 (为目录)
     */
    public static void unZipJar(File file, String unZipPath, boolean isAddQueue) {
        File dir = new File(unZipPath + File.separator + file.getName().replaceAll("\\.jar$", ""));
        InputStream ins = null;
        try {
            JarFile jarFile = new JarFile(file);
            for (Enumeration entries = jarFile.entries(); entries.hasMoreElements();) {
                JarEntry jarEntry = (JarEntry) entries.nextElement();
                File clazz = new File(dir, jarEntry.getName());
                if (jarEntry.isDirectory() || !isClazz(clazz)){
                    continue;
                }
                createFile(clazz);
                ins = jarFile.getInputStream(jarEntry);
                write2File(ins, clazz);
                if (isAddQueue) {
                    ThreadConst.clazzs.add(String.format("%s->%s", file.getAbsolutePath(), clazz.getAbsolutePath()));
                }
                ins.close();
            }

        } catch (IOException e) {
            System.out.println("Convert jar file Failure.");
            System.out.println(e);
        } finally {
            close(ins);
        }

    }

    private static void write2File(InputStream ins, File clazz) {
        OutputStream out = null;
        try {
            out = new BufferedOutputStream(new FileOutputStream(clazz));
            byte[] buffer = new byte[1024];
            int nBytes = 0;
            while ((nBytes = ins.read(buffer)) > 0) {
                out.write(buffer, 0, nBytes);
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(out);
        }

    }

    private static void createFile(File file) {
        File folder = file.getParentFile();
        //文件夹路径不存在
        if (!folder.exists()) {
            folder.mkdirs();
        }
        // 如果文件不存在就创建
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        File jarFile = new File("F:\\work_space\\clazz-parse\\lib\\test-unzip.jar");
        System.out.println(jarFile.getPath());
        System.out.println(jarFile.getAbsolutePath());
    }
}
