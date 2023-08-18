package com.shopcart.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopcart.entity.Cart;
import com.shopcart.repository.CartRepository;

@Service
public class CartService {

	@Autowired
	private CartRepository repo;

	@Autowired
	HttpServletRequest request;

	@Autowired
	UsersService userService;

	public boolean addCart(Cart cart) {

		int user_Id = userService.getSession();

		try {
			if (user_Id == 0) {
				System.out.println("User not logged in");
				return false;
			} else {

				float subtotal = repo.getPrice(cart.getItem().getItem_id()) * cart.getItem_quantity();
//		System.out.println("Price - " + cart.getItem().getPrice());
//		System.out.println("Qty - " + cart.getItem_quantity());
//		System.out.println(cart.getSubtotal());
				cart.setSubtotal(subtotal);

				repo.save(cart);
				return true;
			}
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	public List<Cart> getCartByUserId(int user_id) {

		int user_Id = userService.getSession();

		try {
			if (user_Id == 0) {
				System.out.println("User not logged in");
				return null;
			} else {

				return repo.getCartByUserId(user_id);
			}
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	public boolean deleteItemFromCart(int item_id, int user_id) {

		int user_Id = userService.getSession();

		try {
			if (user_Id == 0) {
				System.out.println("User not logged in");
				return false;
			}

			else {

				repo.deleteItemFromCart(item_id, user_id);
				return true;
			}
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	public float getSubtotal(int user_id) {
		try {
			float subtotal = repo.getSubtotal(user_id);
			System.out.println(subtotal);
			return subtotal;
		} catch (Exception e) {
			System.out.println(e);
			return 0.00f;
		}
	}

	public boolean minusItem(int item_id, int user_id) {

		try {

			repo.minusItem(item_id, user_id);
			repo.updateCart(item_id, user_id);
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	public boolean plusItem(int item_id, int user_id) {
		try {

			repo.plusItem(item_id, user_id);
			repo.updateCart(item_id, user_id);
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	public boolean deleteCart(int user_id) {

		int user_Id = userService.getSession();

		try {
			if (user_Id == 0) {
				System.out.println("User not logged in");
				return false;
			} else {
				repo.deleteCart(user_id);
				System.out.println("true");
				return true;
			}
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

}
