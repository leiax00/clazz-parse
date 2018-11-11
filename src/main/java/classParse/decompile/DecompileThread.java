package classParse.decompile;

import classParse.ThreadConst;

import java.util.NoSuchElementException;

public class DecompileThread implements Runnable {
    private DeCompiler compiler = new DeCompiler();

    @Override
    public void run() {
        while (!ThreadConst.clazzs.isEmpty()) {
            try {
                String clazzPath = ThreadConst.clazzs.remove();
                compiler.deCompile(clazzPath);
            } catch (NoSuchElementException e) {
                System.out.println("ThreadConst.clazzs has bean empty");
            }
        }
    }
}
