package com.shopcart.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopcart.entity.Admin;
import com.shopcart.login.Encryption;
import com.shopcart.login.Login;
import com.shopcart.repository.AdminRepository;

@Service
public class AdminService {
	
	@Autowired
	private AdminRepository repo;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	HttpSession session;
	
	public boolean adminLogin(Login login) {
		
		session = request.getSession(true);
		
		Admin admin = repo.getAdminByUsername(login.getUsername());
		
		try {
			if(admin.equals(null)) {
				return false;
			}
			
			String encryptedPassword = admin.getPassword();
			String password = Encryption.decrypt(encryptedPassword, "secretkey");
			
			if(password.equals(login.getPassword())) {
				int admin_id = admin.getAdmin_id();
				session.setAttribute("admin_id", admin_id);
				System.out.println(true);
				System.out.println("Admin Id = " + (session.getAttribute("admin_id")));
				session.setMaxInactiveInterval(0);
				return true;
			}
			
		}
		catch(Exception e){
			System.out.println(e);
		}
		return false;
	}

	public String adminLogout() {
		
		try {
			session.removeAttribute("admin_id");
			session.invalidate();
			return "Logged out successfully";
		}
		catch(Exception e) {
			System.out.println(e);
			return "Something went wrong";
		}
		
	}
	
	public int getAdminSession() {
		System.out.println(session);

		try {
			System.out.println(session.getAttribute("admin_id"));
			int admin_id = (int) session.getAttribute("admin_id");
			return admin_id;
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Session not present");
			return 0;
		}
	}

}
