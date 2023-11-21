package es.reaktor.reaktorclient.utils;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import es.reaktor.models.Configuration;
import es.reaktor.reaktorclient.utils.exceptions.ConstantsErrors;
import es.reaktor.reaktorclient.utils.exceptions.ParametersParserException;
import lombok.extern.slf4j.Slf4j;
/**
 * @author David Martinez Flores 
 * This class is used for parse arguments and check its
 */
@Slf4j
public class ParametersParser
{
	/** Attribute isAdmin with the admin value */
	private Option isAdmin;

	/** Attribute teacherParameters with the professor parameters value */
	private Option teacherParameters;

	/**
	 * Attribute descriptionParameters with the description parameters argument value
	 */
	private Option trolleyParameters;
	
	/** Attribute andaluciaIdParameters with the andaluciaId value*/
	private Option andaluciaIdParameters;
	
	/** Attribute computerNumberParameters with the computer Number value*/
	private Option computerNumberParameters;

	/** Attribute classRoomParameters with the classroom parameters value */
	private Option classRoomParameters;

	/** Attribute helpParameter for display help */
	private Option helpParameter;

	/** Attribute commandLine with all Options for parse */
	private CommandLine commandLine;

	
	/**
	 * Constructor for create new ParametersParser
	 * @param isAdmin
	 * @param teacherParameters
	 * @param trolleyParameters
	 * @param classRoomParameters
	 * @param helpParameter
	 * @param commandLine
	 */
	public ParametersParser()
	{
		this.isAdmin = new Option(Constants.IS_ADMIN_PARAMETERS,Constants.IS_ADMIN_PARAMETERS_LONG, true, "The admin commands");
		this.teacherParameters =  new Option(Constants.PROFESSOR_PARAMETERS, Constants.PROFESSOR_PARAMETERS_LONG,true, "The professor parameters");
		this.trolleyParameters = new Option(Constants.TROLLEY_PARAMETERS,Constants.TROLLEY_PARAMETERS_LONG, true, "the trolley parameters");
		this.andaluciaIdParameters = new Option(Constants.ANDALUCIA_ID_PARAMETERS,Constants.ANDALUCIA_ID_PARAMETERS_LONG, true, "the trolley parameters");
		this.computerNumberParameters = new Option(Constants.COMPUTER_NUMBER_PARAMETERS,Constants.COMPUTER_NUMBER_PARAMETERS_LONG, true, "the trolley parameters");
		this.classRoomParameters = new Option(Constants.CLASSROOM_PARAMETERS, Constants.CLASSROOM_PARAMETERS_LONG,true, "the classroom parameters");
		this.helpParameter = new Option(Constants.HELP_PARAMETERS, Constants.HELP_PARAMETERS_LONG, false,"The show help parameter");
		this.commandLine = null;
	}

	/**
	 * Method parse that method parse all the arguments
	 * 
	 * @param cmdArgs the arguments String array
	 * @throws ParametersParserException exepction for error on parsing
	 */
	public Configuration parse(String[] cmdArgs) throws ParametersParserException
	{
		// Create the all Options
		Options allOptions = new Options();

		// Add to the Options
		allOptions.addOption(this.classRoomParameters);
		allOptions.addOption(this.trolleyParameters);
		allOptions.addOption(this.andaluciaIdParameters);
		allOptions.addOption(this.computerNumberParameters);
		allOptions.addOption(this.helpParameter);
		allOptions.addOption(this.isAdmin);
		allOptions.addOption(this.teacherParameters);

		// Parse to commandLine
		try
		{
			this.commandLine = new DefaultParser().parse(allOptions, cmdArgs);
		} 
		catch (ParseException parseException)
		{
			String errorString = ConstantsErrors.ERROR_PARSING_ARGUMENTS+" or "+ConstantsErrors.ERROR_ARGUMENTS_NOT_FOUND;
			log.error(errorString);
			throw new ParametersParserException(errorString, parseException);
		}

		// Getting parameter values on Strings values
		String classRoomParametersString = this.getCmdStringOption(this.classRoomParameters);
		String trolleyParametersString = this.getCmdStringOption(this.trolleyParameters);
		String andaluciaIdParametersString = this.getCmdStringOption(this.andaluciaIdParameters);
		String computerNumberParametersString = this.getCmdStringOption(this.computerNumberParameters);
		String isAdminString = this.getCmdStringOption(this.isAdmin);
		String professorParametersString = this.getCmdStringOption(this.teacherParameters);
		boolean helpParam = this.commandLine.hasOption(this.helpParameter.getOpt())|| this.commandLine.hasOption(this.helpParameter.getLongOpt());

		Configuration configuration = new Configuration();

		// If the option is not null or is not empty, that have anything

		// Checking classRoomParameters
		this.checkingClassRoom(classRoomParametersString, configuration);

		// Checking descriptionParameters
		this.checkingTrolley(trolleyParametersString, configuration);
		
		// Checking andaluciaParameters
		this.checkingAndalucia(andaluciaIdParametersString, configuration);
				
		// Checking computerNumbers
		this.checkingComputerNumber(computerNumberParametersString, configuration);

		// Checking isAdmin
		this.checkingAdmin(isAdminString, configuration);

		// Checking teacherParameters
		this.checkingTeacher(professorParametersString, configuration);

		// Checking Help parameter
		this.checkingHelp(helpParam);

		return configuration;
	}

	private void checkingComputerNumber(String computerNumberParametersString, Configuration configuration)
	{
		if (computerNumberParametersString != null && !computerNumberParametersString.isEmpty())
		{
			// Set the parameter
			configuration.setComputerNumber(computerNumberParametersString);
		}
		
	}

	private void checkingAndalucia(String andaluciaIdParametersString, Configuration configuration)
	{
		if (andaluciaIdParametersString != null && !andaluciaIdParametersString.isEmpty())
		{
			// Set the parameter
			configuration.setAndaluciaId(andaluciaIdParametersString);
		}
		
	}

	/**
	 * Method checkingHelp for checking the argument
	 * 
	 * @param helpParam the parameter
	 */
	private void checkingHelp(boolean helpParam)
	{
		if (helpParam)
		{
			// Display the Help
			System.out.println(Constants.HELP_COMMAND_OUTPUT);
			//Exit
			System.exit(0);
		}

	}

	/**
	 * 
	 * Method checkingTeacher for checking the argument
	 * 
	 * @param teacherParameters with the String value
	 * @param configuration the configuration for reaktor
	 */
	private void checkingTeacher(String teacherParameters, Configuration configuration)
	{
		if (teacherParameters != null && !teacherParameters.isEmpty())
		{
			// Set the parameter
			configuration.setTeacher(teacherParameters);
		}
	}

	/**
	 * 
	 * Method checkingAdmin for checking the argument
	 * 
	 * @param isAdmin with the String value
	 * @param configuration the configuration for reaktor
	 */
	private void checkingAdmin(String isAdmin, Configuration configuration)
	{
		if (isAdmin != null && !isAdmin.isEmpty())
		{
			// Set the parameter
			configuration.setIsAdmin(Boolean.parseBoolean(isAdmin));
		}
	}

	/**
	 * 
	 * Method checkingDescription for checking the argument
	 * 
	 * @param trolleyParameter with the String value
	 * @param configuration the configuration for reaktor
	 */
	private void checkingTrolley(String trolleyParameter, Configuration configuration)
	{
		if (trolleyParameter != null && !trolleyParameter.isEmpty())
		{
			// Set the parameter
			configuration.setTrolley(trolleyParameter);
		}
	}

	/**
	 * 
	 * Method checkingClassRoom for checking the argument
	 * 
	 * @param classRoomParameters with the String value
	 * @param configuration the configuration for reaktor
	 */
	private void checkingClassRoom(String classRoomParameters, Configuration configuration)
	{
		if (classRoomParameters != null && !classRoomParameters.isEmpty())
		{
			// Set the parameter
			configuration.setClassroom(classRoomParameters);

		}
	}

	/**
	 * Return the String option value from the command line given the representing
	 * option
	 *
	 * @param option with the representing option of the command line
	 * @return the value of the option, null if unsettled
	 */
	private String getCmdStringOption(final Option option)
	{
		String outcome = null;

		if (this.commandLine.hasOption(option.getOpt()))
		{
			outcome = this.commandLine.getOptionValue(option.getOpt()).trim();
		} 
		else if (this.commandLine.hasOption(option.getLongOpt()))
		{
			outcome = this.commandLine.getOptionValue(option.getLongOpt()).trim();
		}

		return outcome;
	}
}
