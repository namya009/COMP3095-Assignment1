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
 * Description: This java file is used to do crud functions in Cart table.
 **********************************************************************************/
package ca.gbc.comp3095.recipe.repositories;

import ca.gbc.comp3095.recipe.model.Cart;
import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends CrudRepository<Cart, Long> {

	Cart findById(int id);
}
