package jetty.web.server.servlets;

import jetty.web.server.ContentGenerator;
import jetty.web.server.entities.Product;
import jetty.web.server.entities.ProductsDBManager;
import org.eclipse.jetty.http.HttpStatus;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public final class ContentServlet extends HttpServlet implements Servlet {
    private final ContentGenerator contentGenerator;
    private ServletConfig servletConfig;

    public ContentServlet(ContentGenerator contentGenerator) {
        this.contentGenerator = contentGenerator;
    }

    @Override
    public void init(ServletConfig config) {
        this.servletConfig = config;
    }

    @Override
    public ServletConfig getServletConfig() {
        return servletConfig;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (ServletOutputStream outputStream = resp.getOutputStream()) {
            outputStream.write(contentGenerator.content().getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
            resp.setStatus(HttpStatus.OK_200);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        //resp.setContentType("text/plain");
        final var id = req.getParameter("id");
        final var name = req.getParameter("name");
        final var quantity = req.getParameter("quantity");
        final var manufacturersId = req.getParameter("man_id");
        if (id == null || quantity == null || name == null || manufacturersId == null ) {
            resp.setStatus(HttpStatus.BAD_REQUEST_400);
            return;
        }
        ProductsDBManager productsDBManager = new ProductsDBManager();
        productsDBManager.addProduct(new Product(
                Integer.parseInt(id), name, Integer.parseInt(manufacturersId), Integer.parseInt(quantity)));

        resp.setStatus(HttpStatus.OK_200);
        //resp.sendRedirect(req.getContextPath() +"/products");
        doGet(req, resp);

    }

    @Override
    public String getServletInfo() {
        return "my Content Servlet";
    }

    @Override
    public void destroy() {
    }
}
