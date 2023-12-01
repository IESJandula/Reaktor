package es.reaktor.reaktor.exceptions;

import java.util.HashMap;
import java.util.Map;

public class ComputerError extends Exception
{
	private static final long serialVersionUID = 2430243519965740823L ;
    private int code ;

    private String message ;


    public ComputerError(int code, String message)
    {
        this.code    = code ;
        this.message = message ;
    }

    public Object getBodyExceptionMessage()
    {
        Map<String, Object> mapBodyException = new HashMap<>() ;
        mapBodyException.put("code", this.code) ;
        mapBodyException.put("message", this.message) ;

        return mapBodyException ;
    }
}
