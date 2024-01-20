package com.atguigu.common.config.exception;

import com.atguigu.common.result.ResultCodeEnum;
import lombok.Data;

/**
 * 自定义全局异常类
 *
 */
@Data
public class GuiguException extends RuntimeException {

    private Integer code;

    private String msg;

    public GuiguException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
//         * 接收枚举类型对象
//     * @param resultCodeEnum
//
    public GuiguException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
        this.msg = resultCodeEnum.getMessage();
    }

    @Override
    public String toString() {
        return "GuiguException{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
