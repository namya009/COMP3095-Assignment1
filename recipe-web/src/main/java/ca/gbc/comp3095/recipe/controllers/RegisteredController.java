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

import ca.gbc.comp3095.recipe.model.*;
import ca.gbc.comp3095.recipe.repositories.IngredientRepository;
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
import java.time.LocalDate;
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

    @Autowired
    IngredientRepository ingredientRepository;

    @RequestMapping({"", "/", "index", "index.html"})
    public String index(Model model,Authentication authentication) {
        model.addAttribute("uName",userRepository.findByUsername(authentication.getName()).getName());
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
        model.addAttribute("recipe_id",recipeId);
        Meal meal=new Meal();
        model.addAttribute("recipe",recipe);
        model.addAttribute("meal", meal);

        return "registered/create-plan";
    }

    @PostMapping(value = "/save")
    public String save(Model model, Recipe recipe, Authentication authentication) {
        User user=userRepository.findByUsername(authentication.getName());
        model.addAttribute("uName",user.getName());
        recipe.setAuthor(user);
        recipe.setDateAdded(LocalDate.now());
        recipe.setTotalTime(recipe.getPrepTime() + recipe.getCookTime());
        user.getRecipe_fav().add(recipe);
        recipe.getUser_fav().add(user);
        recipeRepository.save(recipe);
        String[] ingre=recipe.getTemp_ingre().split(",");
        Ingredient[] ingredients=new Ingredient[ingre.length+1];
        for (int i=0;i< ingre.length;i++) {
            ingredients[i]=new Ingredient(recipe,ingre[i]);
            ingredientRepository.save(ingredients[i]);
        }
        return "/registered/index";
    }

    @PostMapping(value = "/save-meal")
    public String save(Model model,Meal meal, Authentication authentication,HttpServletRequest request) {
        int recipeId= Integer.valueOf(request.getParameter("recipeId"));
        meal.setUser(userRepository.findByUsername(authentication.getName()));
        meal.setRecipe(recipeRepository.findById(recipeId));
        mealRepository.save(meal);
        List<Meal> m = searchService.listMeal(userRepository.findByUsername(authentication.getName()).getId());
        model.addAttribute("count", m.size());
        model.addAttribute("meals", m);
        return "/registered/plan-meal";
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
        model.addAttribute("searchString", "You searched for \" " + searchName+" \".");
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
    @RequestMapping({"/view", "view.html"})
    public String view(Model model, Authentication authentication,HttpServletRequest request) {
        String recipeId=request.getParameter("recipeId");
        Recipe recipe=recipeRepository.findById(Integer.parseInt(recipeId));
        model.addAttribute("recipe",recipe);
        List<Ingredient> i=ingredientRepository.search(Integer.parseInt(recipeId));
        model.addAttribute("ingredient",i);
        return "registered/view";
    }
}