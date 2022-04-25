package FONTS.CapaDeDatos.Gestores;

import FONTS.CapaDeDatos.Gestores.AdaptadoresGestores.AdaptadorGestorIO;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import static com.opencsv.ICSVWriter.NO_QUOTE_CHARACTER;

public class GestorIO implements AdaptadorGestorIO {

    public GestorIO() {}

    public Vector<String[]> readFile(String path) throws IOException {
        boolean first = true;
        Vector<String[]> res = new Vector<>();
        try {
            CSVReader csvReader = new CSVReader(new FileReader(path));
            String[] nextRow;
            while ((nextRow = csvReader.readNext()) != null) {
                if (first) {
                    first = false;
                }
                res.add(nextRow);
            }
        }
        catch (CsvValidationException e) {
            e.printStackTrace();
        }
        return res;
    }

    public void writeFile(String[] header, String path, Vector<String[]> data) {
        try {
            CSVWriterBuilder builder = new CSVWriterBuilder(new FileWriter(path));
            ICSVWriter writer = builder.withQuoteChar(NO_QUOTE_CHARACTER).build();
            if (header != null) writer.writeNext(header);
            for (String[] row : data) writer.writeNext(row);

            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean existsFile(String path) {
        File file = new File(path);
        return file.exists();
    }
}
