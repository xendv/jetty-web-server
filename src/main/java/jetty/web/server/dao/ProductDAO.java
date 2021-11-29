package jetty.web.server.dao;

import jetty.web.server.entities.Product;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.util.List;

import static db.jooq.generated.Tables.PRODUCTS;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class ProductDAO{
    private final String ID_SEQ_NAME = "products_id_seq";

    protected DSLContext context;

    public ProductDAO(@NotNull Connection connection) {
        setConnection(connection);
    }

    public void setConnection(@NotNull Connection connection) {
        this.context = DSL.using(connection, SQLDialect.POSTGRES);
    }

    @NotNull
    public List<Product> all() {
        return context.select().from(PRODUCTS).fetchInto(Product.class);
    }

    public void save(@NotNull Product entity) {
        entity.setId(context.nextval(ID_SEQ_NAME).intValue());
        var rec = context.newRecord(PRODUCTS, entity);
        rec.store();
    }

    // for future use, may be modified
    public void update(@NotNull Product entity) {
        var rec = context.newRecord(PRODUCTS, entity);
        rec.refresh();
    }

    public void delete(@NotNull Product entity) {
        var rec = context.newRecord(PRODUCTS, entity);
        if (context.executeDelete(rec) == 0)
            throw new IllegalStateException("No record found");
    }
}