package com.knowbidash.knowbidash.controller;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.knowbidash.knowbidash.entities.User;
import com.knowbidash.knowbidash.services.UserServices;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {
    public static String HEADER_ATRIBUTE;
    public static String ATRIBUTE_PREFIX;
    @Autowired
    private UserServices userServices;

    @Autowired
    private final PasswordEncoder encoder;

    public UserController(UserServices services, PasswordEncoder encoder){
        this.userServices = services;
        this.encoder = encoder;
    }

    @GetMapping("/get")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR')")
    public ResponseEntity<List<User>> findAll(){
        List<User> list = userServices.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id){
        User obj = userServices.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Validated @RequestBody User obj) throws Exception{
        obj = userServices.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id){
        userServices.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User obj){
        obj = userServices.update(id, obj);
        return ResponseEntity.ok().body(obj);
    }

}
