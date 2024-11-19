package com.wpits.towardsErp.service;

import org.springframework.stereotype.Service;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;

import com.wpits.towardsErp.beans.AccessTokenBean;
import com.wpits.towardsErp.service.ItemService;
import com.wpits.towardsErp.util.*;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class ItemService {

	@Autowired
    private ApplicationContext applicationContext;

	
	
	
	public ResponseEntity<CustomResponseBody> addItem(HttpServletRequest request){
		ServletContext context =  request.getServletContext();
		String token = takeToken(context);
		System.out.println(token);
		
		return null;
	}
	
	private String takeToken(ServletContext context) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY HH:mm:ss");
		String jwt = null;
		
		if(context.getAttribute("jwt")==null) {
			System.out.println("0000");
			//call api
			// take token and date 
			// expiration date + 1
			String accessToken = call4AccessToken();
			AccessTokenBean accessTokenBean = new AccessTokenBean();
			accessTokenBean.setAccessToken(accessToken);
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime exDateTime = now.plusHours(23);
			
		    accessTokenBean.setExDateTime(exDateTime);
		    context.setAttribute("jwt", accessTokenBean);
			accessTokenBean = (AccessTokenBean)context.getAttribute("jwt");
			jwt = accessTokenBean.getAccessToken();
		}
		else if(context.getAttribute("jwt")!=null) {
			System.out.println("1111");
			AccessTokenBean accessTokenBean = (AccessTokenBean) context.getAttribute("jwt");
			LocalDateTime exDateTime = accessTokenBean.getExDateTime();
			System.out.println(exDateTime);
			LocalDateTime now = LocalDateTime.now();
			
			int value = now.compareTo(exDateTime);
			
			if(value>=0) {
				System.out.println("222222");
				String accessToken = call4AccessToken();
				accessTokenBean = new AccessTokenBean();
				accessTokenBean.setAccessToken(accessToken);
				
				LocalDateTime now1 = LocalDateTime.now();
				LocalDateTime exDateTime1 = now1.plusHours(23);
				
			    accessTokenBean.setExDateTime(exDateTime1);
			    
			    context.setAttribute("jwt", accessTokenBean);
				accessTokenBean = (AccessTokenBean) context.getAttribute("jwt");
				jwt = accessTokenBean.getAccessToken();
			}
			else {
				System.out.println("33333");
				jwt = accessTokenBean.getAccessToken();
			}
		}
		return jwt;
	}
	
	
	private String call4AccessToken() {
		return "qwertjdjbvhfj";
	}
	
	
}
