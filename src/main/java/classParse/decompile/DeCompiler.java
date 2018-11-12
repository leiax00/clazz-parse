package classParse.decompile;

import classParse.ClazzParseConfig;
import classParse.DeCompilerApp;
import classParse.ThreadConst;
import jd.core.loader.Loader;
import jd.core.process.DecompilerImpl;
import org.jd.gui.util.decompiler.GuiPreferences;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class DeCompiler {

    public void deCompile(String srcPath) {
        String[] path = srcPath.split(ThreadConst.CLAZZ_PATH_SEPARATOR);
        DecompilerImpl decompiler = new DecompilerImpl();
        Loader loader = new ClazzLoader(path[1]);
        CustomPrinter printer = new CustomPrinter();
        GuiPreferences preferences = new GuiPreferences();
        try {
            decompiler.decompile(preferences, loader, printer, new File(path[1]).getParent());
        } catch (Exception e) {
//            e.printStackTrace();
            ThreadConst.error.add(srcPath);
        }
        if (printer.isMatchRule()) {
            ThreadConst.result.add(srcPath);
        }
    }

}
