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
 * Description: This java file is used to search from tables in our app.
 **********************************************************************************/
package ca.gbc.comp3095.recipe.repositories;

import ca.gbc.comp3095.recipe.model.Cart;
import ca.gbc.comp3095.recipe.model.Meal;
import ca.gbc.comp3095.recipe.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SearchRepository extends JpaRepository<Recipe, Long> {
	@Query("SELECT r FROM Recipe r WHERE r.recipeName LIKE %?1%")
	List<Recipe> search(String keyword);

	@Query(value = "SELECT * FROM Recipe " +
			"INNER JOIN user_fav_recipe ON Recipe.id LIKE user_fav_recipe.recipe_id " +
			"INNER JOIN user ON user_fav_recipe.user_id LIKE User.id " +
			"WHERE (User.id LIKE %?1%)", nativeQuery = true)
	List<Recipe> searchFavForUser(String id);

	@Query("SELECT r FROM Recipe r WHERE user_id LIKE %?1%")
	List<Recipe> searchForUser(String id);

	@Query(value = "SELECT * FROM Recipe " +
			"INNER JOIN user_fav_recipe ON Recipe.id LIKE user_fav_recipe.recipe_id " +
			"INNER JOIN user ON user_fav_recipe.user_id LIKE User.id " +
			"WHERE (User.id LIKE ?#{[0]}) and (Recipe.id LIKE ?#{[1]})", nativeQuery = true)
	List<Recipe> searchFav(String user_id, String recipe_id);

	@Query("SELECT m FROM Meal m WHERE user_id LIKE %?1% ORDER BY date_to_be_made")
	List<Meal> searchMeal(String id);

	@Query("SELECT DISTINCT c FROM Cart c WHERE user_id LIKE %?1%")
	List<Cart> searchCart(String id);

	@Query("SELECT DISTINCT m FROM Meal m,Cart c WHERE (c.ingredient.recipe.id LIKE m.recipe.id) AND (m.user.id LIKE ?1)")
	List<Meal> searchMealForCart(int user_id);
}
