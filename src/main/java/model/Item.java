package model;

import java.io.Serializable;

public class Item implements Serializable {

    private String stringProperty1;
    private String stringProperty2;
    private String stringProperty3;
    private String stringProperty4;
    private String stringProperty5;

    private Long longProperty1;
    private Long longProperty2;
    private Long longProperty3;

    public Item(String stringProperty1,
                String stringProperty2,
                String stringProperty3,
                String stringProperty4,
                String stringProperty5,
                Long longProperty1,
                Long longProperty2,
                Long longProperty3) {
        this.stringProperty1 = stringProperty1;
        this.stringProperty2 = stringProperty2;
        this.stringProperty3 = stringProperty3;
        this.stringProperty4 = stringProperty4;
        this.stringProperty5 = stringProperty5;
        this.longProperty1 = longProperty1;
        this.longProperty2 = longProperty2;
        this.longProperty3 = longProperty3;
    }

    public Item() {
    }

    public String getStringProperty1() {
        return stringProperty1;
    }

    public void setStringProperty1(String stringProperty1) {
        this.stringProperty1 = stringProperty1;
    }

    public String getStringProperty2() {
        return stringProperty2;
    }

    public void setStringProperty2(String stringProperty2) {
        this.stringProperty2 = stringProperty2;
    }

    public String getStringProperty3() {
        return stringProperty3;
    }

    public void setStringProperty3(String stringProperty3) {
        this.stringProperty3 = stringProperty3;
    }

    public String getStringProperty4() {
        return stringProperty4;
    }

    public void setStringProperty4(String stringProperty4) {
        this.stringProperty4 = stringProperty4;
    }

    public String getStringProperty5() {
        return stringProperty5;
    }

    public void setStringProperty5(String stringProperty5) {
        this.stringProperty5 = stringProperty5;
    }

    public Long getLongProperty1() {
        return longProperty1;
    }

    public void setLongProperty1(Long longProperty1) {
        this.longProperty1 = longProperty1;
    }

    public Long getLongProperty2() {
        return longProperty2;
    }

    public void setLongProperty2(Long longProperty2) {
        this.longProperty2 = longProperty2;
    }

    public Long getLongProperty3() {
        return longProperty3;
    }

    public void setLongProperty3(Long longProperty3) {
        this.longProperty3 = longProperty3;
    }

    @Override
    public String toString() {
        return "Item {" +
                "stringProperty1=" + stringProperty1 +
                ", \nstringProperty2=" + stringProperty2 +
                ", \nstringProperty3=" + stringProperty3 +
                ", \nstringProperty4=" + stringProperty4 +
                ", \nstringProperty5=" + stringProperty5 +
                ", \nlongProperty1=" + longProperty1 +
                ", \nlongProperty2=" + longProperty2 +
                ", \nlongProperty3=" + longProperty3 +
                '}';
    }
}
