package com.example.mockserver.controller;

import com.example.mockserver.dto.DtoWrapper;
import com.example.mockserver.dto.UserDto;
import com.example.mockserver.util.DataPrepare;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserController {

  List<UserDto> list = DataPrepare.getAllUsers();

  @GetMapping(value = "/list")
  public DtoWrapper<List<UserDto>> getAllUsers(@RequestParam Integer current,
      @RequestParam Integer size, @RequestParam String name) {
    List<UserDto> users = list.stream().filter(user -> {
      if (name.equals("")) {
        return true;
      }
      return name.equals(user.getUsername());
    }).collect(Collectors.toList());

    int startIndex = (current - 1) * size;
    int endIndex = Math.min(startIndex + size, users.size());

    return DtoWrapper.<List<UserDto>>builder()
        .code(HttpStatus.OK.value())
        .success(true)
        .data(users.subList(startIndex, endIndex))
        .total(users.size())
        .message("成功返回").build();
  }

  @PostMapping(value = "/login")
  public DtoWrapper<UserDto> login(@RequestBody UserDto userDto) {
    UserDto loginUser = list.stream()
        .filter(
            user -> Objects.equals(userDto.getUsername(), user.getUsername())
                && Objects.equals(userDto.getPassword(), user.getPassword()))
        .findAny().get();

    return DtoWrapper.<UserDto>builder().code(HttpStatus.OK.value()).data(loginUser).success(true).message("成功返回")
        .build();
  }

  @GetMapping(value = "/detail")
  public DtoWrapper<UserDto> detail(@RequestParam Integer id) {
    UserDto userDto = list.stream()
        .filter(user -> Objects.equals(id, user.getId()))
        .findAny().get();

    return DtoWrapper.<UserDto>builder().code(HttpStatus.OK.value()).data(userDto).success(true).message("成功返回")
        .build();
  }

  @DeleteMapping(value = "/delete")
  public DtoWrapper<UserDto> deleteById(@RequestParam Integer id) {
    list.removeIf(userDto -> userDto.getId().equals(id));
    return DtoWrapper.<UserDto>builder().code(HttpStatus.OK.value()).success(true).message("删除成功").build();
  }

  @PostMapping(value = "/detail")
  public DtoWrapper<UserDto> detail(@RequestBody UserDto userDto) {
    list.replaceAll(user -> {
      if (Objects.equals(userDto.getId(), user.getId())) {
        UserDto build = UserDto.builder()
            .address(userDto.getAddress())
            .phoneNumber(userDto.getPhoneNumber())
            .age(userDto.getAge())
            .username(userDto.getUsername())
            .password(userDto.getPassword())
            .id(userDto.getId())
            .build();
        return build;
      }
      return user;
    });
    return DtoWrapper.<UserDto>builder().code(HttpStatus.OK.value()).data(userDto).success(true).message("修改成功")
        .build();
  }
}