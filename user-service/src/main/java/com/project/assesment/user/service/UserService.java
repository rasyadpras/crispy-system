package com.project.assesment.user.service;

import com.project.assesment.user.entity.User;
import com.project.assesment.user.model.UserRequest;
import com.project.assesment.user.model.UserResponse;
import com.project.assesment.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void createUser(UserRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setAddress(request.getAddress());
        user.setAge(request.getAge());
        userRepository.create(user);
    }

    public void updateUser(Integer id, UserRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setAddress(request.getAddress());
        user.setAge(request.getAge());
        userRepository.update(id, user);
    }

    public List<UserResponse> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> {
            UserResponse response = new UserResponse();
            response.setId(user.getId());
            response.setEmail(user.getEmail());
            response.setName(user.getName());
            response.setAddress(user.getAddress());
            response.setAge(user.getAge());
            return response;
        }).collect(Collectors.toList());
    }

    public UserResponse findId(Integer id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id " + id + " not found")
        );
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setName(user.getName());
        response.setAddress(user.getAddress());
        response.setAge(user.getAge());
        return response;
    }

    public void deleteUser(Integer id) {
        userRepository.delete(id);
    }
}
