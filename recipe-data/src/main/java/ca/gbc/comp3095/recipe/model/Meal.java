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
 * Description: This file creates meal table.
 *              This table is needed to plan meals.
 **********************************************************************************/
package ca.gbc.comp3095.recipe.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Meal {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String dateToBeMade;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "recipe_id", nullable = false)
	private Recipe recipe;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public Meal() {
	}

	public Meal(int id, String dateToBeMade, Recipe recipe, User user) {
		this.id = id;
		this.dateToBeMade = dateToBeMade;
		this.recipe = recipe;
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDateToBeMade() {
		return dateToBeMade;
	}

	public void setDateToBeMade(String dateToBeMade) {
		this.dateToBeMade = dateToBeMade;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Meal meal = (Meal) o;
		return Objects.equals(id, meal.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}

