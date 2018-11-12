package classParse.decompile;

import classParse.ThreadConst;
import lombok.Data;
import org.jd.gui.util.decompiler.ClassFileSourcePrinter;

import java.util.regex.Pattern;

@Data
public class CustomPrinter extends ClassFileSourcePrinter {
    private StringBuffer buffer = new StringBuffer();

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
        buffer.append(c);
    }

    @Override
    protected void append(String s) {
        buffer.append(s);
    }

    protected boolean isMatchRule() {
        return Pattern.matches(ThreadConst.config.getPatternStr(), buffer.toString());
    }
}
