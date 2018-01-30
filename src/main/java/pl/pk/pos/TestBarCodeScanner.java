package pl.pk.pos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestBarCodeScanner implements BarCodeScanner {
    List<String> barCodes;

    public TestBarCodeScanner(String... barCodes) {
        this.barCodes = new ArrayList<String>(Arrays.asList(barCodes));
    }

    public String scan() {
        return barCodes.remove(0);
    }
}
