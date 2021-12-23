package Task2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Task2 {
    public static void main(String[] args) throws IOException {

        List<String> temp = new ArrayList<>();
        List<String> temp1 = new ArrayList<>();
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();

        int m = 0;
        while (m < 2) {
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();

            for (String s : line.split(" ")) {
                temp.add(s);
            }
            m++;
//            System.out.println(temp);
        }

        for (String filename : temp) {
            temp1.clear();

            File file = new File(String.valueOf(Paths.get(filename)));
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();

            while (line != null) {
//                System.out.println(line);

                for (String s : line.split(" ")) {
                    temp1.add(s);
                }
                line = br.readLine();
            }
            if (temp1.size() == 3) {
                for (String s : temp1) {
                    list1.add(s);
                }
            } else
                for (String s : temp1) {
                    list2.add(s);
                }
//            System.out.println(list1);
//            System.out.println(list2);
            br.close();
        }
            findingСoordinates(list1, list2);

    }

    public static void findingСoordinates(List<String> list1, List<String> list2) {

        if (list1 == null || list2 == null) {
            System.out.println("Пустой лист");
            return;
        }

        int r = Integer.parseInt(list1.get(2));

        int i = 0;
        while (i < list2.size()) {
            float x = Float.parseFloat(list1.get(0));
            float y = Float.parseFloat(list1.get(1));
            x = Float.parseFloat(list2.get(i)) - x;
            y = Float.parseFloat(list2.get(i + 1)) - y;

            System.out.print("[" + list2.get(i) + ", " + list2.get(i + 1) + "]");

            double sqrt = Math.sqrt(x * x + y * y);
            if (sqrt < r) System.out.println(" - " + 1);
            if (sqrt > r) System.out.println(" - " + 2);
            if (sqrt == r) System.out.println(" - " + 0);
            i += 2;
        }
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
