package com.sabc.digitalchampions.controller;

import com.sabc.digitalchampions.exceptions.AbstractException;
import com.sabc.digitalchampions.security.payload.response.ResponseException;
import com.sabc.digitalchampions.security.payload.response.ResponseModel;
import com.sabc.digitalchampions.services.OtpService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prelogin")
public class OtpController {
    private final OtpService otpService;

    Logger logger = LogManager.getLogger(AppController.class);

    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }

    @GetMapping("/otp/get")
    public ResponseEntity<?> createOtp(@RequestParam(name = "matricule") String matricule, @RequestParam("email") String email){
        try{
            if (otpService.create(matricule, email) != null){
                return ResponseEntity.ok(
                        new ResponseModel<>("The otp code has been successfully created and sent", HttpStatus.OK)
                );
            }else{
                return ResponseEntity.status(500).body(
                        new ResponseModel<>("Unknown Error", HttpStatus.INTERNAL_SERVER_ERROR)
                );
            }
        }catch (AbstractException e){
            return ResponseEntity.ok(
                    new ResponseException(e)
            );
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseEntity.status(500).body(
                    new ResponseModel<>("An error occurred while trying to create and send of OTP code. please contact our support if the problem persist", HttpStatus.INTERNAL_SERVER_ERROR)
            );
        }
    }

    @GetMapping("/otp/verify")
    public ResponseEntity<?> verifyOtp(@RequestParam(name = "code") String code){
        try{
            if (otpService.verify(code)){
                return ResponseEntity.ok(
                        new ResponseModel<>("Successfully validated", HttpStatus.OK)
                );
            }else{
                return ResponseEntity.status(500).body(new ResponseModel<>("This code has expired", HttpStatus.INTERNAL_SERVER_ERROR));
            }
        }catch (AbstractException e){
            return ResponseEntity.ok(
                    new ResponseException(e)
            );
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            return ResponseEntity.status(500).body(
                    new ResponseModel<>("An error occurred while trying to verify the otp code. Please contact our support if the problem persist")
            );
        }
    }
}
