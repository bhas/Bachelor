package testing;

import java.io.IOException;

import data.refac.DataRefactorer;

public class DataTest {
	public static void main(String[] args) {
		try {
			DataRefactorer.refactor();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
