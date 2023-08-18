package com.shopcart.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopcart.repository.CartRepository;
import com.shopcart.repository.OrdersRepository;
import com.shopcart.entity.Cart;
import com.shopcart.entity.Orders;

@Service
public class OrdersService {

	@Autowired
	private OrdersRepository repo;

	@Autowired
	private CartRepository crepo;

	@Autowired
	HttpServletRequest request;

	@Autowired
	UsersService userService;

	@Autowired
	AdminService adminService;

	public boolean addOrder(Orders order) {

		int user_Id = userService.getSession();

		try {
			if (user_Id == 0) {
				System.out.println("User not logged in");
				return false;
			} else {

				float total = 0.0f;

				for (Cart dbcart : crepo.findAll()) {
					if (order.getUser().getUser_id() == dbcart.getUser().getUser_id()) {
						total = total + dbcart.getSubtotal();
					}
				}

				order.setTotal(total);

				repo.save(order);
				return true;
			}
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	public List<Orders> getOrders(int user_id) {

		int user_Id = userService.getSession();

		try {
			if (user_Id == 0) {
				System.out.println("Login to continue");
				return null;
			} else {

				return repo.getOrders(user_id);
			}
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	public boolean deleteOrder(int user_id) {

		HttpSession session = request.getSession();

		if (session.getAttribute("admin_id") == null) {
			System.out.println("Admin not logged in");
			return false;
		} else {

			try {
				repo.deleteOrder(user_id);
				return true;
			} catch (Exception e) {
				System.out.println(e);
				return false;
			}
		}
	}

	public boolean updateStatus(int user_id, int order_id) {

		int admin_Id = adminService.getAdminSession();

		try {
			if (admin_Id == 0) {
				System.out.println("Admin not logged in");
				return false;
			} else {

				repo.updateStatus(user_id, order_id);
				return true;
			}
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	public List<Orders> getPendingOrders() {
		
		int admin_Id = adminService.getAdminSession();
		
		try {
			if(admin_Id == 0) {
				System.out.println("Admin not logged in");
				return null;
			}
			else {
				return repo.getPendingOrders();				
			}
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

}
