package org.ssm.exception;

/**
 * File created by FrankWeapon on 7/31/16 for ssm.
 * Email: helldarkfire@gmail.com
 */
public class SeckillException extends RuntimeException{
    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
