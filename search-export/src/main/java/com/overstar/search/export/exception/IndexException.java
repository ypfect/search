package com.overstar.search.export.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author stanley.yu
 * @Date 2019/9/18 10:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndexException extends RuntimeException {
    private String msg;
    private int code;
}
