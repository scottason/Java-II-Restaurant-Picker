package dmacc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import dmacc.beans.Restaurant;
import dmacc.repository.RestaurantRepository;

@Controller
public class WebController {
	@Autowired
	RestaurantRepository repo;
	
	@GetMapping({"/", "viewAll" })
	public String viewAllRestaurants(Model model) {
		if(repo.findAll().isEmpty()) {
			return addNewRestaurant(model);
		}
		
		model.addAttribute("Restaurants", repo.findAll());
		return "results";
	}
	
	@GetMapping("/inputRestaurant")
	public String addNewRestaurant(Model model) {
		Restaurant r = new Restaurant();
		model.addAttribute("newRestaurant", r);
		return "input";
	}
	
	@PostMapping("/inputRestaurant")
		public String addNewRestaurant(@ModelAttribute Restaurant r, Model model) {
			repo.save(r);
			return viewAllRestaurants(model);
	}
	
	@GetMapping("/edit/{id}")
	public String showUpdateRestaurant(@PathVariable("id") long id, Model model) {
		Restaurant c = repo.findById(id).orElse(null);
		System.out.println("ITEM TO EDIT: " + c.toString());
		model.addAttribute("newRestaurant", c);
		return "input";
	}
	
	@PostMapping("/update/{id}")
	public String reviseRestaurant(Restaurant r, Model model) {
		repo.save(r);
		return viewAllRestaurants(model);
	}
	
	@GetMapping("/delete/{id}")
	public String deleteRestaurant(@PathVariable("id") long id, Model model) {
		Restaurant r = repo.findById(id).orElse(null);
	    repo.delete(r);
	    return viewAllRestaurants(model);
	}
}