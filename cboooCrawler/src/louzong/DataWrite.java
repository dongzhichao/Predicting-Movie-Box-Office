package louzong;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class DataWrite {
	
	public static boolean writeToFile(List<Movie> movies) {
		String filePath = "D:/IMG/cbbomovies.txt";
		File file = new File(filePath);
		try {
			if(!file.exists()){
				file.createNewFile();				
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		FileWriter fWriter = null;
		BufferedWriter bWriter = null;
		
		try {
			fWriter = new FileWriter(file,true);
			bWriter = new BufferedWriter(fWriter);
			for (int i = 0; i < movies.size(); i++) {
				bWriter.write(movies.get(i).toString());
				bWriter.newLine();
			}
		} catch (IOException e) {
			System.out.println("failed to create the writers");
			return false;
		}finally {
			try {
				bWriter.close();
				fWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("failed to close the writers");
			}
		}
		return true;
	}
}

