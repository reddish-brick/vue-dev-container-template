package com.example.mockserver.util;

import com.example.mockserver.dto.UserDto;

import java.util.ArrayList;
import java.util.List;

public class DataPrepare {

  public static List<UserDto> getAllUsers() {
    ArrayList<UserDto> list = new ArrayList<>();

    list.add(UserDto.builder().id(1).username("红砖").password("123456").age(18).phoneNumber("18271959923").address("武汉").build());
    list.add(UserDto.builder().id(2).username("tony2").password("123456").age(18).phoneNumber("18271959923").address("武汉").build());
    list.add(UserDto.builder().id(3).username("tony3").password("123456").age(18).phoneNumber("18271959923").address("武汉").build());
    list.add(UserDto.builder().id(4).username("tony4").password("123456").age(18).phoneNumber("18271959923").address("武汉").build());
    list.add(UserDto.builder().id(5).username("tony5").password("123456").age(18).phoneNumber("18271959923").address("武汉").build());
    list.add(UserDto.builder().id(6).username("tony6").password("123456").age(18).phoneNumber("18271959923").address("武汉").build());
    list.add(UserDto.builder().id(7).username("tony7").password("123456").age(18).phoneNumber("18271959923").address("武汉").build());
    list.add(UserDto.builder().id(8).username("tony8").password("123456").age(18).phoneNumber("18271959923").address("武汉").build());
    list.add(UserDto.builder().id(9).username("tony9").password("123456").age(18).phoneNumber("18271959923").address("武汉").build());
    list.add(UserDto.builder().id(10).username("tony10").password("123456").age(18).phoneNumber("18271959923").address("武汉").build());
    list.add(UserDto.builder().id(11).username("tony11").password("123456").age(18).phoneNumber("18271959923").address("武汉").build());
    list.add(UserDto.builder().id(12).username("tony12").password("123456").age(18).phoneNumber("18271959923").address("武汉").build());
    list.add(UserDto.builder().id(13).username("tony13").password("123456").age(18).phoneNumber("18271959923").address("武汉").build());
    list.add(UserDto.builder().id(14).username("tony14").password("123456").age(18).phoneNumber("18271959923").address("武汉").build());
    list.add(UserDto.builder().id(15).username("tony15").password("123456").age(18).phoneNumber("18271959923").address("武汉").build());
    list.add(UserDto.builder().id(16).username("tony16").password("123456").age(18).phoneNumber("18271959923").address("武汉").build());
    list.add(UserDto.builder().id(17).username("tony17").password("123456").age(18).phoneNumber("18271959923").address("武汉").build());
    list.add(UserDto.builder().id(19).username("tony18").password("123456").age(18).phoneNumber("18271959923").address("武汉").build());
    list.add(UserDto.builder().id(20).username("tony19").password("123456").age(18).phoneNumber("18271959923").address("武汉").build());
    list.add(UserDto.builder().id(21).username("tony20").password("123456").age(18).phoneNumber("18271959923").address("武汉").build());
    list.add(UserDto.builder().id(22).username("tony21").password("123456").age(18).phoneNumber("18271959923").address("武汉").build());
    list.add(UserDto.builder().id(23).username("tony22").password("123456").age(18).phoneNumber("18271959923").address("武汉").build());
    list.add(UserDto.builder().id(24).username("tony23").password("123456").age(18).phoneNumber("18271959923").address("武汉").build());

    return list;
  }
}
