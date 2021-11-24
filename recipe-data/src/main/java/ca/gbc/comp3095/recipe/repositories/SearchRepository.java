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

import ca.gbc.comp3095.recipe.model.Meal;
import ca.gbc.comp3095.recipe.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SearchRepository extends JpaRepository<Recipe, Long> {
    @Query("SELECT r FROM Recipe r WHERE CONCAT(r.recipeName, r.ingredients, r.instructions) LIKE %?1%")
    List<Recipe> search(String keyword);

    @Query(value= "SELECT * FROM Recipe " +
            "INNER JOIN user_fav_recipe ON Recipe.id LIKE user_fav_recipe.recipe_id " +
            "INNER JOIN user ON user_fav_recipe.user_id LIKE User.id " +
            "WHERE (User.id LIKE %?1%)", nativeQuery=true)
    List<Recipe> searchAllForUser(String id);

    @Query("SELECT m FROM Meal m WHERE user_id LIKE %?1%")
    List<Meal> searchMeal(String id);
}
