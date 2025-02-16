package eu.tony.matematika.controller;

import eu.tony.matematika.domain.Operator;
import eu.tony.matematika.domain.Variables;
import eu.tony.matematika.service.TaskService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
  public String generateExercises(Model model) {
    List<Exercise> exercises = new ArrayList<>();

    for (int i = 0; i < 3; i++) {
      Variables variables = new Variables();

      do {
        variables.setNum1(random.nextInt(100) + 1);
        variables.setNum2(random.nextInt(100) + 1);
        variables.setOperator(Operator.getRandomOperator());
        variables.setAnswer(taskService.getAnswer(variables));
      } while (variables.getAnswer() < 0 || variables.getAnswer() > 100);

      exercises.add(new Exercise(taskService.getCorrectTask(variables), variables.getAnswer()));
    }

    model.addAttribute("exercises", exercises);
    return "math-exercise";
  }

  @PostMapping("/check-answers")
  public String checkAnswers(@RequestParam Map<String, String> params, Model model) {
    List<String> resultMessages = new ArrayList<>();

    // Process answers
    for (int i = 0; i < params.size() / 3; i++) {
      String task = params.get("tasks[" + i + "]");
      int userAnswer = Integer.parseInt(params.get("userAnswers[" + i + "]"));
      int correctAnswer = Integer.parseInt(params.get("correctAnswers[" + i + "]"));

      if (userAnswer == correctAnswer) {
        resultMessages.add("Užduotis " + (i + 1) + " (" + task + "): Teisingai! " + userAnswer +" ✅");
      } else {
        resultMessages.add("Užduotis " + (i + 1) + " (" + task + "): Klaida ❌ Teisingas atsakymas: " + correctAnswer + " (Jūsų atsakymas: " + userAnswer + ")");
      }
    }

    // ✅ Repopulate the model before returning the view
    model.addAttribute("resultMessages", resultMessages);

    return "results";
  }

  private static class Exercise {
    private final String task;
    private final int correctAnswer;

    public Exercise(String task, int correctAnswer) {
      this.task = task;
      this.correctAnswer = correctAnswer;
    }

    public String getTask() {
      return task;
    }

    public int getCorrectAnswer() {
      return correctAnswer;
    }
  }
}
