package com.microservice.marks.auth.data.vo;

import lombok.Data;
import lombok.ToString;

@Data
public class UserVO {

    private Long id;
    private String userName;
    private String password;

}
