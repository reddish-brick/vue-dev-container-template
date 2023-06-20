package com.example.fakeapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

  private Integer id;
  private String username;
  private String password;
  private Integer age;
  private String phoneNumber;
  private String address;
}
