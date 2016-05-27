package hr.fer.zemris.java.hw07.shell.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.hw07.shell.environment.Environment;


/**
 * Mete class used for storing name and description the ofe
 * {@link ShellCommand}.
 * 
 * @author Juraj Pejnovic
 *
 */
public abstract class AbstractCommand implements ShellCommand {

	/**
	 * Name of the command.
	 */
	private String name;
	
	/**
	 * Description of the command.
	 */
	private String[] description;
	
	
	/**
	 * Creates the meta command that stores the given name and
	 * description.
	 * @param name name to be stored
	 * @param description description to be stored
	 */
	public AbstractCommand(String name, String[] description) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(description);
		
		this.name = name;
		this.description = description;
	}	
	
	
	@Override
	public abstract ShellStatus executeCommand(Environment env,
			String arguments);


	@Override
	public String getCommandName() {
		return name;
	}
	

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		for(String string : description){
			list.add(string);
		}
		
		return list;
	}

}
