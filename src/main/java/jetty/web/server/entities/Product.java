package jetty.web.server.entities;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
@Data
public class Product {
    int id;
    @NotNull
    String name;
    int manufacturer_id;
    int quantity;

    public Product(@NotNull String name, int manufacturer_id, int quantity){
        this.name = name;
        this.manufacturer_id = manufacturer_id;
        this.quantity = quantity;
    }

    public Product(int id, @NotNull String name, int manufacturer_id, int quantity){
        this.id = id;
        this.name = name;
        this.manufacturer_id = manufacturer_id;
        this.quantity = quantity;
    }

    @Override
    public @NotNull String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", manufacturer_id=" + manufacturer_id +
                ", quantity=" + quantity +
                '}';
    }
}