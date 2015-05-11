package mvcController;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HttpRequestHandler {
	public void handle(HttpServletRequest request, HttpServletResponse response)
			throws ParseException, ServletException, IOException,
			NumberFormatException;
}
