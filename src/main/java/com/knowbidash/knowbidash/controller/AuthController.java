package com.knowbidash.knowbidash.controller;

import com.knowbidash.knowbidash.entities.RefreshToken;
import com.knowbidash.knowbidash.entities.Role;
import com.knowbidash.knowbidash.entities.User;
import com.knowbidash.knowbidash.exceptions.TokenRefreshException;
import com.knowbidash.knowbidash.repositories.RoleRepositories;
import com.knowbidash.knowbidash.repositories.UserRepositories;
import com.knowbidash.knowbidash.roles.ERole;
import com.knowbidash.knowbidash.security.Data.DetailUserData;
import com.knowbidash.knowbidash.security.JwtUtils;
import com.knowbidash.knowbidash.security.payload.request.LoginRequest;
import com.knowbidash.knowbidash.security.payload.request.SignUpRequest;
import com.knowbidash.knowbidash.security.payload.request.TokenRefreshRequest;
import com.knowbidash.knowbidash.security.payload.response.MessageResponse;
import com.knowbidash.knowbidash.security.payload.response.TokenRefreshResponse;
import com.knowbidash.knowbidash.security.payload.response.UserInfoResponse;
import com.knowbidash.knowbidash.services.RefreshTokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepositories userRepositories;
    @Autowired
    RoleRepositories roleRepositories;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    RefreshTokenService refreshTokenService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getAliasName(), loginRequest.getPassWord()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        DetailUserData userDetails = (DetailUserData) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        String jwt = jwtUtils.generateJwtToken(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getUserId());

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new UserInfoResponse(
                        jwt,
                        refreshToken.getToken(),
                        userDetails.getUserId(),
                        userDetails.getFullUserName(),
                        userDetails.getUsername(),
                        userDetails.getEmail(),
                        roles
                ));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> resgisterUser(@Valid @RequestBody SignUpRequest signUpRequest){
        if (userRepositories.existsByUserName(signUpRequest.getUserName())){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepositories.existsByEmail(signUpRequest.getEmail())){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        User user = new User(signUpRequest.getUserName(), signUpRequest.getAliasName(), signUpRequest.getCargo(), signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassWord()));
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
    }

    @PostMapping("/signout")
    private ResponseEntity<?> logoutUser(){
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("You've been signed out!"));
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest request){
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername((user.getAliasName()));
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                }).orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Refresh token is not in database!"));
    }
}
