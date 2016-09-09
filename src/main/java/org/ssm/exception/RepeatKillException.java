package org.ssm.exception;

/**
 * File created by FrankWeapon on 7/31/16 for ssm.
 * Email: helldarkfire@gmail.com
 */
public class RepeatKillException extends SeckillException{

    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
