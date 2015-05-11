package mvcController;

public class MvcException extends Exception {

	private static final long serialVersionUID = 1L;

	public MvcException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public MvcException(String arg0) {
		super(arg0);
	}
}
