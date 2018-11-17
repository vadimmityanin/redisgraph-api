package app.model;


import javax.persistence.Entity;
import java.io.Serializable;
@Entity
public class Item implements Serializable {

    private String name;
    private String color;

    private Long length;

    public Item(String name,
                String color,
                Long length
    ) {
        this.name = name;
        this.color = color;
        this.length = length;
    }

    public Item() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }


    @Override
    public String toString() {
        return "Item {" +
                "name=" + name +
                ", \ncolor=" + color +
                ", \nlength=" + length +
                '}';
    }
}
