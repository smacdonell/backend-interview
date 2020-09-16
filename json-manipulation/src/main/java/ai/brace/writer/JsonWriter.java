package ai.brace.writer;

import ai.brace.pojo.PojoResource;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * For writing the JSON to output.json in the resources directory.
 */
public class JsonWriter {

    private Gson gson;

    // static values to get the appropriate system properties so we can find the resources directory
    private static final String RESOURCE_DIR = System.getProperty("user.dir");
    private static final String FILE_SEPARATOR = System.getProperty("file.separator");
    private static final String PATH = RESOURCE_DIR + FILE_SEPARATOR + "src" + FILE_SEPARATOR
            + "main" + FILE_SEPARATOR + "resources" + FILE_SEPARATOR;

    public JsonWriter() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public void write(PojoResource res) {
        try {
            FileWriter writer = new FileWriter(PATH + "output.json");
            gson.toJson(res, writer);
            writer.close();
        } catch(IOException e) {
            System.err.println(e);
        }
    }
}