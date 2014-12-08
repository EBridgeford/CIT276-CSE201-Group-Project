//Casey Curry
//Test object for BLDGObj Class
package application.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class BLDGObjTest {
	
	//working fine
	@Test
	public void testBLDGObj() {
		BLDGObj testObj = new BLDGObj(null);
		testObj = new BLDGObj("12");
		testObj = new BLDGObj("15151515145415151515154151");
		testObj = new BLDGObj("cat");
		testObj = new BLDGObj("Cat");
		testObj = new BLDGObj("ate");
		testObj = new BLDGObj("0");
		testObj = new BLDGObj("-12");
		testObj = new BLDGObj(".0250512");
	}
	//Working fine
	@Test
	public void testGetBld() {
		BLDGObj testObj = new BLDGObj("15151515145415151515154151");
		testObj.getBld();
		testObj = new BLDGObj("Cat");
		testObj.getBld();
		testObj = new BLDGObj("ate");
		testObj.getBld();
		testObj = new BLDGObj("0");
		testObj.getBld();
		testObj = new BLDGObj("-12");
		testObj.getBld();
		testObj = new BLDGObj(".025051223;/'[.'[");
		testObj.getBld();
		
	}
	
	//Working fine
	@Test
	public void testSetBld() {
		BLDGObj testObj = new BLDGObj(null);
		testObj.setBld("asdkjnawsdkj");
		testObj.setBld("-.5");
		testObj.setBld("0");
		testObj.setBld("54154151");
		testObj.setBld("dfsmlfDSFSDfmdfkslfSDFSD");
		testObj.setBld("RRRRRRRRRRR..;'[p-0");
		testObj.setBld(null);
	}

}
