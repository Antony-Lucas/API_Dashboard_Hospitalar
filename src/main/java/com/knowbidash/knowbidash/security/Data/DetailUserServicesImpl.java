package com.knowbidash.knowbidash.security.Data;

import com.knowbidash.knowbidash.entities.postgres.user.User;
import com.knowbidash.knowbidash.repositories.postgres.repoUser.UserRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DetailUserServicesImpl implements UserDetailsService {
    @Autowired
    UserRepositories userRepositories;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepositories.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return DetailUserData.build(user);
    }
}
