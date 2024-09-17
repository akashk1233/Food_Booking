package com.akash.service;

import com.akash.config.JwtProvider;
import com.akash.model.Users;
import com.akash.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtProvider jwtProvider;
    @Override
    public Users findUserByJwtToken(String jwt) throws Exception {
        String email=jwtProvider.getEmailFromJwtToken(jwt);

        return findUserByEmail(email);
    }

    @Override
    public Users findUserByEmail(String email) throws Exception {

//        Users user = userRepository.findByEmail(email);
//        if(user==null){
//            throw new Exception("user not found");
//        }
//        return userRepository.findByEmail(email);

        // to declare above thinf in single s=line

        return Optional.ofNullable(userRepository.findByEmail(email))
                .orElseThrow(()-> new UsernameNotFoundException("user not found"));
    }
}
