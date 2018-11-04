package classParse.decompile;

import classParse.ThreadConst;
import lombok.Data;
import org.jd.gui.util.decompiler.ClassFileSourcePrinter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;

@Data
public class CustomPrinter extends ClassFileSourcePrinter {

    private StringBuilder builder = new StringBuilder();

    private String jarName;

    @Override
    protected boolean getRealignmentLineNumber() {
        return false;
    }

    @Override
    protected boolean isShowPrefixThis() {
        return false;
    }

    @Override
    protected boolean isUnicodeEscape() {
        return false;
    }

    @Override
    protected void append(char c) {
        builder.append(c);
    }

    @Override
    protected void append(String s) {
        if (Pattern.matches(ThreadConst.REGEX_TARGET, s)) {
            // todo 记录jar名字和class名字
            System.out.println(jarName);
        }
        builder.append(s);
    }

    public void print2File(String srcPath) {
        FileWriter writer = null;
        try {
            File file = new File(srcPath);
            if (!file.exists()) {
                createFile(file);
            }
            writer = new FileWriter(file);
            writer.write(builder.toString());
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
