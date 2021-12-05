package ca.gbc.comp3095.recipe.services;

import ca.gbc.comp3095.recipe.model.Cart;
import ca.gbc.comp3095.recipe.repositories.SearchRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

@Service
public class CSVExportService {
	@Autowired
	private SearchRepository searchRepository;

	public void CsvExportService(SearchRepository searchRepository) {
		this.searchRepository = searchRepository;
	}

	public void writeEmployeesToCsv(Writer writer, String id) {
		List<Cart> carts = searchRepository.searchCart(id);
		try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
			for (Cart cart : carts) {
				csvPrinter.printRecord(cart.getIngredient().getItem(), "for " + cart.getIngredient().getRecipe().getRecipeName());
			}
		} catch (IOException e) {
			System.out.println("Error While writing CSV " + e);
		}
	}
}
