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
 * Description: This file creates ingredient table.
 *              This table is needed to Add recipe items to shopping list.
 **********************************************************************************/
package ca.gbc.comp3095.recipe.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ingredient_id", nullable = false)
	private Ingredient ingredient;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public Cart(int id, Ingredient ingredient, User user) {
		this.id = id;
		this.ingredient = ingredient;
		this.user = user;
	}

	public Cart(Ingredient ingredient, User user) {
		this.ingredient = ingredient;
		this.user = user;
	}

	public Cart() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Ingredient getIngredient() {
		return ingredient;
	}

	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
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
		Cart cart = (Cart) o;
		return Objects.equals(id, cart.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}