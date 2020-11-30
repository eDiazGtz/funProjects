package com.edgar.calendar.model;

import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="booking_calendar")
public class BookingCalendar {
	//Attributes
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;
	
	//Constructors
	public BookingCalendar() {
	}
		
	//Getters & Setters
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	//Methods
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
    	System.out.println(Arrays.toString(digits));
    	return digits;	    	
    }
    

	public int calcForD(int[] arr) {
    	//last 2 digits of year (tens & ones)
    	int d = (arr[2]*10) + arr[3];
    	System.out.println("d = " + d);
    	return d;
    }

    public int calcForC(int[] arr) {
    	//first 2 digits of year (thousands & hundreds)
    	int c = (arr[0]*10) + arr[1];
    	System.out.println("c = " + c);
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
    	System.out.println("monthNum = " + monthNum);
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
    
//    int NumberRowsNeeded(int year, int month) {
//    	  /*
//    	     USE:  Calculates number of rows needed for calendar.
//    	     IN:   year = given year after 1582 (start of the Gregorian calendar).
//    	           month = 0 for January, 1 for February, etc.
//    	     OUT:  Number of rows: 5 or 6, except for a 28 day February with
//    	           the first of the month on Sunday, requiring only four rows.
//    	  */
//
//			int firstDay; /* day of week for first day of month */
//			int numCells; /* number of cells needed by the month */
//
//			/* Start at 1582, when modern calendar starts. */
//			if (year < 1582)
//				return (-1);
//
//			/* Catch month out of range. */
//			if ((month < 0) || (month > 11))
//				return (-1);
//
//			/* Get first day of month. */
//			firstDay = CalcFirstOfMonth(year, month);
//
//			/* Non leap year February with 1st on Sunday: 4 rows. */
//			if ((month == FEBRUARY) && (firstDay == 0) && !IsLeapYear(year))
//				return (4);
//
//			/* Number of cells needed = blanks on 1st row + days in month. */
//			numCells = firstDay + DaysInMonth[month];
//
//			/* One more cell needed for the Feb 29th in leap year. */
//			if ((month == FEBRUARY) && (IsLeapYear(year)))
//				numCells++;
//
//			/* 35 cells or less is 5 rows; more is 6. */
//			return ((numCells <= 35) ? 5 : 6);
//		} // NumberRowsNeeded
}
