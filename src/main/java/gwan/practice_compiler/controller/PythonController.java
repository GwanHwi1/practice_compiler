package gwan.practice_compiler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Controller
public class PythonController {

    @GetMapping("hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("main")
    public String base() {
        System.out.println("aaaaaaaaaaa");
        return "index";
    }

    @PostMapping("/runPythonCode")
    public String runPythonCode(@RequestParam("code") String code) {
        try {
            // 실행 스크립트 호출
            ProcessBuilder processBuilder = new ProcessBuilder("bash", "src/main/resources/scripts/executePython.sh", code);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            // 실행 결과 읽기
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                StringBuilder output = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }

                // 이 부분에서 output을 적절히 처리하거나 모델에 저장하여 결과를 전달합니다.
                System.out.println(output.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

            // 프로세스 종료 대기
            int exitCode = process.waitFor();
            System.out.println("Execution finished with exit code: " + exitCode);

            // 이 부분에서 결과를 적절히 반환하거나 뷰로 이동합니다.
            return "redirect:/result";
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            // 예외 처리 로직을 추가할 수 있습니다.
            return "redirect:/error";
        }
    }
}
