package com.shopcart.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
//import com.shopcart.service.UsersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopcart.entity.Categories;
import com.shopcart.repository.CategoriesRepository;

@Service
public class CategoriesService {

	@Autowired
	private CategoriesRepository repo;

	@Autowired
	HttpServletRequest request;

	@Autowired
	HttpSession session;
	
	@Autowired
	UsersService userService;
	
	@Autowired
	AdminService adminService;

	public boolean addCategory(Categories categories) {

		int admin_Id = adminService.getAdminSession();

		try {
		if (admin_Id == 0) {
			System.out.println("Admin not logged in");
			return false;
		}

		else {

		for (Categories dbCategories : repo.findAll()) {
			if (dbCategories.getCategory_name().equals(categories.getCategory_name())) {
				System.out.println("Category already registerd");
				return false;
			}
		}

			repo.save(categories);
			return true;
		}
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}

	}

	public Categories getCategory(int category_id) {
		
		int user_id = userService.getSession();
		
		try {
			if (user_id == 0) {
				System.out.println("Login to continue");
				return null;
			}

			else {
				return repo.findById(category_id).get();
			}
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	public List<Categories> getAllcategories() {
		try {
			return ((List<Categories>) repo.findAll());
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

}
