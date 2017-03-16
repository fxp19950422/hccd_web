package cn.sportsdata.webapp.youth.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class SoccerProRuntimeException extends RuntimeException {
	private static final long serialVersionUID = -1911035174172590149L;

	public SoccerProRuntimeException() {}

    public SoccerProRuntimeException(String message) {
        super(message);
    }

    public SoccerProRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public SoccerProRuntimeException(Throwable cause) {
        super(cause);
    }
}
