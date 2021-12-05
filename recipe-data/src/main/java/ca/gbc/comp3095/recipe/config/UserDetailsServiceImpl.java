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
 * Description: This file find user in users table.
 **********************************************************************************/
package ca.gbc.comp3095.recipe.config;

import ca.gbc.comp3095.recipe.model.User;
import ca.gbc.comp3095.recipe.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

@Autowired
private UserRepository userRepository;

@Override
public UserDetails loadUserByUsername(String username)
		throws UsernameNotFoundException {
	User user = userRepository.findByUsername(username);

	if (user == null) {
		throw new UsernameNotFoundException("Could not find user");
	}

	return new MyUserDetails(user);
}

}
