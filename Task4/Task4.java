package Task4;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Task4 {
    public static void main(String[] args) throws IOException {

        List<Integer> nums = new ArrayList<>();

        for (String line : args) {
            nums.add(Integer.parseInt(line));
        }
        MinMoveNumber(nums);

    }

    public static void MinMoveNumber(List<Integer> nums) {
        int sum = 0;
        int count = 0;

        for (int num : nums) {
            sum += num;
        }
        int averageValue = sum / nums.size();

        if (sum % nums.size() > 5) averageValue += 1;

        for (int i = 0; i < nums.size(); i++) {
            while (nums.get(i) != averageValue) {
                if (nums.get(i) < averageValue) nums.set(i, nums.get(i) + 1);
                else nums.set(i, nums.get(i) - 1);
                count++;
            }
        }
        System.out.println(count);
    }

}
