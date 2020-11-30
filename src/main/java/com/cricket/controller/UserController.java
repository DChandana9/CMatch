package com.cricket.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cricket.auth.UserAuth;
import com.cricket.data.models.User;
import com.cricket.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	

	ObjectMapper objectMap = new ObjectMapper();

	@RequestMapping(value = "/CreateUser", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> registerNewUser(@RequestParam(required = true, value = "profile") MultipartFile file,
			@RequestParam(required = true, value = "jsondata") String jsondata) throws IOException {
		HashMap<String, String> response = new HashMap();
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(jsondata);

		} catch (JSONException err) {
			response.put("Error", "Cannot serve request : Server ERROR");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		Optional<User> prevUser = userService.findUserByEmail(jsonObject.getString("email"));
		if (jsonObject.getString("email") == null || jsonObject.getString("name") == null
				|| jsonObject.getString("password") == null) {
			response.put("Error", "Invalid Parameters");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		if (prevUser.isPresent()) {
			response.put("Error", "Email already registered");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			User user = new User();
			String temp[] = file.getOriginalFilename().split("\\.");
			String tempFileName = System.currentTimeMillis() + "." + (temp[temp.length - 1]);

			BufferedOutputStream stream = new BufferedOutputStream(
					new FileOutputStream(new File("img/", tempFileName)));
			stream.write(file.getBytes());
			stream.close();
			user.setEmail(jsonObject.getString("email"));
			user.setName(jsonObject.getString("name"));
			user.setPassword(jsonObject.getString("password"));
			user.setImg(tempFileName);
			userService.createUser(user);
			response.put("UserCreated", "New User created successfully");
		}
		return new ResponseEntity<>(response, HttpStatus.OK);

	}
	
	
	@PostMapping(value = "/updateUser", produces = { MediaType.APPLICATION_JSON_VALUE })
	public Map<String, String> updateUser(@RequestParam("oldPassword") String oldPass,
			@RequestParam("newPassword") String newpass) {
		HashMap<String, String> responseMap = new HashMap<>();
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			User loggedinUser = ((UserAuth) principal).getUser();
			if (bCryptPasswordEncoder.matches(oldPass, loggedinUser.getPassword().replace("{bcrypt}", ""))) {

				loggedinUser.setPassword(newpass);
				userService.updateUser(loggedinUser);
				responseMap.put("Updated", "Profile updated");
			} else {
				responseMap.put("Error", "Old password is wrong");
			}
		} else {
			responseMap.put("Error", "Cannot update profile");
		}

		return responseMap;
	}
	
	
	@GetMapping("/getUser")
	public ResponseEntity getUserDeatils() {
		HashMap<String, String> responseMap = new HashMap<>();

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			User loggedinUser = ((UserAuth) principal).getUser();
			responseMap.put("UserName", loggedinUser.getName());
			responseMap.put("Email", loggedinUser.getEmail());
			responseMap.put("img", loggedinUser.getImg());

		} else {
			responseMap.put("Error", "Cannot get user details");
		}
		return new ResponseEntity<>(responseMap, HttpStatus.OK);

	}
}
