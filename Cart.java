package hw2;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;


@Component
@Scope("prototype")
public class Cart implements MessageProvider {

    String value;
    File file;
    String msg;

    private Map<Integer, Integer> cart;
    ProductRepository productRepository;

    public Cart(ProductRepository productRepository) throws IOException {
        cart = new HashMap<>();
        this.productRepository = productRepository;
        if (!Files.exists(Path.of("src/main/java/hw2/cart.txt"))) {
            Files.createFile(Path.of("src/main/java/hw2/cart.txt"));
        }
        file = new File("src/main/java/hw2/cart.txt");
    }

    @PostConstruct
    public void init() throws IOException {

        readCart(file);

        System.out.print("Корзина: добавить(+) или удалить(-) товар по ID:");
        Scanner scanner = new Scanner(System.in);
        value = scanner.next();
        if (value.startsWith("+")) {
            add(Integer.parseInt(value.substring(1)));
        }
        if (value.startsWith("-")) {
            remove(Integer.parseInt(value.substring(1)));
        }
        if (value.equals("0")) {
            cart.clear();
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<Integer, Integer> o : cart.entrySet()) {
            stringBuilder.append("Продукт: " + productRepository.getProduct(o.getKey()).getName()
                    + " Цена: " + productRepository.getProduct(o.getKey()).getCost()
                    + " Количество: " + o.getValue()
                    + " Сумма: " + o.getValue() * productRepository.getProduct(o.getKey()).getCost()
                    + "\n");
        }

        writeCart(file);
        msg = stringBuilder.toString();
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("PreDestroy");
    }

    @Override
    public String getMessage() {
        return msg;
    }

    void add(int id) {
        cart.put(id, cart.containsKey(id) ? cart.get(id) + 1 : 1);
    }

    void remove(int id) {
        if (cart.containsKey(id)) {
            if (cart.get(id) > 1) {
                cart.put(id, cart.get(id) - 1);
            } else {
                cart.remove(id);
            }
        }
    }

    void readCart(File file) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        String str = "";
        while ((str = bufferedReader.readLine()) != null) {
            String[] str1 = str.split(" ");
            cart.put(Integer.parseInt(str1[0]), Integer.parseInt(str1[1]));
        }
        bufferedReader.close();
    }

    void writeCart(File file) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        for (Map.Entry<Integer, Integer> o : cart.entrySet()) {
            bufferedWriter.write(o.getKey() + " " + o.getValue() + "\n");
            bufferedWriter.flush();
        }
        bufferedWriter.close();
    }
}
