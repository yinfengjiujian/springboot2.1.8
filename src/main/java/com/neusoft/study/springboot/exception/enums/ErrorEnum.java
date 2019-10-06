package com.neusoft.study.springboot.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>Title: com.neusoft.study.springboot.exception</p>
 * <p>Company:东软集团(neusoft)</p>
 * <p>Copyright:Copyright(c)</p>
 * User: Administrator
 * Date: 2019/10/4 17:40
 * Description: No Description
 */
@Getter
@AllArgsConstructor
public enum ErrorEnum {

    PARAM_FAIL_CODE(10002,"参数错误异常！"),
    VALIDATION_FAIL_CODE(10003,"参数校验出错，请确认！"),
    METHOD_NOT_SUPPORTED(10004,"不支持的方法调用，请确认！"),
    DUPLICATE_KEY_CODE(10005,"数据重复,请检查后提交");

    private Integer code;

    private String message;
}
