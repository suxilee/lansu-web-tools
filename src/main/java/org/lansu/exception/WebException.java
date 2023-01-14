package org.lansu.exception;

import lombok.Data;
import org.lansu.result.ResultCode;

/**
 * web异常
 *
 * @author lansu
 * @date 2023/01/14
 */
@Data
public class WebException extends RuntimeException implements ResultCode {
    private Integer code;

    private String message;

    private Boolean success;

    public WebException(String message) {
        super(message);
    }

    public WebException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.message = resultCode.getMessage();

    }

    @Override
    public Boolean getSuccess() {
        return success;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}

