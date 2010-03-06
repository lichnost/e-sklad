package org.stablylab.core.report;

import java.math.BigDecimal;

public class ReportFormatter {

	private static final String[] hundreds = {"", " сто", " двести", " триста",
		" четыреста", " пятьсот", " шестьсот", " семьсот", " восемьсот",
		" девятьсот"};
	
	private static final String[] tens = {"", "", " двадцать", " тридцать",
		" сорок", " пятьдесят", " шестьдесят", " семьдесят", " восемьдесят",
		" девяносто"};

	private static final String[] ones = {"", "", "", " три", " четыре", " пять", " шесть",
		" семь", " восемь", " девять", " десять", " одиннадцать",
		" двенадцать", " тринадцать", " четырнадцать", " пятнадцать",
		" шестнадцать", " семнадцать", " восемнадцать", " девятнадцать"};

	private static final String[] razryad = {"", " тысяч", " миллион", " миллиард",
		" триллион", " квадриллион", " квинтиллион"};

//  o  Функцию для перевода слова, связанного с числительным (или единицы
//  изменения) в соответствующий падеж.
//
//  Пример:
//          GeniCase('10', 'штука','штуки','штук')   -->   'штук'
//          GeniCase('1', 'штука','штуки','штук')    -->   'штука'
//          GeniCase('11', 'штука','штуки','штук')   -->   'штук'
//          GeniCase('21', 'штука','штуки','штук')   -->   'штука'
	private String geniCase(String s, String c1, String c2, String c3)
	{
		long det1;
		long det2;
		det2 = Long.parseLong(s.substring(s.length() - 2, s.length()));
		det1 = det2 % 10;
		if(det1 == 1 && det2 != 11)
			return c1;
		else if(det1 > 1 && det1 < 5 && !(det2 > 11 && det2 < 15))
			return c2;
		else return c3;
	}
	
	private String stringNumberToWord(String s) throws Exception
	{
		int i, count;
		String result;
		if(s.length()<=0 || "".equals(s))
			return "ноль";
		count = (s.length() + 2) / 3;
		if(count>7){
			throw new Exception("Number is too large."); 
		}
		result = "";
		s = "00" + s;
		for(i=1;i<=count;i++){
			result = shortWord(Integer.parseInt(s.substring(s.length()-3*i, s.length()-3*i+3)), i-1) + result;
		}

		return result.trim();
	}
	
	private String shortWord(int num, int razr)
	{
		int t; //десятки
		int o; //единицы
		String result = hundreds[(num / 100)];
		if(num == 0)
			return result;
		t = ((num % 100) / 10);
		o = (num % 10);
		if(t != 1){
			result = result + tens[t];
			switch(o){
				case 1:
					if(razr == 1)
						result = result + " одна";
					else result = result + " один";
					break;
				case 2:
					if(razr == 1)
						result = result + " две";
					else result = result + " два";
					break;
				case 3: case 4: case 5: case 6: case 7: case 8: case 9:
					result = result +ones[o];
					break;
			}
			result = result + razryad[razr];
			switch(o){
				case 1:
					if(razr == 1)
						result = result + "а";
					break;
				case 2: case 3: case 4:
					if(razr == 1)
						result = result + "и";
					else if(razr > 1)
						result = result + "а";
					else if(razr > 1)
						result = result + "ов";
			}
		} else {
			result = result + ones[(num % 100)];
			result = result + razryad[razr];
			if(razr > 1)
				result = result + "ов";
		}
		return result;
	}
	
	public String intToWords(Integer number) {
		try {
			return stringNumberToWord(number.toString());
		} catch (Exception e) {
			return "";
		}
	}
	
	public String numberToCurrency(double number, String code) {
		Integer iPart = (int) Math.floor(number);
		Integer fPart = (int) Math.floor((number - iPart) * 100);
		try {
			if("RUB".equalsIgnoreCase(code)){
				return intToWords(iPart) + " " + geniCase(iPart.toString(),"рубль","рубля","рублей")
				+ " " + fPart.toString() + " " + "коп.";
			}
			return "";
		} catch (Exception e) {
			return "";
		}
	}
	
	public String numberToCurrency(BigDecimal number, String code) {
		return numberToCurrency(number.doubleValue(), code);
	}
}
