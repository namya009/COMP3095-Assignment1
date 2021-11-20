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
 * Date: 4th Nov
 * Description: This file is used to control all pages available to registered users.
 **********************************************************************************/
package ca.gbc.comp3095.recipe.controllers;

import ca.gbc.comp3095.recipe.model.Meal;
import ca.gbc.comp3095.recipe.model.Recipe;
import ca.gbc.comp3095.recipe.repositories.MealRepository;
import ca.gbc.comp3095.recipe.repositories.RecipeRepository;
import ca.gbc.comp3095.recipe.repositories.UserRepository;
import ca.gbc.comp3095.recipe.services.SearchService;
import ca.gbc.comp3095.recipe.services.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


@RequestMapping("/registered")
@Controller
public class RegisteredController {

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    MealRepository mealRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SearchService searchService;

    @Autowired
    ViewService viewService;

    @RequestMapping({"", "/", "index", "index.html"})
    public String index(Model model,Authentication authentication) {
        model.addAttribute("uName",userRepository.findByUsername(authentication.getName()).getFirstName()+" "+userRepository.findByUsername(authentication.getName()).getLastName());
        return "registered/index";
    }

    @RequestMapping({"/create", "/create-recipe", "create-recipe.html"})
    public String create(Model model) {
        Recipe recipe = new Recipe();
        model.addAttribute("recipe", recipe);
        return "registered/create-recipe";
    }
    @RequestMapping({"/create-plan", "create-plan.html"})
    public String createPlan(Model model, Recipe recipe,HttpServletRequest request) {
        String recipeId=request.getParameter("recipeId");
        Meal meal=new Meal();
        System.out.println(recipeId);
        System.out.print(recipe.getRecipeName());
        model.addAttribute("recipe",recipe);
        model.addAttribute("meal", meal);

        return "registered/create-plan";
    }

    @PostMapping(value = "/save")
    public String save(Recipe recipe, Authentication authentication) {
        recipe.setAuthor(userRepository.findByUsername(authentication.getName()));
        recipe.setDateAdded(new Date());
        recipe.setTotalTime(recipe.getPrepTime() + recipe.getCookTime());
        recipeRepository.save(recipe);
        return "/registered/index";
    }

    @PostMapping(value = "/save-meal")
    public String save(Meal meal, Authentication authentication) {
        meal.setUser(userRepository.findByUsername(authentication.getName()));

        mealRepository.save(meal);
        return "/registered/index";
    }

    @RequestMapping({"/plan", "/plan-meal", "plan-meal.html"})
    public String plan(Model model , Authentication authentication) {
        List<Meal> meal = searchService.listMeal(userRepository.findByUsername(authentication.getName()).getId());
        model.addAttribute("count", meal.size());
        if (meal.size() > 0) {
            model.addAttribute("meals", meal);
        } else {
            model.addAttribute("message", "No record Found");
        }
        return "registered/plan-meal";
    }

    @RequestMapping(value = {"search", "/search-recipe", "/search-recipe.html"}, method = RequestMethod.GET)
    public String search(Model model) {
        model.addAttribute("recipe", new Recipe());
        return "registered/search-recipe";
    }

    @RequestMapping(value = {"search", "/search-recipe", "/search-recipe.html"}, method = RequestMethod.POST)
    public String search(HttpServletRequest request, Model model) {
        String searchName = request.getParameter("name");
        model.addAttribute("searchString", "You searched for \"" + searchName+"\".");
        List<Recipe> resp = searchService.listAll(searchName);
        model.addAttribute("count", resp.size());
        if (resp.size() > 0) {
            model.addAttribute("recipes", resp);
        } else {
            model.addAttribute("message", "No record Found");
        }
        return "/registered/search-recipe";
    }

    @RequestMapping({"/view-profile", "view-profile.html"})
    public String viewProfile(Model model, Authentication authentication) {
        List<Recipe> resp = searchService.listAllForUser(userRepository.findByUsername(authentication.getName()).getId());
        model.addAttribute("user",userRepository.findByUsername(authentication.getName()));
        model.addAttribute("count", resp.size());
        if (resp.size() > 0) {
            model.addAttribute("recipes", resp);
        } else {
            model.addAttribute("message", "No record Found");
        }
        return "registered/view-profile";
    }

    @RequestMapping({"/view-recipe", "view-recipe.html"})
    public String viewRecipe(Model model) {
        List<Recipe> resp = viewService.findAll();
        model.addAttribute("count", resp.size());
        if (resp.size() > 0) {
            model.addAttribute("recipes", resp);
        } else {
            model.addAttribute("message", "No record Found");
        }
        return "/registered/view-recipe";
    }
}