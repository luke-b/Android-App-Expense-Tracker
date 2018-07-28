package com.steepmax.expenses;

import java.util.Locale;

public class ExpensesCurrency {

	private final static String[] symbols = {
											"USD",
											"EUR",
											"YEN",
											"GBP",
											"CAD",
											"SGD",
											"HKD",
											"CHF",
											"TWD",
											"KRW",
											"INR",
											"CNY",
											"MYR",
											"BND",
											"BRL",
											"MXN",
											"NOK",
											"CZK",
											"DKK",
											"PLN",
											"AUD",
											"NZD",
											"HRK",
											"TRY",
											"UAH",
											"!F1",
											"!F2",
											"!F3",
											"!F4",
											"!F5",
											"ZAR"
											};
	
	private final static String[] symbols_cs = {
		"USD",
		"EUR",
		"YEN",
		"GBP",
		"CAD",
		"SGD",
		"HKD",
		"CHF",
		"TWD",
		"KRW",
		"INR",
		"CNY",
		"MYR",
		"BND",
		"BRL",
		"MXN",
		"NOK",
		"Kè",
		"DKK",
		"PLN",
		"AUD",
		"NZD",
		"HRK",
		"TRY",
		"UAH",
		"!F1",
		"!F2",
		"!F3",
		"!F4",
		"!F5",
		"ZAR"
		};
	
	
	private final static String[] names = {
										  "US Dollar",
										  "Euro",
										  "Japanese Yen",
										  "British Pound",
										  "Canadian Dollar",
										  "Singapore Dollar",
										  "Honk Kong Dollar",
										  "Swiss Franc",
										  "Taiwan New Dollar",
										  "South Korean Won",
										  "Indian Rupee",
										  "Chinese Yuan",
										  "Malay Ringgit",
										  "Brunei Dollar",
										  "Brazilian Real",
										  "Mexican Peso",
										  "Norsk krona",
										  "Czech Koruna",
								          "Danish Krone",	
								          "Polish Zloty",
								          "Aussie Dollar",
								          "Kiwi Dollar",
								          "Croatian Kuna",
								          "Turkish Lira",
								          "Ukrainian Hryvnia",
								          "Foreign curr. #1",
								          "Foreign curr. #2",
								          "Foreign curr. #3",
								          "Foreign curr. #4",
								          "Foreign curr. #5",
								          "South African rand"
										  };
	
	
	private final static String[] names_cs = {
		  "US Dollar",
		  "Euro",
		  "Japanese Yen",
		  "British Pound",
		  "Canadian Dollar",
		  "Singapore Dollar",
		  "Honk Kong Dollar",
		  "Swiss Franc",
		  "Taiwan New Dollar",
		  "South Korean Won",
		  "Indian Rupee",
		  "Chinese Yuan",
		  "Malay Ringgit",
		  "Brunei Dollar",
		  "Brazilian Real",
		  "Mexican Peso",
		  "Norsk krona",
		  "Èeská Koruna",
        "Danish Krone",	
        "Polish Zloty",
        "Aussie Dollar",
        "Kiwi Dollar",
        "Croatian Kuna",
        "Turkish Lira",
        "Ukrainian Hryvnia",
        "Foreign curr. #1",
        "Foreign curr. #2",
        "Foreign curr. #3",
        "Foreign curr. #4",
        "Foreign curr. #5",
        "South African rand"
		  };
	
	
	
	public static int getSymbolIndex(String iso3Code) {
		
	    if (iso3Code.equalsIgnoreCase("CZE"))
	    	return 17;
	    
	    if (iso3Code.equalsIgnoreCase("AUS"))
	    	return 20;
	    
	    if (iso3Code.equalsIgnoreCase("GBR"))
	    	return 3;
	    
	    if (iso3Code.equalsIgnoreCase("CAN"))
	    	return 4;
	    
	    if (iso3Code.equalsIgnoreCase("HKG"))
	    	return 6;
	    
	    if (iso3Code.equalsIgnoreCase("IRL"))
	    	return 1;
	    
	    if (iso3Code.equalsIgnoreCase("NZL"))
	    	return 21;
	    
	    if (iso3Code.equalsIgnoreCase("NOR"))
	    	return 16;
	    
	    if (iso3Code.equalsIgnoreCase("SGP"))
	    	return 5;
	    
	    if (iso3Code.equalsIgnoreCase("ZAF"))
	    	return 30;
	    
	    if (iso3Code.equalsIgnoreCase("TWN"))
	    	return 8;
	    
	    if (iso3Code.equalsIgnoreCase("USA"))
	    	return 0;
		
		return 0;
	}
	
	
	
	public static String[] getCurrencySymbols() {
		
		if (Locale.getDefault().getDisplayLanguage().equalsIgnoreCase("èeština")) {
			return symbols_cs;
		} 
		
		return symbols;
	}

	
	public static String[] getCurrencyNames() {
		
		if (Locale.getDefault().getDisplayLanguage().equalsIgnoreCase("èeština")) {
			return names_cs;
		} 
		
		return names;
		
	}
	
}
