package com.example.mockserver.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDto {
    private Integer id;
    private String username;
    private String password;
    private Integer age;
    private String phoneNumber;
    private String address;
}
