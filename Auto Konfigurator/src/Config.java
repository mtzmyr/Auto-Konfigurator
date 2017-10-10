import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Config {
	
	private Modell[] modells;
	private Package[] packagePool; 

	public Config() {
		loadConfigFromFile();
	}
	
	private void loadConfigFromFile() {
		
		JSONObject jsonObject = getJsonObject();
		parseJsonObject(jsonObject);
	 	
	}
	
	// --- JSON ---
	
	private JSONObject getJsonObject() {		
		JSONParser parser = new JSONParser();
        try {
        	 	File file = new File("resources/config.json");

        	 	Object obj = parser.parse(new FileReader(file));
 
        	 	return (JSONObject) obj;
        } catch (Exception e) {
        		e.printStackTrace();
        		System.exit(1);
        		return null;
        }
	}
	
	private void parseJsonObject(JSONObject jsonObject) {
	 	JSONObject packageList = (JSONObject) jsonObject.get("packages");
	 	packagePool = getPackages(packageList);
		
	 	JSONArray modellList = (JSONArray) jsonObject.get("modells");
	 	modells = getModells(modellList);
	}
	
	// --- PACKAGES ---
	
	private Package[] getPackages(JSONObject jsonObject) {

		ArrayList<PackageBuilder> builders = new ArrayList<PackageBuilder>();
		
		@SuppressWarnings("unchecked")
		Iterator<String> iterator = jsonObject.keySet().iterator();
	    while(iterator.hasNext()) {
	    		String id = iterator.next();
	    		JSONObject value = (JSONObject) jsonObject.get(id);
	    		builders.add(getPackage(value, id));
	    }
	    
	    PackageBuilder[] buildersArray = builders.toArray(new PackageBuilder[0]);
		return buildPackages(buildersArray);
	}
	
	private PackageBuilder getPackage(JSONObject jsonObject, String id) {
		Float price = ((Long) jsonObject.get("price")).floatValue();
		String name = (String) jsonObject.get("name");
		String description = (String) jsonObject.get("description");
		String[] requiredPackages = stringFromJson((JSONArray) jsonObject.get("required_packages"));
		return new PackageBuilder(id, price, name, description, requiredPackages);
	}
	
	private String[] stringFromJson(JSONArray jsonArray) {
		
		if (jsonArray != null) {
			String[] res = new String[jsonArray.size()];
			for(int i = 0; i < jsonArray.size(); i++) {
				res[i] = (String) jsonArray.get(i);
			}
			return res;
		}
		else {
			return new String[0]; 
		}
	}
	
	private Package[] buildPackages(PackageBuilder[] builders) {
		
		Package[] packages = new Package[builders.length];
		for (int i = 0; i < builders.length; i++) {
			packages[i] = builders[i].build(builders);
		}
		return packages;
	}

	// --- MODELLS ---
	
	private Modell[] getModells(JSONArray jsonArray) {
		
		ArrayList<Modell> modells = new ArrayList<Modell>();
		@SuppressWarnings("unchecked")
		Iterator<JSONObject> iterator = jsonArray.iterator();
        while (iterator.hasNext()) {
            Modell modell = getModell(iterator.next());
            modells.add(modell);
        }
  
        Modell[] modellArray = modells.toArray(new Modell[0]);
        return modellArray;
        
	}
	
	private Modell getModell(JSONObject jsonObject) {
		Float price = ((Long) jsonObject.get("price")).floatValue();
		String name = (String) jsonObject.get("name");
		JSONArray packagesConfigJsonObject = (JSONArray) jsonObject.get("package_configs");
		return new Modell(price, name, getPackageConfigs(packagesConfigJsonObject));
	}
	
	//PACKAGE CONFIG 
	
	private PackageConfig[] getPackageConfigs(JSONArray jsonArray) {
	
		ArrayList<PackageConfig> packageConfigs = new ArrayList<PackageConfig>();
		@SuppressWarnings("unchecked")
		Iterator<JSONObject> iterator = jsonArray.iterator();
        while (iterator.hasNext()) {
            PackageConfig packageConfig = getPackageConfig(iterator.next());
            packageConfigs.add(packageConfig);
        }
        PackageConfig[] packageConfigArray = packageConfigs.toArray(new PackageConfig[0]);
        return (PackageConfig[]) packageConfigArray;
		
	}
	
	private PackageConfig getPackageConfig(JSONObject jsonObject) {
		String name = (String) jsonObject.get("name");
		String description = (String) jsonObject.get("description");			
		String[] packageIDs = stringFromJson((JSONArray) jsonObject.get("packages"));
		String[] defaultIDs = stringFromJson((JSONArray) jsonObject.get("defaults"));
		String[] typeIDs = stringFromJson((JSONArray) jsonObject.get("types"));
		PackageConfigBuilder builder = new PackageConfigBuilder(name, description, typeIDs, packageIDs, defaultIDs);
		return builder.build(packagePool);
	}
		
	// --- GETTER & SETTER ---
	
	public Modell[] getModells() {
		return modells;
	}
}
