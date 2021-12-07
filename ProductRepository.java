package hw2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope("singleton")
public class ProductRepository {
    private List<Product> productList;

    public List<Product> getProductList() {
        return productList;
    }

    Product getProduct(int id) {
        for (Product product : productList) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    @Autowired
    Environment environment;

    String[] products;

    @PostConstruct
    public void init() {
        products = environment.getProperty("listOfProducts").split(";");
        for (String product : products) {
            String[] str = product.split(",");
            Product prod = new Product(Integer.parseInt(str[0]), str[1], Integer.parseInt(str[2]));
            productList.add(prod);
        }

        System.out.println(" *** Список продуктов *** ");

        for (Product product : productList) {
            System.out.println("#" + product.getId()
            + " " + product.getName()
            + ", Цена: " + product.getCost());
        }
    }

    public ProductRepository() {
        productList = new ArrayList<>();
    }
}
