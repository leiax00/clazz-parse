package classParse.decompile;

import classParse.ThreadConst;
import jd.core.loader.Loader;
import jd.core.process.DecompilerImpl;
import org.jd.gui.util.decompiler.GuiPreferences;

import java.io.File;

public class DeCompiler {

    public void deCompile(String srcPath) {
        String[] path = srcPath.split(ThreadConst.CLAZZ_PATH_SEPARATOR);
        DecompilerImpl decompiler = new DecompilerImpl();
        Loader loader = new ClazzLoader(path[1]);
        CustomPrinter printer = new CustomPrinter();
        printer.setJar2clazz(srcPath);
        GuiPreferences preferences = new GuiPreferences();
        try {
            decompiler.decompile(preferences, loader, printer, new File(path[1]).getParent());
        } catch (Exception e) {
            e.printStackTrace();
            ThreadConst.error.add(srcPath);
        }
    }

    public static void main(String[] args) {
        String b = "F:\\work_space\\clazz-parse\\temp";
        String c = "F:\\work_space\\clazz-parse\\temp\\src";
        String a = "F:\\work_space\\clazz-parse\\temp\\test-unzip\\classParse\\DeCompilerApp.class";
        System.out.println(c + a.substring(b.length()));
    }
}
