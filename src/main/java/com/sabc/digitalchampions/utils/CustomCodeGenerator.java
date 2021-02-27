package com.sabc.digitalchampions.utils;

import com.sabc.digitalchampions.utils.codegenerator.CodeConfig;
import com.sabc.digitalchampions.utils.codegenerator.CodeConfigBuilder;
import com.sabc.digitalchampions.utils.codegenerator.RbCodeGenerator;

public class CustomCodeGenerator {

    public static String generateUserCode(){
        CodeConfig codeConfig = (new CodeConfigBuilder())
                .setWithDigits(true)
                .setLength(12)
                .build();
        return (new RbCodeGenerator(codeConfig)).generate();
    }
}
