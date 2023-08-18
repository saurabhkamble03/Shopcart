package com.shopcart.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopcart.entity.Wishlists;
import com.shopcart.repository.WishlistsRepository;

@Service
public class WishlistsService {

	@Autowired
	private WishlistsRepository repo;

	@Autowired
	HttpServletRequest request;

	@Autowired
	UsersService userService;

	public boolean addWishlists(Wishlists wishlist) {

		for (Wishlists wish : repo.findAll()) {
			if (wish.getItem().getItem_id() == (wishlist.getItem().getItem_id()) && wish.getUser().getUser_id() == wishlist.getUser().getUser_id()) {
				System.out.println("Item already in wishlist");
				return false;
			}
		}
		try {

			repo.save(wishlist);
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}

	}

	public List<Wishlists> getWishlists(int user_id) {

		int user_Id = userService.getSession();
		try {
			if (user_Id == 0) {
				System.out.println("Login to continue");
				return null;
			} else {
				return repo.getWishlists(user_id);

			}
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	public boolean deleteWishlist(int wishlist_id) {

		int user_Id = userService.getSession();
		try {
			if (user_Id == 0) {
				System.out.println("Login to continue");
				return false;
			} else {
				repo.deleteById(wishlist_id);
				return true;
			}
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

}
