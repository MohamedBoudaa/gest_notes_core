package com.bll;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImportManagerImpl implements ImportManager {

	
	public static final String DEST = "";
	
	public ImportManagerImpl(){
		
	}
	
	
	
	public static void importFromCsvFile(String path) {

		String regex = "^([A-Za-z0-9]{5}-){4}[A-Za-z0-9]{5}$";
		Pattern pattern = Pattern.compile(regex);

		try {
			File file1 = new File(path);

			File file2 = new File(DEST);

			FileWriter w = new FileWriter(DEST);

			Scanner r = new Scanner(file1);

			Set<String> keys = new HashSet<String>();

			while (r.hasNextLine()) {
				String key = r.nextLine();
				Matcher matcher = pattern.matcher(key);

				if (matcher.matches()) {
					keys.add(key);
				}
			}

			r = new Scanner(file2);

			while (r.hasNextLine()) {
				String key = r.nextLine();
				Matcher matcher = pattern.matcher(key);

				if (matcher.matches()) {
					keys.add(key);
				}
			}

			for (String k : keys) {
				System.out.println(k);
				w.write(k + '\n');
			}

			w.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
