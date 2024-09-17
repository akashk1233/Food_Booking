package com.akash.controller;

import com.akash.model.Restorent;
import com.akash.model.Users;
import com.akash.request.CreateRestorentRequest;
import com.akash.service.RestorentService;
import com.akash.service.UserService;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminRestorentController {
    @Autowired
    private RestorentService restorentService;
    @Autowired
    private UserService userService;

    @PostMapping("/restorent")
    public ResponseEntity<Restorent> createRestorent(@RequestBody CreateRestorentRequest req,
                                                     @RequestHeader("Authorization") String jwt) throws Exception {
        Users user = userService.findUserByJwtToken(jwt);

       return new ResponseEntity<>(restorentService.createRestorent(req,user), HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Restorent> updateRestorent(
            @RequestBody CreateRestorentRequest updateRequest,
            @PathVariable Long id) throws Exception{

        return new ResponseEntity<>(restorentService.updateRestorent(id,updateRequest),HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteMapping(@PathVariable Long id) throws Exception{
        return new ResponseEntity<>(restorentService.deleteRestorent(id), HttpStatus.OK);
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<Restorent> updateRestorentStatus(@PathVariable Long id) throws Exception{

        return new ResponseEntity<>(restorentService.updateRestorentStatus(id),HttpStatus.OK);
    }

    @GetMapping("/userid")
    public ResponseEntity<Restorent> getRestorentByUserId(@RequestHeader("Authorization") String jwt) throws Exception{

        Long userId = userService.findUserByJwtToken(jwt).getId();
        return new ResponseEntity<>(restorentService.getRestorentyUserid(userId), HttpStatus.OK);
    }
}