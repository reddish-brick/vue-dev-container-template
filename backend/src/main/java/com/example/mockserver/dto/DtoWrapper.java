package com.example.mockserver.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DtoWrapper<T> {

  private Integer code;
  private String message;
  private T data;
  private Integer total;
  private Boolean success;
}
