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
 * Description: This file creates Recipe table.
 *              This table is needed to add recipes.
 **********************************************************************************/
package ca.gbc.comp3095.recipe.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Recipe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String recipeName;
	private int prepTime;
	private int cookTime;
	private int totalTime;
	private LocalDate dateAdded;
	private String temp_ingre;
	@Lob
	private String instruction;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User author;

	@OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY,
			cascade = CascadeType.ALL)
	private Set<Meal> meals;

	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(name = "user_fav_recipe", joinColumns = {@JoinColumn(name = "recipe_id")}, inverseJoinColumns = {
			@JoinColumn(name = "user_id")})
	private Set<User> user_fav = new HashSet<>();

	@OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY,
			cascade = CascadeType.ALL)
	private Set<Ingredient> ingredients;


	public Recipe() {
	}

	public Recipe(int id, String recipeName, int prepTime, int cookTime, int totalTime, String temp_ingre, LocalDate dateAdded, User user, Set<Meal> meals, Set<User> user_fav, Set<Ingredient> ingredients, String instruction) {
		this.id = id;
		this.recipeName = recipeName;
		this.prepTime = prepTime;
		this.cookTime = cookTime;
		this.totalTime = totalTime;
		this.dateAdded = dateAdded;
		this.author = user;
		this.meals = meals;
		this.user_fav = user_fav;
		this.ingredients = ingredients;
		this.instruction = instruction;
		this.temp_ingre = temp_ingre;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRecipeName() {
		return recipeName;
	}

	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}

	public int getPrepTime() {
		return prepTime;
	}

	public void setPrepTime(int prepTime) {
		this.prepTime = prepTime;
	}

	public int getCookTime() {
		return cookTime;
	}

	public void setCookTime(int cookTime) {
		this.cookTime = cookTime;
	}

	public int getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(int totalTime) {
		this.totalTime = totalTime;
	}

	public LocalDate getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(LocalDate dateAdded) {
		this.dateAdded = dateAdded;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User user) {
		this.author = user;
	}

	public Set<Meal> getMeals() {
		return meals;
	}

	public void setMeals(Set<Meal> meals) {
		this.meals = meals;
	}

	public Set<User> getUser_fav() {
		return user_fav;
	}

	public void setUser_fav(Set<User> user_fav) {
		this.user_fav = user_fav;
	}

	public Set<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(Set<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	public String getTemp_ingre() {
		return temp_ingre;
	}

	public void setTemp_ingre(String temp_ingre) {
		this.temp_ingre = temp_ingre;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Recipe recipe = (Recipe) o;
		return Objects.equals(id, recipe.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

}
