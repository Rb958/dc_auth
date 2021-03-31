package com.sabc.digitalchampions.services;

import com.sabc.digitalchampions.entity.Otp;
import com.sabc.digitalchampions.exceptions.AbstractException;
import com.sabc.digitalchampions.exceptions.InvalidOtpException;
import com.sabc.digitalchampions.exceptions.UserNotFoundException;
import com.sabc.digitalchampions.repository.OtpRepository;
import com.sabc.digitalchampions.repository.UserRepository;
import com.sabc.digitalchampions.utils.MailUtils;
import com.sabc.digitalchampions.utils.codegenerator.CodeConfig;
import com.sabc.digitalchampions.utils.codegenerator.CodeConfigBuilder;
import com.sabc.digitalchampions.utils.codegenerator.RbCodeGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OtpService {
    private final OtpRepository otpRepository;
    private final UserRepository userRepository;
    private final MailUtils mailUtils;
    @Value("${email.username}")
    private String mainAdress;

    public OtpService(OtpRepository otpRepository, UserRepository userRepository, MailUtils mailUtils) {
        this.otpRepository = otpRepository;
        this.userRepository = userRepository;
        this.mailUtils = mailUtils;
    }

    public Otp create(String matricule, String email) throws AbstractException {
        if (!userRepository.existsByMatricule(matricule) || !userRepository.existsByEmail(email)){
            throw new UserNotFoundException();
        }

        CodeConfig codeConfig = (new CodeConfigBuilder())
                .setLength(6)
                .setLowerCase(true)
                .setUpperCase(true)
                .setWithDigits(true).build();

        RbCodeGenerator rbCredentialsGenerator = new RbCodeGenerator(codeConfig);
        String otpCode = rbCredentialsGenerator.generate();
        if (otpRepository.existsByCode(otpCode)){
            otpCode = rbCredentialsGenerator.generate();
        }

        // Code OTP
        Otp otp = new Otp();
        otp.setCode(otpCode);
        otp.setMatricule(matricule);
        otp.setEmail(email);
        otp = otpRepository.save(otp);
        if (userRepository.existsByMatricule(matricule)){
            List<String> address = new ArrayList<>();
            address.add(email);

            String messageBody = "" +
                    "<h1> Welcome to digital champions </h1>" +
                    "It's only a test" +
                    "<p> Here is your code to change your password </p>" +
                    "<p>Your code : </p><strong>"+otp.getCode() +"</strong><br>Date: "+ DateFormat.getDateTimeInstance().format(new Date()) +
                    "<p>This code will expired at : "+DateFormat.getDateTimeInstance().format(otp.getDueDate())+"</p>";

            mailUtils.sendHTMLMail(mainAdress, address, "Digital champions Recover password", messageBody);
        }
        return otp;
    }

    public boolean verify(String code) throws AbstractException{
        if (!otpRepository.existsByCode(code)){
            throw new InvalidOtpException();
        }

        Otp otp = otpRepository.findByCode(code);
        if (otp.isExpired()){
            return false;
        }else{
            otpRepository.delete(otp);
            return true;
        }
    }
}
