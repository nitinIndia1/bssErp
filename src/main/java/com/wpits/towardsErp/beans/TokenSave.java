package com.wpits.towardsErp.beans;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.*;
import java.util.function.*;
public class TokenSave implements Serializable{
	private static final long serialVersionUID = -242973056856159476L;
	
	private String token;
	private LocalDateTime expiryDate;
	
	public TokenSave() {
	}

	public TokenSave(String token, LocalDateTime expiryDate) {
		this.token = token;
		this.expiryDate = expiryDate;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LocalDateTime getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDateTime expiryDate) {
		this.expiryDate = expiryDate;
	}


	
	
	
}
