package pl.pk.pos;

import java.math.BigDecimal;
import java.util.HashMap;

public class TestProductDao implements ProductDao {

    private HashMap<String, Product> db = new HashMap<String, Product>();

    public Product findByBarCode(String barCode) {
        return db.get(barCode);
    }

    public void putProduct(String barCode, String productName, BigDecimal price) {
        db.put(barCode, new Product(productName, price));
    }
}
