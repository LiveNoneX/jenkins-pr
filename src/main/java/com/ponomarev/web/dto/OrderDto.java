package com.ponomarev.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

  private Long id;
  private String hash;
  private String producer;
  private String consumer;
}
