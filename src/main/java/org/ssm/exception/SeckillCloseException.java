package org.ssm.exception;

/**
 * File created by FrankWeapon on 7/31/16 for ssm.
 * Email: helldarkfire@gmail.com
 */
public class SeckillCloseException extends SeckillException{
    public SeckillCloseException(String message) {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
