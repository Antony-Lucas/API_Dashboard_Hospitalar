package com.knowbidash.knowbidash.services.postgres;

import com.knowbidash.knowbidash.entities.postgres.role.Role;
import com.knowbidash.knowbidash.entities.postgres.user.User;
import com.knowbidash.knowbidash.exceptions.DataBaseException;
import com.knowbidash.knowbidash.exceptions.ResourceNotFoundException;
import com.knowbidash.knowbidash.repositories.postgres.repoRoles.RoleRepositories;
import com.knowbidash.knowbidash.repositories.postgres.repoUser.UserRepositories;
import com.knowbidash.knowbidash.roles.ERole;
import com.knowbidash.knowbidash.security.payload.request.SignUpRequest;
import com.knowbidash.knowbidash.security.payload.response.MessageResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServices {
    @Autowired
    private UserRepositories userRepositories;
    @Autowired
    private RoleRepositories roleRepositories;

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
        entity.setfullUserName(obj.getfullUserName());
        entity.setCargo(obj.getCargo());
        entity.setEmail(obj.getEmail());
        entity.setPassWord(encoder.encode(obj.getPassWord()));
    }

    public User updateRoles(Long userId, Set<String> roleNames){
        User user = findById(userId);
        Set<Role> roles = new HashSet<>();

        if (roleNames != null){
            roleNames.forEach(roleName -> {
                Role role = roleRepositories.findByName(ERole.valueOf(roleName.toUpperCase()))
                        .orElseThrow(() -> new RuntimeException("Erro: função não encontrada."));
                roles.add(role);
            });
        }

        user.setRoles(roles);
        return userRepositories.save(user);
    }

    public void updateDataPassword(User entity, User obj) {
        entity.setPassWord(encoder.encode(obj.getPassWord()));
    }
}
