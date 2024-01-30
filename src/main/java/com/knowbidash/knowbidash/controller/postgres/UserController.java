package com.knowbidash.knowbidash.controller.postgres;

import com.knowbidash.knowbidash.entities.postgres.role.Role;
import com.knowbidash.knowbidash.entities.postgres.user.User;
import com.knowbidash.knowbidash.exceptions.DataBaseException;
import com.knowbidash.knowbidash.exceptions.ResourceNotFoundException;
import com.knowbidash.knowbidash.repositories.postgres.repoRoles.RoleRepositories;
import com.knowbidash.knowbidash.repositories.postgres.repoUser.UserRepositories;
import com.knowbidash.knowbidash.roles.ERole;
import com.knowbidash.knowbidash.security.payload.request.SignUpRequest;
import com.knowbidash.knowbidash.security.payload.response.MessageResponse;
import com.knowbidash.knowbidash.services.postgres.RefreshTokenService;
import com.knowbidash.knowbidash.services.postgres.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/users")
public class UserController {
    public static String HEADER_ATRIBUTE;
    public static String ATRIBUTE_PREFIX;
    @Autowired
    private UserServices userServices;
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private UserRepositories userRepositories;
    @Autowired
    private RoleRepositories roleRepositories;
    @Autowired
    private final PasswordEncoder encoder;

    public UserController(UserServices services, PasswordEncoder encoder){
        this.userServices = services;
        this.encoder = encoder;
    }

    @GetMapping("/get")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<User>> findAll(){
        List<User> list = userServices.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> findById(@PathVariable Long id){
        User obj = userServices.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping(value = "/signup")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> insert(@Validated @RequestBody SignUpRequest signUpRequest) throws Exception{
        try {
            if (userRepositories.existsByUserName(signUpRequest.getUserName())){
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
            }

            if (userRepositories.existsByEmail(signUpRequest.getEmail())){
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
            }
            User user = new User(signUpRequest.getUserName(), signUpRequest.getfullUserName(), signUpRequest.getCargo(), signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassWord()));
            Set<String> strRoles = signUpRequest.getRole();
            Set<Role> roles = new HashSet<>();

            if (strRoles == null){
                Role userRole = roleRepositories.findByName(ERole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
            } else{
                strRoles.forEach(role -> {
                    switch (role){
                        case "admin":
                            Role adminRole = roleRepositories.findByName(ERole.ROLE_ADMIN)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(adminRole);
                            break;

                        case "mod":
                            Role modRole = roleRepositories.findByName(ERole.ROLE_MODERATOR)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(modRole);
                            break;

                        default:
                            Role userRole = roleRepositories.findByName(ERole.ROLE_USER)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(userRole);
                            break;
                    }
                });
            }

            user.setRoles(roles);
            userRepositories.save(user);

            return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
        }catch (DataIntegrityViolationException e){
            throw new DataBaseException(e.getMessage());
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> deleteUser(@PathVariable Long id){
        refreshTokenService.deleteyUserId(id);
        userServices.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@PathVariable Long id, @Validated @RequestBody SignUpRequest updatedUserData){
        User existingUser = userRepositories.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        existingUser.setUserName(updatedUserData.getUserName());
        existingUser.setfullUserName(updatedUserData.getfullUserName());
        existingUser.setCargo(updatedUserData.getCargo());
        existingUser.setEmail(updatedUserData.getEmail());
        existingUser.setPassWord(encoder.encode(updatedUserData.getPassWord()));

        Set<Role> roles = new HashSet<>();
        if (updatedUserData.getRole() != null) {
            updatedUserData.getRole().forEach(role -> {
                switch (role) {
                    case "admin":
                        roles.add(roleRepositories.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found.")));
                        break;
                    case "mod":
                        roles.add(roleRepositories.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found.")));
                        break;
                    default:
                        roles.add(roleRepositories.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found.")));
                        break;
                }
            });
        }
        existingUser.setRoles(roles);
        userRepositories.save(existingUser);

        return ResponseEntity.ok(new MessageResponse("User updated successfully!"));
    }

}
