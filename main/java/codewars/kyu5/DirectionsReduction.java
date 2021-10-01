package codewars.kyu5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DirectionsReduction {
    public static void main(String[] args) {
        String[] arr = new String[]{"NORTH", "EAST", "NORTH", "EAST", "WEST", "WEST", "EAST", "EAST", "WEST", "SOUTH"};
        Arrays.toString(dirReduc(arr));
    }

    public static String[] dirReduc(String[] arr) {
        List<String> list = new ArrayList<>(Arrays.asList(arr));
        int cnt = 1;
        while (cnt != 0) {
            cnt = 0;
            for (int i = 0; i < list.size() - 1; i++) {
                if (list.get(i).equals("NORTH")) {
                    if (list.get(i + 1).equals("SOUTH")) {
                        list.remove(i + 1);
                        list.remove(i);
                        cnt++;
                        break;
                    }
                }
                if (list.get(i).equals("SOUTH")) {
                    if (list.get(i + 1).equals("NORTH")) {
                        list.remove(i + 1);
                        list.remove(i);
                        cnt++;
                        break;
                    }
                }
                if (list.get(i).equals("EAST")) {
                    if (list.get(i + 1).equals("WEST")) {
                        list.remove(i + 1);
                        list.remove(i);
                        cnt++;
                        break;
                    }
                }
                if (list.get(i).equals("WEST")) {
                    if (list.get(i + 1).equals("EAST")) {
                        list.remove(i + 1);
                        list.remove(i);
                        cnt++;
                        break;
                    }
                }
            }
            System.out.println(list.toString());
        }
        String[] strings = new String[list.size()];

        for (int i = 0; i < list.size(); i++) {
            strings[i] = list.get(i);
        }


        return strings;

    }
}
