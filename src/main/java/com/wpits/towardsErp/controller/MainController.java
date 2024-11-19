package com.wpits.towardsErp.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.wpits.towardsErp.beans.AccessTokenResponse;
import com.wpits.towardsErp.beans.ItemBean;
import com.wpits.towardsErp.beans.TokenSave;
import com.wpits.towardsErp.service.ItemService;
import com.wpits.towardsErp.util.*;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/erp")
@RequiredArgsConstructor
public class MainController {

	

	@PostMapping(value="item/addItem")
	public ResponseEntity<?> addItem(@RequestBody JSONObject json){

		String accessToken = TokenUtility.getAccessTokenFromFile();
		//System.out.println("ACCESS TOKEN : "+accessToken);

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.add("Authorization", ""+accessToken);
		ResponseEntity<String> response = null;
		HttpEntity formEntity = new HttpEntity(json, headers);
		try {
			response = restTemplate.exchange("https://api.metricserp4.metricserp.net/api/item/add-item", HttpMethod.POST,
					formEntity, String.class);

			if(response!=null && response.getStatusCode().is2xxSuccessful()) {
				String actualResponse = response.getBody();
				JSONParser parser =new JSONParser();
				JSONObject obj=null;
				try {
					obj = (JSONObject) parser.parse(actualResponse);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return new ResponseEntity<>(obj, HttpStatus.OK);
			}
			else if(response!=null && !response.getStatusCode().is2xxSuccessful()){
				System.out.println("1");
				return new ResponseEntity<>(response, response.getStatusCode());
			}
			else
			{
				System.out.println("2");
				return new ResponseEntity<>("ERROR", response.getStatusCode());

			}
		}
		//			catch(HttpClientErrorException ex) {
		//			ex.printStackTrace();
		//			String lMsg = ex.getLocalizedMessage();
		//			//String msg = ex.getMessage();
		////			JSONParser parser =new JSONParser();
		////			JSONObject obj = null;
		////			try {
		////			obj = (JSONObject)parser.parse(lMsg);
		////		}catch(Exception ex_) {
		////			ex_.printStackTrace();
		////		}
		//			System.out.println("exception check check check check check ");
		//			
		//			long l_end_time = System.currentTimeMillis();
		//			long l_diff = l_end_time-l_time_start;
		//			return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(response.getStatusCode(), ResponseStatusEnum.FAILED, ApplicationResponse.Failed,lMsg,l_diff+" ms"),response.getStatusCode());				
		//
		//		}


		catch(HttpClientErrorException ex) {
			System.out.println("herehherehehrhehehrehrherhe");
			ex.printStackTrace();
			String msg = ex.getResponseBodyAsString();

			JSONParser parser =new JSONParser();
			JSONObject obj = null;
			try {
				obj = (JSONObject)parser.parse(msg);

			}catch(Exception ex_) {
				ex_.printStackTrace();
				return new ResponseEntity<>(obj+" error", HttpStatus.INTERNAL_SERVER_ERROR);

			}
			return new ResponseEntity<>(obj+" error", ex.getStatusCode());

		}
	}


	@PutMapping(value="item/updateItem")
	public ResponseEntity<?> updateItem(@RequestParam(name = "item_id",required = true)String item_id,  @RequestBody JSONObject json){

		String accessToken = TokenUtility.getAccessTokenFromFile();
		//System.out.println("ACCESS TOKEN : "+accessToken);

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.add("Authorization", ""+accessToken);
		ResponseEntity<String> response = null;
		HttpEntity formEntity = new HttpEntity(json, headers);
		try {
			response = restTemplate.exchange("https://api.metricserp4.metricserp.net/api/item/update-item?item_id="+item_id, HttpMethod.PUT,
					formEntity, String.class);

			if(response!=null && response.getStatusCode().is2xxSuccessful()) {
				String actualResponse = response.getBody();
				JSONParser parser =new JSONParser();
				JSONObject obj=null;
				try {
					obj = (JSONObject) parser.parse(actualResponse);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return new ResponseEntity<>(obj, HttpStatus.OK);
			}
			else if(response!=null && !response.getStatusCode().is2xxSuccessful()){
				System.out.println("1");
				return new ResponseEntity<>(response, response.getStatusCode());
			}
			else
			{
				System.out.println("2");
				return new ResponseEntity<>("ERROR", response.getStatusCode());

			}
		}
		//			catch(HttpClientErrorException ex) {
		//			ex.printStackTrace();
		//			String lMsg = ex.getLocalizedMessage();
		//			//String msg = ex.getMessage();
		////			JSONParser parser =new JSONParser();
		////			JSONObject obj = null;
		////			try {
		////			obj = (JSONObject)parser.parse(lMsg);
		////		}catch(Exception ex_) {
		////			ex_.printStackTrace();
		////		}
		//			System.out.println("exception check check check check check ");
		//			
		//			long l_end_time = System.currentTimeMillis();
		//			long l_diff = l_end_time-l_time_start;
		//			return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(response.getStatusCode(), ResponseStatusEnum.FAILED, ApplicationResponse.Failed,lMsg,l_diff+" ms"),response.getStatusCode());				
		//
		//		}


		catch(HttpClientErrorException ex) {
			System.out.println("herehherehehrhehehrehrherhe");
			ex.printStackTrace();
			String msg = ex.getResponseBodyAsString();

			JSONParser parser =new JSONParser();
			JSONObject obj = null;
			try {
				obj = (JSONObject)parser.parse(msg);

			}catch(Exception ex_) {
				ex_.printStackTrace();
				return new ResponseEntity<>(obj+" error", HttpStatus.INTERNAL_SERVER_ERROR);

			}
			return new ResponseEntity<>(obj+" error", ex.getStatusCode());

		}
	}


	
	
	@GetMapping(value="item/allItems")
	public ResponseEntity<?> allItems(@RequestParam(name = "pageNo",required = true)String pageNo,@RequestParam(name = "pageSize",required = true)String pageSize){

		String accessToken = TokenUtility.getAccessTokenFromFile();
		//System.out.println("ACCESS TOKEN : "+accessToken);

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.add("Authorization", ""+accessToken);
		ResponseEntity<String> response = null;
		HttpEntity formEntity = new HttpEntity(null, headers);
		try {
			response = restTemplate.exchange("https://api.metricserp4.metricserp.net/api/item/all-items?pageNo="+pageNo+"&pageSize="+pageSize, HttpMethod.GET,
					formEntity, String.class);

			if(response!=null && response.getStatusCode().is2xxSuccessful()) {
				String actualResponse = response.getBody();
				JSONParser parser =new JSONParser();
				JSONObject obj=null;
				try {
					obj = (JSONObject) parser.parse(actualResponse);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return new ResponseEntity<>(obj, HttpStatus.OK);
			}
			else if(response!=null && !response.getStatusCode().is2xxSuccessful()){
				System.out.println("1");
				return new ResponseEntity<>(response, response.getStatusCode());
			}
			else
			{
				System.out.println("2");
				return new ResponseEntity<>("ERROR", response.getStatusCode());

			}
		}
		//			catch(HttpClientErrorException ex) {
		//			ex.printStackTrace();
		//			String lMsg = ex.getLocalizedMessage();
		//			//String msg = ex.getMessage();
		////			JSONParser parser =new JSONParser();
		////			JSONObject obj = null;
		////			try {
		////			obj = (JSONObject)parser.parse(lMsg);
		////		}catch(Exception ex_) {
		////			ex_.printStackTrace();
		////		}
		//			System.out.println("exception check check check check check ");
		//			
		//			long l_end_time = System.currentTimeMillis();
		//			long l_diff = l_end_time-l_time_start;
		//			return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(response.getStatusCode(), ResponseStatusEnum.FAILED, ApplicationResponse.Failed,lMsg,l_diff+" ms"),response.getStatusCode());				
		//
		//		}


		catch(HttpClientErrorException ex) {
			System.out.println("herehherehehrhehehrehrherhe");
			ex.printStackTrace();
			String msg = ex.getResponseBodyAsString();

			JSONParser parser =new JSONParser();
			JSONObject obj = null;
			try {
				obj = (JSONObject)parser.parse(msg);

			}catch(Exception ex_) {
				ex_.printStackTrace();
				return new ResponseEntity<>(obj+" error", HttpStatus.INTERNAL_SERVER_ERROR);

			}
			return new ResponseEntity<>(obj+" error", ex.getStatusCode());

		}
	}
	
	
	@GetMapping(value="item/getItem")
	public ResponseEntity<?> getItem(@RequestParam(name = "item_id",required = true)String item_id){

		String accessToken = TokenUtility.getAccessTokenFromFile();
		//System.out.println("ACCESS TOKEN : "+accessToken);

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.add("Authorization", ""+accessToken);
		ResponseEntity<String> response = null;
		HttpEntity formEntity = new HttpEntity(null, headers);
		try {
			response = restTemplate.exchange("https://api.metricserp4.metricserp.net/api/item/get-item?item_id="+item_id, HttpMethod.GET,
					formEntity, String.class);

			if(response!=null && response.getStatusCode().is2xxSuccessful()) {
				String actualResponse = response.getBody();
				JSONParser parser =new JSONParser();
				JSONObject obj=null;
				try {
					obj = (JSONObject) parser.parse(actualResponse);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return new ResponseEntity<>(obj, HttpStatus.OK);
			}
			else if(response!=null && !response.getStatusCode().is2xxSuccessful()){
				System.out.println("1");
				return new ResponseEntity<>(response, response.getStatusCode());
			}
			else
			{
				System.out.println("2");
				return new ResponseEntity<>("ERROR", response.getStatusCode());

			}
		}
		//			catch(HttpClientErrorException ex) {
		//			ex.printStackTrace();
		//			String lMsg = ex.getLocalizedMessage();
		//			//String msg = ex.getMessage();
		////			JSONParser parser =new JSONParser();
		////			JSONObject obj = null;
		////			try {
		////			obj = (JSONObject)parser.parse(lMsg);
		////		}catch(Exception ex_) {
		////			ex_.printStackTrace();
		////		}
		//			System.out.println("exception check check check check check ");
		//			
		//			long l_end_time = System.currentTimeMillis();
		//			long l_diff = l_end_time-l_time_start;
		//			return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(response.getStatusCode(), ResponseStatusEnum.FAILED, ApplicationResponse.Failed,lMsg,l_diff+" ms"),response.getStatusCode());				
		//
		//		}


		catch(HttpClientErrorException ex) {
			System.out.println("herehherehehrhehehrehrherhe");
			ex.printStackTrace();
			String msg = ex.getResponseBodyAsString();

			JSONParser parser =new JSONParser();
			JSONObject obj = null;
			try {
				obj = (JSONObject)parser.parse(msg);

			}catch(Exception ex_) {
				ex_.printStackTrace();
				return new ResponseEntity<>(obj+" error", HttpStatus.INTERNAL_SERVER_ERROR);

			}
			return new ResponseEntity<>(obj+" error", ex.getStatusCode());

		}
	}
//============================ C U S T O M E R ==================================	
	@PostMapping(value="customer/addCustomer")
	public ResponseEntity<?> addCustomer(@RequestBody JSONObject json){

		String accessToken = TokenUtility.getAccessTokenFromFile();
		//System.out.println("ACCESS TOKEN : "+accessToken);

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.add("Authorization", ""+accessToken);
		ResponseEntity<String> response = null;
		HttpEntity formEntity = new HttpEntity(json, headers);
		try {
			response = restTemplate.exchange("https://api.metricserp4.metricserp.net/api/customer/add-customer", HttpMethod.POST,
					formEntity, String.class);

			if(response!=null && response.getStatusCode().is2xxSuccessful()) {
				String actualResponse = response.getBody();
				JSONParser parser =new JSONParser();
				JSONObject obj=null;
				try {
					obj = (JSONObject) parser.parse(actualResponse);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return new ResponseEntity<>(obj, HttpStatus.OK);
			}
			else if(response!=null && !response.getStatusCode().is2xxSuccessful()){
				System.out.println("1");
				return new ResponseEntity<>(response, response.getStatusCode());
			}
			else
			{
				System.out.println("2");
				return new ResponseEntity<>("ERROR", response.getStatusCode());

			}
		}
		//			catch(HttpClientErrorException ex) {
		//			ex.printStackTrace();
		//			String lMsg = ex.getLocalizedMessage();
		//			//String msg = ex.getMessage();
		////			JSONParser parser =new JSONParser();
		////			JSONObject obj = null;
		////			try {
		////			obj = (JSONObject)parser.parse(lMsg);
		////		}catch(Exception ex_) {
		////			ex_.printStackTrace();
		////		}
		//			System.out.println("exception check check check check check ");
		//			
		//			long l_end_time = System.currentTimeMillis();
		//			long l_diff = l_end_time-l_time_start;
		//			return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(response.getStatusCode(), ResponseStatusEnum.FAILED, ApplicationResponse.Failed,lMsg,l_diff+" ms"),response.getStatusCode());				
		//
		//		}


		catch(HttpClientErrorException ex) {
			System.out.println("herehherehehrhehehrehrherhe");
			ex.printStackTrace();
			String msg = ex.getResponseBodyAsString();

			JSONParser parser =new JSONParser();
			JSONObject obj = null;
			try {
				obj = (JSONObject)parser.parse(msg);

			}catch(Exception ex_) {
				ex_.printStackTrace();
				return new ResponseEntity<>(obj+" error", HttpStatus.INTERNAL_SERVER_ERROR);

			}
			return new ResponseEntity<>(obj+" error", ex.getStatusCode());

		}
	}


	@PutMapping(value="customer/updateCustomer")
	public ResponseEntity<?> updateCustomer(@RequestParam(name = "customer_id",required = true)String customer_id,  @RequestBody JSONObject json){

		String accessToken = TokenUtility.getAccessTokenFromFile();
		//System.out.println("ACCESS TOKEN : "+accessToken);

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.add("Authorization", ""+accessToken);
		ResponseEntity<String> response = null;
		HttpEntity formEntity = new HttpEntity(json, headers);
		try {
			response = restTemplate.exchange("https://api.metricserp4.metricserp.net/api/customer/update-customer?customer_id="+customer_id, HttpMethod.PUT,
					formEntity, String.class);

			if(response!=null && response.getStatusCode().is2xxSuccessful()) {
				String actualResponse = response.getBody();
				JSONParser parser =new JSONParser();
				JSONObject obj=null;
				try {
					obj = (JSONObject) parser.parse(actualResponse);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return new ResponseEntity<>(obj, HttpStatus.OK);
			}
			else if(response!=null && !response.getStatusCode().is2xxSuccessful()){
				System.out.println("1");
				return new ResponseEntity<>(response, response.getStatusCode());
			}
			else
			{
				System.out.println("2");
				return new ResponseEntity<>("ERROR", response.getStatusCode());

			}
		}
		//			catch(HttpClientErrorException ex) {
		//			ex.printStackTrace();
		//			String lMsg = ex.getLocalizedMessage();
		//			//String msg = ex.getMessage();
		////			JSONParser parser =new JSONParser();
		////			JSONObject obj = null;
		////			try {
		////			obj = (JSONObject)parser.parse(lMsg);
		////		}catch(Exception ex_) {
		////			ex_.printStackTrace();
		////		}
		//			System.out.println("exception check check check check check ");
		//			
		//			long l_end_time = System.currentTimeMillis();
		//			long l_diff = l_end_time-l_time_start;
		//			return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(response.getStatusCode(), ResponseStatusEnum.FAILED, ApplicationResponse.Failed,lMsg,l_diff+" ms"),response.getStatusCode());				
		//
		//		}


		catch(HttpClientErrorException ex) {
			System.out.println("herehherehehrhehehrehrherhe");
			ex.printStackTrace();
			String msg = ex.getResponseBodyAsString();

			JSONParser parser =new JSONParser();
			JSONObject obj = null;
			try {
				obj = (JSONObject)parser.parse(msg);

			}catch(Exception ex_) {
				ex_.printStackTrace();
				return new ResponseEntity<>(obj+" error", HttpStatus.INTERNAL_SERVER_ERROR);

			}
			return new ResponseEntity<>(obj+" error", ex.getStatusCode());

		}
	}


	
//	
	@GetMapping(value="customer/allCustomers")
	public ResponseEntity<?> allICustomers(@RequestParam(name = "pageNo",required = true)String pageNo,@RequestParam(name = "pageSize",required = true)String pageSize){

		String accessToken = TokenUtility.getAccessTokenFromFile();
		//System.out.println("ACCESS TOKEN : "+accessToken);

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.add("Authorization", ""+accessToken);
		ResponseEntity<String> response = null;
		HttpEntity formEntity = new HttpEntity(null, headers);
		try {
			response = restTemplate.exchange("https://api.metricserp4.metricserp.net/api/customer/all-customers?pageNo="+pageNo+"&pageSize="+pageSize, HttpMethod.GET,
					formEntity, String.class);

			if(response!=null && response.getStatusCode().is2xxSuccessful()) {
				String actualResponse = response.getBody();
				JSONParser parser =new JSONParser();
				JSONObject obj=null;
				try {
					obj = (JSONObject) parser.parse(actualResponse);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return new ResponseEntity<>(obj, HttpStatus.OK);
			}
			else if(response!=null && !response.getStatusCode().is2xxSuccessful()){
				System.out.println("1");
				return new ResponseEntity<>(response, response.getStatusCode());
			}
			else
			{
				System.out.println("2");
				return new ResponseEntity<>("ERROR", response.getStatusCode());

			}
		}
		//			catch(HttpClientErrorException ex) {
		//			ex.printStackTrace();
		//			String lMsg = ex.getLocalizedMessage();
		//			//String msg = ex.getMessage();
		////			JSONParser parser =new JSONParser();
		////			JSONObject obj = null;
		////			try {
		////			obj = (JSONObject)parser.parse(lMsg);
		////		}catch(Exception ex_) {
		////			ex_.printStackTrace();
		////		}
		//			System.out.println("exception check check check check check ");
		//			
		//			long l_end_time = System.currentTimeMillis();
		//			long l_diff = l_end_time-l_time_start;
		//			return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(response.getStatusCode(), ResponseStatusEnum.FAILED, ApplicationResponse.Failed,lMsg,l_diff+" ms"),response.getStatusCode());				
		//
		//		}


		catch(HttpClientErrorException ex) {
			System.out.println("herehherehehrhehehrehrherhe");
			ex.printStackTrace();
			String msg = ex.getResponseBodyAsString();

			JSONParser parser =new JSONParser();
			JSONObject obj = null;
			try {
				obj = (JSONObject)parser.parse(msg);

			}catch(Exception ex_) {
				ex_.printStackTrace();
				return new ResponseEntity<>(obj+" error", HttpStatus.INTERNAL_SERVER_ERROR);

			}
			return new ResponseEntity<>(obj+" error", ex.getStatusCode());

		}
	}
	
	
	@GetMapping(value="customer/getCustomer")
	public ResponseEntity<?> getCustomer(@RequestParam(name = "customer_id",required = true)String customer_id){

		String accessToken = TokenUtility.getAccessTokenFromFile();
		//System.out.println("ACCESS TOKEN : "+accessToken);

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.add("Authorization", ""+accessToken);
		ResponseEntity<String> response = null;
		HttpEntity formEntity = new HttpEntity(null, headers);
		try {
			response = restTemplate.exchange("https://api.metricserp4.metricserp.net/api/customer/get-customer?customer_id="+customer_id, HttpMethod.GET,
					formEntity, String.class);

			if(response!=null && response.getStatusCode().is2xxSuccessful()) {
				String actualResponse = response.getBody();
				JSONParser parser =new JSONParser();
				JSONObject obj=null;
				try {
					obj = (JSONObject) parser.parse(actualResponse);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return new ResponseEntity<>(obj, HttpStatus.OK);
			}
			else if(response!=null && !response.getStatusCode().is2xxSuccessful()){
				System.out.println("1");
				return new ResponseEntity<>(response, response.getStatusCode());
			}
			else
			{
				System.out.println("2");
				return new ResponseEntity<>("ERROR", response.getStatusCode());

			}
		}
		//			catch(HttpClientErrorException ex) {
		//			ex.printStackTrace();
		//			String lMsg = ex.getLocalizedMessage();
		//			//String msg = ex.getMessage();
		////			JSONParser parser =new JSONParser();
		////			JSONObject obj = null;
		////			try {
		////			obj = (JSONObject)parser.parse(lMsg);
		////		}catch(Exception ex_) {
		////			ex_.printStackTrace();
		////		}
		//			System.out.println("exception check check check check check ");
		//			
		//			long l_end_time = System.currentTimeMillis();
		//			long l_diff = l_end_time-l_time_start;
		//			return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(response.getStatusCode(), ResponseStatusEnum.FAILED, ApplicationResponse.Failed,lMsg,l_diff+" ms"),response.getStatusCode());				
		//
		//		}


		catch(HttpClientErrorException ex) {
			System.out.println("herehherehehrhehehrehrherhe");
			ex.printStackTrace();
			String msg = ex.getResponseBodyAsString();

			JSONParser parser =new JSONParser();
			JSONObject obj = null;
			try {
				obj = (JSONObject)parser.parse(msg);

			}catch(Exception ex_) {
				ex_.printStackTrace();
				return new ResponseEntity<>(obj+" error", HttpStatus.INTERNAL_SERVER_ERROR);

			}
			return new ResponseEntity<>(obj+" error", ex.getStatusCode());

		}
	}

	
//===================== S A L E S   O R D E R ====================================
	@PostMapping(value="sales/addSalesOrder")
	public ResponseEntity<?> addSalesOrder(@RequestBody JSONObject json){

		String accessToken = TokenUtility.getAccessTokenFromFile();
		//System.out.println("ACCESS TOKEN : "+accessToken);

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.add("Authorization", ""+accessToken);
		ResponseEntity<String> response = null;
		HttpEntity formEntity = new HttpEntity(json, headers);
		try {
			response = restTemplate.exchange("https://api.metricserp4.metricserp.net/api/sales-order/add-sales-order", HttpMethod.POST,
					formEntity, String.class);

			if(response!=null && response.getStatusCode().is2xxSuccessful()) {
				String actualResponse = response.getBody();
				JSONParser parser =new JSONParser();
				JSONObject obj=null;
				try {
					obj = (JSONObject) parser.parse(actualResponse);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return new ResponseEntity<>(obj, HttpStatus.OK);
			}
			else if(response!=null && !response.getStatusCode().is2xxSuccessful()){
				System.out.println("1");
				return new ResponseEntity<>(response, response.getStatusCode());
			}
			else
			{
				System.out.println("2");
				return new ResponseEntity<>("ERROR", response.getStatusCode());

			}
		}
		//			catch(HttpClientErrorException ex) {
		//			ex.printStackTrace();
		//			String lMsg = ex.getLocalizedMessage();
		//			//String msg = ex.getMessage();
		////			JSONParser parser =new JSONParser();
		////			JSONObject obj = null;
		////			try {
		////			obj = (JSONObject)parser.parse(lMsg);
		////		}catch(Exception ex_) {
		////			ex_.printStackTrace();
		////		}
		//			System.out.println("exception check check check check check ");
		//			
		//			long l_end_time = System.currentTimeMillis();
		//			long l_diff = l_end_time-l_time_start;
		//			return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(response.getStatusCode(), ResponseStatusEnum.FAILED, ApplicationResponse.Failed,lMsg,l_diff+" ms"),response.getStatusCode());				
		//
		//		}


		catch(HttpClientErrorException ex) {
			System.out.println("herehherehehrhehehrehrherhe");
			ex.printStackTrace();
			String msg = ex.getResponseBodyAsString();

			JSONParser parser =new JSONParser();
			JSONObject obj = null;
			try {
				obj = (JSONObject)parser.parse(msg);

			}catch(Exception ex_) {
				ex_.printStackTrace();
				return new ResponseEntity<>(obj+" error", HttpStatus.INTERNAL_SERVER_ERROR);

			}
			return new ResponseEntity<>(obj+" error", ex.getStatusCode());

		}
	}


	@PutMapping(value="sales/updateSalesOrder")
	public ResponseEntity<?> updateSalesOrder(@RequestParam(name = "id",required = true)String id,  @RequestBody JSONObject json){

		String accessToken = TokenUtility.getAccessTokenFromFile();
		//System.out.println("ACCESS TOKEN : "+accessToken);

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.add("Authorization", ""+accessToken);
		ResponseEntity<String> response = null;
		HttpEntity formEntity = new HttpEntity(json, headers);
		try {
			response = restTemplate.exchange("https://api.metricserp4.metricserp.net/api/sales-order/update-sales-order?id="+id, HttpMethod.PUT,
					formEntity, String.class);

			if(response!=null && response.getStatusCode().is2xxSuccessful()) {
				String actualResponse = response.getBody();
				JSONParser parser =new JSONParser();
				JSONObject obj=null;
				try {
					obj = (JSONObject) parser.parse(actualResponse);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return new ResponseEntity<>(obj, HttpStatus.OK);
			}
			else if(response!=null && !response.getStatusCode().is2xxSuccessful()){
				System.out.println("1");
				return new ResponseEntity<>(response, response.getStatusCode());
			}
			else
			{
				System.out.println("2");
				return new ResponseEntity<>("ERROR", response.getStatusCode());

			}
		}
		//			catch(HttpClientErrorException ex) {
		//			ex.printStackTrace();
		//			String lMsg = ex.getLocalizedMessage();
		//			//String msg = ex.getMessage();
		////			JSONParser parser =new JSONParser();
		////			JSONObject obj = null;
		////			try {
		////			obj = (JSONObject)parser.parse(lMsg);
		////		}catch(Exception ex_) {
		////			ex_.printStackTrace();
		////		}
		//			System.out.println("exception check check check check check ");
		//			
		//			long l_end_time = System.currentTimeMillis();
		//			long l_diff = l_end_time-l_time_start;
		//			return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(response.getStatusCode(), ResponseStatusEnum.FAILED, ApplicationResponse.Failed,lMsg,l_diff+" ms"),response.getStatusCode());				
		//
		//		}


		catch(HttpClientErrorException ex) {
			System.out.println("herehherehehrhehehrehrherhe");
			ex.printStackTrace();
			String msg = ex.getResponseBodyAsString();

			JSONParser parser =new JSONParser();
			JSONObject obj = null;
			try {
				obj = (JSONObject)parser.parse(msg);

			}catch(Exception ex_) {
				ex_.printStackTrace();
				return new ResponseEntity<>(obj+" error", HttpStatus.INTERNAL_SERVER_ERROR);

			}
			return new ResponseEntity<>(obj+" error", ex.getStatusCode());

		}
	}


	
//	
	@GetMapping(value="sales/allSalesOrder")
	public ResponseEntity<?> allSalesOrder(@RequestParam(name = "pageNo",required = true)String pageNo,@RequestParam(name = "pageSize",required = true)String pageSize,@RequestParam(name = "currency_id",required = true)String currency_id){

		String accessToken = TokenUtility.getAccessTokenFromFile();
		//System.out.println("ACCESS TOKEN : "+accessToken);

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.add("Authorization", ""+accessToken);
		ResponseEntity<String> response = null;
		HttpEntity formEntity = new HttpEntity(null, headers);
		try {
			response = restTemplate.exchange("https://api.metricserp4.metricserp.net/api/sales-order/all-sales-order?pageNo="+pageNo+"&pageSize="+pageSize+"&currency_id="+currency_id, HttpMethod.GET,
					formEntity, String.class);

			if(response!=null && response.getStatusCode().is2xxSuccessful()) {
				String actualResponse = response.getBody();
				JSONParser parser =new JSONParser();
				JSONObject obj=null;
				try {
					obj = (JSONObject) parser.parse(actualResponse);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return new ResponseEntity<>(obj, HttpStatus.OK);
			}
			else if(response!=null && !response.getStatusCode().is2xxSuccessful()){
				System.out.println("1");
				return new ResponseEntity<>(response, response.getStatusCode());
			}
			else
			{
				System.out.println("2");
				return new ResponseEntity<>("ERROR", response.getStatusCode());

			}
		}
		//			catch(HttpClientErrorException ex) {
		//			ex.printStackTrace();
		//			String lMsg = ex.getLocalizedMessage();
		//			//String msg = ex.getMessage();
		////			JSONParser parser =new JSONParser();
		////			JSONObject obj = null;
		////			try {
		////			obj = (JSONObject)parser.parse(lMsg);
		////		}catch(Exception ex_) {
		////			ex_.printStackTrace();
		////		}
		//			System.out.println("exception check check check check check ");
		//			
		//			long l_end_time = System.currentTimeMillis();
		//			long l_diff = l_end_time-l_time_start;
		//			return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(response.getStatusCode(), ResponseStatusEnum.FAILED, ApplicationResponse.Failed,lMsg,l_diff+" ms"),response.getStatusCode());				
		//
		//		}


		catch(HttpClientErrorException ex) {
			System.out.println("herehherehehrhehehrehrherhe");
			ex.printStackTrace();
			String msg = ex.getResponseBodyAsString();

			JSONParser parser =new JSONParser();
			JSONObject obj = null;
			try {
				obj = (JSONObject)parser.parse(msg);

			}catch(Exception ex_) {
				ex_.printStackTrace();
				return new ResponseEntity<>(obj+" error", HttpStatus.INTERNAL_SERVER_ERROR);

			}
			return new ResponseEntity<>(obj+" error", ex.getStatusCode());

		}
	}
	
	
//	
	@GetMapping(value="sales/getById")
	public ResponseEntity<?> getById(@RequestParam(name = "pageNo",required = true)String pageNo,@RequestParam(name = "pageSize",required = true)String pageSize,@RequestParam(name = "id",required = true)String id){

		String accessToken = TokenUtility.getAccessTokenFromFile();
		//System.out.println("ACCESS TOKEN : "+accessToken);

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.add("Authorization", ""+accessToken);
		ResponseEntity<String> response = null;
		HttpEntity formEntity = new HttpEntity(null, headers);
		try {
			response = restTemplate.exchange("https://api.metricserp4.metricserp.net/api/sales-order/get-sales-order?id="+id+"&pageNo="+pageNo+"&pageSize="+pageSize, HttpMethod.GET,
					formEntity, String.class);

			if(response!=null && response.getStatusCode().is2xxSuccessful()) {
				String actualResponse = response.getBody();
				JSONParser parser =new JSONParser();
				JSONObject obj=null;
				try {
					obj = (JSONObject) parser.parse(actualResponse);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return new ResponseEntity<>(obj, HttpStatus.OK);
			}
			else if(response!=null && !response.getStatusCode().is2xxSuccessful()){
				System.out.println("1");
				return new ResponseEntity<>(response, response.getStatusCode());
			}
			else
			{
				System.out.println("2");
				return new ResponseEntity<>("ERROR", response.getStatusCode());

			}
		}
		//			catch(HttpClientErrorException ex) {
		//			ex.printStackTrace();
		//			String lMsg = ex.getLocalizedMessage();
		//			//String msg = ex.getMessage();
		////			JSONParser parser =new JSONParser();
		////			JSONObject obj = null;
		////			try {
		////			obj = (JSONObject)parser.parse(lMsg);
		////		}catch(Exception ex_) {
		////			ex_.printStackTrace();
		////		}
		//			System.out.println("exception check check check check check ");
		//			
		//			long l_end_time = System.currentTimeMillis();
		//			long l_diff = l_end_time-l_time_start;
		//			return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(response.getStatusCode(), ResponseStatusEnum.FAILED, ApplicationResponse.Failed,lMsg,l_diff+" ms"),response.getStatusCode());				
		//
		//		}


		catch(HttpClientErrorException ex) {
			System.out.println("herehherehehrhehehrehrherhe");
			ex.printStackTrace();
			String msg = ex.getResponseBodyAsString();

			JSONParser parser =new JSONParser();
			JSONObject obj = null;
			try {
				obj = (JSONObject)parser.parse(msg);

			}catch(Exception ex_) {
				ex_.printStackTrace();
				return new ResponseEntity<>(obj+" error", HttpStatus.INTERNAL_SERVER_ERROR);

			}
			return new ResponseEntity<>(obj+" error", ex.getStatusCode());

		}
	}

//======================= R E C E I V A B L E    I N V O I C E ==================
	
	
//
	@PutMapping(value="invoice/updateReceivableInvoice")
	public ResponseEntity<?> updateReceivableInvoice(@RequestParam(name = "si_header_id",required = true)String si_header_id,  @RequestBody JSONObject json){

		String accessToken = TokenUtility.getAccessTokenFromFile();
		//System.out.println("ACCESS TOKEN : "+accessToken);

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.add("Authorization", ""+accessToken);
		ResponseEntity<String> response = null;
		HttpEntity formEntity = new HttpEntity(json, headers);
		try {
			response = restTemplate.exchange("https://api.metricserp4.metricserp.net/api/receivable-invoice/update-receivable-invoice?si_header_id="+si_header_id, HttpMethod.PUT,
					formEntity, String.class);

			if(response!=null && response.getStatusCode().is2xxSuccessful()) {
				String actualResponse = response.getBody();
				JSONParser parser =new JSONParser();
				JSONObject obj=null;
				try {
					obj = (JSONObject) parser.parse(actualResponse);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return new ResponseEntity<>(obj, HttpStatus.OK);
			}
			else if(response!=null && !response.getStatusCode().is2xxSuccessful()){
				System.out.println("1");
				return new ResponseEntity<>(response, response.getStatusCode());
			}
			else
			{
				System.out.println("2");
				return new ResponseEntity<>("ERROR", response.getStatusCode());

			}
		}
		//			catch(HttpClientErrorException ex) {
		//			ex.printStackTrace();
		//			String lMsg = ex.getLocalizedMessage();
		//			//String msg = ex.getMessage();
		////			JSONParser parser =new JSONParser();
		////			JSONObject obj = null;
		////			try {
		////			obj = (JSONObject)parser.parse(lMsg);
		////		}catch(Exception ex_) {
		////			ex_.printStackTrace();
		////		}
		//			System.out.println("exception check check check check check ");
		//			
		//			long l_end_time = System.currentTimeMillis();
		//			long l_diff = l_end_time-l_time_start;
		//			return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(response.getStatusCode(), ResponseStatusEnum.FAILED, ApplicationResponse.Failed,lMsg,l_diff+" ms"),response.getStatusCode());				
		//
		//		}


		catch(HttpClientErrorException ex) {
			System.out.println("herehherehehrhehehrehrherhe");
			ex.printStackTrace();
			String msg = ex.getResponseBodyAsString();

			JSONParser parser =new JSONParser();
			JSONObject obj = null;
			try {
				obj = (JSONObject)parser.parse(msg);

			}catch(Exception ex_) {
				ex_.printStackTrace();
				return new ResponseEntity<>(obj+" error", HttpStatus.INTERNAL_SERVER_ERROR);

			}
			return new ResponseEntity<>(obj+" error", ex.getStatusCode());

		}
	}


	
//	
	@GetMapping(value="invoice/getAll")
	public ResponseEntity<?> getAll(@RequestParam(name = "pageNo",required = true)String pageNo,@RequestParam(name = "pageSize",required = true)String pageSize,@RequestParam(name = "customer",required = true)String customer){

		String accessToken = TokenUtility.getAccessTokenFromFile();
		//System.out.println("ACCESS TOKEN : "+accessToken);

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.add("Authorization", ""+accessToken);
		ResponseEntity<String> response = null;
		HttpEntity formEntity = new HttpEntity(null, headers);
		try {
			response = restTemplate.exchange("https://api.metricserp4.metricserp.net/api/receivable-invoice/all-receivable-invoice?pageNo=1&pageSize=10&customer="+customer, HttpMethod.GET,
					formEntity, String.class);

			if(response!=null && response.getStatusCode().is2xxSuccessful()) {
				String actualResponse = response.getBody();
				JSONParser parser =new JSONParser();
				JSONObject obj=null;
				try {
					obj = (JSONObject) parser.parse(actualResponse);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return new ResponseEntity<>(obj, HttpStatus.OK);
			}
			else if(response!=null && !response.getStatusCode().is2xxSuccessful()){
				System.out.println("1");
				return new ResponseEntity<>(response, response.getStatusCode());
			}
			else
			{
				System.out.println("2");
				return new ResponseEntity<>("ERROR", response.getStatusCode());

			}
		}
		//			catch(HttpClientErrorException ex) {
		//			ex.printStackTrace();
		//			String lMsg = ex.getLocalizedMessage();
		//			//String msg = ex.getMessage();
		////			JSONParser parser =new JSONParser();
		////			JSONObject obj = null;
		////			try {
		////			obj = (JSONObject)parser.parse(lMsg);
		////		}catch(Exception ex_) {
		////			ex_.printStackTrace();
		////		}
		//			System.out.println("exception check check check check check ");
		//			
		//			long l_end_time = System.currentTimeMillis();
		//			long l_diff = l_end_time-l_time_start;
		//			return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(response.getStatusCode(), ResponseStatusEnum.FAILED, ApplicationResponse.Failed,lMsg,l_diff+" ms"),response.getStatusCode());				
		//
		//		}


		catch(HttpClientErrorException ex) {
			System.out.println("herehherehehrhehehrehrherhe");
			ex.printStackTrace();
			String msg = ex.getResponseBodyAsString();

			JSONParser parser =new JSONParser();
			JSONObject obj = null;
			try {
				obj = (JSONObject)parser.parse(msg);

			}catch(Exception ex_) {
				ex_.printStackTrace();
				return new ResponseEntity<>(obj+" error", HttpStatus.INTERNAL_SERVER_ERROR);

			}
			return new ResponseEntity<>(obj+" error", ex.getStatusCode());

		}
	}
	
	
//	
	@GetMapping(value="invoice/getById")
	public ResponseEntity<?> getById(@RequestParam(name = "id",required = true)String id){

		String accessToken = TokenUtility.getAccessTokenFromFile();
		//System.out.println("ACCESS TOKEN : "+accessToken);

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.add("Authorization", ""+accessToken);
		ResponseEntity<String> response = null;
		HttpEntity formEntity = new HttpEntity(null, headers);
		try {
			response = restTemplate.exchange("https://api.metricserp4.metricserp.net/api/receivable-invoice/get-receivable-invoice?id="+id, HttpMethod.GET,
					formEntity, String.class);

			if(response!=null && response.getStatusCode().is2xxSuccessful()) {
				String actualResponse = response.getBody();
				JSONParser parser =new JSONParser();
				JSONObject obj=null;
				try {
					obj = (JSONObject) parser.parse(actualResponse);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return new ResponseEntity<>(obj, HttpStatus.OK);
			}
			else if(response!=null && !response.getStatusCode().is2xxSuccessful()){
				System.out.println("1");
				return new ResponseEntity<>(response, response.getStatusCode());
			}
			else
			{
				System.out.println("2");
				return new ResponseEntity<>("ERROR", response.getStatusCode());

			}
		}
		//			catch(HttpClientErrorException ex) {
		//			ex.printStackTrace();
		//			String lMsg = ex.getLocalizedMessage();
		//			//String msg = ex.getMessage();
		////			JSONParser parser =new JSONParser();
		////			JSONObject obj = null;
		////			try {
		////			obj = (JSONObject)parser.parse(lMsg);
		////		}catch(Exception ex_) {
		////			ex_.printStackTrace();
		////		}
		//			System.out.println("exception check check check check check ");
		//			
		//			long l_end_time = System.currentTimeMillis();
		//			long l_diff = l_end_time-l_time_start;
		//			return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(response.getStatusCode(), ResponseStatusEnum.FAILED, ApplicationResponse.Failed,lMsg,l_diff+" ms"),response.getStatusCode());				
		//
		//		}


		catch(HttpClientErrorException ex) {
			System.out.println("herehherehehrhehehrehrherhe");
			ex.printStackTrace();
			String msg = ex.getResponseBodyAsString();

			JSONParser parser =new JSONParser();
			JSONObject obj = null;
			try {
				obj = (JSONObject)parser.parse(msg);

			}catch(Exception ex_) {
				ex_.printStackTrace();
				return new ResponseEntity<>(obj+" error", HttpStatus.INTERNAL_SERVER_ERROR);

			}
			return new ResponseEntity<>(obj+" error", ex.getStatusCode());

		}
	}

	
	//======================= P A Y M E N T ==================
	@PostMapping(value="payment/addPayment")
	public ResponseEntity<?> addPayment(@RequestBody JSONObject json){

		String accessToken = TokenUtility.getAccessTokenFromFile();
		//System.out.println("ACCESS TOKEN : "+accessToken);

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.add("Authorization", ""+accessToken);
		ResponseEntity<String> response = null;
		HttpEntity formEntity = new HttpEntity(json, headers);
		try {
			response = restTemplate.exchange("https://api.metricserp4.metricserp.net/api/receivable-payment/add-receivable-payment", HttpMethod.POST,
					formEntity, String.class);

			if(response!=null && response.getStatusCode().is2xxSuccessful()) {
				String actualResponse = response.getBody();
				JSONParser parser =new JSONParser();
				JSONObject obj=null;
				try {
					obj = (JSONObject) parser.parse(actualResponse);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return new ResponseEntity<>(obj, HttpStatus.OK);
			}
			else if(response!=null && !response.getStatusCode().is2xxSuccessful()){
				System.out.println("1");
				return new ResponseEntity<>(response, response.getStatusCode());
			}
			else
			{
				System.out.println("2");
				return new ResponseEntity<>("ERROR", response.getStatusCode());

			}
		}
		//			catch(HttpClientErrorException ex) {
		//			ex.printStackTrace();
		//			String lMsg = ex.getLocalizedMessage();
		//			//String msg = ex.getMessage();
		////			JSONParser parser =new JSONParser();
		////			JSONObject obj = null;
		////			try {
		////			obj = (JSONObject)parser.parse(lMsg);
		////		}catch(Exception ex_) {
		////			ex_.printStackTrace();
		////		}
		//			System.out.println("exception check check check check check ");
		//			
		//			long l_end_time = System.currentTimeMillis();
		//			long l_diff = l_end_time-l_time_start;
		//			return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(response.getStatusCode(), ResponseStatusEnum.FAILED, ApplicationResponse.Failed,lMsg,l_diff+" ms"),response.getStatusCode());				
		//
		//		}


		catch(HttpClientErrorException ex) {
			System.out.println("herehherehehrhehehrehrherhe");
			ex.printStackTrace();
			String msg = ex.getResponseBodyAsString();

			JSONParser parser =new JSONParser();
			JSONObject obj = null;
			try {
				obj = (JSONObject)parser.parse(msg);

			}catch(Exception ex_) {
				ex_.printStackTrace();
				return new ResponseEntity<>(obj+" error", HttpStatus.INTERNAL_SERVER_ERROR);

			}
			return new ResponseEntity<>(obj+" error", ex.getStatusCode());

		}
	}


		
	
	@GetMapping(value="payment/getAll")
	public ResponseEntity<?> paymentgetall(@RequestParam(name = "pageNo",required = true)String pageNo,@RequestParam(name = "pageSize",required = true)String pageSize){

		String accessToken = TokenUtility.getAccessTokenFromFile();
		//System.out.println("ACCESS TOKEN : "+accessToken);

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.add("Authorization", ""+accessToken);
		ResponseEntity<String> response = null;
		HttpEntity formEntity = new HttpEntity(null, headers);
		try {
			response = restTemplate.exchange("https://api.metricserp4.metricserp.net/api/receivable-payment/all-receivable-payment?pageNo="+pageNo+"&pageSize="+pageSize, HttpMethod.GET,
					formEntity, String.class);

			if(response!=null && response.getStatusCode().is2xxSuccessful()) {
				String actualResponse = response.getBody();
				JSONParser parser =new JSONParser();
				JSONObject obj=null;
				try {
					obj = (JSONObject) parser.parse(actualResponse);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return new ResponseEntity<>(obj, HttpStatus.OK);
			}
			else if(response!=null && !response.getStatusCode().is2xxSuccessful()){
				System.out.println("1");
				return new ResponseEntity<>(response, response.getStatusCode());
			}
			else
			{
				System.out.println("2");
				return new ResponseEntity<>("ERROR", response.getStatusCode());

			}
		}
		//			catch(HttpClientErrorException ex) {
		//			ex.printStackTrace();
		//			String lMsg = ex.getLocalizedMessage();
		//			//String msg = ex.getMessage();
		////			JSONParser parser =new JSONParser();
		////			JSONObject obj = null;
		////			try {
		////			obj = (JSONObject)parser.parse(lMsg);
		////		}catch(Exception ex_) {
		////			ex_.printStackTrace();
		////		}
		//			System.out.println("exception check check check check check ");
		//			
		//			long l_end_time = System.currentTimeMillis();
		//			long l_diff = l_end_time-l_time_start;
		//			return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(response.getStatusCode(), ResponseStatusEnum.FAILED, ApplicationResponse.Failed,lMsg,l_diff+" ms"),response.getStatusCode());				
		//
		//		}


		catch(HttpClientErrorException ex) {
			System.out.println("herehherehehrhehehrehrherhe");
			ex.printStackTrace();
			String msg = ex.getResponseBodyAsString();

			JSONParser parser =new JSONParser();
			JSONObject obj = null;
			try {
				obj = (JSONObject)parser.parse(msg);

			}catch(Exception ex_) {
				ex_.printStackTrace();
				return new ResponseEntity<>(obj+" error", HttpStatus.INTERNAL_SERVER_ERROR);

			}
			return new ResponseEntity<>(obj+" error", ex.getStatusCode());

		}
	}
	
	
	@GetMapping(value="payment/getById")
	public ResponseEntity<?> paymentgetbyid(@RequestParam(name = "id",required = true)String id){

		String accessToken = TokenUtility.getAccessTokenFromFile();
		//System.out.println("ACCESS TOKEN : "+accessToken);

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.add("Authorization", ""+accessToken);
		ResponseEntity<String> response = null;
		HttpEntity formEntity = new HttpEntity(null, headers);
		try {
			response = restTemplate.exchange("https://api.metricserp4.metricserp.net/api/receivable-payment/get-receivable-payment?id="+id, HttpMethod.GET,
					formEntity, String.class);

			if(response!=null && response.getStatusCode().is2xxSuccessful()) {
				String actualResponse = response.getBody();
				JSONParser parser =new JSONParser();
				JSONObject obj=null;
				try {
					obj = (JSONObject) parser.parse(actualResponse);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return new ResponseEntity<>(obj, HttpStatus.OK);
			}
			else if(response!=null && !response.getStatusCode().is2xxSuccessful()){
				System.out.println("1");
				return new ResponseEntity<>(response, response.getStatusCode());
			}
			else
			{
				System.out.println("2");
				return new ResponseEntity<>("ERROR", response.getStatusCode());

			}
		}
		//			catch(HttpClientErrorException ex) {
		//			ex.printStackTrace();
		//			String lMsg = ex.getLocalizedMessage();
		//			//String msg = ex.getMessage();
		////			JSONParser parser =new JSONParser();
		////			JSONObject obj = null;
		////			try {
		////			obj = (JSONObject)parser.parse(lMsg);
		////		}catch(Exception ex_) {
		////			ex_.printStackTrace();
		////		}
		//			System.out.println("exception check check check check check ");
		//			
		//			long l_end_time = System.currentTimeMillis();
		//			long l_diff = l_end_time-l_time_start;
		//			return	new ResponseEntity<CoreResponseHandler>(new SuccessResponseBeanRefined(response.getStatusCode(), ResponseStatusEnum.FAILED, ApplicationResponse.Failed,lMsg,l_diff+" ms"),response.getStatusCode());				
		//
		//		}


		catch(HttpClientErrorException ex) {
			System.out.println("herehherehehrhehehrehrherhe");
			ex.printStackTrace();
			String msg = ex.getResponseBodyAsString();

			JSONParser parser =new JSONParser();
			JSONObject obj = null;
			try {
				obj = (JSONObject)parser.parse(msg);

			}catch(Exception ex_) {
				ex_.printStackTrace();
				return new ResponseEntity<>(obj+" error", HttpStatus.INTERNAL_SERVER_ERROR);

			}
			return new ResponseEntity<>(obj+" error", ex.getStatusCode());

		}
	}
}
