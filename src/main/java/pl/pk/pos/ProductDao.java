package pl.pk.pos;

public interface ProductDao {
    Product findByBarCode(String barCode);
}
