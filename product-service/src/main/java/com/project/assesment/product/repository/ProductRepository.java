package com.project.assesment.product.repository;

import com.project.assesment.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductRepository {
    private final JdbcTemplate jdbcTemplate;

    public void create(Product product) {
        String query = "INSERT INTO products (product, price, stock) VALUES (?, ?, ?)";
        jdbcTemplate.update(query, product.getProduct(), product.getPrice(), product.getStock());
    }

    public List<Product> findAll() {
        String query = "SELECT * FROM products";
        return jdbcTemplate.query(query, (rs, rowNum) -> {
            Product product = new Product();
            product.setId(rs.getInt("id"));
            product.setProduct(rs.getString("product"));
            product.setPrice(rs.getLong("price"));
            product.setStock(rs.getInt("stock"));
            return product;
        });
    }

    public Optional<Product> findById(Integer id) {
        String query = "SELECT * FROM products WHERE id = ?";
        try {
            Product data = jdbcTemplate.queryForObject(query, new Object[]{id}, (rs, rowNum) -> {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setProduct(rs.getString("product"));
                product.setPrice(rs.getLong("price"));
                product.setStock(rs.getInt("stock"));
                return product;
            });
            return Optional.ofNullable(data);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public void update(Integer id, Product product) {
        String query = "UPDATE products SET product = ?, price = ?, stock = ? WHERE id = ?";
        int update = jdbcTemplate.update(query, product.getProduct(), product.getPrice(), product.getStock(), id);
        if (update == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with ID " + id + " not found");
        }
    }

    public void delete(Integer id) {
        String query = "DELETE FROM products WHERE id = ?";
        int delete = jdbcTemplate.update(query, id);
        if (delete == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with ID " + id + " not found");
        }
    }
}
