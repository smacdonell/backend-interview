package ai.brace.reader;

import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import com.google.gson.Gson;
import ai.brace.pojo.PojoResource;
import java.util.List;
import java.util.ArrayList;

/**
 * Used to load and read resources from the RESOURCE_DIR.
 */
public class JsonReader {

    // single resource
    private File resource;

    // multiple resources
    private List<File> resources;

    private Gson gson;

    // static values to get the appropriate system properties so we can find the resources directory
    private static final String RESOURCE_DIR = System.getProperty("user.dir");
    private static final String FILE_SEPARATOR = System.getProperty("file.separator");
    private static final String PATH = RESOURCE_DIR + FILE_SEPARATOR + "src" + FILE_SEPARATOR
            + "main" + FILE_SEPARATOR + "resources" + FILE_SEPARATOR;

    public JsonReader() {
        this.gson = new Gson();
    }

    public JsonReader(String fileName) {
        this();

        resource = new File(PATH + fileName);
    }

    public JsonReader(List<String> fileNames) {
        this();

        this.resources = new ArrayList<>();

        for(String fileName : fileNames) {
            resources.add(new File(PATH + fileName));
        }
    }

    public PojoResource parseJson(File fileToParse) {

        try {
            return this.gson.fromJson(new FileReader(fileToParse), PojoResource.class);
        } catch(FileNotFoundException e) {
            System.err.println(e);
        }

        return null;
    }

    public List<PojoResource> parseAllJson() {
        List<PojoResource> resourcesList = new ArrayList<>();

        for(File res : resources) {
            try {
                resourcesList.add(this.gson.fromJson(new FileReader(res), PojoResource.class));
            } catch(FileNotFoundException e) {
                System.err.println(e);
            }
        }

        return resourcesList;
    }

    public PojoResource parseDefaultJson() {
        return parseJson(this.resource);
    }

    public PojoResource parseJson(String fileName) {
        File file = new File(PATH + fileName);

        return parseJson(file);
    }

    /*public void loadAll() {
        for(File f : resources) {
            try {
                Object o = this.gson.fromJson(new FileReader(f), Object.class);
                for(o) {

                }
            } catch(FileNotFoundException e) {
                System.err.println(e);
            }
        }
    }*//*public void loadAll() {
        for(File f : resources) {
            try {
                Object o = this.gson.fromJson(new FileReader(f), Object.class);
                for(o) {

                }
            } catch(FileNotFoundException e) {
                System.err.println(e);
            }
        }
    }*/
}