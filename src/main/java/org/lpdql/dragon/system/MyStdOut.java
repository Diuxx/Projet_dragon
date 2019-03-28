package org.lpdql.dragon.system;

/**
 * class MyStdOut
 *
 * @author: Diuxx
 */
public class MyStdOut {

    /**
     * Class constructor
     */
    private MyStdOut() {

    }

    /**
     *
     * @param text
     * @param color
     */
    public static void write(MyStdColor color, String text) {
        System.out.println(color.getValue() + text);
        System.out.print(MyStdColor.RESET.getValue());
    }
}
