/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.balamut2227.rsdemo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Balamut2227
 */

@Controller
@RequestMapping("/app")
public class AppController {
    
    @RequestMapping(value="/", method = RequestMethod.GET)
    public String go() {
        return "index";
    }
    
}
