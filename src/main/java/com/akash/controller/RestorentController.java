package com.akash.controller;

import com.akash.model.Restorent;
import com.akash.model.RestorentDto;
import com.akash.model.Users;
import com.akash.service.RestorentService;
import com.akash.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/api/user/restorent")
public class RestorentController {
    @Autowired
    private RestorentService restorentService;
    @Autowired
    private UserService userService;

    @GetMapping("/search")
    public ResponseEntity<List<Restorent>> searchRestorent(@RequestHeader("Authorization") String jwt,
                                                           @RequestParam String keyword){
        List<Restorent> restorents = restorentService.searchRestorent(keyword);
        return new ResponseEntity<>(restorents, HttpStatus.OK);
    }

    @GetMapping("/user/getAll")
    public ResponseEntity<List<Restorent>> getAllRestorents(){
        return new ResponseEntity<>(restorentService.getAllRestorent(),HttpStatus.OK);
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<Restorent> findByRestorentid(@PathVariable Long restoid) throws Exception{
        return new ResponseEntity<>(restorentService.findRestorentById(restoid).get(), HttpStatus.OK);
    }

    @PutMapping("/{id}/addFavorites")
    public ResponseEntity<RestorentDto> addRestorentToFavorites(@PathVariable Long id,
                                                                @RequestHeader("Authorization") String jwt) throws Exception{

        Users user = userService.findUserByJwtToken(jwt);
        return new ResponseEntity<>(restorentService.addToFavorites(id,user), HttpStatus.OK);

    }
}
