package classParse.decompile;

import classParse.ThreadConst;
import lombok.Data;
import org.jd.gui.util.decompiler.ClassFileSourcePrinter;

import java.util.regex.Pattern;

@Data
public class CustomPrinter extends ClassFileSourcePrinter {
    private String pattern_template = ".*%s.*";

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
        if (Pattern.matches(ThreadConst.config.getPatternStr(), s)) {
            ThreadConst.result.add(jar2clazz);
        }
    }
}
