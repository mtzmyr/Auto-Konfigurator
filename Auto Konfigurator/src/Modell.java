import java.util.Set;
import java.util.HashSet;

public class Modell {

	private Float price;
	private String name;
	private PackageConfig[] packageConfig;
	private Set<Package> selectedConfigs;
	
	public Modell(Float price, String name, PackageConfig[] packageConfig) {
		super();
		this.price = price;
		this.name = name;
		this.packageConfig = packageConfig;
		resetToDefaults();
	}
	
	public boolean isSelected(Package pack) {
		Package[] selectedPack = getSelectedConfigs();
		for (Package pack2: selectedPack) {
			if (pack.getId() == pack2.getId()) {
				return true;
			}
		}
		return false;
	}
	
	public Float getTotalPrice() {
		Float price = this.price;
		for (Package object: selectedConfigs) {
			price += object.getPrice();
		}
		return price;
	}
	
	public void resetToDefaults() {
		selectedConfigs = new HashSet<Package>();
		for (PackageConfig object: packageConfig) {
			Package[] defaults = object.getDefaults();
			if (defaults != null && defaults.length != 0) {
				for (Package pack: defaults) {
					selectPackage(pack, object);
				}
			}
		} 
	}
	
	public void selectPackage(Package pack, PackageConfig conf) {
		boolean isSingle = false;
		for (PackageConfigType type: conf.getTypes()) {
			if (type == PackageConfigType.SINGLE) {
				isSingle = true;
			}
		}
		
		if (isSingle) {
			for (Package pack2: conf.getPackages()) {
				selectedConfigs.remove(pack2);
			}
		}
		
		Package[] required = pack.getRequiredPackages(); 
		if (required != null && required.length  != 0) {
			for (Package req: required) {
				selectedConfigs.add(req);
			}
		}
		selectedConfigs.add(pack);
	}
	
	public boolean deselectPackage(Package pack, PackageConfig conf) {
		boolean isRequired = false;
		for (PackageConfigType type: conf.getTypes()) {
			if (type == PackageConfigType.REQUIRED) {
				isRequired = true;
			}
		}
		
		if (!isRequired) {
			selectedConfigs.remove(pack);
			return true;
		}
		else {
			return false;
		}
	}

	public Float getPrice() {
		return price;
	}

	public String getName() {
		return name;
	}

	public PackageConfig[] getPackageConfig() {
		return packageConfig;
	}
	
	public Package[] getSelectedConfigs() {
		return selectedConfigs.toArray(new Package[0]);
	}
	
}
