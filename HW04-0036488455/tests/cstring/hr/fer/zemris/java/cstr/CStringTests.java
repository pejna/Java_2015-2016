package hr.fer.zemris.java.cstr;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import hr.fer.zemris.java.cstr.CString;


/**
 * Tests the work of {@link CString} class.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class CStringTests {

	private char[] testArrayNormal = { '1', '2', '3', 'A', 'B', 'C' };
	private char[] testArrayReduced = { '3', 'A', 'B' };
	private char[] testArrayEmpty = {  };

	// FROM STRING FACTORY TESTS
	@Test(expected=IllegalArgumentException.class)
	public void testFromStringFactoryWithNullInput(){
		CString.fromString(null);
	}
	
	@Test
	public void testFromStringFactoryValidInput1(){
		assertEquals(CString.fromString("").toString(), "");
	}
	
	@Test
	public void testFromStringFactoryValidInput2(){
		assertEquals(CString.fromString("123").toString(), "123");
	}
	
	// ARRAY CONSTRUCTOR TESTS
	@Test(expected=IllegalArgumentException.class)
	public void testArrayConstructorWithNullInput(){
		new CString ( null, 0, 0 );
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testArrayConstructorOffsetPlusLenghtGreaterThanContentsLength(){
		new CString ( testArrayNormal, 0, 10 );
	}
	
	@Test
	public void testArrayConstructorValidInputs1(){
		CString testCString = new CString (testArrayNormal, 0, 6);
		assertEquals ( "CString construction incorrent!", CString.fromString("123ABC"), testCString );
	}
	
	@Test
	public void testArrayConstructorValidInputs2(){
		CString testCString = new CString (testArrayNormal, 0, 2);
		assertEquals ( "CString construction incorrent!", CString.fromString("12"), testCString );
	}
	
	@Test
	public void testArrayConstructorValidInputs3(){
		CString testCString = new CString (testArrayNormal, 4, 2);
		assertEquals ( "CString construction incorrent!", CString.fromString("BC"), testCString );
	}
	
	@Test
	public void testArrayConstructorValidInputs4(){
		CString testCString = new CString (testArrayNormal, 2, 2);
		assertEquals ( "CString construction incorrent!", CString.fromString("3A"), testCString );
	}
	
	@Test
	public void testArrayConstructorValidInputs5(){
		CString testCString = new CString (testArrayNormal, 2, 0);
		assertEquals ( "CString construction incorrent!", CString.fromString(""), testCString );
	}
	
	// CSTRING CONSTRUCTOR TESTS
	@Test(expected=IllegalArgumentException.class)
	public void testCStringConstructorWithNullInput(){
		new CString ( (CString)null );
	}
	
	@Test
	public void testCStringConstructorValidInput1(){
		CString testCString = new CString ( new CString ( testArrayNormal, 2, 4 ) );
		assertEquals( CString.fromString("3ABC"), testCString );
	}

	@Test
	public void testCStringConstructorValidInput2(){
		CString testCString = new CString ( new CString ( testArrayNormal, 2, 0 ) );
		assertEquals( CString.fromString(""), testCString );
	}
	
	// CSTRING LENGTH METHOD TESTS
	@Test
	public void testLength1(){
		assertEquals(3, CString.fromString("123").length());
	}

	@Test
	public void testLength2(){
		assertEquals(0, CString.fromString("").length());
	}
	
	// CSTRING TOCHARARRAY METHOD TESTS
	@Test
	public void testToCharArray1(){
		char[] fromCStringArray = CString.fromString("123ABC").toCharArray();
		for ( int index = 0, arrayLength = fromCStringArray.length; index < arrayLength; index++ ) {
			assertEquals( testArrayNormal[index], fromCStringArray[index] );
		}
	}
	
	@Test
	public void testToCharArray2(){
		char[] fromCStringArray = CString.fromString("").toCharArray();
		assertEquals(testArrayEmpty.length, fromCStringArray.length);
		for ( int index = 0, arrayLength = fromCStringArray.length; index < arrayLength; index++ ) {
			assertEquals( testArrayEmpty[index], fromCStringArray[index] );
		}
	}

	@Test
	public void testToCharArray3(){
		char[] fromCStringArray = CString.fromString("3AB").toCharArray();
		assertEquals(testArrayReduced.length, fromCStringArray.length);
		for ( int index = 0, arrayLength = fromCStringArray.length; index < arrayLength; index++ ) {
			assertEquals( testArrayReduced[index], fromCStringArray[index] );
		}
	}
	
	// CSTRING TOSTRING METHOD TESTS
	@Test
	public void testToString1(){
		assertEquals("123", CString.fromString("123").toString());
	}
	
	@Test
	public void testToString2(){
		assertEquals("", CString.fromString("").toString());
	}
	
	// CSTRING INDEXOF METHOD TESTS
	@Test
	public void testIndexOf1(){
		assertEquals(2, CString.fromString("123").indexOf('3'));
	}
	
	@Test
	public void testIndexOf2(){
		assertEquals(-1, CString.fromString("123").indexOf('A'));
	}
	
	// CSTRING STARTSWITH METHOD TESTS
	@Test
	public void testStartsWith1(){
		assertTrue(CString.fromString("ABC").startsWith(CString.fromString("AB")));
	}
	
	@Test
	public void testStartsWith2(){
		assertFalse(CString.fromString("ABC").startsWith(CString.fromString("BA")));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testStartsWith3(){
		CString.fromString("ABC").startsWith(null);
	}
	
	// CSTRING ENDSWITH METHOD TESTS
	@Test
	public void testEndsWith1(){
		assertTrue(CString.fromString("ABC").endsWith(CString.fromString("BC")));
	}
	
	@Test
	public void testEndsWith2(){
		assertFalse(CString.fromString("ABC").endsWith(CString.fromString("CB")));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testEndsWith3(){
		CString.fromString("ABC").endsWith(null);
	}
	
	// CSTRING CONTAINS METHOD TESTS
	@Test
	public void testContains1(){
		assertTrue(CString.fromString("ABC").contains(CString.fromString("BC")));
	}

	@Test
	public void testContains2(){
		assertFalse(CString.fromString("ABC").contains(CString.fromString("CB")));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testContains3(){
		//will throw
		CString.fromString("ABC").contains(CString.fromString(null));
	}
	
	// CSTRING SUBSTRING METHOD TESTS
	@Test
	public void testSubstring1(){
		assertEquals( CString.fromString("23A"), new CString( testArrayNormal ).substring( 1, 4 ) );
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSubstring2(){
		new CString( testArrayNormal ).substring( -1, 4 );
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSubstring3(){
		new CString( testArrayNormal ).substring( 5, 4 );
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSubstring4(){
		new CString( testArrayNormal ).substring( -1, 8 );
	}
	
	// CSTRING LEFT METHOD TESTS
	@Test
	public void testLeft1(){
		assertEquals(CString.fromString("123A"), new CString( testArrayNormal ).left( 4 ) );
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testLeft2(){
		new CString( testArrayNormal ).left( 7 );
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testLeft4(){
		new CString( testArrayNormal ).left( -1 );
	}
	
	// CSTRING RIGHT METHOD TESTS
	@Test
	public void testRight1(){
		assertEquals(CString.fromString("3ABC"), new CString( testArrayNormal ).right( 4 ) );
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testRight2(){
		new CString( testArrayNormal ).right( 7 );
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testRight3(){
		new CString( testArrayNormal ).right( -1 );
	}
	
	// CSTRING ADD METHOD TESTS
	@Test
	public void testAdd1(){
		assertEquals( CString.fromString( "3AB123ABC" ), new CString( testArrayReduced ).add(new CString( testArrayNormal ) ) );
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAdd2(){
		new CString( testArrayReduced ).add( null );
	}
		
	// CSTRING REPLACEALL(CHAR) METHOD TESTS
	@Test
	public void testReplaceAllChar1(){
		assertEquals( CString.fromString( "153ABC" ), new CString( testArrayNormal ).replaceAll( '2', '5' ) );
	}
	
	// CSTRING REPLACEALL(CSTRING) METHOD TESTS
	@Test
	public void testReplaceAllCString1(){
		assertEquals( CString.fromString("1ABC"), new CString( testArrayNormal ).replaceAll( CString.fromString( "23" ), CString.fromString( "" ) ) );
	}
	
	@Test
	public void testReplaceWithLongerMiddle(){
		assertEquals( CString.fromString("1245ABC"), new CString( testArrayNormal ).replaceAll( CString.fromString( "3" ), CString.fromString( "45" ) ) );
	}
	
	@Test
	public void testReplaceWithShorterStart(){
		assertEquals( CString.fromString("53ABC"), new CString( testArrayNormal ).replaceAll( CString.fromString( "12" ), CString.fromString( "5" ) ) );
	}
	
	@Test
	public void testReplaceWithLongerStart(){
		assertEquals( CString.fromString("4523ABC"), new CString( testArrayNormal ).replaceAll( CString.fromString( "1" ), CString.fromString( "45" ) ) );
	}
	
	@Test
	public void testReplaceAllCString5(){
		assertEquals( CString.fromString("123AD"), new CString( testArrayNormal ).replaceAll( CString.fromString( "BC" ), CString.fromString( "D" ) ) );
	}
	
	@Test
	public void testReplaceAllCString6(){
		assertEquals( CString.fromString("123ABDE"), new CString( testArrayNormal ).replaceAll( CString.fromString( "C" ), CString.fromString( "DE" ) ) );
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testReplaceAllCString7(){
		new CString( testArrayNormal ).replaceAll( CString.fromString( null ), CString.fromString( "D" ) );
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testReplaceAllCString8(){
		new CString( testArrayNormal ).replaceAll( CString.fromString( "C" ), CString.fromString( null ) );
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testReplaceAllCString9(){
		new CString( testArrayNormal ).replaceAll( CString.fromString( null ), CString.fromString( null ) );
	}
	
	@Test
	public void testReplaceAllCString10(){
		assertEquals( CString.fromString("D1D2D3DADBDCD"), new CString( testArrayNormal ).replaceAll( CString.fromString( "" ), CString.fromString( "D" ) ) );
	}
		
	@Test
	public void testRegularCreation() {
		CString string = new CString(String.valueOf("Štefica").toCharArray(), 1, 4);
		assertEquals("Invalid string.", "tefi", string.toString());
		assertEquals("Invalid length.", 4, string.length());
		assertEquals("Invalid character.", 'e', string.charAt(1));

		CString string2 = new CString(String.valueOf("Štefica").toCharArray());
		assertEquals("Expected 'Štefica'", "Štefica", string2.toString());

		CString string3 = new CString(string2);
		assertEquals("Expected 'Štefica'", "Štefica", string3.toString());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullInputOnFirstConstructor() {
		// must throw!
		new CString(null, 2, 5);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidMargins() {
		// must throw!
		new CString(String.valueOf("Štefica").toCharArray(), 2, 6);
	}

	@Test
	public void testValidMargins1() {
		CString string = new CString(String.valueOf("Štefica").toCharArray(), 1, 6);
		assertEquals("Expected 'tefica'.", "tefica", string.toString());
		assertEquals("Expected 't'.", 't', string.charAt(0));
		assertEquals("Expected 'f'.", 'f', string.charAt(2));
		assertEquals("Expected 'i'.", 'i', string.charAt(3));
		assertEquals("Expected 'a'.", 'a', string.charAt(5));
	}

	@Test
	public void testValidMargins2() {
		CString string = new CString(String.valueOf("Štefica").toCharArray(), 0, 3);
		assertEquals("Expected 'Šte'.", "Šte", string.toString());
		assertEquals("Expected 'Š'.", 'Š', string.charAt(0));
		assertEquals("Expected 'e'.", 'e', string.charAt(2));
		assertEquals("Expected 'e'.", 'e', string.charAt(string.length() - 1));
	}

	@Test
	public void testValidMargins3() {
		CString string = new CString(String.valueOf("Štefica").toCharArray(), 0, 7);
		assertEquals("Expected 'Šte'.", "Štefica", string.toString());
		assertEquals("Expected 'Š'.", 'Š', string.charAt(0));
		assertEquals("Expected 'e'.", 'e', string.charAt(2));
		assertEquals("Expected 'a'.", 'a', string.charAt(string.length() - 1));
	}

	@Test
	public void testLengthsStartsEnds() {
		CString string = new CString(String.valueOf("Štefica Štefica Štefica").toCharArray(), 0, 23);
		assertEquals("Expected 3x 'Štefica' with whitespaces.", "Štefica Štefica Štefica", string.toString());
		assertEquals("Expected 23.", 23, string.length());
		assertEquals("Expected 23.", 23, string.toCharArray().length);
		assertEquals("Expected ' '.", ' ', string.charAt(7));

		assertEquals("Expected 'true'", true,
				string.endsWith(new CString(String.valueOf("Štefica").toCharArray(), 0, 7)));
		assertEquals("Expected 'true'", true,
				string.startsWith(new CString(String.valueOf("Štefica").toCharArray(), 0, 7)));

		assertEquals("Expected 'false'", false,
				string.endsWith(new CString(String.valueOf("Šfetica").toCharArray(), 0, 7)));
		assertEquals("Expected 'false'", false,
				string.endsWith(new CString(String.valueOf("Šfe").toCharArray(), 0, 3)));
	}

	@Test
	public void testLeft() {
		CString string = new CString(String.valueOf("Štefica Štefica Štefica").toCharArray(), 0, 23);
		assertEquals("Expected 'Šte'.", "Šte", string.left(3).toString());
		assertEquals("Expected 'Štefica '.", "Štefica ", string.left(8).toString());
		assertEquals("Expected ''.", "", string.left(0).toString());
		assertEquals("Expected 'Štefica Štefica Štefica'.", "Štefica Štefica Štefica", string.left(23).toString());
	}

	@Test
	public void testRight() {
		CString string = new CString(String.valueOf("Štefica Štefica Štefica").toCharArray(), 0, 23);
		assertEquals("Expected 'ica'.", "ica", string.right(3).toString());
		assertEquals("Expected ' Štefica'.", " Štefica", string.right(8).toString());
		assertEquals("Expected ''.", "", string.right(0).toString());
		assertEquals("Expected 'Štefica Štefica Štefica'.", "Štefica Štefica Štefica", string.right(23).toString());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testLeftInvalidIndexes1() {
		// must throw!
		new CString(String.valueOf("Štefica Štefica Štefica").toCharArray(), 0, 23).left(-1);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testLeftInvalidIndexes2() {
		// must throw!
		new CString(String.valueOf("Štefica Štefica Štefica").toCharArray(), 0, 23).left(24);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRightInvalidIndexes1() {
		// must throw!
		new CString(String.valueOf("Štefica Štefica Štefica").toCharArray(), 0, 23).right(-1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRightInvalidIndexes2() {
		// must throw!
		new CString(String.valueOf("Štefica Štefica Štefica").toCharArray(), 0, 23).right(24);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testStartsWithNullValue() {
		// must throw!
		new CString(String.valueOf(" Štefica ").toCharArray()).startsWith(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEndsWithNullValue() {
		// must throw!
		new CString(String.valueOf("  Štefica  ").toCharArray()).endsWith(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFromStringWithNullValue() {
		// must throw!
		CString.fromString(null);
	}

	@Test
	public void testFromStringWithRegularValue() {
		CString string1 = new CString(String.valueOf("Štefica Štefica Štefica").toCharArray(), 7, 9);
		CString string2 = new CString(String.valueOf(" Štefica i Marko  ").toCharArray());
		CString string3 = new CString(string1.right(7));

		assertEquals("Expected '0'", 0, string1.toString().compareTo(CString.fromString(" Štefica ").toString()));
		assertEquals("Expected '0'", 0,
				string2.toString().compareTo(CString.fromString(" Štefica i Marko  ").toString()));
		assertEquals("Expected '0'", 0, string3.toString().compareTo(CString.fromString("tefica ").toString()));
	}

	@Test
	public void testContainsWithRegularValue() {
		CString string = new CString(String.valueOf(" Štefica i Marko  ").toCharArray());

		CString test1 = CString.fromString("Štefica");
		CString test2 = CString.fromString("Marko");
		CString test3 = CString.fromString(" Štefica i Marko  ");
		CString test4 = CString.fromString(" Šte");
		CString test5 = CString.fromString("rko ");
		CString test6 = CString.fromString("Ante");

		assertEquals("Expected 'true'", true, string.contains(test1));
		assertEquals("Expected 'true'", true, string.contains(test2));
		assertEquals("Expected 'true'", true, string.contains(test3));
		assertEquals("Expected 'true'", true, string.contains(test4));
		assertEquals("Expected 'true'", true, string.contains(test5));
		assertEquals("Expected 'true'", false, string.contains(test6));

	}

	@Test(expected = IllegalArgumentException.class)
	public void testContainsWithNullValue() {
		// must throw!
		new CString(String.valueOf(" Štefica i Marko  ").toCharArray()).contains(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSubstringWithInvalidArguments1() {
		// must throw!
		CString.fromString("Štefica i Marko").substring(-1, 3);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSubstringWithInvalidArguments2() {
		// must throw!
		CString.fromString("Štefica i Marko").substring(2, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSubstringWithInvalidArguments3() {
		// must throw!
		CString.fromString("Štefica i Marko").substring(2, 16);
	}

	@Test
	public void testSubstringWithValidArguments() {
		CString string = CString.fromString(" Štefica i Marko ");

		assertEquals("Expected that they are the same.", 0, " Štefica".compareTo(string.substring(0, 8).toString()));
		assertEquals("Expected that they are the same.", 0, "Marko ".compareTo(string.substring(11, 17).toString()));
		assertEquals("Expected that they are the same.", 0, "ica".compareTo(string.substring(5, 8).toString()));
		assertEquals("Expected that they are the same.", 0, " ".compareTo(string.substring(0, 1).toString()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAllWithNullValue() {
		// must throw!
		CString.fromString("Marko").add(null);
	}

	@Test
	public void testAllWithValidArgumetns() {
		CString string1 = CString.fromString("Štefica").add(CString.fromString(" i "));
		CString string2 = CString.fromString("Marko");

		assertEquals("Expected 'Štefica i '", "Štefica i ", string1.toString());
		assertEquals("Expected 'Štefica i Marko'.", "Štefica i Marko", string1.add(string2).toString());
	}

	@Test
	public void testReplaceAllWithCharsAsArguments() {
		CString string1 = CString.fromString("# Štefica# i Marko # se vole. # #");
		CString string2 = string1.replaceAll('#', '*');
		CString string3 = string2.replaceAll('$', '*');
		CString string4 = string3.replaceAll(' ', '$');

		assertEquals("I should be full of stars.", "* Štefica* i Marko * se vole. * *", string2.toString());
		assertEquals("I should be also full of stars.", "* Štefica* i Marko * se vole. * *", string3.toString());
		assertEquals("I should be also full of stars and dollaz.", "*$Štefica*$i$Marko$*$se$vole.$*$*",
				string4.toString());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testReplaceAllWithNullAsValue1() {
		// must throw!
		CString.fromString("Štefica i Marko šetaju se šumom.").replaceAll(null, CString.fromString("zeko"));

	}

	@Test(expected = IllegalArgumentException.class)
	public void testReplaceAllWithNullAsValue2() {
		// must throw!
		CString.fromString("Štefica i Marko šetaju se šumom.").replaceAll(CString.fromString("Marko"), null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testReplaceAllWithNullAsValue3() {
		// must throw!
		CString.fromString("Štefica i Marko šetaju se šumom.").replaceAll(null, null);
	}

	@Test
	public void testReplaceAllWithCStringsAsArguments() {
		CString string1 = CString.fromString("Štefica i Marko šetaju šumom.\n" + "Marko i Štefica drže se za ruke.\n"
				+ "Štefici se sviđa Marko.\n" + "Marku se sviđa Štefica.");
		CString result1 = string1.replaceAll(CString.fromString("Štefica"), CString.fromString("Marica"));
		CString result2 = string1.replaceAll(CString.fromString("Marko"), CString.fromString("Ivica"));
		CString result3 = string1.replaceAll(CString.fromString("medvjed"), CString.fromString("zeko"));

		CString compare1 = CString.fromString("Marica i Marko šetaju šumom.\n" + "Marko i Marica drže se za ruke.\n"
				+ "Štefici se sviđa Marko.\n" + "Marku se sviđa Marica.");
		CString compare2 = CString.fromString("Štefica i Ivica šetaju šumom.\n" + "Ivica i Štefica drže se za ruke.\n"
				+ "Štefici se sviđa Ivica.\n" + "Marku se sviđa Štefica.");

		assertEquals("We should be the same.", 0, result1.toString().compareTo(compare1.toString()));
		assertEquals("We should be the same.", 0, result2.toString().compareTo(compare2.toString()));
		assertEquals("We should be the same.", 0, result3.toString().compareTo(string1.toString()));
	}
}
