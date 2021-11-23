package jetty.web.server.entities;

import jetty.web.server.entities.Product;
import jetty.web.server.entities.ProductsDBManager;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("NotNullNullableValidation")
public final class ContentGenerator {
    private final ProductsDBManager productsDBManager;

    public ContentGenerator(){
        productsDBManager = new ProductsDBManager();
    }

    public String content() {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head><title>Content Generator</title></head>\n" +
                "<body>\n<h1>Products:</h1>" + getProductsInHtml() + "\n</body>" +
                "\n</html>";
    }

    private String getProductsInHtml(){
        StringBuilder result = new StringBuilder();
        List<Product> pl = this.productsDBManager.getAllProducts();
        List<String> products = pl.stream()
                .map(product -> {
                    String productStr = product.toString();
                    return "\n<p>" + productStr + "</p>";
                }).collect(Collectors.toList());
        for (String productString: products) {
            result.append(productString);
        }
        return result.toString();
    }
}
