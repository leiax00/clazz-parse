package classParse.decompile;

import jd.core.loader.Loader;
import jd.core.loader.LoaderException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClazzLoader implements Loader {
    private String filePath;

    @Override
    public DataInputStream load(String s) throws LoaderException {
        try {
            FileInputStream in = new FileInputStream(filePath);
            return new DataInputStream(in);
        } catch (FileNotFoundException e) {
            throw new LoaderException("Fail to load file:" + filePath);
        }
    }

    @Override
    public boolean canLoad(String s) {
        return false;
    }
}
