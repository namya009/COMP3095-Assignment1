package ca.gbc.comp3095.recipe.services;

import ca.gbc.comp3095.recipe.model.Recipe;
import ca.gbc.comp3095.recipe.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ViewService {
    @Autowired
    private RecipeRepository recipeRepository;
    public List<Recipe> findAll(){
        return recipeRepository.findAll();
    }
}
