package com.ghassen.spring.web.controllers.viewControllers.adminControllers;

import com.ghassen.spring.domain.Candidature;
import com.ghassen.spring.domain.Role;
import com.ghassen.spring.domain.User;
import com.ghassen.spring.service.CandidatureDtoService;
import com.ghassen.spring.service.CandidatureService;
import com.ghassen.spring.service.CandidatureUpdateDtoService;
import com.ghassen.spring.service.EmailService;
import com.ghassen.spring.service.UserService;
import com.ghassen.spring.service.searching.CandidatureFinder;
import com.ghassen.spring.service.searching.CandidatureSearchErrorResponse;
import com.ghassen.spring.service.searching.CandidatureSearchParameters;
import com.ghassen.spring.service.searching.CandidatureSearchResult;
import com.ghassen.spring.web.dto.CandidatureUpdateDto;
import com.ghassen.spring.web.paging.InitialPagingSizes;
import com.ghassen.spring.web.paging.Pager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.mail.SimpleMailMessage;

/**
 * Created by Keno&Kemo on 20.11.2017..
 */
@Controller
@RequestMapping("/adminPage")
public class CandidaturesController {
    private CandidatureService candidatureService;
    private UserService userService;
    private CandidatureUpdateDtoService candidatureUpdateDtoService;
    private CandidatureDtoService candidatureDtoService;
    private CandidatureFinder candidatureFinder;
    private CandidatureSearchErrorResponse candidatureSearchErrorResponse;
    private EmailService emailService;

    public CandidaturesController(CandidatureService candidatureService, UserService userService, CandidatureUpdateDtoService candidatureUpdateDtoService, CandidatureDtoService candidatureDtoService, CandidatureFinder candidatureFinder, CandidatureSearchErrorResponse candidatureSearchErrorResponse,EmailService emailService) {
        this.candidatureService = candidatureService;
        this.userService = userService;
        this.candidatureUpdateDtoService = candidatureUpdateDtoService;
        this.candidatureDtoService = candidatureDtoService;
        this.candidatureFinder = candidatureFinder;
        this.candidatureSearchErrorResponse = candidatureSearchErrorResponse;
        this.emailService = emailService;
    }

    /*
     * Get all users or search users if there are searching parameters
     */
    @GetMapping("/candidatures")
    public ModelAndView getCandidatures (ModelAndView modelAndView, CandidatureSearchParameters candidatureSearchParameters) {
        int selectedPageSize = candidatureSearchParameters.getPageSize().orElse(InitialPagingSizes.INITIAL_PAGE_SIZE);
        int selectedPage = (candidatureSearchParameters.getPage().orElse(0) < 1) ? InitialPagingSizes.INITIAL_PAGE : (candidatureSearchParameters.getPage().get() - 1);

        PageRequest pageRequest = PageRequest.of(selectedPage, selectedPageSize, new Sort(Sort.Direction.ASC, "id"));
        CandidatureSearchResult candidatureSearchResult = new CandidatureSearchResult();

        //Empty search parameters
        if (!candidatureSearchParameters.getPropertyValue().isPresent() || candidatureSearchParameters.getPropertyValue().get().isEmpty())
            candidatureSearchResult.setCandidaturePage(candidatureDtoService.findAllPageable(pageRequest));

        //Search queries
        else {
            candidatureSearchResult = candidatureFinder.searchCandidaturesByProperty(pageRequest, candidatureSearchParameters);

            if (candidatureSearchResult.isNumberFormatException())
                return candidatureSearchErrorResponse.respondToNumberFormatException(candidatureSearchResult, modelAndView);

            if (candidatureSearchResult.getCandidaturePage().getTotalElements() == 0){
                modelAndView = candidatureSearchErrorResponse.respondToEmptySearchResult(modelAndView, pageRequest);
                candidatureSearchResult.setCandidaturePage(candidatureDtoService.findAllPageable(pageRequest));
            }
            modelAndView.addObject("candidaturesProperty", candidatureSearchParameters.getCandidaturesProperty().get());
            modelAndView.addObject("propertyValue", candidatureSearchParameters.getPropertyValue().get());
        }

        Pager pager = new Pager(candidatureSearchResult.getCandidaturePage().getTotalPages(),
                                candidatureSearchResult.getCandidaturePage().getNumber(),
                                InitialPagingSizes.BUTTONS_TO_SHOW,
                                candidatureSearchResult.getCandidaturePage().getTotalElements());
        modelAndView.addObject("pager", pager);
        modelAndView.addObject("candidatures", candidatureSearchResult.getCandidaturePage());
        modelAndView.addObject("selectedPageSize", selectedPageSize);
        modelAndView.addObject("pageSizes", InitialPagingSizes.PAGE_SIZES);
        modelAndView.setViewName("adminPage/candidature/candidatures");
        return modelAndView;
    }

    @GetMapping("/candidatures/{id}")
    public String getEditCandidatureForm(@PathVariable Long id, Model model) {
        CandidatureUpdateDto candidatureUpdateDto = candidatureUpdateDtoService.findById(id);
        List<User> allUsers = userService.findAll();
        candidatureUpdateDto.setUsers(candidatureService.getAssignedUsersList(candidatureUpdateDto));
        model.addAttribute("candidatureUpdateDto", candidatureUpdateDto);
        model.addAttribute("allUsers", allUsers);
        return "adminPage/candidature/editCandidature";
    }
    @PostMapping("/candidatures/{id}")
    public String updateCandidature(Model model, @PathVariable Long id, @ModelAttribute("oldCandidature") @Valid CandidatureUpdateDto candidatureUpdateDto,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        String formWithErrors = "adminPage/candidature/editCandidature";
        Optional<Candidature> persistedCandidature = candidatureService.findById(id);
        List<User> allUsers = userService.findAll();
        //User emailAlreadyExists = userService.findByEmailAndIdNot(userUpdateDto.getEmail(), id);
        //User usernameAlreadyExists = userService.findByUsernameAndIdNot(userUpdateDto.getUsername(), id);
        boolean hasErrors = false;
//        if (emailAlreadyExists != null) {
//            bindingResult.rejectValue("email", "emailAlreadyExists");
//            hasErrors = true;
//        }
//
//        if (usernameAlreadyExists != null) {
//            bindingResult.rejectValue("username", "usernameAlreadyExists");
//            hasErrors = true;
//        }
        if (bindingResult.hasErrors()) hasErrors = true;
        if (hasErrors) {
            model.addAttribute("candidatureUpdateDto", candidatureUpdateDto);
            model.addAttribute("allUsers", allUsers);
            model.addAttribute("org.springframework.validation.BindingResult.candidatureUpdateDto", bindingResult);
            return formWithErrors;
        }
        else {
              List<User> users = userService.findByCandidatureEagerly(persistedCandidature.get().getId());
              candidatureService.save(candidatureService.getUpdatedCandidature(persistedCandidature.get(), candidatureUpdateDto));                           
              
              for(User user:users)
              {
                  SimpleMailMessage registrationEmail = new SimpleMailMessage();                            
                  registrationEmail.setTo(user.getEmail());
                  registrationEmail.setSubject("Dossier de candidature");
                  registrationEmail.setText("Dossier Numero"+persistedCandidature.get().getId());
                  emailService.sendEmail(registrationEmail);
              }
              
              

                             
//            cand.getUsers().stream().map((user) -> {
//                registrationEmail.setTo(emails)
//                return user;
//            }).map((_item) -> {
//                registrationEmail.setSubject("Dossier de candidature");
//                return _item;
//            }).map((_item) -> {
//                registrationEmail.setText("Dossier Numero"+persistedCandidature.get().getId());
//                return _item;            
//            }).forEachOrdered((_item) -> {
//                emailService.sendEmail(registrationEmail);
//            });
            redirectAttributes.addFlashAttribute("candidatureHasBeenUpdated", true);
            return "redirect:/adminPage/candidatures";
        }
    }

//    @GetMapping("/users/newUser")
//    public String getAddNewUserForm(Model model) {
//        model.addAttribute("newUser", new UserDto());
//        return "adminPage/user/newUser";
//    }
//
//    @PostMapping("/users/newUser")
//    public String saveNewUser(@ModelAttribute("newUser") @Valid UserDto newUser, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
//        User emailAlreadyExists = userService.findByEmail(newUser.getEmail());
//        User usernameAlreadyExists = userService.findByUsername(newUser.getUsername());
//        boolean hasErrors = false;
//        String formWithErrors = "adminPage/user/newUser";
//
//        if (emailAlreadyExists != null) {
//            bindingResult.rejectValue("email", "emailAlreadyExists");
//            hasErrors = true;
//        }
//
//        if (usernameAlreadyExists != null) {
//            bindingResult.rejectValue("username", "usernameAlreadyExists");
//            hasErrors = true;
//        }
//
//        if (bindingResult.hasErrors()) hasErrors = true;
//
//        if (hasErrors) return formWithErrors;
//
//        else {
//            User user = userService.createNewAccount(newUser);
//            user.setEnabled(true);
//
//            userService.save(user);
//            redirectAttributes.addFlashAttribute("userHasBeenSaved", true);
//            return "redirect:/adminPage/users";
//        }
//    }

}
