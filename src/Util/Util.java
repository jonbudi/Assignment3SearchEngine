package Util;

import java.util.ArrayList;
import java.util.List;

public class Util {

	/** tokenizes String, omitting all stopwords **/
	public static List<String> tokenize(String s) {

		List<String> result = new ArrayList<String>();
		StringBuilder token = new StringBuilder();
		String tokenString;

		for (char c : s.toCharArray()) {
			if (c == -1) {
				if (token.length() != 0) {
					tokenString = token.toString().toLowerCase();	
					result.add(tokenString);
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
					result.add(tokenString);
					// clear token object
					token.delete(0, token.length());
				}
			}
		}
		return result;
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