package dataReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReadFile {
	FileReader fr;
	BufferedReader br;

	public static void main(String[] args) {
		try{
			ReadFile rf = new ReadFile("JimR");
			System.out.println(rf.find("975934044"));
			System.out.println(rf.find("975942216"));
			System.out.println(rf.find("975949671"));
			rf.closeConnection();
		}
		catch(IOException i){
			
		}
	}
	

	public ReadFile(String player) throws IOException {
		openConnection("data/players/pdb." + player);
	}
	
	public String find(String game) throws IOException{
		String line;
		int i =0;
		while ((line = br.readLine()) != null) {
			i++;
			if (line.contains(game)) {
				System.out.println(i);
				return line;
			}
		}
		return null;

	}
	
	private void openConnection(String file) throws IOException {
		this.fr = new FileReader(file);
		this.br = new BufferedReader(fr);
	}
	
	public void closeConnection() throws IOException{
		br.close();
		fr.close();
	}
}

