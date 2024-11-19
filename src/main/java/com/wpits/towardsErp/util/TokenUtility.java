package com.wpits.towardsErp.util;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.*;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.wpits.towardsErp.beans.AccessTokenResponse;
import com.wpits.towardsErp.beans.TokenSave;

import java.util.function.*;
public class TokenUtility {
	public static void saveToFile(String token) {
		System.out.println("SAVE~TO~FILE");
		TokenSave tokenSave = new TokenSave();
		tokenSave.setToken(token);
		tokenSave.setExpiryDate(LocalDateTime.now().plusHours(24));
		
		ObjectOutputStream oos=null;
		ObjectInputStream ois = null;
		
		try {
		oos = new ObjectOutputStream(new FileOutputStream("/home/bsserptoken.ser"));
		oos.writeObject(tokenSave);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		finally {
			try {
			oos.close();
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public static String getAccessToken() {
		System.out.println("ACCESS~TOKEN~FROM~API");
		//String CRM_LoginUrl="http://172.5.10.2:9090/api/login";
		//http://172.17.1.20:9090/
		String CRM_LoginUrl="https://api.metricserp4.metricserp.net/api/auth/sign-in";
		String jwtToken = null;
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);

		String credential = "{\"email\" : \"prabir.prab2007@gmail.com\", \"password\" : \"Prabir.k12\"}";

		//System.out.println("@@@@@ "+ credential);

		HttpEntity<String> requestEntity = new HttpEntity<>(credential, header);

		//System.out.println("!!!!!requestEntity "+ requestEntity);
		ResponseEntity<String> responseEntity = restTemplate.exchange(CRM_LoginUrl, HttpMethod.POST, requestEntity,String.class);

		//System.out.println("$$$$$$$$ responseEntity" +responseEntity);
		HttpStatusCode statusCode = responseEntity.getStatusCode();

		//System.out.println("CRM login Api Status Code1: " + statusCode);
		if (statusCode == HttpStatus.OK) {

			String response = responseEntity.getBody();
			// System.out.println(response);

			org.json.JSONObject jsonResponse = new org.json.JSONObject(response);


			Gson gson = new Gson();
			
			// from JSON to object 
			AccessTokenResponse accessTokenResponse = gson.fromJson(jsonResponse.toString(),AccessTokenResponse.class);
			String idToken = accessTokenResponse.getData().getResponse().getIdToken();
			jwtToken = idToken;
			saveToFile(jwtToken);
			//System.out.println("XXXXXX"+jwtToken);

		}
		return jwtToken;

	}


	public static String getAccessTokenFromFile() {
		ObjectInputStream ois = null;
//		LocalDateTime now = null;
		try {
			ois = new ObjectInputStream(new FileInputStream("/home/bsserptoken.ser"));
			TokenSave tokenSave = (TokenSave) ois.readObject();
			LocalDateTime ldt1 =  tokenSave.getExpiryDate();
			LocalDateTime now = LocalDateTime.now();
			int value = now.compareTo(ldt1);
			if(value<0) {
				System.err.println("ACCESS~TOKEN~FROM~FILE");
			return tokenSave.getToken();
			}
			else { 
				return getAccessToken();
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		finally {
			try {
			ois.close();
			}catch(Exception ex2) {
				ex2.printStackTrace();
			}
		}
		return null;
	}

}
