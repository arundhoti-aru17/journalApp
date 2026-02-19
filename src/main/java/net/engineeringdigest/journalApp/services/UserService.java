package net.engineeringdigest.journalApp.services;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();



    public boolean saveNewUser(User user){
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
             userRepository.save(user);
            return true;

        } catch (Exception e) {
            System.out.println("🔥 CATCH BLOCK EXECUTED");

            log.info("haaaaahaaah");
            log.error("haaaaahaaah");

            return false;
        }

    }
    @Autowired
    private MongoTemplate mongoTemplate;

    @PostConstruct
    public void checkDb() {
        System.out.println("🔥 Mongo DB in use = " + mongoTemplate.getDb().getName());
        System.out.println("🔥 Mongo URI = " + mongoTemplate.getMongoDatabaseFactory());
    }

    public void saveUser(User user){
        // Only encode password if it is not already encoded (starts with $2a$ for BCrypt)
        String password = user.getPassword();
        if (password != null && !password.startsWith("$2a$")) {
            user.setPassword(passwordEncoder.encode(password));
        }
        userRepository.save(user);
    }

    public void saveAdmin(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER", "ADMIN"));
        userRepository.save(user);
    }


    public List<User> getAll(){
        return userRepository.findAll();
    }

    public Optional<User> findById(ObjectId id){
        return userRepository.findById(id);
    }

    public void deleteById(ObjectId id, String userName){
        userRepository.deleteById(id);
    }

    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }
}
