package com.meli.pronostico.sistemasolar.controllers.web;
 
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
 
@Controller
@ControllerAdvice(annotations=Controller.class)
public class ErrorHtmlController {
 
    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorHtmlController.class);
 
    @ExceptionHandler
    public String handleException(Exception ex, HttpServletRequest request, Model model) {
        LOGGER.info("executing exception handler (web)");
 
        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        model.addAttribute("error", ex.getClass().getName());
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("path", request.getRequestURI());
 
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
 
        model.addAttribute("trace", sw.toString());
        return "error";
    }
}
