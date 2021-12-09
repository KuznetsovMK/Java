package hw2.repository;

import hw2.entity.Product;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ProductRepository {
    List<Product> products = new ArrayList<>();

    public Product save(Product product) {

        products.add(product);
        product.setId(products.size() - 1);
        return Product.builder()
                .id(product.getId())
                .name(product.getName())
                .cost(product.getCost())
                .build();
    }

    public Product edit(Product product) {
        return products.set(product.getId(), product);
    }

    public Optional<Product> findById(int id) {
        if (id < products.size()) {
            return Optional.ofNullable(products.get(id));
        } else {
            return Optional.empty();
        }
    }

    public Iterable<Product> findAll() {
        return new ArrayList<>(products);
    }

    public long count() {
        return products.size();
    }

    public void deleteById(int id) {
        if (id < products.size()) {
            products.remove(id);
        }
    }
}
