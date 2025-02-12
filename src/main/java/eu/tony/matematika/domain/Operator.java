package eu.tony.matematika.domain;

import java.util.Random;

public enum Operator {
  ADD("+"),
  SUBTRACT("-"),
  MULTIPLY("*"),
  DIVIDE("/");

  private final String symbol;

  Operator(String symbol) {
    this.symbol = symbol;
  }

  public String getSymbol() {
    return symbol;
  }

  public static Operator getRandomOperator() {
    Random random = new Random();
    return values()[random.nextInt(2)];
  }
}
