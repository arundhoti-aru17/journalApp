package net.engineeringdigest.journalApp.controller;

import jakarta.annotation.PostConstruct;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping("/health-check")
    public String healthCheck(){
        return "OK";
    }

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestBody User user){

        boolean saved = userService.saveNewUser(user);

        if(saved){
            return new ResponseEntity<>("User created successfully", HttpStatus.CREATED); // 201
        } else {
            return new ResponseEntity<>("Username already exists", HttpStatus.CONFLICT); // 409
        }
    }

    @Autowired
    private MongoTemplate mongoTemplate;
    @PostConstruct
    public void checkDb() {
        System.out.println("🔥 Connected Mongo DB = " +
                mongoTemplate.getDb().getName());
    }

}