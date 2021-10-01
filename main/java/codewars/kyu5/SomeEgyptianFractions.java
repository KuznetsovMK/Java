//package codewars.kyu5;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class SomeEgyptianFractions {
//
//    public static String decompose(String nrStr, String drStr) {
//        List<String> list = new ArrayList<>();
//        double res = Double.parseDouble(nrStr) / Double.parseDouble(drStr);
//        double i = 2, j;
//        if (res % 1 == 0 && res != 0) {
//            list.add("" + (int) res);
//            return list.toString();
//        }
//        if (res == 0) return list.toString();
//        while (res > 0.00000000001) {
//            j = 1 / i;
//            if ((res - j) >= 0) {
//                res = res - j;
//                list.add("1/" + (int) i);
//            }
//            i++;
//        }
//        System.out.println(res);
//        return list.toString();
//    }
//
//    public static void main(String[] args) {
//        System.out.println(decompose("21", "23"));
//    }
//}
