package com.hwua.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Component
public class ResponseData <T> {
    //响应的标号
    private Integer code;
    //响应对象
    private T t;
    //详细的错误描述信息
    private String message;
    private  String accessToken;
    private  String refreshToken;
}
