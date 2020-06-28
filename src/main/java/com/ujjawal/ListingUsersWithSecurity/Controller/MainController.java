package com.ujjawal.ListingUsersWithSecurity.Controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ujjawal.ListingUsersWithSecurity.Dto.LoginUserObject;
import com.ujjawal.ListingUsersWithSecurity.Dto.UserAddObject;
import com.ujjawal.ListingUsersWithSecurity.Entity.UserInfo;
import com.ujjawal.ListingUsersWithSecurity.Repo.UserRepository;

@Controller
public class MainController {
	Map<String, String> map = new HashMap<>();
	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@Autowired
	UserRepository userRepo;
	@Autowired
    AuthenticationManager authenticationManager;

	@RequestMapping("/")
	public String Home(ModelMap modelMap) {
		List<UserInfo> users=userRepo.findAll();
		modelMap.addAttribute("users", users);
		String userid=SecurityContextHolder.getContext().getAuthentication().getName();
		UserInfo user=userRepo.findByEmail(userid);
		String userName=user.getFirstName()+" "+user.getLastName();
		modelMap.addAttribute("userName", userName);
		return "home";

	}

	@RequestMapping("/loginPage")
	public String showLogin(ModelMap modelMap) {
		if (map.size()> 0) {
			for (Map.Entry<String, String> entry : map.entrySet()) {
				modelMap.addAttribute(entry.getKey(), entry.getValue());
			}
            map.clear();
		}
		return "loginPage";

	}
	
	@PostMapping("/login")
	public String loginUser(LoginUserObject user) {
		
		 try {
			 UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
				Authentication auth=authenticationManager
						.authenticate(token);
				SecurityContext sc=SecurityContextHolder.getContext();
				
				
					sc.setAuthentication(auth);
	       }
	       catch (Exception e)
	       {
	           map.clear();
	           map.put("emailError", "Invalid email");
	           
	           System.out.println("Login Failed");
	           
	           return "redirect:loginPage";
	       }
		UserInfo savedUser=userRepo.findByEmail(user.getEmail());
		
		
		
		return "redirect:/";

	}

	@RequestMapping("/registerUser")
	public String showRegister(ModelMap modelMap) {
		if (map.size()> 0) {
			for (Map.Entry<String, String> entry : map.entrySet()) {
				modelMap.addAttribute(entry.getKey(), entry.getValue());
			}
            map.clear();
		}

		return "showAddUser";
	}

	@RequestMapping("/addUser")

	public String addUser(@Valid UserAddObject user, BindingResult bindingResult) throws IOException {
		if (bindingResult.hasErrors()) {
			List<FieldError> errors = bindingResult.getFieldErrors();
			map.clear();

			for (FieldError e : errors) {

				map.put(e.getField(), e.getDefaultMessage());
			}

			return "redirect:registerUser";
		} 
		else if(!user.getPassword().equals(user.getConfirmPassword()))
		{
			map.clear();

			map.put("confirmPassword", "Password doesn't match");
			return "redirect:registerUser";
		}
		
		else {
			UserInfo saveUser = new UserInfo();
			saveUser.setFirstName(user.getFirstName());
			saveUser.setLastName(user.getLastName());
			saveUser.setEmail(user.getEmail());
			saveUser.setPhoneNumber(user.getPhoneNumber());
			saveUser.setPassword(passwordEncoder.encode(user.getPassword()));

			userRepo.save(saveUser);

			return "redirect:loginPage";
		}

	}
	
	

}
