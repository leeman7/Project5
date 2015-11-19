/*
 * Car Dealership Managment Software v0.1 
 * Developed for CS4354: Object Oriented Design and Implementation.
 * Copyright: Vangelis Metsis (vmetsis@txstate.edu)
 */
package project5;

import javax.swing.*;

/**
 * Custom Exception type, used to report bad input from user.
 * @author vangelis
 */
public class BadInputException extends Exception {
    /**
     * Constructor, allows custom message assignment for thrown exception.
     * @param message
     */
    public BadInputException(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
}
