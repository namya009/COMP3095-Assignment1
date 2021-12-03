/**********************************************************************************
 * Project: Recipe Project
 * Assignment: Assignment 1
 * Author(s): Namya Patel
 *            Pruthvi Soni
 *            Prishita Ribadia
 *            Sahay Patel
 * Student ID: 101281322
 *             101276714
 *             101284685
 *             101283555
 * Date: 7th Nov
 * Description: This file is used to control our pages for non-registered users.
 **********************************************************************************/
package ca.gbc.comp3095.recipe.controllers;

import ca.gbc.comp3095.recipe.model.User;
import ca.gbc.comp3095.recipe.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class IndexController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping({"/register", "register.html"})
    public String index(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "/register";
    }

    @PostMapping(value = "/save")
    public String save(User user, Model model) {
        if(userRepository.findByUsername(user.getUsername()) != null){
            model.addAttribute("err","The username <b>"+user.getUsername()+"</b> is already in use.");
            return "/register";
        }
        String fname=user.getName();
        user.setName(fname.substring(0, 1).toUpperCase() + fname.substring(1));
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return "/login";
    }

    @RequestMapping(value = {"", "/", "/login", "/login.html"}, method = RequestMethod.GET)
    public String showLoginPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "/login";
        }
        return "redirect:/registered/index";
    }

    @RequestMapping({"/logout", "logout.html"})
    public String logout() {
        return "/logout";
    }

}
