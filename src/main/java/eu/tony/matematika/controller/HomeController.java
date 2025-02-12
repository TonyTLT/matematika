package eu.tony.matematika.controller;

import eu.tony.matematika.domain.Operator;
import eu.tony.matematika.domain.Variables;
import eu.tony.matematika.service.TaskService;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class HomeController {

  private final TaskService taskService;

  private final Random random = new Random();

  @GetMapping("/math-exercise")
  public String generateExercise(Model model) {
    Variables variables = new Variables();
    while (variables.getAnswer() < 0 || variables.getAnswer() > 100) {
      variables.setNum1(random.nextInt(100) + 1);
      variables.setNum2(random.nextInt(100) + 1);
      variables.setOperator(Operator.getRandomOperator());
      variables.setAnswer(taskService.getAnswer(variables));
    }

    model.addAttribute("task", taskService.getCorrectTask(variables));
    model.addAttribute("correctAnswer", variables.getAnswer());

    return "math-exercise";
  }

  @PostMapping("/check-answer")
  public String checkAnswer(
      @RequestParam int userAnswer, @RequestParam int correctAnswer, Model model) {
    String resultMessage;
    if (userAnswer == correctAnswer) {
      resultMessage = "Teisingai! Puiku!";
    } else {
      resultMessage = "Neteisingai. Atsakymas yra " + correctAnswer + ".";
    }

    model.addAttribute("resultMessage", resultMessage);
    model.addAttribute("task", "");
    return "math-exercise";
  }
}
