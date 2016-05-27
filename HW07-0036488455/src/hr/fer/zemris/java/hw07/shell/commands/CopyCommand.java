package hr.fer.zemris.java.hw07.shell.commands;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

import hr.fer.zemris.java.hw07.shell.environment.Environment;

/**
 * Copy command to be used with {@link Environment} implementing classes. Takes
 * in 2 arguments, a path to the input file and either goal file or the goal
 * folder.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class CopyCommand extends AbstractCommand {

	/**
	 * Name of the command.
	 */
	private static final String NAME = "copy";

	/**
	 * Description of the command.
	 */
	private static final String[] DESCRIPTION = {
			" - copies the contents from the first given file to the"
					+ "second file or folder" };

	/**
	 * Position of input path.
	 */
	private static final int INPUT_POSITION = 0;

	/**
	 * Position of output path.
	 */
	private static final int OUTPUT_POSITION = 1;

	/**
	 * Number of paths expected.
	 */
	private static final int EXPECTED_ARGUMENTS = 2;

	/**
	 * Size of the block on the disk.
	 */
	private static final int BLOCK_SIZE = 4096;

	
	/**
	 * Creates the command.
	 */
	public CopyCommand() {
		super(NAME, DESCRIPTION);
	}

	
	@Override
	public ShellStatus executeCommand(Environment env,
			String arguments) {
		Objects.requireNonNull(env);

		try {
			if (arguments == null) {
				env.writeln("No arguments given!");
				return ShellStatus.CONTINUE;
			}

			String[] pathNames = FileReadHelper
					.divideIntoPaths(arguments);

			if (pathNames.length != EXPECTED_ARGUMENTS) {
				env.writeln("Not given enough arguments!");
				return ShellStatus.CONTINUE;
			}

			Path inputPath = Paths.get(pathNames[INPUT_POSITION]);
			Path outputPath = Paths.get(pathNames[OUTPUT_POSITION]);

			if (!inputPath.toFile().exists()) {
				env.writeln("Given input path does not exist!");
				return ShellStatus.CONTINUE;
			}

			if (inputPath.toFile().isDirectory()) {
				env.writeln("Given input path is a directory!");
				return ShellStatus.CONTINUE;
			}

			if (outputPath.toFile().exists()) {
				if (!outputPath.toFile().isDirectory()) {
					if (!outputPath.toFile().canWrite()) {
						env.writeln("Given output path is not allowed"
								+ "to be rewritten!");
						return ShellStatus.CONTINUE;

					}
				} else {
					outputPath = outputPath
							.resolve(inputPath.getFileName());

				}
			} else {
				File outputParent = outputPath.getParent().toFile();

				if (!outputParent.exists()) {
					outputParent.mkdirs();
				}
			}

			transfer(inputPath, outputPath, env);

		} catch (InvalidPathException e) {
			try {
				env.writeln("Couldn't deduce "
						+ "paths from given arguments!");
			} catch (IOException ignorable) {
			}
		} catch (IOException e2) {
			try {
				env.writeln("Couldn't open files!" + e2.getMessage());
			} catch (IOException ignorable) {
			}
		}

		return ShellStatus.CONTINUE;
	}

	
	/**
	 * Transfers the data from given input to given output path. Writes to given
	 * environment when finished.
	 * 
	 * @param inputPath
	 *            input path
	 * @param outputPath
	 *            output Path
	 * @param env
	 *            environment to be written to
	 * @throws IOException
	 *             throw in couldn't open file or couldn't write to environment
	 */
	private static void transfer(Path inputPath, Path outputPath,
			Environment env) throws IOException {
		InputStream input = Files.newInputStream(inputPath,
				StandardOpenOption.READ);
		OutputStream output = new BufferedOutputStream(
				new FileOutputStream(outputPath.toFile()));

		byte[] buffer = new byte[BLOCK_SIZE];
		int read = 0;

		while ((read = input.read(buffer)) > 0) {
			output.write(buffer, 0, read);
		}

		input.close();
		output.close();
		env.writeln("Finished copying!");
	}
}
