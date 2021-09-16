package codewars.kyu6;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class VasyaClerk {
    public static void main(String[] args) {
        System.out.println("" +
                "Может ли Вася продать билет каждому и отдать сдачу, если у него изначально " +
                "нет денег и он продает билеты строго в порядке очереди?");
        System.out.println(Tickets(new int[]{25, 50, 25, 100, 25, 50}));
    }

    public static String Tickets(int[] peoplesInLine) {
        int change, cinemaTicketPrice = 25;
        Map<Integer, Integer> map = new HashMap<>();
//        System.out.println("*** ***");
//        System.out.println("Купюры людей в очереди: " + Arrays.toString(peoplesInLine));
//        System.out.println("Стоимость билета: 25 долларов");
//        System.out.println("***");
        int peopleInLine = 1;
        for (int moneyForATicket : peoplesInLine) {
//            System.out.printf("Покупатель №%d заплатил %d долларов. \n", peopleInLine, moneyForATicket);
            change = moneyForATicket - cinemaTicketPrice;
//            if (change > 0) System.out.printf("Выдать сдачу из кассы %d долларов. \n", change);
            while ((change / 50) > 0 && map.containsKey(50) && map.get(50) > 0) {
//                System.out.println("Выдана из кассы одна купюра 50 долларов.");
                map.put(50, map.get(50) - 1);
                change -= 50;
            }
            while ((change / 25) > 0 && map.containsKey(25) && map.get(25) > 0) {
//                System.out.println("Выдана из кассы одна купюра 25 долларов.");
                map.put(25, map.get(25) - 1);
                change -= 25;
            }

            //Недостаточно денег в кассе, чтобы выдать всю сдачу.
            if (change > 0) {
//                System.out.println("Купюры в кассе: " + map);
//                System.out.println("Недостаточно денег в кассе, чтобы выдать сдачу.");
                return "NO";
            }

            if (!map.containsKey(moneyForATicket)) {
                map.put(moneyForATicket, 1);
//                System.out.println("Купюры в кассе: " + map);
            } else {
                map.put(moneyForATicket, map.get(moneyForATicket) + 1);
//                System.out.println("Купюры в кассе: " + map);
            }
        peopleInLine++;
        }
//        System.out.println("Вася может продать билет каждому и отдать сдачу");
        return "YES";
    }
}
