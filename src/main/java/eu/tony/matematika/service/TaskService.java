package eu.tony.matematika.service;

import eu.tony.matematika.domain.Variables;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

  public int getAnswer(Variables variables) {
    int num1 = variables.getNum1();
    int num2 = variables.getNum2();
    switch (variables.getOperator()) {
      case ADD:
        return num1 + num2;
      case SUBTRACT:
        return num1 - num2;
      case MULTIPLY:
        return num1 * num2;
      case DIVIDE:
        if (num2 != 0) {
          return num1 / num2;
        } else {
          return 0;
        }
      default:
        throw new IllegalArgumentException("Invalid operator");
    }
  }

  public String getCorrectTask(Variables variables) {
    switch (variables.getOperator()) {
      case ADD:
        return variables.getNum1() + " + " + variables.getNum2() + " = ?";
      case SUBTRACT:
        return variables.getNum1() + " - " + variables.getNum2() + " = ?";
      case MULTIPLY:
        return variables.getNum1() + " * " + variables.getNum2() + " = ?";
      case DIVIDE:
        return variables.getNum1() + " / " + variables.getNum2() + " = ?";
      default:
        throw new IllegalArgumentException("Invalid operator");
    }
  }
}
