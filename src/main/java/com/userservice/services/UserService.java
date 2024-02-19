package com.userservice.services;
import com.userservice.exceptions.PasswordNotMatchException;
import com.userservice.exceptions.TokenNotExistException;
import com.userservice.exceptions.UserAlreadyExistException;
import com.userservice.exceptions.UserNotExistException;
import com.userservice.models.Token;
import com.userservice.models.User;
import com.userservice.repositories.TokenRepository;
import com.userservice.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder; //create a bean for autowired
    private TokenRepository tokenRepository;
     public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, TokenRepository tokenRepository){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenRepository = tokenRepository;
    }
    public User signUp(String name, String email, String password) throws UserAlreadyExistException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()){
            User newUser = new User();
            newUser.setName(name);
            newUser.setEmail(email);
            newUser.setHashedPassword(bCryptPasswordEncoder.encode(password));
            return userRepository.save(newUser);
        }
        else{
            throw new UserAlreadyExistException("User with email id: "+email +" already exists!");
        }
    }

    public Token login(String email, String password) throws UserNotExistException, PasswordNotMatchException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()){
            throw new UserNotExistException("User with email id: "+email +" doesn't exists!");
        }
        //check the password match
        User loginUser = optionalUser.get();
        if(!bCryptPasswordEncoder.matches(password, loginUser.getHashedPassword())){
            throw new PasswordNotMatchException("Password doesn't Match");
        }

        //Generate Token
        LocalDate today = LocalDate.now();
        LocalDate thirtyDaysLater = today.plus(30, ChronoUnit.DAYS);
        // Convert LocalDate to Date
        Date expiryDate = Date.from(thirtyDaysLater.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Token token = new Token();
        token.setUser(loginUser);
        token.setExpiryDate(expiryDate);
        token.setValue(RandomStringUtils.randomAlphanumeric(128));
        return tokenRepository.save(token);
    }

    public void logout(String token) throws TokenNotExistException{
        Optional<Token> optionalToken =  tokenRepository.findByValueAndDeleted(token, false);
        if(optionalToken.isEmpty()){
            throw new TokenNotExistException("Token doesn't exist or already expired!");
        }

        Token tkn = optionalToken.get();
        tkn.setDeleted(true);
        tokenRepository.save(tkn);
    }

    public User validateToken(String token) {
        Optional<Token> optionalToken =  tokenRepository.findByValueAndDeletedAndExpiryDateGreaterThan(token, false, new Date());
        if(optionalToken.isEmpty()){
            return null;
        }
        return optionalToken.get().getUser();
    }
}
