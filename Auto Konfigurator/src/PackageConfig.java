public class PackageConfig {

	private String name;
	private String description;
	private PackageConfigType[] types;
	private Package[] packages;
	private Package[] defaults;
	
	public PackageConfig(String name, String description, PackageConfigType[] types, Package[] packages, Package[] defaults) {
		super();
		this.name = name;
		this.description = description;
		this.types = types;
		this.packages = packages;
		this.defaults = defaults;
	}
	
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public PackageConfigType[] getTypes() {
		return types;
	}
	public Package[] getPackages() {
		return packages;
	}
	public Package[] getDefaults() {
		return defaults;
	}
	
}
