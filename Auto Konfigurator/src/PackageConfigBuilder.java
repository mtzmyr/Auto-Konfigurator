public class PackageConfigBuilder {

	private String name;
	private String description;
	private String[] typeIDs;
	private String[] packageIDs;
	private String[] defaultIDs;
	
	public PackageConfigBuilder(String name, String description, String[] typesIDs, String[] packageIDs, String[] defaultIDs) {
		super();
		this.name = name;
		this.description = description;
		this.typeIDs = typesIDs;
		this.packageIDs = packageIDs;
		this.defaultIDs = defaultIDs;
	}
	
	public PackageConfig build(Package[] packages) {
		
		Package[] packagesN = null;
		if (packageIDs != null && packageIDs.length != 0) {
			packagesN = new Package[packageIDs.length];
			
			for (int i = 0; i < packageIDs.length; i++) {
			    String id = packageIDs[i];
				Package pack = null;
	
			    for (int ii = 0; ii < packages.length; ii++) {
			    	 	if (packages[ii].getId().equals(id)) {
			    	 		pack = packages[ii];
			    	 		break;
			    	 	}
			    }
			    packagesN[i] = pack;
			}
		}
		
		Package[] defaults = null;
		if (defaultIDs != null && defaultIDs.length != 0) {
			defaults = new Package[defaultIDs.length];
			
			for (int i = 0; i < defaultIDs.length; i++) {
			    String id = defaultIDs[i];
				Package pack = null;
	
			    for (int ii = 0; ii < packages.length; ii++) {
			    		if (packages[ii].getId().equals(id)) {
			    	 		pack = packages[ii];
			    	 		break;
			    	 	}
			    }
			    defaults[i] = pack;
			}
		}
		
		PackageConfigType[] types = null;
		if (typeIDs != null && typeIDs.length != 0) {
			types = new PackageConfigType[typeIDs.length];
			for (int i = 0; i < typeIDs.length; i++) {
				types[i] = PackageConfigType.valueOf(typeIDs[i]);
			}
		}
			
		return new PackageConfig(name, description, types, packagesN, defaults);
	}
	
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public String[] getTypeIDs() {
		return typeIDs;
	}
	public String[] getPackageIDs() {
		return packageIDs;
	}
	public String[] getDefaultIDs() {
		return defaultIDs;
	}
	
	
}
