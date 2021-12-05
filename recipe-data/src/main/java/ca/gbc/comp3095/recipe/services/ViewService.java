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
 * Description: This java file is used to return our data from table.
 **********************************************************************************/
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

	public List<Recipe> findAll() {
		return recipeRepository.findAll();
	}
}
