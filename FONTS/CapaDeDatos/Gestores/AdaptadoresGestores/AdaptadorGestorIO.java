package FONTS.CapaDeDatos.Gestores.AdaptadoresGestores;

import java.io.IOException;
import java.util.Vector;

public interface AdaptadorGestorIO {

    Vector<String[]> readFile(String path) throws IOException;

    void writeFile(String[] header, String path, Vector<String[]> data) throws IOException;

    boolean existsFile(String path);
}
