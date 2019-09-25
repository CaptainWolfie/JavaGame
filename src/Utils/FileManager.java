package Utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {
	
	public String readFile(String path) {
		StringBuilder builder = new StringBuilder();
		
		try{
			BufferedReader br = new BufferedReader(new java.io.FileReader(path));
			String line;
			while((line = br.readLine()) != null)
				builder.append(line + "\n");
			
			br.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return builder.toString();
	}
	
	public boolean fileExists(String path) {
		boolean exists = true;
		try{
			BufferedReader br = new BufferedReader(new java.io.FileReader(path));
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}catch(FileNotFoundException e){
			exists = false;
		}
		return exists;
	}
	
	public boolean createFile(String path) {
		File file = new File(path);
		
		boolean status = false;
		//Create the file
		try {
			file.getParentFile().mkdirs();
			if (file.createNewFile()) {
			    System.out.println(path + " didn't found. Creating a new one..");

			    FileWriter writer;

				writer = new FileWriter(file);
				writer.write("");
				writer.close();
				status = true;
			} else
				status = false;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public void writeFile(String path, String data) {
		if (!fileExists(path))
			createFile(path);
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(path));
			writer.write(data);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
