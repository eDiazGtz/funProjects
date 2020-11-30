package com.edgar.calendar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edgar.calendar.repositories.CalendarRepository;

@Service
public class CalendarService {

	@Autowired
	private CalendarRepository cRepo;
	
	public int[] yearToArray(int n) {
    	int ones;
    	int tens;
    	int hundreds;
    	int thousands;
    	
    	ones = (n%10);
    	tens = ((n/10)%10);
    	hundreds = ((n/100)%10);
    	thousands = ((n/1000));
    	
    	int[] digits = new int[4];
    	digits[0] = thousands;
    	digits[1] = hundreds;
    	digits[2] = tens;
    	digits[3] = ones;
    	return digits;	    	
    }
    

	public int calcForD(int[] arr) {
    	//last 2 digits of year (tens & ones)
    	int d = (arr[2]*10) + arr[3];
    	return d;
    }

    public int calcForC(int[] arr) {
    	//first 2 digits of year (thousands & hundreds)
    	int c = (arr[0]*10) + arr[1];
    	return c;
    }
    
    //Given Month, cal monthNum. Number of Month must start with March=1; Jan = 11, Feb=12;
    public int monthNumber(int m) {
    	//Calendar.Month pulls month number from 0 Index list -- jan=0.
    	int monthNum;
    	monthNum = (m-1);
    	//Set February
    	if(m == 0) {
    		monthNum=11;
    	}
    	//set January
    	if(m == -1) {
    		monthNum=12;
    	}
    	return monthNum;
    }
	
    //Using Zeller's Rule, for 1st of the Month. k=1;
    public int firstDayOfMonth(int year, int month) {
		int dateNum;
    	int k = 1; //Day of the Month 1st.
    	int m = monthNumber(month);
    	int[] dc = yearToArray(year);
    	int d = calcForD(dc);
    	int c = calcForC(dc);
    	
    	//Zeller's Rule
    	int f = k + ((13*m-1)/5) + d + (d+4) + (c/4) - 2 * c;
    	if(f<0) {
    		dateNum = (f%7)+7;
    	} else {
    		dateNum = f%7;
    	}
    	return dateNum;
    }
	
}
