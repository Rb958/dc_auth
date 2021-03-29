package com.sabc.digitalchampions.services;

import com.sabc.digitalchampions.entity.User;
import com.sabc.digitalchampions.exceptions.*;
import com.sabc.digitalchampions.repository.UserRepository;
import com.sabc.digitalchampions.security.jwt.JwtUtils;
import com.sabc.digitalchampions.security.payload.response.JwtResponse;
import com.sabc.digitalchampions.security.services.UserDetailsImpl;
import com.sabc.digitalchampions.utils.codegenerator.CodeConfig;
import com.sabc.digitalchampions.utils.codegenerator.CodeConfigBuilder;
import com.sabc.digitalchampions.utils.codegenerator.RbCodeGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Value("${email.username}")
    private String mainAdress;

    public UserService(AuthenticationManager authenticationManager, JwtUtils jwtUtils, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public User register(User user) throws AbstractException{

        user.checkValidity();

        if (existsByEmail(user.getEmail())){
            throw new EmailExistException();
        }
        if (existsByPhone(user.getPhone())){
            throw new PhoneExistException();
        }
        // Create new user's account
        user.setPassword(this.passwordEncoder.encode(user.getPassword()))
                .setCreatedAt(new Date())
                .setLastUpdatedAt(new Date());

        if(user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("ROLE_USER");
        }
        return this.userRepository.save(user);
    }

    @Transactional
    public JwtResponse login(User user) throws UserNotEnabledException {
        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        if (!userDetails.getEnable()){
            throw new UserNotEnabledException();
        }

        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        User tmpUser = userRepository.findByMatricule(userDetails.getRef());
        tmpUser.setLastConnected(new Date());
        userRepository.save(tmpUser);

        CodeConfig codeConfig = (new CodeConfigBuilder())
                .setLength(6)
                .setLowerCase(true)
                .setUpperCase(true)
                .setWithDigits(true).build();

//        RbCodeGenerator rbCredentialsGenerator = new RbCodeGenerator(codeConfig);
//        String generatedPassword = rbCredentialsGenerator.generate();

        // Code OTP
//        OTP otp = new OTP();
//
//        if (userRepository.existsByMatricule(user.getMatricule())){
//            List<String> address = new ArrayList<>();
//            address.add(user.getEmail());
//            MailUtils mailUtils = new MailUtils();
//
//            String messageBody = "" +
//                    "<h1> Welcome to digital champions </h1>" +
//                    "It's only a test" +
//                    "<p> You have been successfully registered on our platform. Here is your password to access your space </p>" +
//                    "<p> We suggest you change this password when you access the platform for the first time </p>"+
//                    "<p>Your password : </p><strong>"+generatedPassword +"</strong><br>Date: "+ DateFormat.getDateTimeInstance().format(new Date());
//
//            mailUtils.sendHTMLMail(mainAdress, address, "Digital champions Your credentials", messageBody);
//        }
        return new JwtResponse(jwtToken,userDetails.getLastName(), userDetails.getFirstName(), userDetails.getUsername(), roles, userDetails.getRef());
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public Page<User> getUsers(Optional<String> firstName, Optional<String> lastName, Pageable pageable){
        return userRepository.findAll(firstName.orElse(""), lastName.orElse(""), pageable);
    }

    public boolean existByCode(String code) {
        return userRepository.existsByMatricule(code);
    }

    public boolean existsByPhone(String phone){
        return userRepository.existsByPhone(phone);
    }

    public User findByCode(String code) throws UserNotFoundException {
        if (existByCode(code)){
            return userRepository.findByMatricule(code);
        }else{
            throw new UserNotFoundException();
        }
    }

    public User saveUser(User user) throws EmailExistException, PhoneExistException {
        if (existsByEmail(user.getEmail())){
            throw new EmailExistException();
        }
        if (existsByPhone(user.getPhone())){
            throw new PhoneExistException();
        }
        CodeConfig codeConfig = (new CodeConfigBuilder())
                .setLength(6)
                .setLowerCase(true)
                .setUpperCase(true)
                .setWithDigits(true).build();

        RbCodeGenerator rbCredentialsGenerator = new RbCodeGenerator(codeConfig);
        String generatedPassword = rbCredentialsGenerator.generate();
        user.setPassword(passwordEncoder.encode(generatedPassword));

        user.setRole("ROLE_USER")
                .setCreatedAt(new Date())
                .setLastUpdatedAt(new Date());
        User newUser = userRepository.save(user);

        // Envoie du code OTP

//        if (userRepository.existsByMatricule(user.getMatricule())){
//            List<String> address = new ArrayList<>();
//            address.add(user.getEmail());
//            MailUtils mailUtils = new MailUtils();
//
//            String messageBody = "" +
//                    "<h1> Welcome to digital champions </h1>" +
//                    "It's only a test" +
//                    "<p> You have been successfully registered on our platform. Here is your password to access your space </p>" +
//                    "<p> We suggest you change this password when you access the platform for the first time </p>"+
//                    "<p>Your password : </p><strong>"+generatedPassword +"</strong><br>Date: "+ DateFormat.getDateTimeInstance().format(new Date());
//
//            mailUtils.sendHTMLMail(mainAdress, address, "Digital champions Your credentials", messageBody);
//        }
        return newUser;
    }

    public void deleteByRef(String ref) throws UserNotFoundException {
        if (!existByCode(ref)){
            throw new UserNotFoundException();
        }
        User user = findByCode(ref);
        userRepository.delete(user);
    }

    public User updateUser(User user, String ref) throws UserNotFoundException, EmailExistException, PhoneExistException {

        if (!existByCode(ref)){
            throw new UserNotFoundException();
        }

        User tmpUser = findByCode(user.getMatricule());

        if (!user.getEmail().equals(tmpUser.getEmail()) && existsByEmail(user.getEmail())){
            throw new EmailExistException();
        }

        if (!user.getPhone().equals(tmpUser.getPhone()) && existsByPhone(user.getPhone())){
            throw new PhoneExistException();
        }

        user.setRole(tmpUser.getRole())
                .setMatricule(tmpUser.getMatricule())
                .setLastUpdatedAt(new Date())
                .setPassword(tmpUser.getPassword())
                .setId(tmpUser.getId());
        return userRepository.save(user);
    }

    public void disableUser(String code) throws UserNotFoundException {
        if (!existByCode(code)){
            throw new UserNotFoundException();
        }
        User user = userRepository.findByMatricule(code);
        user.setEnabled(false)
                .setDeletedAt(new Date());
        userRepository.save(user);
    }

    public void enableUser(String ref) throws UserNotFoundException {
        if (!existByCode(ref)){
            throw new UserNotFoundException();
        }
        User user = userRepository.findByMatricule(ref);
        user.setEnabled(true)
                .setEnabledAt(new Date())
                .setDeletedAt(null);
        userRepository.save(user);
    }

    public boolean checkAdmin() {
        return userRepository.existsByRole("ROLE_ADMIN");
    }

    public void promote(String code) throws UserNotFoundException, UnPromotableException {
        User user = findByCode(code);
        if (user.getRole().equals("ROLE_ADMIN")){
            throw new UnPromotableException();
        }else{
            user.setRole("ROLE_ADMIN");
            userRepository.save(user);
        }
    }

    public void demote(String code) throws UserNotFoundException, UndemotableException {
        User user = findByCode(code);
        if (user.getRole().equals("ROLE_USER")){
            throw new UndemotableException();
        }else{
            user.setRole("ROLE_USER");
            userRepository.save(user);
        }
    }

    public User findByEmail(String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null){
            throw new UserNotFoundException();
        }else{
            return user;
        }
    }

    public User findByPhone(String phone) throws UserNotFoundException {
        User user = userRepository.findByPhone(phone);
        if (user == null){
            throw new UserNotFoundException();
        }else{
            return user;
        }
    }
}
