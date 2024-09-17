package com.akash.service;


import com.akash.model.Users;

public interface UserService {

    Users findUserByJwtToken(String jwt) throws Exception;
    Users findUserByEmail(String email) throws Exception;
}
