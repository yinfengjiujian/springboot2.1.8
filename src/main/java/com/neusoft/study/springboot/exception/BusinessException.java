package com.neusoft.study.springboot.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <p>Title: com.neusoft.study.springboot.exception</p>
 * <p>Company:东软集团(neusoft)</p>
 * <p>Copyright:Copyright(c)</p>
 * User: Administrator
 * Date: 2019/10/4 12:14
 * Description: No Description
 */
@Data
@AllArgsConstructor
public class BusinessException extends RuntimeException {

    private String errorCode;

    private String errorMsg;
}
