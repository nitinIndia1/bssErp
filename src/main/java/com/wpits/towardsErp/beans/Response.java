package com.wpits.towardsErp.beans;
import java.io.Serializable;
import java.util.*;
import java.util.stream.*;

import lombok.Data;

import java.util.function.*;
@Data
public class Response implements Serializable{

	/*
	 */
	private static final long serialVersionUID = -956229468681473205L;
	
	
	private String idToken;
	private String refreshToken;

}
