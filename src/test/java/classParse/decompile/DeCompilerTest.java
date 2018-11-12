package classParse.decompile;

import classParse.ClazzParseConfig;
import classParse.DeCompilerApp;
import classParse.ThreadConst;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.regex.Pattern;


public class DeCompilerTest {

    String pattern = "[\\s\\S]*com\\.sun[\\s\\S]*|[\\s\\S]*org\\.apache[\\s\\S]*";
    @Test
    public void test_deCompile1() {
        System.out.println(pattern);
        String x = pattern;
        System.out.println(x);
        System.out.println(Pattern.matches(x, str));
    }

    @Test
    public void test_deCompile() {
        String jarPath = DeCompilerTest.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        System.out.println(new File(jarPath).getParent());
        File file = new File(new File(jarPath).getParent() + "/test-classes", "clazz-parse.yml");
        try {
            ThreadConst.config = new Yaml().loadAs(new FileInputStream(file), ClazzParseConfig.class);
            System.out.println("ThreadConst.config:" + ThreadConst.config);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("找不到配置文件！");
        }
        new DeCompiler().deCompile("a->C:\\Users\\Administrator\\Desktop\\XYPainter.class");
    }

    private String str = "package com.sun.tools.visualvm.charts.xy;\n" +
            "\n" +
            "import java.awt.Color;\n" +
            "import java.awt.Component;\n" +
            "import java.awt.Graphics;\n" +
            "import javax.swing.Icon;\n" +
            "\n" +
            "final class ColorIcon\n" +
            "  implements Icon\n" +
            "{\n" +
            "  private Color borderColor = Color.BLACK;\n" +
            "  private Color color = Color.BLACK;\n" +
            "  private int height = 5;\n" +
            "  private int width = 5;\n" +
            "  \n" +
            "  public ColorIcon() {}\n" +
            "  \n" +
            "  public ColorIcon(Color color)\n" +
            "  {\n" +
            "    this();\n" +
            "    setColor(color);\n" +
            "  }\n" +
            "  \n" +
            "  public ColorIcon(Color color, int width, int height) {\n" +
            "    this(color);\n" +
            "    setIconWidth(width);\n" +
            "    setIconHeight(height);\n" +
            "  }\n" +
            "  \n" +
            "  public ColorIcon(Color color, Color borderColor, int width, int height) {\n" +
            "    this(color, width, height);\n" +
            "    setBorderColor(borderColor);\n" +
            "  }\n" +
            "  \n" +
            "  public void setBorderColor(Color borderColor)\n" +
            "  {\n" +
            "    this.borderColor = borderColor;\n" +
            "  }\n" +
            "  \n" +
            "  public Color getBorderColor() {\n" +
            "    return borderColor;\n" +
            "  }\n" +
            "  \n" +
            "  public void setColor(Color color) {\n" +
            "    this.color = color;\n" +
            "  }\n" +
            "  \n" +
            "  public Color getColor() {\n" +
            "    return color;\n" +
            "  }\n" +
            "  \n" +
            "  public void setIconHeight(int height) {\n" +
            "    this.height = height;\n" +
            "  }\n" +
            "  \n" +
            "  public int getIconHeight() {\n" +
            "    return height;\n" +
            "  }\n" +
            "  \n" +
            "  public void setIconWidth(int width) {\n" +
            "    this.width = width;\n" +
            "  }\n" +
            "  \n" +
            "  public int getIconWidth() {\n" +
            "    return width;\n" +
            "  }\n" +
            "  \n" +
            "  public void paintIcon(Component c, Graphics g, int x, int y) {\n" +
            "    if (color != null) {\n" +
            "      g.setColor(color);\n" +
            "      g.fillRect(x, y, width, height);\n" +
            "    }\n" +
            "    \n" +
            "    if (borderColor != null) {\n" +
            "      g.setColor(borderColor);\n" +
            "      g.drawRect(x, y, width - 1, height - 1);\n" +
            "    }\n" +
            "  }\n" +
            "}\n";

}
