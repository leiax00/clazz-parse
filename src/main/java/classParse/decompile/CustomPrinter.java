package classParse.decompile;

import classParse.ThreadConst;
import lombok.Data;
import org.jd.gui.util.decompiler.ClassFileSourcePrinter;

import java.util.regex.Pattern;

@Data
public class CustomPrinter extends ClassFileSourcePrinter {

    private String jar2clazz;

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
    }

    @Override
    protected void append(String s) {
        if (Pattern.matches(ThreadConst.REGEX_TARGET, s)) {
            // todo 记录jar名字和class名字
            System.out.println(jar2clazz);
            ThreadConst.result.add(jar2clazz);
        }
    }
}
