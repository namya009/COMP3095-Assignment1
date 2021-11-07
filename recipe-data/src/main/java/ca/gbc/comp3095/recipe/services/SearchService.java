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
 * Description: This java file is used to return our searches for our app.
 **********************************************************************************/
package ca.gbc.comp3095.recipe.services;

import ca.gbc.comp3095.recipe.model.Recipe;
import ca.gbc.comp3095.recipe.repositories.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {
    @Autowired
    private SearchRepository searchRepository;

    public List<Recipe> listAll(String keyword){
        return searchRepository.search(keyword);
    }

    public List<Recipe> listAllForUser(int id){
        String id2= Long.toString(id);
        return  searchRepository.searchAllForUser(id2);
    }
}
