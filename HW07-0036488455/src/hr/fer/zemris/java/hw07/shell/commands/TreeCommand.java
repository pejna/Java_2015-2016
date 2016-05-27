package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.Objects;

import hr.fer.zemris.java.hw07.shell.environment.Environment;


/**
 * Tree command to be used with the {@link Environment} implementing
 * classes. Takes a path to a folder and prints out all the
 * contents to the {@link Environment} in the form of a tree.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class TreeCommand extends AbstractCommand{

	/**
	 * Name of the command.
	 */
	private static final String NAME = "tree";
	
	/**
	 * Description of the command.
	 */
	private static final String[] DESCRIPTION = {
		" - prints out all contents of the folder at the given path "
		+ "in a tree format"	
	};
	
	private static final String INDENTER = "  ";
	
	
	/**
	 * Creates the command.
	 */
	public TreeCommand() {
		super(NAME, DESCRIPTION);
	}
	
	
	@Override
	public ShellStatus executeCommand(Environment env,
			String arguments) {
		Objects.requireNonNull(env);
		
		File file;

		try{
			if(arguments == null){
				file = Paths.get(".").toFile();
				
			} else {
				arguments = arguments.replace('"', ' ').trim();
				file = Paths.get(arguments).toFile();
			}
		
		if(!file.exists()){
			env.writeln("No file found at that location!");
			return ShellStatus.CONTINUE;
		}
		
		printTree(0, file, env);
		
		} catch (InvalidPathException e) {
			try {
				env.writeln("Couldn't find file with given path!");
			} catch (IOException e1) {}
		} catch (IOException e) {}
		
		return ShellStatus.CONTINUE;
	}
	

	/**
	 * Recursively prints the tree to the environments input.
	 * 
	 * @param level current depth of the traversal
	 * @param file current file to be traversed
	 * @param env environment with output stream
	 */
	private void printTree(int level, File file, 
			Environment env){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < level; i++){
			sb.append(INDENTER);
		}
		sb.append(file.getName());	
		
		try {
			env.writeln(sb.toString());
		} catch (IOException ignorable) {}
		
		if(file.isDirectory()){
			File[] children = file.listFiles();
			
			if(children == null){
				return;
			}
			
			if(children.length == 0){
				return;
			}
			
			for(File child : children){
				printTree(level + 1, child, env);
			}
		}
	}
}
