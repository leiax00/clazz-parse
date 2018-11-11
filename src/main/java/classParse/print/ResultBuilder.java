package classParse.print;

import classParse.ThreadConst;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static classParse.ThreadConst.resultPath;

public class ResultBuilder implements Runnable {
    private Map<String, HashSet<String>> resultMap = new HashMap<>();

    @Override
    public void run() {
        while (!ThreadConst.result.isEmpty()) {
            String entity = ThreadConst.result.remove();
            String[] jar2clazz = entity.split(ThreadConst.CLAZZ_PATH_SEPARATOR);
            HashSet<String> clazzSet = resultMap.get(jar2clazz[0]);
            if (clazzSet == null) {
                clazzSet = new HashSet<>();
            }
            clazzSet.add(jar2clazz[1]);
            resultMap.put(jar2clazz[0], clazzSet);
        }
        print2File(resultPath);
    }

    public void print2File(String srcPath) {
        FileWriter writer = null;
        try {
            File file = new File(srcPath);
            if (!file.exists()) {
                createFile(file);
            }
            writer = new FileWriter(file);
            for (Map.Entry<String, HashSet<String>> entry : resultMap.entrySet()) {
                writer.write("\r\n=======================  ");
                writer.write(entry.getKey());
                writer.write("  =======================\r\n");
                for (String value : entry.getValue()) {
                    writer.write("    ");
                    writer.write(value);
                    writer.write("    \r\n");
                }
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer == null) writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void createFile(File file) throws IOException {

        File folder = file.getParentFile();
        //文件夹路径不存在
        if (!folder.exists()) {
            boolean mkdirs = folder.mkdirs();
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
}
