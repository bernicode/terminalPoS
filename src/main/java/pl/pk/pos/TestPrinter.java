package pl.pk.pos;

public class TestPrinter implements Printer {
    private String toPrint;

    public void print(String toPrint) {

        this.toPrint = toPrint;
    }

    public String getToPrint() {
        return toPrint;
    }
}
