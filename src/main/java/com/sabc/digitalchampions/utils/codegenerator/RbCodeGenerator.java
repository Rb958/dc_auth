package com.sabc.digitalchampions.utils.codegenerator;

import java.util.Random;

/**
 * Implementation of codeGenerator
 * @author Richie
 * @version 1.0
 * <br>
 * <b>Example</b>
 * How to generate a basic code
 * <blockquote><pre>
 *     CodeConfigBuilder ccb = (new CodeConfigBuilder())
 *           .setLength(12)
 *           .setWithDigits(true)
 *           .setWithLetters(true)
 *           .setUpperCase(false)
 *           .setLowerCase(true);
 *
 *     CodeConfig config = ccb.build();
 *
 *     RbCodeGenerator cg = new RbCodeGenerator(config);
 *     String code = cg.generate();
 *     System.out.println("My generated Code : " + code); // Output: T5sOiB4p905F
 * </pre></blockquote>
 * @see CodeConfigBuilder
 * @see CodeConfig
 */
public class RbCodeGenerator {
    private final CodeConfig codeConfig;

    /**
     * Constructor of RbCodeGenerator
     * @param config CodeConfig
     */
    public RbCodeGenerator(CodeConfig config){
        this.codeConfig = config;
    }

    public RbCodeGenerator(){
        this.codeConfig = new CodeConfig();
    }

    /**
     * Generate the code
     * @return String
     * @throws ConfigurationNotFoundException Throw this Exception if the configuration was not precised
     */
    public String generate() throws ConfigurationNotFoundException{
        if (codeConfig == null)
            throw new ConfigurationNotFoundException("The Code configurator need a configuration to generate a code");
        else
            return generateConfiguratedCode();
    }

    private String generateConfiguratedCode() {
        StringBuilder sb = new StringBuilder();

        String charPattern = getChars();

        if (codeConfig.getPrefix() != null && !codeConfig.getPrefix().trim().isEmpty())
            sb.append(codeConfig.getPrefix());

        for (int i = 0; i < codeConfig.getLength(); i++) {
            sb.append(getRandomLetter(charPattern));
        }

        if (codeConfig.getSuffix()!= null && !codeConfig.getSuffix().trim().isEmpty())
            sb.append(codeConfig.getSuffix());

        return sb.toString();
    }

    /**
     * Get a random character from a chars String
     * @param chars String
     * @return String
     */
    private String getRandomLetter(String chars){
        Random random = new Random();
        int rndInt = 0;
        if (getChars()!= null)
            rndInt = random.nextInt(chars.length());
        return String.valueOf(chars.charAt(rndInt));
    }

    /**
     * Get the characters using configuration
     * @return String
     */
    private String getChars(){
        StringBuilder sb = new StringBuilder();
        String letters = "abcdefghijklmnopqrstuvwxyz";
        if (codeConfig != null){
            if (codeConfig.isWithDigits())
                sb.append("0123456789");
            if (codeConfig.isWithLetters()){
                if (codeConfig.isLowerCase())
                    sb.append(letters.toLowerCase());
                if (codeConfig.isUpperCase())
                    sb.append(letters.toUpperCase());
                if (!codeConfig.isUpperCase() && !codeConfig.isLowerCase())
                    sb.append(letters);
            }
            return sb.toString();
        }else{
            return null;
        }
    }
}
