package de.gandalf1783.tilegame.utils;

import java.io.*;
import java.util.stream.Collectors;

public class Utils {
	
	public static String loadFileAsString(String path){
		InputStream stream = Utils.class.getResourceAsStream("/worlds/world1.txt");
		
		return new BufferedReader(new InputStreamReader(stream))
				.lines().collect(Collectors.joining("\n"));
	}
	
	public static int parseInt(String number){
		try{
			return Integer.parseInt(number);
		}catch(NumberFormatException e){
			return 0;
		}
	}

}
