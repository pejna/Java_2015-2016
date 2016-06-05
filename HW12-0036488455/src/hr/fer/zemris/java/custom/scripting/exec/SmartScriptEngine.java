package hr.fer.zemris.java.custom.scripting.exec;

import hr.fer.zemris.java.webserver.RequestContext;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

/**
 * Engine for execution of the SmartScript script texts.
 * <p>
 * Engine takes in the constructor the {@link DocumentNode} created with the use
 * of {@link SmartScriptParser}. Also takes in a {@link RequestContext} and
 * writes the output of the script to the given class.
 * </p>
 * <p>
 * Executes the script when execute() is called to support more calls of the
 * same script.
 * </p>
 * @author Juraj Pejnovic
 * @version 1.0
 * @see RequestContext
 * @see SmartScriptParser
 */
public class SmartScriptEngine {

	/**
	 * Interface for simple operations that use fields and parameters from the
	 * engine as it's inputs and outputs.
	 * 
	 * @author Juraj Pejnovic
	 * @version 1.0
	 */
	private static interface Operation {

		/**
		 * Executes the operation.
		 */
		void execute();
	}

	/**
	 * Top node with the script.
	 */
	private DocumentNode documentNode;

	/**
	 * Context.
	 */
	private RequestContext requestContext;

	/**
	 * Multistack for operation manipulation.
	 */
	private ObjectMultistack multistack = new ObjectMultistack();

	/**
	 * Operations mapped to their keyword symbols found in the script.
	 */
	private Map<String, Operation> operations;

	/**
	 * Functions mapped to their keyword found in the script.
	 */
	private Map<String, Operation> functions;

	/**
	 * Keyword for the temporary stack used in echo node executions.
	 */
	private static final String TEMPORARY_STACK = "temp";

	/**
	 * Keyword for helper stack used in echo node executions.
	 */
	private static final String HELP_STACK = "help";

	/*
	 * ******** Operation names ************************************************
	 */

	/**
	 * Addition operatr.
	 */
	private static final String ADDITION = "+";

	/**
	 * Subtraction operator.
	 */
	private static final String SUBTRACTION = "-";

	/**
	 * Multiplication operator.
	 */
	private static final String MULTIPLICATION = "*";

	/**
	 * Division operator.
	 */
	private static final String DIVISION = "/";

	/*
	 * ******** Function names *************************************************
	 */

	/**
	 * Sine function.
	 */
	private static final String SIN = "sin";

	/**
	 * Decimal format function.
	 */
	private static final String DECIMAL_FORMAT = "decfmt";

	/**
	 * Stack top duplication function.
	 */
	private static final String DUPLICATE = "dup";

	/**
	 * Stack top swapping function.
	 */
	private static final String SWAP = "swap";

	/**
	 * Mime type setting function.
	 */
	private static final String SET_MIME_TYPE = "setMimeType";

	/**
	 * Get the context parameter function.
	 */
	private static final String PARAM_GET = "paramGet";

	/**
	 * Get the persistent context parameter function.
	 */
	private static final String PPARAM_GET = "pparamGet";

	/**
	 * Set the persistent context parameter function.
	 */
	private static final String PPARAM_SET = "pparamSet";

	/**
	 * Delete the persistent context parameter function.
	 */
	private static final String PPARAM_DEL = "pparamDel";

	/**
	 * Get the temporary paramter function.
	 */
	private static final String TPARAM_GET = "tparamGet";

	/**
	 * Set the temporary parameter function.
	 */
	private static final String TPARAM_SET = "tparamSet";

	/**
	 * Delete the temporary parameter function.
	 */
	private static final String TPARAM_DEL = "tparamDel";

	/**
	 * Visitor that executes the script.
	 */
	private INodeVisitor visitor = new INodeVisitor() {

		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.getText());
			} catch (IOException e) {
				System.err
						.println("Warning - SmartScriptEngine: Couldn't write "
								+ node.getText()
								+ " to requestContext output!");
			}
		}


		@Override
		public void visitForLoopNode(ForLoopNode node) {
			ValueWrapper start = new ValueWrapper(
					node.getStartExpression().asText());
			ValueWrapper end = new ValueWrapper(
					node.getEndExpression().asText());
			ValueWrapper increment = new ValueWrapper(
					node.getStepExpression().asText());

			while (start.numCompare(end.getValue()) <= 0) {
				multistack.push(node.getVariable().getName(), start);
				visitChildren(node);
				start = multistack.pop(node.getVariable().getName());
				start.increment(increment.getValue());
			}
		}


		@Override
		public void visitEchoNode(EchoNode node) {
			for (Element e : node.getElements()) {
				if (e.getClass().equals(ElementConstantDouble.class)) {
					multistack.push(TEMPORARY_STACK, new ValueWrapper(
							((ElementConstantDouble) e).getValue()));

				} else if (e.getClass().equals(ElementConstantInteger.class)) {
					multistack.push(TEMPORARY_STACK, new ValueWrapper(
							((ElementConstantInteger) e).getValue()));

				} else if (e.getClass().equals(ElementString.class)) {
					multistack.push(TEMPORARY_STACK,
							new ValueWrapper(((ElementString) e).getValue()));

				} else if (e.getClass().equals(ElementVariable.class)) {
					multistack.push(TEMPORARY_STACK, new ValueWrapper(
							multistack.peek(e.asText()).getValue()));

				} else if (e.getClass().equals(ElementOperator.class)) {
					operations.get(e.asText()).execute();

				} else if (e.getClass().equals(ElementFunction.class)) {
					functions.get(((ElementFunction) e).getName()).execute();
				}
			}

			while (!multistack.isEmpty(TEMPORARY_STACK)) {
				multistack.push(HELP_STACK, multistack.pop(TEMPORARY_STACK));
			}

			while (!multistack.isEmpty(HELP_STACK)) {
				try {
					requestContext.write(
							multistack.pop(HELP_STACK).getValue().toString());
				} catch (IOException e) {
					System.err.println("Warning "
							+ "- SmartScriptEngine: Couldn't pass from stack to outputStream!");
				}
			}
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
	};


	/**
	 * Creates the engine that processes the script from a document represented
	 * by the given node.
	 * 
	 * @param documentNode
	 *            node with script from a document
	 * @param requestContext
	 *            request context
	 */
	public SmartScriptEngine(DocumentNode documentNode,
			RequestContext requestContext) {
		Objects.requireNonNull(documentNode);
		Objects.requireNonNull(requestContext);
		this.documentNode = documentNode;
		this.requestContext = requestContext;

		initOperations();
		initFunctions();
	}


	/**
	 * Initializes the operations.
	 */
	private void initOperations() {
		operations = new HashMap<>();
		operations.put(ADDITION, () -> {
			ValueWrapper b = multistack.pop(TEMPORARY_STACK);
			ValueWrapper a = multistack.pop(TEMPORARY_STACK);
			a.increment(b.getValue());
			multistack.push(TEMPORARY_STACK, a);
		});

		operations.put(SUBTRACTION, () -> {
			ValueWrapper b = multistack.pop(TEMPORARY_STACK);
			ValueWrapper a = multistack.pop(TEMPORARY_STACK);
			a.decrement(b.getValue());
			multistack.push(TEMPORARY_STACK, a);
		});

		operations.put(MULTIPLICATION, () -> {
			ValueWrapper b = multistack.pop(TEMPORARY_STACK);
			ValueWrapper a = multistack.pop(TEMPORARY_STACK);
			a.multiply(b.getValue());
			multistack.push(TEMPORARY_STACK, a);
		});

		operations.put(DIVISION, () -> {
			ValueWrapper b = multistack.pop(TEMPORARY_STACK);
			ValueWrapper a = multistack.pop(TEMPORARY_STACK);
			a.divide(b.getValue());
			multistack.push(TEMPORARY_STACK, a);
		});
	}


	/**
	 * Initializes the functions.
	 */
	private void initFunctions() {
		functions = new HashMap<>();
		functions.put(SIN, () -> {
			String value = multistack.pop(TEMPORARY_STACK).getValue()
					.toString();
			multistack.push(TEMPORARY_STACK, new ValueWrapper(
					Math.sin(Double.parseDouble(value) * 0.0174532925)));
		});

		functions.put(DECIMAL_FORMAT, () -> {
			DecimalFormat format = new DecimalFormat(
					(String) multistack.pop(TEMPORARY_STACK).getValue());
			Object value = multistack.pop(TEMPORARY_STACK).getValue();
			multistack.push(TEMPORARY_STACK,
					new ValueWrapper(format.format(value)));
		});

		functions.put(DUPLICATE, () -> {
			ValueWrapper x = new ValueWrapper(
					multistack.peek(TEMPORARY_STACK).getValue());
			multistack.push(TEMPORARY_STACK, x);
		});

		functions.put(SWAP, () -> {
			ValueWrapper a = multistack.pop(TEMPORARY_STACK);
			ValueWrapper b = multistack.pop(TEMPORARY_STACK);
			multistack.push(TEMPORARY_STACK, a);
			multistack.push(TEMPORARY_STACK, b);
		});

		functions.put(SET_MIME_TYPE, () -> {
			requestContext.setMimeType(
					(String) multistack.pop(TEMPORARY_STACK).getValue());
		});

		functions.put(PARAM_GET, () -> {
			Object defVal = multistack.pop(TEMPORARY_STACK).getValue();
			String name = multistack.pop(TEMPORARY_STACK).getValue().toString();
			String value = requestContext.getParameter(name);
			multistack.push(TEMPORARY_STACK,
					new ValueWrapper(value == null ? defVal : value));
		});

		functions.put(PPARAM_GET, () -> {
			Object defVal = multistack.pop(TEMPORARY_STACK).getValue();
			String name = multistack.pop(TEMPORARY_STACK).getValue().toString();
			String value = requestContext.getPersistentParameter(name);
			multistack.push(TEMPORARY_STACK,
					new ValueWrapper(value == null ? defVal : value));
		});

		functions.put(TPARAM_GET, () -> {
			Object defVal = multistack.pop(TEMPORARY_STACK).getValue();
			String name = multistack.pop(TEMPORARY_STACK).getValue().toString();
			String value = requestContext.getTemporaryParameter(name);
			multistack.push(TEMPORARY_STACK,
					new ValueWrapper(value == null ? defVal : value));
		});

		functions.put(PPARAM_SET, () -> {
			String name = multistack.pop(TEMPORARY_STACK).getValue().toString();
			String value = multistack.pop(TEMPORARY_STACK).getValue()
					.toString();
			requestContext.setPersistentParameter(name, value);
		});

		functions.put(TPARAM_SET, () -> {
			String name = multistack.pop(TEMPORARY_STACK).getValue().toString();
			String value = multistack.pop(TEMPORARY_STACK).getValue()
					.toString();
			requestContext.setTemporaryParameter(name, value);
		});

		functions.put(PPARAM_DEL, () -> {
			String name = multistack.pop(TEMPORARY_STACK).getValue().toString();
			requestContext.removePersistentParameter(name);
		});

		functions.put(TPARAM_DEL, () -> {
			String name = multistack.pop(TEMPORARY_STACK).getValue().toString();
			requestContext.removeTemporaryParameter(name);
		});
	}


	/**
	 * Executes the script placed in the engine.
	 */
	public void execute() {
		documentNode.accept(visitor);
	}

}
