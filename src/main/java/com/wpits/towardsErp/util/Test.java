package com.wpits.towardsErp.util;

import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import com.wpits.towardsErp.beans.TokenSave;

public class Test {
public static void main(String[] args) {
	
	
//	LocalDateTime now  = LocalDateTime.now();
//	
//	LocalDateTime nextDay = now.plusHours(1);
//	
//	System.out.println(nextDay);
//	
//	int value = now.compareTo(nextDay);
//	System.out.println(value);

	System.out.println("=======================================");
	
	
	LocalDateTime ldt1 = LocalDateTime.parse("2024-10-17T08:41:43.073132");
	LocalDateTime now = LocalDateTime.parse("2024-10-17T08:40:43.073132");
	
	System.out.println(now.compareTo(ldt1));
	
	
	
	}
    
 
}

