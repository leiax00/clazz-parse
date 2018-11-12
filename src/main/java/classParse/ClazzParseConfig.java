package classParse;

import lombok.Data;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Data
public class ClazzParseConfig {
    private static final String PATTERN_TEMPLATE = ".*%s.*";
    private String scanDir;

    private List<String> matchRules;

    private String resultFile;

    private String errorFile;

    private String clazzTemp;

    private boolean patternFlag;

    public void setResultFile(String resultFile) {
        this.resultFile = resultFile;
        if (resultFile == null) {
            this.resultFile = getJarPath() + File.separator + "result.txt";
        }
    }

    public void setErrorFile(String errorFile) {
        this.errorFile = errorFile;
        if (errorFile == null) {
            this.errorFile = getJarPath() + File.separator + "error.txt";
        }
    }

    public void setClazzTemp(String clazzTemp) {
        this.clazzTemp = clazzTemp;
        if (clazzTemp == null) {
            this.clazzTemp = getJarPath() + File.separator + "temp";
        }
    }

    public String getPatternStr() {
        StringBuffer buffer = new StringBuffer();
        for (String rule: matchRules) {
            if (patternFlag) {
                buffer.append(rule).append("|");
            } else {
                if (rule.contains(".")) {
                    rule = rule.replaceAll("\\.", "\\\\.");
                }
                buffer.append(String.format(PATTERN_TEMPLATE, rule)).append("|");
            }
        }
        if (buffer.length() > 0) {
            return buffer.substring(0, buffer.length()-1);
        }
        return "";
    }

    private String getJarPath() {
        String jarPath = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        return new File(jarPath).getParent();
    }

    public static void main(String[] args) {
        ClazzParseConfig config = new ClazzParseConfig();
        config.setMatchRules(Arrays.asList(new String[]{"com.sun", "org.apache"}));
        config.setPatternFlag(true);
        System.out.println(config.getPatternStr());
        boolean r1 = Pattern.matches(config.getPatternStr(), "import com.sun.temp");
        boolean r2 = Pattern.matches(config.getPatternStr(), "import org.apache.temp");
        boolean r3 = Pattern.matches(config.getPatternStr(), "org.apache.temp");
        boolean r4 = Pattern.matches(config.getPatternStr(), "orgaapache");
        System.out.println(r1);
        System.out.println(r2);
        System.out.println(r3);
        System.out.println(r4);
    }
}
