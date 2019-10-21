package com.overstar.search.export.exception;

import lombok.Data;

/**
 * @Description
 * @Author stanley.yu
 * @Date 2019/9/10 15:50
 */
@Data
public class SysException extends RuntimeException {
    private String msg;
    private Integer error;
}
