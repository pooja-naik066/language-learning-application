package com.pooja.question_service.services;

import com.pooja.question_service.model.Users;
import com.pooja.question_service.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class UserService {
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    private UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }


    public Users register(Users user) {
        Optional<Users> users=userRepository.findByUsername(user.getUsername());
        if(users.isPresent()){
            throw new RuntimeException("Try different username");
        }
            user.setPassword(encoder.encode(user.getPassword()));
            userRepository.save(user);
            System.out.println("Saved");
            return user;

    }
}
