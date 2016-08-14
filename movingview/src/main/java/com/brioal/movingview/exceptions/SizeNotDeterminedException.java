package com.brioal.movingview.exceptions;

/**宽高都未指明的异常类
 * Created by Brioal on 2016/8/13.
 */

public class SizeNotDeterminedException extends Exception {
    public SizeNotDeterminedException(String message) {
        super(message);
    }
}
