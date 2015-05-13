package testing;

import java.io.IOException;

import data.analyse.DataReader;

public class DataReaderTest {
	
	
	public static void main(String[] args) throws IOException {
		DataReader dr = new DataReader();
		
		dr.runIt("kfish");
	}

}
