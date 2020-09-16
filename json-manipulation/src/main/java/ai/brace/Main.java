package ai.brace;

import ai.brace.jsonmanipulator.JsonManipulator;
import ai.brace.reader.JsonReader;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        List<String> resources = new ArrayList<>(Arrays.asList("a1.json", "a2.json"));
        JsonManipulator manipulator = new JsonManipulator(new JsonReader(resources));

        printDivider("Task 1: Load, parse, and sort");
        manipulator.sortOne();

        printDivider("Task 2: Merging data");
        manipulator.mergeAndSort();

        printDivider("Task 3: Simple analysis - a word frequency counter");
        manipulator.countWordFreq();

        printDivider("Task 4: Additive merging of JSON data");
        manipulator.addativeMerge();
        System.out.println("See resources directory for output.json");
    }

    /**
     * Print a divider between different tasks.
     *
     * @param title
     */
    public static void printDivider(String title) {
        System.out.println();
        System.out.println(String.format("========== %s ==========", title));
        System.out.println();
        System.out.println();
    }
}
