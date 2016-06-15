package hr.fer.zemris.java.hw13.voting;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Utility class for voting servlet family. Enables procurement of results and
 * artist associated results with the ranking as the key.
 * 
 * @author Juraj Pejnovic
 * @version 1.0
 */
public class VoteUtil {

	/**
	 * Gets the results from the votingResults.txt file if exists, otherwise
	 * gets a new array of results to be filled. Data in the array consists of
	 * the band ids and scores separated by tab. Each entry is info for
	 * different music band.
	 * 
	 * @param path
	 *            path to the file
	 * @param req
	 *            request context
	 * @return returns the result data
	 * @throws IOException
	 *             thrown if an error with file reading occurs
	 */
	public static String[] getResults(String path, HttpServletRequest req)
			throws IOException {
		if (!Files.exists(Paths.get(path))) {
			String definitionFileName = req.getServletContext()
					.getRealPath("/WEB-INF/votingDefinition.txt");
			byte[] encoded = Files.readAllBytes(Paths.get(definitionFileName));
			String[] definitions = new String(encoded, "UTF-8").split("\n");
			StringBuilder sb = new StringBuilder();
			for (String definition : definitions) {
				sb.append(definition.split("\t")[0].trim()).append("\t");
				sb.append("0").append("\n");
			}
			return sb.toString().split("\n");
		} else {
			byte[] encoded = Files.readAllBytes(Paths.get(path));
			return new String(encoded, "UTF-8").split("\n");
		}
	}


	/**
	 * Gets the map of results. Map keys are the current score positions of the
	 * bands. Map values are the type of <band name>TAB<band score>.
	 * 
	 * @param req
	 *            request context
	 * @return returns the scoring map
	 * @throws IOException
	 *             thrown if error happens in reading the files
	 */
	public static Map<Integer, String> getResultMap(HttpServletRequest req)
			throws IOException {
		String[] results = VoteUtil.getResults(req.getServletContext()
				.getRealPath("/WEB-INF/votingResults.txt"), req);

		byte[] encoded = Files.readAllBytes(Paths.get(req.getServletContext()
				.getRealPath("/WEB-INF/votingDefinition.txt")));
		String[] definitions = new String(encoded, "UTF-8").split("\n");

		Arrays.sort(results, (a, b) -> {
			Integer ai = Integer.parseInt(((String) a).split("\t")[1].trim());
			Integer bi = Integer.parseInt(((String) b).split("\t")[1].trim());
			return bi.compareTo(ai);
		});

		Map<Integer, String> map = new HashMap<>();
		int counter = 0;
		for (String result : results) {
			String id = result.split("\t")[0].trim();
			for (String definition : definitions) {
				if (definition.split("\t")[0].trim().equals(id)) {
					map.put(counter, definition.split("\t")[1].trim() + "\t"
							+ result.split("\t")[1].trim());
					break;
				}
			}
			counter++;
		}

		return map;
	}
}
