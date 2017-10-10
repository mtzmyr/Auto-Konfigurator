import static org.junit.Assert.*;

import org.junit.Test;


public class JTest {
	
	@Test
    public void test() {
		
        PackageBuilder testBuilder = new PackageBuilder("asd", (float)10000.00, "Mercedes Benz C63 AMG","..." ,null);
        Package package1 = testBuilder.build( new PackageBuilder[] { testBuilder } );
        
        assertEquals("asd", package1.getId());
        assertEquals(10000.0, (double) package1.getPrice(), 0.001);
        assertEquals("Mercedes Benz C63 AMG", package1.getName());
        assertEquals("...", package1.getDescription());
        assertArrayEquals(null, package1.getRequiredPackages());
    }

}
