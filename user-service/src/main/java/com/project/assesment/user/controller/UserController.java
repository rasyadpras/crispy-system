package com.project.assesment.user.controller;

import com.project.assesment.user.model.Response;
import com.project.assesment.user.model.UserRequest;
import com.project.assesment.user.model.UserResponse;
import com.project.assesment.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Response<String>> createUser(@RequestBody UserRequest request) {
        userService.createUser(request);
        Response<String> response = new Response<>();
        response.setStatus(HttpStatus.CREATED.value());
        response.setMessage(HttpStatus.CREATED.getReasonPhrase());
        response.setData("User created");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Response<List<UserResponse>>> findUsers() {
        List<UserResponse> data = userService.findAll();
        Response<List<UserResponse>> response = new Response<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage(HttpStatus.OK.getReasonPhrase());
        response.setData(data);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Response<UserResponse>> findUserById(@PathVariable Integer id) {
        UserResponse data = userService.findId(id);
        Response<UserResponse> response = new Response<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage(HttpStatus.OK.getReasonPhrase());
        response.setData(data);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Response<String>> updateUser(@PathVariable Integer id, @RequestBody UserRequest request) {
        userService.updateUser(id, request);
        Response<String> response = new Response<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage(HttpStatus.OK.getReasonPhrase());
        response.setData("User with ID " + id + " updated");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Response<String>> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        Response<String> response = new Response<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage(HttpStatus.OK.getReasonPhrase());
        response.setData("User with ID " + id + " deleted");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
