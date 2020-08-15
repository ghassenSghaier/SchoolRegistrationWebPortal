package com.ghassen.spring.web.controllers.viewControllers;

import com.ghassen.spring.web.dto.CandidatureDto;
import com.ghassen.spring.web.dto.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

/**
 * Created by Keno&Kemo on 30.09.2017..
 */

@Controller
@RequestMapping("")
public class IndexController {

    @GetMapping(value = {"/", "/index"})
    public String index (){
        return "website/index";
    }

    @GetMapping(value = "/login")
    public String login (){
        return "website/login";
    }

    @GetMapping(value = "/register")
    public String showRegistrationForm(WebRequest request, Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);
        return "website/register";
    }
     @GetMapping(value = "/inscription")
    public String showInscriptionForm(WebRequest request, Model model) {
        CandidatureDto candidatureDto = new CandidatureDto();
        model.addAttribute("candidatureDto", candidatureDto);
        return "website/inscription";
    }   
}
