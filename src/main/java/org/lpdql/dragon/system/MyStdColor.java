package org.lpdql.dragon.system;


/**
 * class MyStdColor
 *
 * @author: Diuxx
 */
public enum MyStdColor {
    BLACK("\u001b[30;1m"),
    RED("\u001b[31;1m"),
    GREEN("\u001b[32;1m"),
    YELLOW("\u001b[33;1m"),
    BLUE("\u001b[33;1m"),
    MAGENTA("\u001b[35;1m"),
    CYAN("\u001b[36;1m"),
    WHITE("\u001b[37;1m"),
    RESET("\u001b[0m");

    private String value;
    private MyStdColor(String value) {
        this.value = value;
    }
    public String getValue() {
        return this.value;
    }
}