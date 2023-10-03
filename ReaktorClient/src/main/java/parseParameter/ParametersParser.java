package parseParameter;

import org.apache.commons.cli.CommandLine;


import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import es.reaktor.models.Configuration;


public class ParametersParser {

	
	private static final Logger LOGGER = LogManager.getLogger() ;

	/**
	 * Input parameter option - classroom 
	 */
	private final Option classroomOption  = new Option("a", "classroom", true, "This is the number of the class") ;

	/**
     * Input parameter option - profesor
     */
    private final Option profesorOption = new Option("p", "profesor", true, "This is the name of the profesor") ;
    
    /**
     * Input parameter option - description
     */
    private final Option descriptionOption   = new Option("d", "description", true, "This is the description") ;
    
    /**
     * The command line with all the values parsed
     */
    private CommandLine commandLine ;

    /**
     * Parse the console parameters
     *
     * @param cmdArgs with the command line arguments
     * @throws ConsoleParametersParserException exception thrown if there is any problem
     */
    public Configuration parse(final String[] cmdArgs) throws ConsoleParametersParserException
    {
        Configuration configuration=new Configuration();

        // Parse the command line arguments
        this.parseAndValidateCommandLine(cmdArgs) ;

        // Apply the common parameters to the parsed information
        this.applyCommonParameters(configuration) ;
        
        // Logging it
        ParametersParser.LOGGER.info("Reaktor information: {}", configuration) ;
        
        return configuration;
       
    }

    /**
     * Add the command line options including the specific parser ones, parse the command line and validate the found options
     *
     * @param args command line arguments
     * @throws ConsoleParametersParserException exception thrown if there is a problem
     */
    private void parseAndValidateCommandLine(final String[] args) throws ConsoleParametersParserException
    {
    	ParametersParser.LOGGER.info("Parsing command line arguments: {}", (Object) args);

        // Create the options
        final Options commandLineOptions = new Options() ;

        // Add the common command line options
        this.addCommonCommandLineOptions(commandLineOptions) ;

        // Parse the command line
        final CommandLineParser commandLineParser = new DefaultParser() ;

        try
        {
            this.commandLine = commandLineParser.parse(commandLineOptions, args) ;
        }
        catch (ParseException parseException)
        {
            String errorString = "Error parsing command line arguments" ;

            ParametersParser.LOGGER.error(errorString, parseException) ;
            throw new ConsoleParametersParserException(errorString, parseException) ;
        }

        // Verify the command line arguments, both the common and the additional ones
        this.validateCommandLineArguments() ;
    }

    /**
     * Add the common command line options to the options list, it will be used to parse the command line later
     *
     * @param options with the options object to add the common options to
     */
    private void addCommonCommandLineOptions(final Options options)
    {
        options.addOption(this.classroomOption) ;
        options.addOption(this.profesorOption) ;
        options.addOption(this.descriptionOption) ;
    }

    /**
     * Validate the input command line arguments parsed from the command line
     *
     * @throws ConsoleParametersParserException exception thrown if the validation is not correct
     */
    private void validateCommandLineArguments() throws ConsoleParametersParserException
    {
        final String classroom 	  = this.getCmdStringOption(this.classroomOption) ;
        final String profesor = this.getCmdStringOption(this.profesorOption) ;
        final String description = this.getCmdStringOption(this.descriptionOption) ;

        String errorString = null ;
        
        if (classroom == null || classroom.isEmpty())
        {
            errorString = "classroom identificator is null or empty" ;
        }
        else if (profesor == null || profesor.isEmpty())
        {
            errorString = "profesor is null or empty" ;
        }
        else if (description == null || description.isEmpty())
        {
            errorString = "description is null or empty" ;
        }
        if (errorString != null)
        {
        	ParametersParser.LOGGER.error(errorString) ;
            throw new ConsoleParametersParserException(errorString);
        }
    }
    
    /**
     * Applies the common parameters of the command line
     *
     * @param smartphone with the smartphone model
     * @throws ConsoleParametersParserException exception thrown if there is any problem
     */
    private void applyCommonParameters( Configuration configuration) throws ConsoleParametersParserException
    {
        final String classroom     = this.getMultiWordCmdOption(this.classroomOption) ;
        final String description   = this.getMultiWordCmdOption(this.descriptionOption);
        final String profesor = this.getMultiWordCmdOption(this.profesorOption);

        // Set the parameters
        configuration.setClassroom(classroom) ;
        configuration.setDescription(description) ;
        configuration.setProfessor(profesor) ;
    }
    
    /**
     * Return the String option value from the command line given the representing option
     *
     * @param option with the representing option of the command line
     * @return the value of the option, null if unsettled
     */
    private String getMultiWordCmdOption(final Option option)
    {
    	String outcome = null ;
    	
        String[] optionValues = this.commandLine.getOptionValues(option.getOpt()) ;

        if (optionValues != null && optionValues.length > 0)
        {
            // Join the values into a single string
        	outcome = String.join(" ", optionValues) ;
        }

        return outcome ;
    }


    /**
     * Return the String option value from the command line given the representing option
     *
     * @param option with the representing option of the command line
     * @return the value of the option, null if unsettled
     */
    private String getCmdStringOption(final Option option)
    {
    	String outcome = null ;
    	
        if (this.commandLine.hasOption(option.getOpt()))
        {
        	outcome = this.commandLine.getOptionValue(option.getOpt()).trim() ;
        }
        else if (this.commandLine.hasOption(option.getLongOpt()))
        {
        	outcome = this.commandLine.getOptionValue(option.getLongOpt()).trim() ;
        }

        return outcome ;
    }
}
