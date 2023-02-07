package hello.springtx.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/")
    public String ok() {
        return "<h1>성공 ~ ! </h1> " +
                "<h1>성공 ~ ! </h1>" +
                "<h1>성공 ~ ! </h1>" +
                "<h1>성공 ~ ! </h1>" +
                "<h1>성공 ~ ! </h1>" +
                "<h1>성공 ~ ! </h1>" +
                "<h1>성공 ~ ! </h1>" +
                "<h1>성공 ~ ! </h1>" +
                "<h1>성공 ~ ! </h1>" +
                "<h1>성공 ~ ! </h1>";
    }
}
