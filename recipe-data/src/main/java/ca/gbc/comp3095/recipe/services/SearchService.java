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
 * Description: This java file is used to return our searches.
 **********************************************************************************/
package ca.gbc.comp3095.recipe.services;

import ca.gbc.comp3095.recipe.model.Cart;
import ca.gbc.comp3095.recipe.model.Meal;
import ca.gbc.comp3095.recipe.model.Recipe;
import ca.gbc.comp3095.recipe.repositories.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

	@Autowired
	private SearchRepository searchRepository;

	public List<Recipe> listAll(String keyword) {
		return searchRepository.search(keyword);
	}

	public List<Recipe> listFavForUser(int id) {
		String id2 = Long.toString(id);
		return searchRepository.searchFavForUser(id2);
	}

	public List<Recipe> listForUser(int id) {
		String id2 = Long.toString(id);
		return searchRepository.searchForUser(id2);
	}

	public List<Meal> listMeal(int id) {
		String id2 = Long.toString(id);
		return searchRepository.searchMeal(id2);
	}

	public List<Recipe> listFav(String recipe_id, String user_id) {
		return searchRepository.searchFav(user_id, recipe_id);
	}

	public List<Cart> listCart(int id) {
		String id2 = Long.toString(id);
		return searchRepository.searchCart(id2);
	}
	public List<Meal> SearchMealForCart(int id) {
		return searchRepository.searchMealForCart(id);
	}
}
