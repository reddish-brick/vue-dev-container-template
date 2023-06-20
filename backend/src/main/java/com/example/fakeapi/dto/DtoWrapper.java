package com.example.fakeapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoWrapper<T> {

  private Integer resultCode;
  private String message;
  private T data;
  private Integer total;
  private Boolean success;
}
