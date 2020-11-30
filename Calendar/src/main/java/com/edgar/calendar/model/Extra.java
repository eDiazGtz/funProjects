package com.edgar.calendar.model;

public class Extra {
//	//SpringApplication.run(CalendarApplication.class, args);
//	
//	//Can create new Calendar by Stating Year, Month, and Day -- optional to give hourOfDay minute and second
//	//GregorianCalendar calendar = new GregorianCalendar(2001, 11, 15);
//	
//	//Can pass TimeZone to create calendar with default time zone. GMT-8:00 is SanDiego
//	//GregorianCalendar tZoneCal = new GregorianCalendar(TimeZone.getTimeZone("GMT-8:00"));
//
//	//If printed will give day, Month, Year - formatted
//	//String date = new GregorianCalendar(2020, 11, 15).toZonedDateTime().format(DateTimeFormatter.ofPattern("d MMM uuuu"));
//
//	Calendar calendar = Calendar.getInstance();
//	
////	System.out.println(calendar.get(Calendar.YEAR));
////	calendar.set(Calendar.YEAR, 1990);
////	System.out.println(calendar.get(Calendar.YEAR));
////	printToday();
//	
//	
//}
//
////Calendar.MONTH is a 0-Index List. January = 0;
//public static void printToday() {
//	Calendar calendar = Calendar.getInstance();
//	System.out.println((calendar.get(Calendar.MONTH) + 1 ) + "/" + calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.YEAR));
//	System.out.println(calendar.get(Calendar.DAY_OF_WEEK));
//	System.out.println(calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH));
//
//	
//	
////    // months[i] = name of month i
////    String[] months = {
////            "",                               // leave empty so that we start with months[1] = "January"
////            "January", "February", "March",
////            "April", "May", "June",
////            "July", "August", "September",
////            "October", "November", "December"
////        };
//	
////    // days[i] = number of days in month i
////    int[] days = {
////        0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
////    };
//	
////    // check for leap year
////    if  ((((Y % 4 == 0) && (Y % 100 != 0)) ||  (Y % 400 == 0)) && M == 2)
////        days[M] = 29;
//
//	
//	}

	public void calendarguts() {
		
	}
	
	int CalcLeapYears(int year) {
	  /*
     USE:  Calculate number of leap years since 1582.
     IN:   year = given year after 1582 (start of the Gregorian calendar).
     OUT:  number of leap years since the given year, -1 if year < 1582
     NOTE: Count doesn't include the given year if it is a leap year.
           In the Gregorian calendar, used since 1582, every fourth year
           is a leap year, except for years that are a multiple of a
           hundred, but not a multiple of 400, which are no longer leap
           years. Years that are a multiple of 400 are still leap years:
           1700, 1800, 1990 were not leap years, but 2000 will be.
  */

		int leapYears; /* number of leap years to return */
		int hundreds; /* number of years multiple of a hundred */
		int fourHundreds; /* number of years multiple of four hundred */
  
		/* Start at 1582, when modern calendar starts. */
		if (year < 1582)
			return (-1);

		/* Calculate number of years in interval that are a multiple of 4. */
		leapYears = (year - 1581) / 4;

		/*
		 * Calculate number of years in interval that are a multiple of 100; subtract,
		 * since they are not leap years.
		 */
		hundreds = (year - 1501) / 100;
		leapYears -= hundreds;

		/*
		 * Calculate number of years in interval that are a multiple of 400; add back
		 * in, since they are still leap years.
		 */
		fourHundreds = (year - 1201) / 400;
		leapYears += fourHundreds;

		return (leapYears);
	}
}
