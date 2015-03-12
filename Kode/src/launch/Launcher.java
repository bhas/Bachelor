package launch;

import texasholdem.TableType;
import gui.MainWindow;

public class Launcher {
	public static void main(String[] args) {
		// should read data from file
		
		new MainWindow(TableType.NO_LIMIT, 50, 1000);
	}
}
