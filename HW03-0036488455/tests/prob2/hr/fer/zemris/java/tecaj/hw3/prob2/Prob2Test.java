package hr.fer.zemris.java.tecaj.hw3.prob2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;

import hr.fer.zemris.java.custom.scripting.lexer.*;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * Contains JUnit tests for second assignment of HW3 of Java FER 
 * 2015/2016.
 */
public class Prob2Test {

	@Test
	public void testNotNull() {
		Lexer lexer = new Lexer("");
		
		assertNotNull("Token was expected but null was returned.", lexer.nextToken());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testNullInput() {
		// must throw!
		new Lexer(null);
	}

	@Test
	public void testEmpty() {
		Lexer lexer = new Lexer("");
		
		assertEquals("Empty input must generate only EOF token.", TokenType.EOF, lexer.nextToken().getType());
	}

	@Test
	public void testGetReturnsLastNext() {
		// Calling getToken once or several times after calling nextToken must return each time what nextToken returned...
		Lexer lexer = new Lexer("");
		
		Token token = lexer.nextToken();
		assertEquals("getToken returned different token than nextToken.", token, lexer.getToken());
		assertEquals("getToken returned different token than nextToken.", token, lexer.getToken());
	}

	@Test(expected=LexerException.class)
	public void testRadAfterEOF() {
		Lexer lexer = new Lexer("");

		// will obtain EOF
		lexer.nextToken();
		// will throw!
		lexer.nextToken();
	}

	@Test
	public void testTwoWords() {
		// Lets check for several words...
		Lexer lexer = new Lexer("  Štefanija\r\n\t Automobil{ $   ");

		// We expect the following stream of tokens
		Token correctData[] = {
			new Token(TokenType.TEXT, "  Štefanija"
					+ "\r\n\t Automobil{ $   "),
			new Token(TokenType.EOF, null)
		};

		checkTokenStream(lexer, correctData);
	}
	
	@Test(expected=LexerException.class)
	public void testInvalidEscape() {
		Lexer lexer = new Lexer("   \\a    ");

		// will throw!
		lexer.nextToken();
	}

	@Test(expected=LexerException.class)
	public void testInvalidEscapeEnding() {
		Lexer lexer = new Lexer("   \\");  // this is three spaces and a single backslash -- 4 letters string

		// will throw!
		lexer.nextToken();
	}

	@Test
	public void testEscapedCharacters() {
		Lexer lexer = new Lexer("\\{\\\\");

		// We expect the following stream of tokens
		Token correctData[] = {
			new Token(TokenType.TEXT, "{\\"),
		};

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testSwitchToTag() {
		Lexer lexer = new Lexer("$}{$");

		// We expect the following stream of tokens
		Token correctData[] = {
			new Token(TokenType.TEXT, "$}"),
			new Token(TokenType.TRANSITION_IN, "{$")
		};

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testKeyword() {
		Lexer lexer = new Lexer("{$        FOR      end =");

		// We expect the following stream of tokens
		Token correctData[] = {
			new Token(TokenType.TRANSITION_IN, "{$"),
			new Token(TokenType.KEYWORD, "FOR"),
			new Token(TokenType.KEYWORD, "end"),
			new Token(TokenType.KEYWORD, "=")
		};

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testVariableGood() {
		Lexer lexer = new Lexer("{$ ante");

		// We expect the following stream of tokens
		Token correctData[] = {
			new Token(TokenType.TRANSITION_IN, "{$"),
			new Token(TokenType.VARIABLE, "ante"),
		};

		checkTokenStream(lexer, correctData);
	}
	
	@Test(expected=LexerException.class)
	public void testVariableBad() {
		Lexer lexer = new Lexer("{$ _ante      ");
		
		lexer.nextToken();

		// will throw!
		lexer.nextToken();
	}
	
	@Test
	public void testNumberGood() {
		Lexer lexer = new Lexer("{$ 12 -12.3");

		// We expect the following stream of tokens
		Token correctData[] = {
			new Token(TokenType.TRANSITION_IN, "{$"),
			new Token(TokenType.INTEGER, 12),
			new Token(TokenType.DOUBLE, -12.3)
		};

		checkTokenStream(lexer, correctData);
	}
	
	@Test(expected=LexerException.class)
	public void testNumberBad() {
		Lexer lexer = new Lexer("{$ 12.3.3      ");
		
		lexer.nextToken();

		// will throw!
		lexer.
		nextToken();
	}
	
	@Test
	public void testFunctionGood() {
		Lexer lexer = new Lexer("{$ @a___111ada");

		// We expect the following stream of tokens
		Token correctData[] = {
			new Token(TokenType.TRANSITION_IN, "{$"),
			new Token(TokenType.FUNCTION, "a___111ada")
		};

		checkTokenStream(lexer, correctData);
	}
	
	@Test(expected=LexerException.class)
	public void testFunctionBad() {
		Lexer lexer = new Lexer("{$ @");
		
		lexer.nextToken();

		// will throw!
		lexer.
		nextToken();
	}
	
	@Test
	public void testStringGood() {
		Lexer lexer = new Lexer("{$ \"an7e.\" ");

		// We expect the following stream of tokens
		Token correctData[] = {
			new Token(TokenType.TRANSITION_IN, "{$"),
			new Token(TokenType.STRING, "an7e.")
		};

		checkTokenStream(lexer, correctData);
	}
	
	@Test(expected=LexerException.class)
	public void testStringBadEof() {
		Lexer lexer = new Lexer("{$ \"ante");
		
		lexer.nextToken();

		// will throw!
		lexer.
		nextToken();
	}
	
	@Test
	public void testStringGoodEscape() {
		Lexer lexer = new Lexer("{$ \"\\\\ \\\" \\n \\r \\t\" ");

		// We expect the following stream of tokens
		Token correctData[] = {
			new Token(TokenType.TRANSITION_IN, "{$"),
			new Token(TokenType.STRING, "\\ \" \n \r \t")
		};

		checkTokenStream(lexer, correctData);
	}
	
	@Test(expected=LexerException.class)
	public void testStringBadEscape() {
		Lexer lexer = new Lexer("{$ \"\\a");
		
		lexer.nextToken();

		// will throw!
		lexer.
		nextToken();
	}
	
	
	@Test
	public void testOperator() {
		Lexer lexer = new Lexer("{$ */+-^");

		// We expect the following stream of tokens
		Token correctData[] = {
			new Token(TokenType.TRANSITION_IN, "{$"),
			new Token(TokenType.OPERATOR, "*"),
			new Token(TokenType.OPERATOR, "/"),
			new Token(TokenType.OPERATOR, "+"),
			new Token(TokenType.OPERATOR, "-"),
			new Token(TokenType.OPERATOR, "^")

		};

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testTransitionOut() {
		Lexer lexer = new Lexer("{$=ana$} {$");

		// We expect the following stream of tokens
		Token correctData[] = {
			new Token(TokenType.TRANSITION_IN, "{$"),
			new Token(TokenType.KEYWORD, "="),
			new Token(TokenType.VARIABLE, "ana"),
			new Token(TokenType.TRANSITION_OUT, "$}"),
			new Token(TokenType.TEXT, " "),
			new Token(TokenType.TRANSITION_IN, "{$")
		};

		checkTokenStream(lexer, correctData);
	}
	
	@Ignore
	@Test
	public void testParser(){
		String text = "ante {$for ana 12 \"22\" mirko$} "
				+ "{$=ana$}{$END$}";
		SmartScriptParser parser = new SmartScriptParser(text);
		
	}
	
	// Helper method for checking if lexer generates the same stream of tokens
	// as the given stream.
	private void checkTokenStream(Lexer lexer, Token[] correctData) {
		int counter = 0;
		for(Token expected : correctData) {
			Token actual = lexer.nextToken();
			String msg = "Checking token "+counter + ":";
			assertEquals(msg, expected.getType(), actual.getType());
			assertEquals(msg, expected.getValue(), actual.getValue());
			counter++;
		}
	}
	
}
