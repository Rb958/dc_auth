package com.sabc.digitalchampions.controller;

import com.sabc.digitalchampions.entity.User;
import com.sabc.digitalchampions.exceptions.*;
import com.sabc.digitalchampions.security.payload.response.ResponseException;
import com.sabc.digitalchampions.security.payload.response.ResponseModel;
import com.sabc.digitalchampions.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    Logger  log = LogManager.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @GetMapping("/user")
    public ResponseEntity<?> findAllUsers(Optional<String> firstname, Optional<String> lastName, @RequestParam("page") int page, @RequestParam("size") int size){
        return ResponseEntity.ok(
                new ResponseModel<>(
                        userService.getUsers(firstname, lastName, PageRequest.of(page, size))
                )
        );
    }

    @GetMapping("/user-enabled")
    public ResponseEntity<?> findAllUsersEnabled(Optional<String> firstname, Optional<String> lastName, @RequestParam("page") int page, @RequestParam("size") int size){
        return ResponseEntity.ok(
                new ResponseModel<>(
                        userService.getUsersEnabled(firstname, lastName, PageRequest.of(page, size))
                )
        );
    }

    @GetMapping("/user/{ref}")
    public ResponseEntity<?> findUserByRef(@PathVariable(name="ref") String ref){
        try {
            return ResponseEntity.ok(
                    new ResponseModel<User>(
                            userService.findByCode(ref)
                    )
            );
        }catch (AbstractException e){
            return ResponseEntity.ok()
                    .body(new ResponseException(e));
        }
    }

    @GetMapping("/user/email/{email}")
    public ResponseEntity<?> findUserByEmail(@PathVariable(name="email") String email){
        try {
            return ResponseEntity.ok(
                    new ResponseModel<User>(
                            userService.findByEmail(email)
                    )
            );
        }catch (AbstractException e){
            return ResponseEntity.ok()
                    .body(new ResponseException(e));
        }
    }

    @GetMapping("/user/phone/{phone}")
    public ResponseEntity<?> findUserByPhone(@PathVariable(name="phone") String phone){
        try {
            return ResponseEntity.ok(
                    new ResponseModel<User>(
                            userService.findByPhone(phone)
                    )
            );
        }catch (AbstractException e){
            return ResponseEntity.ok()
                    .body(new ResponseException(e));
        }
    }

    @PutMapping("/user/{ref}")
    public ResponseEntity<?> updateUser(@RequestBody @Valid User user, @PathVariable(name = "ref") String ref){

        try {
            user.checkValidity();
            User tmpUser = userService.updateUser(user, ref);
            return ResponseEntity.ok(
                    new ResponseModel<>(tmpUser)
            );
        }catch (AbstractException e){
            return ResponseEntity.ok()
                    .body(new ResponseException(e));
        }catch (Exception e){
            log.error("Update Error: ", e);
            return ResponseEntity.status(500).body(
                    new ResponseModel<>("And Error occurred while trying to update this user. Contact the support if the problem persist", HttpStatus.INTERNAL_SERVER_ERROR)
            );
        }
    }

    @PostMapping("/user")
    public ResponseEntity<?> createUser(@RequestBody @Valid User user){

        try{
            user.checkValidity(true);
        } catch (AbstractException e) {
            return ResponseEntity.ok().body(
                    new ResponseException(e)
            );
        }

        User userTmp;
        try{
            userTmp = userService.saveUser(user);
            return ResponseEntity.ok(new ResponseModel<>(userTmp));
        }catch (AbstractException e){
            return ResponseEntity.ok()
                    .body(new ResponseException(e));
        }catch(Exception e){
            log.error(e);
            return ResponseEntity.status(500).body(
                    new ResponseModel<>("And Error occured while trying to registre this user. Contact the support if the problem persist", HttpStatus.INTERNAL_SERVER_ERROR)
            );
        }
    }

    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @DeleteMapping("/user/{ref}")
    public ResponseEntity<?> deleteUser(@Valid @PathVariable(name = "ref") String ref){
        try{
            userService.deleteByRef(ref);
            return ResponseEntity.ok(
                    new ResponseModel<>("Successfully deleted", HttpStatus.OK)
            );
        }catch(AbstractException e){
            return ResponseEntity.ok().body(new ResponseException(e));
        }
        catch(Exception e){
            log.error(e);
            return ResponseEntity.status(500).body(
                    new ResponseModel<>("And Error occurred while trying to delete this user. Contact the support if the problem persist", HttpStatus.INTERNAL_SERVER_ERROR)
            );
        }
    }

    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @PatchMapping("/user/{ref}/disable")
    public ResponseEntity<?> desableUser(@Valid @PathVariable(name = "ref") String ref){
        try{
            userService.disableUser(ref);
            return ResponseEntity.ok(
                    new ResponseModel<>("Successfully Disabled", HttpStatus.OK)
            );
        }catch(AbstractException  e){
            return ResponseEntity.ok().body(
                    new ResponseException(e)
            );
        }catch (Exception e){
            log.error(e);
            return ResponseEntity.status(500).body(
                    new ResponseModel<>("And Error occurred while trying to disable this user. Contact the support if the problem persist", HttpStatus.INTERNAL_SERVER_ERROR)
            );
        }
    }

    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @PatchMapping("/user/{ref}/enable")
    public ResponseEntity<?> enableUser(@Valid @PathVariable(name = "ref") String ref){
        try{
            userService.enableUser(ref);
            return ResponseEntity.ok(
                    new ResponseModel<>("Successfully Enabled", HttpStatus.OK)
            );
        }catch (AbstractException e){
            return ResponseEntity.ok()
                    .body(new ResponseException(e));
        }catch(Exception e){
            log.error(e);
            return ResponseEntity.status(500).body(
                    new ResponseModel<>("And Error occurred while trying to enable this user. Contact the support if the problem persist", HttpStatus.INTERNAL_SERVER_ERROR)
            );
        }
    }

    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @PatchMapping("/user/{code}/promote")
    public ResponseEntity<?> promoteUser(@PathVariable(name = "code") String code){
        try{
            userService.promote(code);
            return ResponseEntity.ok(new ResponseModel<>("This user has been successfully promoted", HttpStatus.OK));
        }catch (AbstractException e){
            return ResponseEntity.ok()
                    .body(new ResponseException(e));
        }catch(Exception e){
            return ResponseEntity.status(500).body(
                    new ResponseModel<>("And Error occurred while trying to promote this user. Contact the support if the problem persist", HttpStatus.INTERNAL_SERVER_ERROR)
            );
        }
    }

    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @PatchMapping("/user/{code}/demote")
    public ResponseEntity<?> demoteUser(@PathVariable(name = "code") String code){
        try{
            userService.demote(code);
            return ResponseEntity.ok(new ResponseModel<>("This user has been successfully demoted", HttpStatus.OK));
        }catch (AbstractException e){
            return ResponseEntity.ok()
                    .body(new ResponseException(e));
        }catch(Exception e){
            return ResponseEntity.status(500).body(
                    new ResponseModel<>("And Error occurred while trying to demote this user. Contact the support if the problem persist", HttpStatus.INTERNAL_SERVER_ERROR)
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
            log.error(e.getMessage(),e);
            return ResponseEntity.status(500).body(
                    new ResponseModel<>("An error occurred while trying to change the password. Pleace contact our support if the problem persist", HttpStatus.INTERNAL_SERVER_ERROR)
            );
        }
    }


}
