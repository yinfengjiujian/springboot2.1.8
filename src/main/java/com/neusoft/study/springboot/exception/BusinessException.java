package com.neusoft.study.springboot.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>Title: com.neusoft.study.springboot.exception</p>
 * <p>Company:东软集团(neusoft)</p>
 * <p>Copyright:Copyright(c)</p>
 * User: Administrator
 * Date: 2019/10/4 12:14
 * Description: No Description
 */
@Data
public class BusinessException extends RuntimeException {

    private String errorCode;

    private String errorMsg;

    private final Integer code = 100000;

    public BusinessException() {

    }
    public BusinessException(String message) {
        this.errorMsg = message;
        this.errorCode = code.toString();
    }

    public BusinessException(String errorCode,String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

}
