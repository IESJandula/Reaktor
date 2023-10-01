package es.reaktor.reaktorclient.utils;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import es.reaktor.models.Configuration;
import es.reaktor.reaktorclient.utils.exceptions.ConstantsErrors;

/**
 * @author David Martinez Flores 
 * This class is used for parse arguments and check its
 */
public class ParametersParser
{
	/** Attribute isAdmin with the admin value */
	private Option isAdmin;

	/** Attribute professorParameters with the professor parameters value */
	private Option professorParameters;

	/**
	 * Attribute descriptionParameters with the description parameters argument value
	 */
	private Option descriptionParameters;

	/** Attribute classRoomParameters with the classroom parameters value */
	private Option classRoomParameters;

	/** Attribute helpParameter for display help */
	private Option helpParameter;

	/** Attribute commandLine with all Options for parse */
	private CommandLine commandLine;

	
	/**
	 * Constructor for create new ParametersParser
	 * @param isAdmin
	 * @param professorParameters
	 * @param descriptionParameters
	 * @param classRoomParameters
	 * @param helpParameter
	 * @param commandLine
	 */
	public ParametersParser()
	{
		this.isAdmin = new Option(Constants.IS_ADMIN_PARAMETERS,Constants.IS_ADMIN_PARAMETERS_LONG, true, "The admin commands");
		this.professorParameters =  new Option(Constants.PROFESSOR_PARAMETERS, Constants.PROFESSOR_PARAMETERS_LONG,true, "The professor parameters");
		this.descriptionParameters = new Option(Constants.DESCRIPTION_PARAMETERS,Constants.DESCRIPTION_PARAMETERS_LONG, true, "the description parameters");
		this.classRoomParameters = new Option(Constants.CLASSROOM_PARAMETERS, Constants.CLASSROOM_PARAMETERS_LONG,true, "the classroom parameters");
		this.helpParameter = new Option(Constants.HELP_PARAMETERS, Constants.HELP_PARAMETERS_LONG, false,"The show help parameter");
		this.commandLine = null;
	}

	/**
	 * Method parse that method parse all the arguments
	 * 
	 * @param cmdArgs the arguments String array
	 * @throws IllegalArgumentException exepction for error on parsing
	 */
	public Configuration parse(String[] cmdArgs) throws IllegalArgumentException
	{
		// Create the all Options
		Options allOptions = new Options();

		// Add to the Options
		allOptions.addOption(this.classRoomParameters);
		allOptions.addOption(this.descriptionParameters);
		allOptions.addOption(this.helpParameter);
		allOptions.addOption(this.isAdmin);
		allOptions.addOption(this.professorParameters);

		// Parse to commandLine
		try
		{
			this.commandLine = new DefaultParser().parse(allOptions, cmdArgs);
		} 
		catch (ParseException parseException)
		{
			// Using StringBuilder for String exception msg
			StringBuilder fullError = new StringBuilder();
			fullError.append("Error parsing command line arguments");
			fullError.append(" or ");
			fullError.append(ConstantsErrors.ERROR_ARGUMENTS_NOT_FOUND);

			// trhowing the exception IllegalArgumentException if exist any error with arguments
			throw new IllegalArgumentException(fullError.toString(), parseException);
		}

		// Getting parameter values on Strings values
		String classRoomParametersString = this.getCmdStringOption(this.classRoomParameters);
		String descriptionParametersString = this.getCmdStringOption(this.descriptionParameters);
		String isAdminString = this.getCmdStringOption(this.isAdmin);
		String professorParametersString = this.getCmdStringOption(this.professorParameters);
		boolean helpParam = this.commandLine.hasOption(this.helpParameter.getOpt())|| this.commandLine.hasOption(this.helpParameter.getLongOpt());

		Configuration configuration = new Configuration();

		// If the option is not null or is not empty, that have anything

		// Checking classRoomParameters
		this.checkingClassRoom(classRoomParametersString, configuration);

		// Checking descriptionParameters
		this.checkingDescription(descriptionParametersString, configuration);

		// Checking isAdmin
		this.checkingAdmin(isAdminString, configuration);

		// Checking professorParameters
		this.checkingProfessor(professorParametersString, configuration);

		// Checking Help parameter
		this.checkingHelp(helpParam);

		return configuration;
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
	 * Method checkingProfessor for checking the argument
	 * 
	 * @param professorParameters with the String value
	 * @param configuration the configuration for reaktor
	 */
	private void checkingProfessor(String professorParameters, Configuration configuration)
	{
		if (professorParameters != null && !professorParameters.isEmpty())
		{
			// Set the parameter
			configuration.setProfessor(professorParameters);
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
	 * @param descriptionParameters with the String value
	 * @param configuration the configuration for reaktor
	 */
	private void checkingDescription(String descriptionParameters, Configuration configuration)
	{
		if (descriptionParameters != null && !descriptionParameters.isEmpty())
		{
			// Set the parameter
			configuration.setDescription(descriptionParameters);
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
