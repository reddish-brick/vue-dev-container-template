package com.example.mockserver.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchForm {

  private Integer current;
  private Integer size;
  private String name;
}
