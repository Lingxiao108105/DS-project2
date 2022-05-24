package edu.service.Impl;

import edu.service.Register;

public class RegisterImpl implements Register {

    public String sayHello(String string){
        System.out.println("RegisterImpl says Hello!");
        return "RegisterImpl: Hello!";
    }

}
