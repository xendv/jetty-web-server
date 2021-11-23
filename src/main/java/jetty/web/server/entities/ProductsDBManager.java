package jetty.web.server.entities;

import jetty.web.server.dao.ProductDAO;
import jetty.web.server.initializers.DBFlywayInitializer;
import jetty.web.server.initializers.JDBCSettingsProvider;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductsDBManager {
    public static final @NotNull
    JDBCSettingsProvider JDBC_SETTINGS = JDBCSettingsProvider.DEFAULT;

    ProductDAO productDAO;

    private
    Connection connection;

    public ProductsDBManager(){
        try (Connection connection = DriverManager.getConnection(JDBC_SETTINGS.url(), JDBC_SETTINGS.login(), JDBC_SETTINGS.password())) {
            this.connection = connection;
            DBFlywayInitializer.initDBFlyway();
            productDAO = new ProductDAO(connection);
        }
        catch (SQLException exception) {
            System.err.println(exception.getMessage());
        }
    }

    public List<Product> getAllProducts(){
        List<Product> productsList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(JDBC_SETTINGS.url(), JDBC_SETTINGS.login(), JDBC_SETTINGS.password())) {
            this.connection = connection;
            productDAO = new ProductDAO(connection);
            productsList = productDAO.all();
        }
        catch (SQLException exception) {
            System.err.println(exception.getMessage());
        }
        return productsList;
    }

    public void addProduct(Product product){
        try (Connection connection = DriverManager.getConnection(JDBC_SETTINGS.url(), JDBC_SETTINGS.login(), JDBC_SETTINGS.password())) {
            this.connection = connection;
            productDAO = new ProductDAO(connection);
            productDAO.save(product);
        }
        catch (SQLException exception) {
            System.err.println(exception.getMessage());
        }
    }
}
