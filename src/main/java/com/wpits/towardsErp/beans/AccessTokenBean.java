package com.wpits.towardsErp.beans;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccessTokenBean implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = -3198877781394255036L;
private String accessToken;
private LocalDateTime exDateTime;
}
