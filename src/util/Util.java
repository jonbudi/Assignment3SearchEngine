package util;

import domain.FileDumpObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class Util {

	private static final String STOPWORDSPATH = "Stopwords.txt";
	private static HashSet<String> stopwords = new HashSet<String>();

	private static final int MAXWORDSIZE = 64;

	/** get list of all files in path **/
	public static List<File> getFilesInPath(String path) {
		List<File> files = new ArrayList<File>();
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; ++i) {
			if (listOfFiles[i].isFile()) {
				files.add(listOfFiles[i]);
			} else if (listOfFiles[i].isDirectory()) {
				files.addAll(getFilesInPath(listOfFiles[i].getAbsolutePath()));
			}
		}

		return files;
	}

	/** read file into String **/
	public static String readFile(File file) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
		return new String(encoded, StandardCharsets.UTF_8);
	}

	/** tokenizes FileDumpObject, omitting all stopwords **/
	public static List<String> tokenizeFileDumpObject(FileDumpObject fdo) {
		if (fdo.isValid()) {
			return tokenize(fdo.getText());
		}
		return new ArrayList<String>();
	}

	/** tokenizes String, omitting all stopwords **/
	public static List<String> tokenize(String s) {
		initStopWords(STOPWORDSPATH);

		List<String> result = new ArrayList<String>();
		StringBuilder token = new StringBuilder();
		String tokenString;

		for (char c : s.toCharArray()) {
			if (c == -1) {
				if (token.length() != 0) {
					tokenString = token.toString().toLowerCase();
					if (!stopwords.contains(tokenString) && tokenString.length() <= MAXWORDSIZE) {
						result.add(tokenString);
					}
				}
				break; // EOF
			}
			if (isValidChar(c)) {
				// append c to end of accumulated token
				token.append(c);
			} else {
				if (token.length() != 0) {
					// add accumulated token to list
					tokenString = token.toString().toLowerCase();
					if (!stopwords.contains(tokenString) && tokenString.length() <= MAXWORDSIZE) {
						result.add(tokenString);
					}
					// clear token object
					token.delete(0, token.length());
				}
			}
		}
		return result;
	}

	/** init global set of stop words **/
	public static void initStopWords(String path) {
		if (!stopwords.isEmpty()) {
			return;
		}
		BufferedReader br = null;

		try {
			String currentLine;
			br = new BufferedReader(new FileReader(path));
			while ((currentLine = br.readLine()) != null) {
				stopwords.add(currentLine.trim());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/** convert File to FileDumpObject **/
	public static FileDumpObject fileToFDO(File file) throws JSONException, IOException {
		return new FileDumpObject(new JSONObject(Util.readFile(file)));
	}

	/**
	 * Determines if char c is valid char in token.
	 * 
	 * @param c Char to examine.
	 * @return Validity of c.
	 */
	private static boolean isValidChar(int c) {
		return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9')
				|| (c == '\'');
	}

}