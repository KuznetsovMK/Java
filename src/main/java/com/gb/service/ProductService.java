package com.gb.service;

import com.gb.entity.Cat;
import com.gb.entity.Product;

import java.util.Arrays;
import java.util.List;

public class ProductService {
    public List<Product> createNewProduct() {
        return Arrays.asList(
                new Product(1, "Ноутбук IRBIS NB NB69, NB69, серебристый", 11990),
                new Product(2, "Ноутбук Digma EVE 10 A201, ES1053EW, черный", 12990),
                new Product(3, "Ноутбук IRBIS NB NB104, NB104, черный", 13490),
                new Product(4, "Ноутбук IRBIS NB NB550, NB550, синий", 13990),
                new Product(5, "Ноутбук IRBIS NB NB244, NB244, серебристый", 14490),
                new Product(6, "Ноутбук ASUS L210MA-GJ206T, 90NB0R41-M09030, синий", 20690),
                new Product(7, "Ноутбук Acer Aspire 3 A314-35-C60A, NX.A7SER.001, серебристый", 23190),
                new Product(8, "Ноутбук Apple MacBook Pro M1, Z11F00031, серебристый", 202790),
                new Product(9, "Ноутбук Dell Precision 7560, 7560-7319, серый", 377700),
                new Product(10, "Ноутбук-трансформер Acer ConceptD 7 Ezel CC715-91P-X7V8, NX.C5FER.001, белый", 508160)
                );
    }
}
