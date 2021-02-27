package com.sabc.digitalchampions.utils.codegenerator;

/**
 * Configure Code Building
 * this class describe the configuration of the future code
 *
 * @author Richie
 * @version 1.0
 */
public class CodeConfig {

    private final int length;
    private final boolean withDigits;
    private final boolean withLetters;
    private final boolean upperCase;
    private final boolean lowerCase;
    private final String suffix;
    private final String prefix;

    /**
     * Constructor of CodeConfig
     * @param builder CodeConfigBulder
     * @throws BadCodeConfigurationException CodeConfig have a wrong parameters
     */
    CodeConfig(CodeConfigBuilder builder) throws BadCodeConfigurationException {

        if (!builder.isWithLetters() && !builder.isWithDigits()){
            throw new BadCodeConfigurationException("The code must have a digits or letters ");
        }

        if (builder.getLength() <= 0){
            throw new BadCodeConfigurationException("The code must have at least one caracter");
        }
        this.length = builder.getLength();
        this.withDigits = builder.isWithDigits();
        this.withLetters = builder.isWithLetters();
        this.upperCase = builder.isUpperCase();
        this.lowerCase = builder.isLowerCase();
        this.suffix = builder.getSuffix();
        this.prefix = builder.getPrefix();
    }

    /**
     * Default Configuration
     */
    CodeConfig(){
        this.length = 12;
        this.withDigits = true;
        this.withLetters = false;
        this.upperCase = false;
        this.lowerCase = false;
        this.suffix = "";
        this.prefix = "";
    }

    /**
     * Get the length of the code
     * this length is given without prefix and suffix
     * @return int
     */
    public int getLength() {
        return length;
    }

    /**
     * True if the code contain some digits
     * @return boolean
     */
    public boolean isWithDigits() {
        return withDigits;
    }

    /**
     * True if the code contain letters
     * @return boolean
     */
    public boolean isWithLetters() {
        return withLetters;
    }

    /**
     * True if the code is in upper case
     * @return boolean
     */
    public boolean isUpperCase() {
        return upperCase;
    }

    /**
     * True if the code is in lower case
     * @return boolean
     */
    public boolean isLowerCase() {
        return lowerCase;
    }

    /**
     * Return the suffix of the code
     * @return String
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * Return the prefix of the code
     * @return String
     */
    public String getPrefix() {
        return prefix;
    }
}
