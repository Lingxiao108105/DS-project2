package edu.service.Impl;

import edu.service.CanvasService;

public class CanvasServiceImpl implements CanvasService {

    public String sayHello(){
        System.out.println("CanvasService says Hello!");
        return "CanvasService: Hello!";
    }
}
