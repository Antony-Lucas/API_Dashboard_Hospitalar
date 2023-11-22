package com.knowbidash.knowbidash.services;

import com.knowbidash.knowbidash.entities.User;
import com.knowbidash.knowbidash.exceptions.DataBaseException;
import com.knowbidash.knowbidash.exceptions.ResourceNotFoundException;
import com.knowbidash.knowbidash.repositories.UserRepositories;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServices {
    @Autowired
    private UserRepositories userRepositories;

    @Autowired
    private final PasswordEncoder encoder;
    public UserServices(UserRepositories userRepositories, PasswordEncoder encoder){
        this.userRepositories = userRepositories;
        this.encoder = encoder;
    }

    public List<User> findAll(){
        return userRepositories.findAll();
    }

    public User findById(Long id){
        Optional<User> obj = userRepositories.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public User insert(User obj){
        try {
            obj.setPassWord(encoder.encode(obj.getPassWord()));
            return userRepositories.save(obj);
        }catch (DataIntegrityViolationException e){
            throw new DataBaseException(e.getMessage());
        }
    }

    public void delete(Long id){
        try {
            userRepositories.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException(id);
        }catch (DataIntegrityViolationException e){
            throw new DataBaseException(e.getMessage());
        }
    }

    public User update(Long id, User obj){
        try {
            User entity = userRepositories.getReferenceById(id);
            updateData(entity, obj);
            return userRepositories.save(entity);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException(id);
        }
    }

    public User updatePass(Long id, User obj) {
        try {
            User entity = userRepositories.getReferenceById(id);
            updateDataPassword(entity, obj);
            return userRepositories.save(entity);
        }catch(EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(User entity, User obj){
        entity.setUserName(obj.getUserName());
        entity.setAliasName(obj.getAliasName());
        entity.setEmail(obj.getEmail());
    }

    public void updateDataPassword(User entity, User obj) {
        entity.setPassWord(encoder.encode(obj.getPassWord()));
    }
}
