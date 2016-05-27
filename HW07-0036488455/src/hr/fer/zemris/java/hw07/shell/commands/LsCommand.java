package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import hr.fer.zemris.java.hw07.shell.environment.Environment;


/**
 * Ls command to be used by {@link Environment} implementing classes.
 * Prints out the immediate contents of the folder at the given 
 * path and their informations.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class LsCommand extends AbstractCommand{

	/**
	 * NAme of the command.
	 */
	private static final String NAME = "ls";
	
	/**
	 * Description of the command.
	 */
	private static final String[] DESCRIPTION = {
		" - writes out the immediate contents of the folder at the "
		+ "given location"	
	};
	
	/**
	 * Format of the date to be listed.
	 */
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * Printed if attribute is not present.
	 */
	private static final String IS_NOT_ATTRIBUTE = "-";
	
	/**
	 * Printed if file is directory.
	 */
	private static final String IS_DIRECTORY = "d";
	
	/**
	 * Printed if file is readable.
	 */
	private static final String IS_READABLE = "r";
	
	/**
	 * Printed if file is writable.
	 */
	private static final String IS_WRITABLE = "w";
	
	/**
	 * Printed if file is executable.
	 */
	private static final String IS_EXECUTABLE = "x";
	
	/**
	 * Legth of string containing size.
	 */
	private static final int SIZE_LENGTH = 10;
	
	/**
	 * Denotes a different attribute is coming.
	 */
	private static final String ATTRIBUTE_SPLITTER = " ";
	
	/**
	 * Name of the default file if no file is given.
	 */
	private static final String DEFAULT_FILE_NAME = ".";
	
	
	/**
	 * Creates the command.
	 */
	public LsCommand() {
		super(NAME, DESCRIPTION);
	}
	
	
	@Override
	public ShellStatus executeCommand(Environment env,
			String arguments) {
		Objects.requireNonNull(env);
		
		File file;
		
		try{
			if(arguments == null){
				file = Paths.get(DEFAULT_FILE_NAME).toFile();
				
			} else {
				arguments = arguments.replace('"', ' ').trim();
				file = Paths.get(arguments).toFile();
			}
		
		if(!file.exists()){
			env.writeln("No file found at that location!");
			return ShellStatus.CONTINUE;
		}
		
		if(!file.isDirectory()){
			env.writeln("Given file is not a directory!");
			return ShellStatus.CONTINUE;
		}
		
		File[] children = file.listFiles();
		
		if(children == null){
			env.writeln("The gien directory is empty!");
			return ShellStatus.CONTINUE;
		}
		
		for(File child : children){
			StringBuilder sb = new StringBuilder();
			
			sb.append(getAttributes(child))
					.append(ATTRIBUTE_SPLITTER);
			sb.append(getSize(child)).append(ATTRIBUTE_SPLITTER);
			sb.append(getDate(child)).append(ATTRIBUTE_SPLITTER);
			sb.append(child.getName());
			env.writeln(sb.toString());
		}
		
		} catch (InvalidPathException e) {
			try {
				env.writeln("Couldn't find file with given path!");
			} catch (IOException e1) {}
		} catch (IOException e) {}
		
		return ShellStatus.CONTINUE;
	}
	
	
	/**
	 * Gets the size of the given file in a string with length
	 * of 10.
	 * @param file file to be sized
	 * @return returns the string containing size
	 */
	private String getSize(File file) {
		return String.format("%" + SIZE_LENGTH + "d", file.length());
	}


	/**
	 * Gets the attributes of the given file as a string. Tells
	 * if a file is directory, readable, writable and executable. 
	 * If not true for file will print - in the place of that letter.
	 * 
	 * @param file file to be checked
	 * @return returns the string with attributes
	 */
	private String getAttributes(File file) {
		StringBuilder sb = new StringBuilder();
		
		if(file.isDirectory()){
			sb.append(IS_DIRECTORY);
		} else {
			sb.append(IS_NOT_ATTRIBUTE);
		}
		
		if(file.canRead()){
			sb.append(IS_READABLE);
		} else {
			sb.append(IS_NOT_ATTRIBUTE);
		}
		
		if(file.canWrite()){
			sb.append(IS_WRITABLE);
		} else {
			sb.append(IS_NOT_ATTRIBUTE);
		}
		
		if(file.canExecute()){
			sb.append(IS_EXECUTABLE);
		} else {
			sb.append(IS_NOT_ATTRIBUTE);
		}
		
		return sb.toString();
	}


	/**
	 * Gets the date of creation of the file in the given path.
	 * 
	 * @param file file to be dated
	 * @return returns date in format yyyy-MM-dd HH:mm:ss
	 * @throws IOException thrown if error occured
	 */
	private String getDate(File file) throws IOException{
		SimpleDateFormat sdf = 
				new SimpleDateFormat(DATE_FORMAT);
		
		BasicFileAttributeView faView = Files.getFileAttributeView(
				file.toPath(), BasicFileAttributeView.class, 
				LinkOption.NOFOLLOW_LINKS);
		
		BasicFileAttributes attributes = faView.readAttributes();
		
		FileTime fileTime = attributes.creationTime();
		
		String formattedDateTime = sdf.format(
				new Date(fileTime.toMillis()));
		
		return formattedDateTime;
	}
}
