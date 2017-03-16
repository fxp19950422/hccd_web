package cn.sportsdata.webapp.youth.common.exceptions;


public class SoccerProException extends Exception {
	private static final long serialVersionUID = -1911035174172590150L;

	public SoccerProException() {}

    public SoccerProException(String message) {
        super(message);
    }

    public SoccerProException(String message, Throwable cause) {
        super(message, cause);
    }

    public SoccerProException(Throwable cause) {
        super(cause);
    }
}
