package tr.firefighters.hobilandgelecegiyazanlar.Hobbie;


public class HobbieClass {

    private String hobbieName;
    private boolean selected;
    private String key;

    public HobbieClass(String hobbieName, boolean selected, String key) {
        this.hobbieName = hobbieName;
        this.selected = selected;
        this.key=key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getHobbieName() {
        return hobbieName;
    }

    public void setHobbieName(String hobbieName) {
        this.hobbieName = hobbieName;
    }

    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
