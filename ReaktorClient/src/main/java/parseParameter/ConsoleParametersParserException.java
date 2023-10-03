package parseParameter;
import org.apache.commons.cli.ParseException;
public class ConsoleParametersParserException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public ConsoleParametersParserException(String errorString){
	      super(errorString);
	      }

	public ConsoleParametersParserException(String errorString, ParseException parseException){
	    super(errorString, parseException);
	    }
}
