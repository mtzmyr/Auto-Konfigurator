public class Package {
	
	private String id;
	private Float price;
	private String name;
	private String description;
	private Package[] requiredPackages;
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Package) {
			Package pack = (Package) obj;
			return pack.getId().equals(id);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}
	
	public Package(String id, Float price, String name, String description, Package[] requiredPackages) {
		super();
		this.id = id;
		this.price = price;
		this.name = name;
		this.description = description;
		this.requiredPackages = requiredPackages;
	}

	public String getId() {
		return id;
	}
	
	public Float getPrice() {
		return price;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Package[] getRequiredPackages() {
		return requiredPackages;
	}
	
}
