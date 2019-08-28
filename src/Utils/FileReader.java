package Utils;

import java.io.BufferedReader;
import java.io.IOException;

public class FileReader {

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
}
