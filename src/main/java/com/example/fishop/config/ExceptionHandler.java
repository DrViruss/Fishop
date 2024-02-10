package com.example.fishop.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(NoHandlerFoundException.class)
    public String handle(HttpServletRequest req, Exception e, Model model) throws Exception {
        if (AnnotationUtils.findAnnotation (e.getClass(), ResponseStatus.class) != null)
            throw e;

        model.addAttribute("request",req.getRequestURL());
        model.addAttribute("method",req.getMethod());
//        model.addAttribute("error",e);
        return "error";
    }
}
