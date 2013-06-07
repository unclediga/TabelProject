package model;

import java.util.ArrayList;

/**
 *
 */
public enum WageRateType {
    HOURS(1, "часы","ч."),
    RATE(2, "ставка","ст.");
    private final int id;
    private final String name;
    private final String socr;
    WageRateType(int id, String name, String socr) {
        this.id = id;
        this.name = name;
        this.socr = socr;
    }

    public String toString() {
        return socr;
    }

    public String getName() {
        return name;
    }

    public String getSocr() {
        return socr;
    }

    public static WageRateType getById(int id) {
        switch (id) {
            case 1:
                return HOURS;
            case 2:
                return RATE;
        }
        return null;
    }

    public int getId() {
        return id;
    }

}
