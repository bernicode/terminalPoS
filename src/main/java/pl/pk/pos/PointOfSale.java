package pl.pk.pos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PointOfSale {
    private final ProductDao productDao;

    private BarCodeScanner scanner;
    private Display display;
    private Printer printer;

    private List<Product> productList = new ArrayList<Product>();

    public PointOfSale(ProductDao productDao, TestBarCodeScanner scanner, Display display, TestPrinter printer) {
        this.productDao = productDao;
        this.scanner = scanner;
        this.display = display;
        this.printer = printer;
    }

    public void scanNextProduct() {
        String barCode = scanner.scan();

        if (barCode == null || barCode.trim().isEmpty()) {
            display.showText("Invalid bar-code");
            return;
        }

        if (barCode.equals("exit")) {
            BigDecimal totalSum = countTotalSum();
            printer.print(getReceipt());
            display.showText(totalSum.toPlainString());
            productList.clear();
            return;
        }

        Product product = productDao.findByBarCode(barCode);
        if (product != null) {
            display.showText(product.getName() + " " + product.getPrice().toPlainString());
            productList.add(product);
        } else {
            display.showText("Product not found");
        }
    }

    private String getReceipt() {
        String out = "";
        for (Product product : productList) {
            out += "" + product.getName() + " " + product.getPrice() + "\n";
        }

        out += "total sum: " + countTotalSum() + "\n";

        return out;
    }

    private BigDecimal countTotalSum() {
        BigDecimal sum = BigDecimal.ZERO;
        for (Product product : productList) {
            sum = sum.add(product.getPrice());
        }
        return sum;
    }
}
