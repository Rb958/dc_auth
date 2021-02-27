package com.sabc.digitalchampions.utils.codegenerator;

/**
 * Build a CodeConfig Object<br>
 * <b>Example</b>
 * <blockquote><pre>
 *     CodeConfigBuilder ccb = (new CodeConfigBuilder())
 *           .setLength(11)         // Set the length of code at 11 characters
 *           .setWithDigits(true)   // Set true if we want to use digits in our future code
 *           .setWithLetters(true)  // Set true if we want to use letters in our future code
 *           .setUpperCase(false)   // Set true if we want our code be in upper case
 *           .setLowerCase(true)    // Set true if our code will be in lower case
 *           .setSuffix("_RB")      // Our code will be suffixed by "_RB"
 *           .setPrefix("CODE_);     // Our code will be prefixed by "CODE_"
 *
 *     CodeConfig config = ccb.build(); // Build our Configuration. this will return a CodeConfig Object
 *     // Use it to generate your code {@code RbCodeGenerator}
 * </pre></blockquote>
 */
public class CodeConfigBuilder {

    private int length = 12;
    private boolean withDigits = true;
    private boolean withLetters = false;
    private boolean upperCase = false;
    private boolean lowerCase = false;
    private String suffix = "";
    private String prefix = "";

    public int getLength() {
        return length;
    }

    /**
     * Set the length of the code
     * this length is given without prefix and suffix
     * @param length int
     * @return int
     */
    public CodeConfigBuilder setLength(int length) {
        this.length = length;
        return this;
    }

    public boolean isWithDigits() {
        return withDigits;
    }

    /**
     * True if the code contains some digits
     * @param withDigits boolean
     * @return boolean
     */
    public CodeConfigBuilder setWithDigits(boolean withDigits) {
        this.withDigits = withDigits;
        return this;
    }

    public boolean isWithLetters() {
        return withLetters;
    }

    /**
     * True if the code contains letters
     * @param withLetters boolean
     * @return boolean
     */
    public CodeConfigBuilder setWithLetters(boolean withLetters) {
        this.withLetters = withLetters;
        return this;
    }

    public boolean isUpperCase() {
        return upperCase;
    }

    /**
     * True if the code will be in upper case
     * @param upperCase boolean
     * @return boolean
     */
    public CodeConfigBuilder setUpperCase(boolean upperCase) {
        this.upperCase = upperCase;
        return this;
    }

    public boolean isLowerCase() {
        return lowerCase;
    }

    /**
     * True if the code will be in lower case
     * @param lowerCase boolean
     * @return boolean
     */
    public CodeConfigBuilder setLowerCase(boolean lowerCase) {
        this.lowerCase = lowerCase;
        return this;
    }

    public String getSuffix() {
        return suffix;
    }

    /**
     * Set the suffix of the code
     * @param suffix String
     * @return String
     */
    public CodeConfigBuilder setSuffix(String suffix) {
        this.suffix = suffix;
        return this;
    }

    public String getPrefix() {
        return prefix;
    }

    /**
     * Return the prefix of the code
     * @param prefix String
     * @return String
     */
    public CodeConfigBuilder setPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public CodeConfig build(){
        return new CodeConfig(this);
    }
}
