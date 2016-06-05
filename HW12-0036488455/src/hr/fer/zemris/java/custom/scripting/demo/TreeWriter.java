package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.lexer.EscapeManager;
import hr.fer.zemris.java.custom.scripting.lexer.KeywordManager;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * Program test the traversal skills of {@link INodeVisitor} implementing
 * classes and {@link SmartScriptParser} class.
 * <p>
 * Takes in 1 parameter. A path to the SmartScript document. Parses it and
 * writes it's contents to the standard output.
 * </p>
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 * @see SmartScriptParser
 * @see Node
 * @see INodeVisitor
 */
public class TreeWriter {

	/**
	 * Runs the program.
	 * 
	 * @param args
	 *            address of the document
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
			System.err.println("Not enough arguments! Aborting!");
			System.exit(-1);
		}

		String filepath = args[0];

		String docBody = "";
		try {
			docBody = new String(Files.readAllBytes(Paths.get(filepath)),
					StandardCharsets.UTF_8);
		} catch (IOException e1) {
			System.err.println("Incorrect input! Aborting!");
			System.exit(-1);
		}

		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(docBody);
		} catch (SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch (Exception e) {
			System.out.println(
					"If this line ever executes, you have failed this class!");
			System.exit(-1);
		}

		DocumentNode document = parser.getDocumentNode();
		document.accept(new WriterVisitor());

	}

	/**
	 * Visitor implementation that writes out the contents of nodes to the
	 * standard output recreating the document to be parsed again.
	 * 
	 * @author Juraj Pejnovic
	 * @version 1.0
	 * @see INodeVisitor
	 */
	private static class WriterVisitor implements INodeVisitor {

		/**
		 * Creates the visitor.
		 */
		public WriterVisitor() {
		}


		@Override
		public void visitTextNode(TextNode node) {
			String string = node.getText();

			for (char c : EscapeManager.ESCAPABLE_TEXT) {
				string = string.replace("" + c,
						"" + EscapeManager.ESCAPE_CHARACTER + c);
			}

			System.out.print(string);
		}


		@Override
		public void visitForLoopNode(ForLoopNode node) {
			String string = "{$ " + KeywordManager.KEYWORD_FOR + " ";

			string += node.getVariable().asText() + " ";
			string += node.getStartExpression().asText() + " ";
			string += node.getEndExpression().asText() + " ";
			if (node.getStepExpression() != null) {
				string += node.getStepExpression().asText() + " ";
			}
			string += "$}";

			System.out.print(string);

			visitChildren(node);

			System.out.print("{$ " + KeywordManager.KEYWORD_END + " $}");
		}


		@Override
		public void visitEchoNode(EchoNode node) {
			String string = "{$ " + KeywordManager.KEYWORD_ECHO + " ";
			for (Element element : node.getElements()) {
				string += element.asText() + " ";
			}

			string += "$}";
			System.out.print(string);
		}


		@Override
		public void visitDocumentNode(DocumentNode node) {
			visitChildren(node);
		}


		/**
		 * Visits all children of the given node.
		 * 
		 * @param node
		 *            node with children
		 */
		private void visitChildren(Node node) {
			int numberOfChildrent = node.numberOfChildren();
			for (int i = 0; i < numberOfChildrent; i++) {
				node.getChild(i).accept(this);
			}
		}
	}
}
