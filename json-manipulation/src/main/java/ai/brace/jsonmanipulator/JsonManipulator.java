package ai.brace.jsonmanipulator;

import com.google.gson.Gson;
import java.io.FileReader;
import java.io.File;
import ai.brace.reader.JsonReader;
import ai.brace.writer.JsonWriter;
import ai.brace.pojo.PojoResource;
import ai.brace.pojo.PojoResource.TextArray;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;
import java.text.SimpleDateFormat;

/**
 * Class with appropriate methods to call for all JsonManipulator tasks.
 */
public class JsonManipulator {
    private JsonReader reader;
    private JsonWriter writer;

    private List<String> fileNames;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    public JsonManipulator(JsonReader reader) {
        this.reader = reader;
        this.writer = new JsonWriter();
    }

    /**
     * Loads PojoResource from JSON.
     */
    public PojoResource loadAndParse(String fileName) {
        return reader.parseJson(fileName);
    }

    public List<PojoResource> loadAndParse() {
        return reader.parseAllJson();
    }

    /**
     * Print out TextArray list textdata values in current order.
     * @param aOne
     */
    private void printTextArrays(List<PojoResource.TextArray> textArrays) {
        for(PojoResource.TextArray textArray : textArrays) {
            System.out.println(textArray.getTextdata());
        }
    }

    public void sortOne() {
        PojoResource aOne = loadAndParse("a1.json");
        sortById(aOne.getTextArray());

        printTextArrays(aOne.getTextArray());
    }

    /**
     * sort TextArray by ID asc, and print out the TextArray.textdata values
     */
    public void sortById(List<PojoResource.TextArray> textArrays) {
        textArrays.sort(new Comparator<PojoResource.TextArray>() {
            @Override
            public int compare(TextArray o1, TextArray o2) {
                return o1.getId() - o2.getId();
            }
        });
    }

    public void mergeAndSort() {
        List<PojoResource.TextArray> textArrays = mergeTextData();
        sortById(textArrays);

        printTextArrays(textArrays);
    }

    /**
     * merges the two TextArray ArrayLists
     */
    public List<PojoResource.TextArray> mergeTextData() {
        PojoResource aOne = loadAndParse("a1.json");
        PojoResource aTwo = loadAndParse("a2.json");

        List<PojoResource.TextArray> textArrays = aOne.getTextArray();
        textArrays.addAll(aTwo.getTextArray());
        return textArrays;
    }

    /**
     * Counts the frequence of each word in the combined TextArray using a HashMap to store the count.
     * Each key is generated based on each token lower-cased with alpha characters removed by regex [^A-Za-z]+
     */
    public void countWordFreq() {
        List<PojoResource.TextArray> textArrays = mergeTextData();
        Map<String, Integer> freqMap = new HashMap<>();

        for(PojoResource.TextArray textArray : textArrays) {
            for(String token : textArray.getTextdata().split(" ")) {
                // remove all non a-Z characters, then set key to lowercase
                String key = token.replaceAll("[^A-Za-z]+", "").toLowerCase();

                Integer count = freqMap.get(key);
                if(count == null) {
                    count = 1;
                } else {
                    count++;
                }

                freqMap.put(key, count);
            }
        }

        printWordFreq(freqMap, "(%s) : %s");
    }

    /**
     * Prints all values in a Map based on the passed in fmt String.
     *
     * @param freqMap
     * @param fmt
     */
    private void printWordFreq(Map<String, Integer> freqMap, String fmt) {
        for(String key : freqMap.keySet()) {
            System.out.println(String.format(fmt, key, freqMap.get(key)));
        }
    }

    /**
     * Sorts all resources by lastModified, oldest first, and the merges the information.  Once the merge is done,
     * and new UUID is generated and the lastModified date is updated from EPOCH to a formatted date.
     *
     */
    public void addativeMerge() {
        List<PojoResource> resources = loadAndParse();
        sortByMostRecent(resources);
        PojoResource merged = resources.get(0);

        for(int i = 1; i < resources.size(); i++) {
            PojoResource next = resources.get(i);
            merged.copy(next);
        }

        // new random UUID
        merged.setUuid(UUID.randomUUID().toString());

        // format date\
        merged.setLastModified(DATE_FORMAT.format(merged.getLastModifiedAsDate()).toString());

        // sort the TextArrays
        sortById(merged.getTextArray());

        writer.write(merged);
    }

    /**
     * Sort resource list by most recent lastModified date.
     *
     * @param resources
     * @return
     */
    private void sortByMostRecent(List<PojoResource> resources) {
        resources.sort(new Comparator<PojoResource>() {
            @Override
            public int compare(PojoResource o1, PojoResource o2) {
                return o1.getLastModifiedAsDate().before(o2.getLastModifiedAsDate()) ? -1 : 1;
            }
        });
    }
}