import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class KotlinWriter {
    private BufferedWriter writer;

    public KotlinWriter(String filePath) {
        try {
            // Sobrescreve o arquivo no início da execução
            writer = new BufferedWriter(new FileWriter(filePath, false)); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(String line) {
        try {
            writer.write(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (writer != null) writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
