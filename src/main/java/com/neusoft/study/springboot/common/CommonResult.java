package com.neusoft.study.springboot.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * <p>Title: com.neusoft.study.springboot.common</p>
 * <p>Company:东软集团(neusoft)</p>
 * <p>Copyright:Copyright(c)</p>
 * User: Administrator
 * Date: 2019/10/4 9:38
 * Description: 统一返回对象，封装固定返回格式
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public final class CommonResult<T> implements Serializable {

    private int status = HttpStatus.OK.value();

    private String message;

    private String errorCode = "";

    private String errorMsg = "";

    private T resultBody;

    public CommonResult(T resultBody) {
        this.resultBody = resultBody;
    }

    public CommonResult(T resultBody,String message) {
        this.message = message;
        this.resultBody = resultBody;
    }

    /**
     * 统一返回成功处理
     * @param message
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> successResult(String message){
        CommonResult<T> commonResult = new CommonResult<>();
        commonResult.message = message;
        return commonResult;
    }

    /**
     * 统一异常处理，返回错误结果
     * @param errorCode
     * @param errorMsg
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> errorResult(String errorCode, String errorMsg){
        CommonResult<T> commonResult = new CommonResult<>();
        commonResult.errorCode = errorCode;
        commonResult.errorMsg = errorMsg;
        commonResult.status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        return commonResult;
    }

    /**
     * 业务需要，不是错误，只是提醒操作
     * @param warnCode
     * @param warnMsg
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> warnResult(String warnCode, String warnMsg){
        CommonResult<T> commonResult = new CommonResult<>();
        commonResult.errorCode = warnCode;
        commonResult.errorMsg = warnMsg;
        commonResult.status = HttpStatus.MULTIPLE_CHOICES.value();
        return commonResult;
    }
}
