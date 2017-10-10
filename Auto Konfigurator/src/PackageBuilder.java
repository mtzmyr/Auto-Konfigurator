public class PackageBuilder {

	private String id;
	private Float price;
	private String name;
	private String description;
	private String[] requiredPackageIDs;
	
	public PackageBuilder(String id, Float price, String name, String description, String[] requiredPackageIDs) {
		super();
		this.id = id;
		this.price = price;
		this.name = name;
		this.description = description;
		this.requiredPackageIDs = requiredPackageIDs;
	}
	
	public Package build(PackageBuilder[] builders) {
		
		if (requiredPackageIDs != null && requiredPackageIDs.length != 0) {
			Package[] requiredPackages = new Package[requiredPackageIDs.length];
			
			for (int i = 0; i < requiredPackageIDs.length; i++) {
			    String id = requiredPackageIDs[i];
				Package pack = null;
	
			    for (int ii = 0; ii < builders.length; ii++) {
			    		PackageBuilder temp = builders[ii];
			    	 	if (!temp.id.equals(this.id) && temp.id.equals(id)) {
			    	 		pack = new Package(temp.id, temp.price, temp.name, temp.description, null);
			    	 		break;
			    	 	}
			    }
			    requiredPackages[i] = pack;
			}
			return new Package(id, price, name, description, requiredPackages);		
		}
		else {
			return new Package(id, price, name, description, null);
		}
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

	public String[] getRequiredPackageIDs() {
		return requiredPackageIDs;
	}
	
	
	
}
