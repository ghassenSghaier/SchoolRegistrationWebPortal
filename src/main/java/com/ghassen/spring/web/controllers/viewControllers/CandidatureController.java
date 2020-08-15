package com.ghassen.spring.web.controllers.viewControllers;
import com.ghassen.spring.domain.Candidature;
import com.ghassen.spring.service.CandidatureService;
import com.ghassen.spring.service.EmailService;
import com.ghassen.spring.web.dto.CandidatureDto;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.mail.SimpleMailMessage;

@Controller
@RequestMapping("")
public class CandidatureController {
    private CandidatureService candidatureService;
    private EmailService emailService;

    public CandidatureController(CandidatureService candidatureService, EmailService emailService) {
        this.candidatureService = candidatureService;
        this.emailService = emailService;
    }

    @PostMapping(value = "/submit-candidature")
    public ModelAndView saveUser(ModelAndView modelAndView, @ModelAttribute("candidatureDto") @Valid final CandidatureDto candidatureDto,
                                 BindingResult bindingResult, HttpServletRequest request, Errors errors){

        Candidature emailExists = candidatureService.findByEmail(candidatureDto.getEmail());
        //User userNameExists = userService.findByUsername(userDto.getUsername());

        System.out.println(emailExists);

        if (emailExists != null) {
            modelAndView.setViewName("website/inscription");
            bindingResult.rejectValue("email", "emailAlreadyExists");
        }

        /**if (userNameExists!= null) {
            modelAndView.setViewName("website/register");
            bindingResult.rejectValue("username", "usernameAlreadyExists");
        } **/

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("website/inscription");
        }
        else { // new user so we create user and send confirmation e-mail
            
            Candidature candidature = candidatureService.createNewCandidature(candidatureDto);                        
            candidature.setEnabled(true);
            candidatureService.save(candidature);
            // Disable user until they click on confirmation link in email
            SimpleMailMessage registrationEmail = new SimpleMailMessage();
            registrationEmail.setTo(candidature.getEmail());
            registrationEmail.setSubject("confirmation d'inscription");
            registrationEmail.setText("Please confirm the registration");
            registrationEmail.setFrom("email@email.com");
            emailService.sendEmail(registrationEmail);
            String appUrl = request.getScheme() + "://" + request.getServerName();
            modelAndView.addObject("confirmationMessage", "A confirmation e-mail has been sent to "
                                    + candidatureDto.getEmail());
            modelAndView.setViewName("website/inscrit");
        }
        return modelAndView;
    }
}
