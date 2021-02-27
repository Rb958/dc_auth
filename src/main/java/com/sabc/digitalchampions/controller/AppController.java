package com.sabc.digitalchampions.controller;

import com.sabc.digitalchampions.entity.User;
import com.sabc.digitalchampions.exceptions.*;
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
                    new ResponseModel<>(e)
            );
        }
    }

    @GetMapping("/user/email/{email}")
    public ResponseEntity<ResponseModel<?>> findUserByEmail(@PathVariable(name="email") String email){
        try {
            return ResponseEntity.ok(
                    new ResponseModel<User>(
                            userService.findByEmail(email)
                    )
            );
        }catch (AbstractException e){
            return ResponseEntity.ok()
                    .body(new ResponseModel<>(e));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseModel<?>> createUser(@RequestBody @Valid User user){

        try{
            user.checkUser();
        } catch (AbstractException e) {
            return ResponseEntity.ok().body(
                    new ResponseModel<>(e)
            );
        }

        User userTmp;
        try{
            userTmp = userService.saveUser(user);
            return ResponseEntity.ok(new ResponseModel<>(userTmp));
        }catch(Exception e){

            if (e instanceof AbstractException){
                return ResponseEntity.status(500).body(
                        new ResponseModel<>(e)
                );
            }else{
                logger.error(e);
                return ResponseEntity.status(500).body(
                        new ResponseModel<>("And Error occured while trying to registre this user. Contact the support if the problem persist", HttpStatus.INTERNAL_SERVER_ERROR)
                );
            }
        }
    }

}
