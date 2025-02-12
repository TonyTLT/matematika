package eu.tony.matematika.domain;

import lombok.Data;

@Data
public class Variables {
  int num1 = -1;
  int num2 = -1;
  Operator operator;
  int answer = -1;
}
