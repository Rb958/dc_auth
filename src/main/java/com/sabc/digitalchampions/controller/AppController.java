package com.sabc.digitalchampions.controller;

import com.sabc.digitalchampions.entity.User;
import com.sabc.digitalchampions.exceptions.*;
import com.sabc.digitalchampions.security.payload.response.ResponseException;
import com.sabc.digitalchampions.security.payload.response.ResponseModel;
import com.sabc.digitalchampions.services.UserService;
import com.sabc.digitalchampions.utils.codegenerator.RbCodeGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/prelogin")
public class AppController {
    private final UserService userService;

    Logger logger = LogManager.getLogger(AppController.class);

    public AppController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody User utilisateur){
        try {
            return ResponseEntity.ok(this.userService.login(utilisateur));
        }catch(AbstractException e){
            return ResponseEntity.ok().body(
                    new ResponseException(e)
            );
        }catch(Exception ex){
            logger.error(ex.getMessage(),ex);
            return ResponseEntity.status(500).body(
                    new ResponseModel<>("Authentication failed", HttpStatus.INTERNAL_SERVER_ERROR)
            );
        }
    }

    @GetMapping("/user/email/{email}")
    public ResponseEntity<?> findUserByEmail(@PathVariable(name="email") String email){
        try {
            return ResponseEntity.ok(
                    new ResponseModel<>(
                            userService.findByEmail(email)
                    )
            );
        }catch (AbstractException e){
            return ResponseEntity.ok()
                    .body(new ResponseException(e));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody @Valid User user){

        try{
            user.checkValidity();
            User userTmp = userService.register(user);
            return ResponseEntity.ok(new ResponseModel<>(userTmp));
        }catch (AbstractException e){
            return ResponseEntity.ok(
                    new ResponseException(e)
            );
        }catch(Exception e){
                logger.error(e.getMessage(),e);
                return ResponseEntity.status(500).body(
                        new ResponseModel<>("And Error occured while trying to registre this user. Contact the support if the problem persist", HttpStatus.INTERNAL_SERVER_ERROR)
                );
        }
    }

    @PatchMapping("/user/{code}/password")
    public ResponseEntity<?> changePassword(@PathVariable(name = "code") String code, @RequestBody @Valid User user){
        try{
            if(userService.changePassword(code, user)){
                return ResponseEntity.ok(new ResponseModel<>("Successfully Changed", HttpStatus.OK));
            }else{
                return ResponseEntity.status(500).body(
                        new ResponseModel<>("Unable to change the password", HttpStatus.INTERNAL_SERVER_ERROR)
                );
            }
        }catch(AbstractException e){
            return ResponseEntity.ok(
                    new ResponseException(e)
            );
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            return ResponseEntity.status(500).body(
                    new ResponseModel<>("An error occurred while trying to change the password. Pleace contact our support if the problem persist", HttpStatus.INTERNAL_SERVER_ERROR)
            );
        }
    }

}
