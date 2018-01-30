package pl.pk.pos;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class PointOfSaleTest {

    @Test
    public void shouldDisplayProductNameAndPriceWhenScannedBarCodeFoundInProductDataBase() {
        // given
        String barCode = "123";
        TestBarCodeScanner scanner = new TestBarCodeScanner(barCode, "exit");
        TestDisplay display = new TestDisplay();
        TestPrinter printer = new TestPrinter();
        TestProductDao productDao = new TestProductDao();
        productDao.putProduct(barCode, "woda", new BigDecimal("111"));
        PointOfSale pointOfSale = new PointOfSale(productDao, scanner, display, printer);

        // when
        pointOfSale.scanNextProduct();

        // then
        Assert.assertTrue(display.getText().contains("woda"));
        Assert.assertTrue(display.getText().contains("111"));
    }

    @Test
    public void shouldHandleProductNotFound() {
        // given
        String barCode = "123";
        TestBarCodeScanner scanner = new TestBarCodeScanner(barCode, "exit");
        TestDisplay display = new TestDisplay();
        TestPrinter printer = new TestPrinter();
        TestProductDao productDao = new TestProductDao();
        PointOfSale pointOfSale = new PointOfSale(productDao, scanner, display, printer);

        // when
        pointOfSale.scanNextProduct();

        // then
        Assert.assertTrue(display.getText().contains("Product not found"));
    }

    @Test
    public void shouldHandleInvalidBarCode() {
        // given
        String barCode = "";
        TestBarCodeScanner scanner = new TestBarCodeScanner(barCode, "exit");
        TestDisplay display = new TestDisplay();
        TestPrinter printer = new TestPrinter();
        TestProductDao productDao = new TestProductDao();
        PointOfSale pointOfSale = new PointOfSale(productDao, scanner, display, printer);

        // when
        pointOfSale.scanNextProduct();

        // then
        Assert.assertTrue(display.getText().contains("Invalid bar-code"));
    }

    @Test
    public void onExitShouldDisplayTotalPrice() {
        // given
        String barCode1 = "1";
        String barCode2 = "2";
        TestBarCodeScanner scanner = new TestBarCodeScanner(barCode1, barCode2, "exit");
        TestDisplay display = new TestDisplay();
        TestPrinter printer = new TestPrinter();
        TestProductDao productDao = new TestProductDao();
        productDao.putProduct(barCode1, "woda", new BigDecimal("11"));
        productDao.putProduct(barCode2, "mleko", new BigDecimal("22"));
        PointOfSale pointOfSale = new PointOfSale(productDao, scanner, display, printer);

        // when
        pointOfSale.scanNextProduct();
        pointOfSale.scanNextProduct();
        pointOfSale.scanNextProduct();

        // then
        Assert.assertTrue(display.getText().contains("33"));
    }

    @Test
    public void onExitShouldPrintReceipt() {
        // given
        String barCode1 = "1";
        String barCode2 = "2";
        TestBarCodeScanner scanner = new TestBarCodeScanner(barCode1, barCode2, "exit");
        TestDisplay display = new TestDisplay();
        TestPrinter printer = new TestPrinter();
        TestProductDao productDao = new TestProductDao();
        productDao.putProduct(barCode1, "woda", new BigDecimal("11"));
        productDao.putProduct(barCode2, "mleko", new BigDecimal("22"));
        PointOfSale pointOfSale = new PointOfSale(productDao, scanner, display, printer);

        // when
        pointOfSale.scanNextProduct();
        pointOfSale.scanNextProduct();
        pointOfSale.scanNextProduct();

        // then
        Assert.assertTrue(printer.getToPrint().contains("woda"));
        Assert.assertTrue(printer.getToPrint().contains("mleko"));
        // check for total sum
        Assert.assertTrue(printer.getToPrint().contains("33"));
    }
}