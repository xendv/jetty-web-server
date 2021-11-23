package jetty.web.server.dao;

import jetty.web.server.entities.Product;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO{

    private final @NotNull
    Connection connection;

    final String getAllProductsQuery = "SELECT * FROM products";
    final String saveProductQuery = "INSERT INTO products (id,name,manufacturer_id,quantity) VALUES(?,?,?,?)";

    public ProductDAO(@NotNull Connection connection) {
        this.connection = connection;
    }

    @NotNull
    public List<Product> all() {
        final List<Product> result = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement(getAllProductsQuery)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(new Product(resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getInt("manufacturer_id"),
                            resultSet.getInt("quantity")));
                }
                return result;
            }
        }
        catch (SQLException e) {
            System.out.println(e.getErrorCode());
            System.out.println(e.getSQLState());
            System.out.println(e.getLocalizedMessage());
        }
        return result;
    }

    public void save(@NotNull Product entity) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(saveProductQuery)){
            preparedStatement.setInt(1, entity.getId());
            preparedStatement.setString(2, entity.getName());
            preparedStatement.setInt(3, entity.getManufacturer_id());
            preparedStatement.setInt(4, entity.getQuantity());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}