package model;

/**
 *
 */
public class Appoint {
    Integer id;
    String name;
    String socr;

    public Appoint() {
        this.id = null;
        this.name = "Космонавт";
        this.socr = "косм.";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSocr() {
        return socr;
    }

    public void setSocr(String socr) {
        this.socr = socr;
    }

    @Override
    public String toString() {
        return socr;
    }
}
