import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class CommandLine {

	private Config config;
	private Scanner scanner = new Scanner(System.in);
	
	private Modell selectedModell = null;
	private PackageConfig selectedConfig = null;
	
	public CommandLine(Config config) {
		this.config = config;
	}
	
	public void start() {
		cycle();
	}
	
	private void clear() {
		try {
			Runtime.getRuntime().exec("cls");
		} catch (IOException e) {
			System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		}
	}
	
	private void cycle() {
		clear();
		
		if (selectedModell == null) {
			System.out.println("Choose a Modell:");
			Modell[] modells = config.getModells();
			String[] options = new String[modells.length];
			for (int i=0; i < modells.length; i++) {
				options[i] = i + "";
				System.out.println(i + ": " + modells[i].getName());
			}
			int selected = waitForOptionInput(options);
			selectedModell = modells[selected];
			selectedModell.resetToDefaults();
		}
		else if (selectedConfig == null){
			PackageConfig[] configs = selectedModell.getPackageConfig();
			String[] options = new String[configs.length + 2];
			System.out.println("Modell: " + selectedModell.getName() );
			System.out.println("Price: " + selectedModell.getTotalPrice() );
			options[0] = "E";
			System.out.println("E: Exit");
			options[1] = "B";
			System.out.println("B: Buy");
			for (int i=0; i < configs.length; i++) {
				options[i + 2] = i + "";
				System.out.println(i + ": " + configs[i].getName());
			}
			int selected = waitForOptionInput(options);
			if (selected == 0) {
				selectedModell = null;
			}
			else if (selected == 1) {
				printResult();
			}
			else {
				selectedConfig = configs[selected - 2];
			}
		}
		else {
			Package[] packages = selectedConfig.getPackages();
			String[] options = new String[packages.length + 1];
			System.out.println("Modell: " + selectedModell.getName() );
			System.out.println("Price: " + selectedModell.getTotalPrice() );
			System.out.println("Config: " + selectedConfig.getName() );
			options[0] = "B";
			System.out.println("B: Back");
			for (int i=0; i < packages.length; i++) {
				options[i + 1] = i + "";
				Package pack = packages[i];
				if (selectedModell.isSelected(pack)) {
					System.out.println(i + ": " + pack.getName() + " (" + pack.getPrice() + ")" + " [X]");
				}
				else {
					System.out.println(i + ": " + pack.getName() + "(" + pack.getPrice() + ")" + " [ ]");
				}
			}
			int selected = waitForOptionInput(options);
			if (selected == 0) {
				selectedConfig = null;
			}
			else {
				Package pack = packages[selected - 1];
				if (selectedModell.isSelected(pack)) {
					selectedModell.deselectPackage(pack, selectedConfig);
				}
				else {
					selectedModell.selectPackage(pack, selectedConfig);
				}
			}
		}
		cycle();
	}
	
	private void printResult() {
		clear();
		
		System.out.println("Modell: " + selectedModell.getName() + " (" + selectedModell.getPrice() + ")");
		System.out.println("Packages:");
		Package[] packs = selectedModell.getSelectedConfigs();
		for (Package pack: packs) {
			System.out.println("    " + pack.getId() + " (" + pack.getPrice() + ")");
		}
		System.out.println("Total Price: " + selectedModell.getTotalPrice());
		
		System.exit(1);
	}
	
	
	private int waitForOptionInput(String[] options) {
		String input = waitForInput("Enter one: " + Arrays.toString(options));
		for (int i=0; i < options.length; i++) {
			   if (input.equalsIgnoreCase(options[i])) {
				   return i;
			   }
		}
		return waitForOptionInput(options);
	}
	
	private String waitForInput(String text) {
		System.out.print(text + ": ");
		return scanner.next();
	}
	
}
