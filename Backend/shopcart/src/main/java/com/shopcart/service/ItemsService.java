package com.shopcart.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopcart.entity.Items;
import com.shopcart.repository.ItemsRepository;

@Service
public class ItemsService {

	@Autowired
	private ItemsRepository repo;

	@Autowired
	HttpServletRequest request;

	@Autowired
	HttpSession session;

	@Autowired
	UsersService userService;

	@Autowired
	AdminService adminService;

	public boolean addItem(Items item) {

		int admin_Id = adminService.getAdminSession();

		try {
			if (admin_Id == 0) {
				System.out.println("Admin not logged in");
				return false;
			} else {

				repo.save(item);
				return true;

			}
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	public Items getItem(int item_id) {

		int user_id = userService.getSession();
		int admin_Id = adminService.getAdminSession();

		try {
			if (user_id == 0 && admin_Id == 0) {
				System.out.println("Login to continue");
				return null;
			} else {

				return repo.findById(item_id).get();
			}
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	public boolean deleteItem(int item_id) {

		int admin_Id = adminService.getAdminSession();
		try {

			if (admin_Id == 0) {
				System.out.println("Admin not logged in");
				return false;
			} else {

				repo.deleteById(item_id);
				return true;
			}
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	public List<Items> getItemsByCategory(String category) {

		try {
			return repo.getItemsByCategory(category);
		} catch (Exception e) {
			System.out.println(e);
		}

		return null;
	}

	public boolean updateItem(int item_id, Items item) {

		int admin_Id = adminService.getAdminSession();
		
		try {
			if(admin_Id == 0) {
				System.out.println("Admin not logged in");
				return false;
			}
			else {
				String item_name = item.getItem_name();
				float price = item.getPrice();
				int quantity = item.getQuantity();
				String img = item.getImg();
				
				repo.updateItem(item_id, item_name, price, quantity, img);
				
				return true;				
			}
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

}
