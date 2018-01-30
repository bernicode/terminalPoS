package pl.pk.pos;

public class TestDisplay implements Display {

    private String text;

    public void showText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
