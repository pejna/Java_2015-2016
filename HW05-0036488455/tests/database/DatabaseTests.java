
import static org.junit.Assert.*;

import org.junit.Test;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;
import hr.fer.zemris.java.tecaj.hw5.db.lexer.DatabaseToken;
import hr.fer.zemris.java.tecaj.hw5.db.lexer.Lexer;
import hr.fer.zemris.java.tecaj.hw5.db.lexer.DatabaseToken.DatabaseTokenType;
import hr.fer.zemris.java.tecaj.hw5.db.operators.EqualsComparisonOperator;
import hr.fer.zemris.java.tecaj.hw5.db.operators.LessComparisonOperator;
import hr.fer.zemris.java.tecaj.hw5.db.operators.LessOrEqualsComparisonOperator;
import hr.fer.zemris.java.tecaj.hw5.db.operators.LikeComparisonOperator;
import hr.fer.zemris.java.tecaj.hw5.db.operators.MoreComparisonOperator;
import hr.fer.zemris.java.tecaj.hw5.db.operators.MoreOrEqualsComparisonOperator;
import hr.fer.zemris.java.tecaj.hw5.db.operators.NotEqualsComparisonOperator;
import hr.fer.zemris.java.tecaj.hw5.db.lexer.QueryLexer;


public class DatabaseTests {

	/*
	 * ******** Operator tests ***************************************
	 */
	
	@Test
	public void LessTrueTest(){
		LessComparisonOperator less = new LessComparisonOperator();
		
		
		assertTrue(less.satisfied("ana", "babo"));
	}
	
	@Test
	public void LessFalseTest(){
		LessComparisonOperator less = new LessComparisonOperator();
		
		
		assertFalse(less.satisfied("babo", "ana"));
	}
	
	@Test
	public void MoreTrueTest(){
		MoreComparisonOperator more = 
				new MoreComparisonOperator();
		
		assertTrue(more.satisfied("babo", "ana"));
	}
	
	@Test
	public void MoreFalseTest(){
		MoreComparisonOperator more = new MoreComparisonOperator();
		
		
		assertFalse(more.satisfied("ana", "babo"));
	}
	@Test
	public void LessOrEqualsTrueTest(){
		LessOrEqualsComparisonOperator less = 
				new LessOrEqualsComparisonOperator();
		
		
		assertTrue(less.satisfied("babo", "babo"));
	}
	
	@Test
	public void LessOrEqualsFalseTest(){
		LessOrEqualsComparisonOperator less = 
				new LessOrEqualsComparisonOperator();
		
		
		assertFalse(less.satisfied("babo", "ana"));
	}
	
	@Test
	public void MoreOrEqualsTrueTest(){
		MoreOrEqualsComparisonOperator more = 
				new MoreOrEqualsComparisonOperator();
		assertTrue(more.satisfied("ana", "ana"));
	}
	
	@Test
	public void MoreOrEqualsFalseTest(){
		MoreOrEqualsComparisonOperator less = 
				new MoreOrEqualsComparisonOperator();
		
		
		assertFalse(less.satisfied("ana", "babo"));
	}
	
	@Test
	public void NotEqualTrueTest(){
		NotEqualsComparisonOperator notEq = 
				new NotEqualsComparisonOperator();
		
		assertTrue(notEq.satisfied("ana", "babo"));
	}
	
	@Test
	public void NotEqualFalseTest(){
		NotEqualsComparisonOperator notEq = 
				new NotEqualsComparisonOperator();
		
		assertFalse(notEq.satisfied("ana", "ana"));
	}
	
	@Test
	public void EqualTrueTest(){
		EqualsComparisonOperator notEq = 
				new EqualsComparisonOperator();
		
		assertTrue(notEq.satisfied("ana", "ana"));
	}
	
	@Test
	public void EqualFalseTest(){
		EqualsComparisonOperator notEq = 
				new EqualsComparisonOperator();
		
		assertFalse(notEq.satisfied("ana", "babo"));
	}
	
	@Test
	public void LikeWildCardFirstTrue(){
		LikeComparisonOperator like = new LikeComparisonOperator();
		
		assertTrue(like.satisfied("ana", "*a"));
	}
	
	@Test
	public void LikeWildCardLastTrue(){
		LikeComparisonOperator like = new LikeComparisonOperator();
		
		assertTrue(like.satisfied("ana", "a*"));
	}
	
	@Test
	public void LikeWildCardMiddleTrue(){
		LikeComparisonOperator like = new LikeComparisonOperator();
		
		assertTrue(like.satisfied("ana", "a*a"));
	}
	
	/*
	 * ******** Lexer tests ******************************************
	 */

	@Test
	public void testLexerGood(){
		QueryLexer lexer = new QueryLexer("firstName lastName= != <= >= LIKE AnD jmbag \"ana\"");
		DatabaseToken[] tokens = {
				new DatabaseToken("firstName", DatabaseTokenType.FIRST_NAME),
				new DatabaseToken("lastName", DatabaseTokenType.LAST_NAME),		
				new DatabaseToken("firstName", DatabaseTokenType.FIRST_NAME),
				new DatabaseToken("=", DatabaseTokenType.OPERATOR),
				new DatabaseToken("!=", DatabaseTokenType.OPERATOR),
				new DatabaseToken("<=", DatabaseTokenType.OPERATOR),
				new DatabaseToken(">=", DatabaseTokenType.OPERATOR),
				new DatabaseToken("LIKE", DatabaseTokenType.OPERATOR),
				new DatabaseToken("AnD", DatabaseTokenType.AND),
				new DatabaseToken("jmbag", DatabaseTokenType.JMBAG),
				new DatabaseToken("ana", DatabaseTokenType.STRING),
		};
		int index = 0;
		boolean same = true;
		for(DatabaseToken token : lexer){
			if(token.equals(tokens[index++])){
				same = false;
			}
		}
		
		assertTrue(same);
	}
	
	@Test (expected=Lexer.LexerException.class)
	public void testLexerStringBad(){
		QueryLexer lexer = new QueryLexer("\"an");
		
		//will throw
		lexer.nextToken();
	}
	/*
	 * ******** StudentRecord tests **********************************
	 */
	
	@Test
	public void studentInit(){
		StudentRecord student = new StudentRecord("0000000001\tDragisa\tAnte\t2");
		assertEquals(2, student.getFinalGrade());
	}
	
}
