package com.akash.controller;

import com.akash.config.JwtProvider;
import com.akash.enums.USER_ROLE;
import com.akash.model.Cart;
import com.akash.model.Users;
import com.akash.repository.CartRepository;
import com.akash.repository.UserRepository;
import com.akash.request.LoginRequest;
import com.akash.response.AuthResponse;
import com.akash.service.CustomerUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody Users users) throws Exception{

        Users users1 = userRepository.findByEmail(users.getEmail());
        if(users1!=null){
            throw new Exception("User already exist");
        }

        Users createUser = Users.builder()
                .email(users.getEmail())
                .role(users.getRole())
                .addresses(users.getAddresses())
                .password(passwordEncoder.encode(users.getPassword()))
                .favorites(users.getFavorites())
                .fullName(users.getFullName())
//                .orders(users.getOrders())
                .build();

        Users savedUser = userRepository.save(createUser);

        Cart cart=new Cart();
        cart.setCustomer(savedUser);
        cartRepository.save(cart);

        Authentication authentication=new UsernamePasswordAuthenticationToken(users.getEmail(),users.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt=jwtProvider.generateToken(authentication);
        AuthResponse authResponse =new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Register success");
        authResponse.setRole(savedUser.getRole());
        
        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/singin")
    public ResponseEntity<AuthResponse> singIn(@RequestBody LoginRequest loginRequest){
        String userName = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(userName,password);
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role= authorities.isEmpty()?null:authorities.iterator().next().getAuthority();
        String jwt = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Login Success");
        // here role is of string convert it into User authority
        authResponse.setRole(USER_ROLE.valueOf(role));

        return new ResponseEntity<>(authResponse,HttpStatus.OK);

    }

    private Authentication authenticate(String userName, String password) {
        UserDetails userDetails = customerUserDetailsService.loadUserByUsername(userName);

        if(userDetails == null){
            throw new BadCredentialsException("Invalid username");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Invalid Password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }

}
