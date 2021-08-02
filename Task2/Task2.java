package Task2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Task2 {
    public static void main(String[] args) throws IOException {
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();

        Path file1 = Paths.get("Task2/File1.txt");
        Path file2 = Paths.get("Task2/File2.txt");

        if (args.length == 3) {
            list1.addAll(Arrays.asList(args));
        } else list2.addAll(Arrays.asList(args));

        for (String str : list1) {
            Files.writeString(Paths.get(String.valueOf(file1)), str);
        }

        for (String str : list2) {
            Files.writeString(Paths.get(String.valueOf(file2)), str);
        }

        findingСoordinates(file1, file2);

    }

    public static void findingСoordinates(Path file1, Path file2) throws IOException {
        List<String> list1 = new ArrayList<>();
        for (String line : Files.readAllLines(file1)) {
            list1.addAll(Arrays.asList(line.split(" ")));
        }

        int r = Integer.parseInt(list1.get(2));

        List<String> list2 = new ArrayList<>();
        for (String line : Files.readAllLines(file2)) {
            float x = Float.parseFloat(list1.get(0));
            float y = Float.parseFloat(list1.get(1));

            list2.addAll(Arrays.asList(line.split(" ")));
            System.out.print(list2);

            x = Float.parseFloat(list2.get(0)) - x;
            y = Float.parseFloat(list2.get(1)) - y;

            double sqrt = Math.sqrt(x * x + y * y);
            if (sqrt < r) System.out.println(" - " + 1);
            if (sqrt > r) System.out.println(" - " + 2);
            if (sqrt == r) System.out.println(" - " + 0);
            list2.clear();
        }
    }
}
