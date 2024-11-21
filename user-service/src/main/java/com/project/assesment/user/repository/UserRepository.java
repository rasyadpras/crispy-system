package com.project.assesment.user.repository;

import com.project.assesment.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public void create(User user) {
        String query = "INSERT INTO users (name, email, address, age) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(query, user.getName(), user.getEmail(), user.getAddress(), user.getAge());
    }

    public List<User> findAll() {
        String query = "SELECT * FROM users";
        return jdbcTemplate.query(query, (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setAddress(rs.getString("address"));
            user.setAge(rs.getInt("age"));
            return user;
        });
    }

    public Optional<User> findById(Integer id) {
        String query = "SELECT * FROM users WHERE id = ?";
        try {
            User data = jdbcTemplate.queryForObject(query, new Object[]{id}, (rs, rowNum) -> {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setAddress(rs.getString("address"));
                user.setAge(rs.getInt("age"));
                return user;
            });
            return Optional.ofNullable(data);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public void update(Integer id, User user) {
        String query = "UPDATE users SET name = ?, email = ?, address = ?, age = ? WHERE id = ?";
        int update = jdbcTemplate.update(query, user.getName(), user.getEmail(), user.getAddress(), user.getAge(), id);
        if (update == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with ID " + id + " not found");
        }
    }

    public void delete(Integer id) {
        String query = "DELETE FROM users WHERE id = ?";
        int delete = jdbcTemplate.update(query, id);
        if (delete == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with ID " + id + " not found");
        }
    }
}
