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

import ca.gbc.comp3095.recipe.model.Ingredient;
import ca.gbc.comp3095.recipe.model.Meal;
import ca.gbc.comp3095.recipe.model.Recipe;
import ca.gbc.comp3095.recipe.model.User;
import ca.gbc.comp3095.recipe.repositories.IngredientRepository;
import ca.gbc.comp3095.recipe.repositories.MealRepository;
import ca.gbc.comp3095.recipe.repositories.RecipeRepository;
import ca.gbc.comp3095.recipe.repositories.UserRepository;
import ca.gbc.comp3095.recipe.services.SearchService;
import ca.gbc.comp3095.recipe.services.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;


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
    public String index(Model model, Authentication authentication) {
        model.addAttribute("uName", userRepository.findByUsername(authentication.getName()).getName());
        return "registered/index";
    }

    @RequestMapping({"/create", "/create-recipe", "create-recipe.html"})
    public String create(Model model) {
        Recipe recipe = new Recipe();
        model.addAttribute("recipe", recipe);

        return "registered/create-recipe";
    }

    @RequestMapping({"/create-plan", "create-plan.html"})
    public String createPlan(Model model, Recipe recipe, HttpServletRequest request) {
        String recipeId = request.getParameter("recipeId");
        model.addAttribute("recipe_id", recipeId);
        Meal meal = new Meal();
        model.addAttribute("recipe", recipe);
        model.addAttribute("meal", meal);

        return "registered/create-plan";
    }

    @PostMapping(value = "/save")
    public String save(Model model, Recipe recipe, Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName());
        model.addAttribute("uName", user.getName());
        recipe.setAuthor(user);
        recipe.setDateAdded(LocalDate.now());
        recipe.setTotalTime(recipe.getPrepTime() + recipe.getCookTime());
        recipeRepository.save(recipe);
        String[] ingre = recipe.getTemp_ingre().split(",");
        Ingredient[] ingredients = new Ingredient[ingre.length + 1];
        for (int i = 0; i < ingre.length; i++) {
            ingredients[i] = new Ingredient(recipe, ingre[i]);
            ingredientRepository.save(ingredients[i]);
        }
        return "/registered/index";
    }

    @PostMapping(value = "/save-meal")
    public String save(Model model, Meal meal, Authentication authentication, HttpServletRequest request) {
        int recipeId = Integer.valueOf(request.getParameter("recipeId"));
        meal.setUser(userRepository.findByUsername(authentication.getName()));
        meal.setRecipe(recipeRepository.findById(recipeId));
        mealRepository.save(meal);
        List<Meal> m = searchService.listMeal(userRepository.findByUsername(authentication.getName()).getId());
        model.addAttribute("count", m.size());
        model.addAttribute("meals", m);
        return "/registered/plan-meal";
    }

    @PostMapping(value = "/add-favorite")
    public ModelAndView addFav(Model model, HttpServletRequest request, Authentication authentication) {
        String recipeId = request.getParameter("recipeId");
        Recipe recipe = recipeRepository.findById(Integer.parseInt(recipeId));
        User user = userRepository.findByUsername(authentication.getName());
        model.addAttribute("recipe", recipe);
        List<Ingredient> i = ingredientRepository.search(Integer.parseInt(recipeId));
        model.addAttribute("ingredient", i);
        boolean isAuthorised = recipe.getAuthor().getId() == user.getId();
        model.addAttribute("isAuthorised", isAuthorised);
        List<Recipe> resp = searchService.listFav(recipeId, Integer.toString(user.getId()));
        if (resp.isEmpty()) {
            user.getRecipe_fav().add(recipe);
            recipe.getUser_fav().add(user);
        } else {
            user.getRecipe_fav().remove(recipe);
            recipe.getUser_fav().remove(user);
        }
        recipeRepository.save(recipe);
        return new ModelAndView("redirect:/registered/view-recipe?recipeId=" + recipeId);
    }

    @RequestMapping({"/plan", "/plan-meal", "plan-meal.html"})
    public String plan(Model model, Authentication authentication) {
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
        model.addAttribute("searchString", "You searched for \" " + searchName + " \".");
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
        List<Recipe> resp = searchService.listFavForUser(userRepository.findByUsername(authentication.getName()).getId());
        model.addAttribute("user", userRepository.findByUsername(authentication.getName()));
        model.addAttribute("count", resp.size());
        if (resp.size() > 0) {
            model.addAttribute("recipes", resp);
        } else {
            model.addAttribute("message", "No record Found");
        }
        List<Recipe> resp2 = searchService.listForUser(userRepository.findByUsername(authentication.getName()).getId());
        model.addAttribute("count2", resp2.size());
        if (resp2.size() > 0) {
            model.addAttribute("recipes2", resp2);
        } else {
            model.addAttribute("message2", "No record Found");
        }
        return "/registered/view-profile";
    }

    @RequestMapping({"/view-recipes", "view-recipes.html"})
    public String viewRecipes(Model model) {
        List<Recipe> resp = viewService.findAll();
        model.addAttribute("count", resp.size());
        if (resp.size() > 0) {
            model.addAttribute("recipes", resp);
        } else {
            model.addAttribute("message", "No record Found");
        }
        return "/registered/view-recipes";
    }

    @RequestMapping({"/view-recipe", "view-recipe.html"})
    public String viewRecipe(Model model, Authentication authentication, HttpServletRequest request) {
        String recipeId = request.getParameter("recipeId");
        Recipe recipe;
        Boolean isAuthorised = false;
        if (recipeId != null) {
            recipe = recipeRepository.findById(Integer.parseInt(recipeId));
            model.addAttribute("recipe", recipe);
            List<Ingredient> i = ingredientRepository.search(Integer.parseInt(recipeId));
            model.addAttribute("ingredient", i);
            User user = userRepository.findByUsername(authentication.getName());
            List<Recipe> resp = searchService.listFav(recipeId, Integer.toString(user.getId()));
            if (resp.isEmpty()) {
                model.addAttribute("fav", false);
            } else {
                model.addAttribute("fav", true);
            }
            if (recipe.getAuthor().getId() == user.getId()) {
                isAuthorised = true;
            }
            model.addAttribute("isAuthorised", isAuthorised);
        }
        return "/registered/view-recipe";
    }

    @RequestMapping({"/edit-profile", "edit-profile.html"})
    public String editProfile(Authentication authentication, Model model) {
        User user = userRepository.findByUsername(authentication.getName());
        model.addAttribute("user", user);
        return "/registered/edit-profile";
    }

    @PostMapping(value = "/save-profile")
    public String saveProfile(User user, Model model, Authentication authentication) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            if (!Objects.equals(user.getUsername(), authentication.getName())) {
                model.addAttribute("err", "The username <b>" + user.getUsername() + "</b> is already in use.");
                return "/registered/edit-profile";
            }
        }
        User u = userRepository.findByUsername(authentication.getName());

        String name = user.getName();
        u.setName(name.substring(0, 1).toUpperCase() + name.substring(1));

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        u.setPassword(encodedPassword);

        u.setEmailId(user.getEmailId());

        u.setUsername(user.getUsername());
        userRepository.save(u);
        if (Objects.equals(user.getUsername(), authentication.getName())) {
            model.addAttribute("uName", u.getName());
            return "/registered/index";
        } else {
            return "/login";
        }
    }

    @PostMapping(value = "/delete-ingredient")
    public ModelAndView delIngredient(Model model, HttpServletRequest request, Authentication authentication) {
        String recipeId = request.getParameter("recipeId");
        String ingredientId = request.getParameter("ingredientId");
        ingredientRepository.delete(ingredientRepository.findById(Integer.parseInt(ingredientId)));

        Recipe recipe = recipeRepository.findById(Integer.parseInt(recipeId));
        User user = userRepository.findByUsername(authentication.getName());
        model.addAttribute("recipe", recipe);
        List<Ingredient> i = ingredientRepository.search(Integer.parseInt(recipeId));
        model.addAttribute("ingredient", i);
        boolean isAuthorised = recipe.getAuthor().getId() == user.getId();
        model.addAttribute("isAuthorised", isAuthorised);

        return new ModelAndView("redirect:/registered/view-recipe?recipeId=" + recipeId);
    }

    @RequestMapping({"/edit-ingredient","/edit-ingredient.html"})
    public String editIngredient(HttpServletRequest request, Model model) {
        String ingredientId = request.getParameter("ingredientId");
        Ingredient ingredient=ingredientRepository.findById(Integer.parseInt(ingredientId));
        model.addAttribute("ingredient",ingredient);

        return "/registered/edit-ingredient";
    }
    @PostMapping(value = "/save-ingredient")
    public ModelAndView saveIngredient(Model model, Ingredient ingredient,HttpServletRequest request, Authentication authentication) {
        String ingredientId = request.getParameter("ingredientId");

        Ingredient ingre=ingredientRepository.findById(Integer.parseInt(ingredientId));
        if(ingre!=null) {
            ingre.setItem(ingredient.getItem());
            ingredientRepository.save(ingre);

            Recipe recipe = ingre.getRecipe();
            User user = userRepository.findByUsername(authentication.getName());
            model.addAttribute("recipe", recipe);
            List<Ingredient> i = ingredientRepository.search(recipe.getId());
            model.addAttribute("ingredient", i);
            boolean isAuthorised = recipe.getAuthor().getId() == user.getId();
            model.addAttribute("isAuthorised", isAuthorised);
            return new ModelAndView("redirect:/registered/view-recipe?recipeId=" + recipe.getId());
        }
        return new ModelAndView("redirect:/registered/");
    }
}