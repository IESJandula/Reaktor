package es.reaktor.reaktorclient.utils;

import org.apache.commons.cli.*;

import es.reaktor.models.Configuration;
import es.reaktor.reaktorclient.utils.exceptions.ConstantsErrors;


/**
 * @author David Martinez Flores
 */
public class ParametersParser
{
	/** Attribute isAdmin */
	private Option isAdmin = new Option(Constants.IS_ADMIN_PARAMETERS, "admin", true, "The admin commands");

	/** Attribute professorParameters */
	private Option professorParameters = new Option(Constants.PROFESSOR_PARAMETERS, "professor", true,
			"The professor parameters");

	/** Attribute descriptionParameters */
	private Option descriptionParameters = new Option(Constants.DESCRIPTION_PARAMETERS, "description", true,"the description parameters");

	/** Attribute classRoomParameters */
	private Option classRoomParameters = new Option(Constants.CLASSROOM_PARAMETERS, "classroom", true,
			"the classroom parameters");

	/** Attribute helpParameter */
	private Option helpParameter = new Option(Constants.HELP_PARAMETERS, "help", false, "The show help parameter");

	/** Attribute commandLine*/
	private CommandLine commandLine;

	/**
	 * Method parse
	 * 
	 * @param cmdArgs
	 * @throws IllegalArgumentException
	 */
	public Configuration parse(String[] cmdArgs) throws IllegalArgumentException
	{
		// Create the options
		Options commandLineOptions = new Options();

		// Add to the command line options
		commandLineOptions.addOption(this.classRoomParameters);
		commandLineOptions.addOption(this.descriptionParameters);
		commandLineOptions.addOption(this.helpParameter);
		commandLineOptions.addOption(this.isAdmin);
		commandLineOptions.addOption(this.professorParameters);

		// Parse to commandLine
		try
		{
			this.commandLine = new DefaultParser().parse(commandLineOptions, cmdArgs);
		} catch (ParseException parseException)
		{
			String errorString = "Error parsing command line arguments";
			throw new IllegalArgumentException(errorString, parseException);
			
		} catch (IllegalArgumentException parseException)
		{
			
			throw new IllegalArgumentException(ConstantsErrors.ERROR_ARGUMENTS_NOT_FOUND);
		}

		// Getting parameter values
		String classRoomParameters = this.getCmdStringOption(this.classRoomParameters);
		String descriptionParameters = this.getCmdStringOption(this.descriptionParameters);
		String isAdmin = this.getCmdStringOption(this.isAdmin);
		String professorParameters = this.getCmdStringOption(this.professorParameters);
		boolean helpParam = this.commandLine.hasOption(this.helpParameter.getOpt()) || this.commandLine.hasOption(this.helpParameter.getLongOpt());

		Configuration configuration = new Configuration();
		
		// If the option is not null or is not empty, that have anything

		//Checking classRoomParameters
		this.checkingClassRoom(classRoomParameters,configuration);

		// Checking descriptionParameters
		this.checkingDescription(descriptionParameters,configuration);

		// Checking isAdmin
		this.checkingAdmin(isAdmin,configuration);

		// Checking professorParameters
		this.checkingProfessor(professorParameters,configuration);

		// Checking Help parameter
		this.checkingHelp(helpParam);
		
		System.out.println(configuration);
		
		return configuration;
	}

	/**
	 * Method checkingHelp
	 * @param helpParam
	 */
	private void checkingHelp(boolean helpParam)
	{
		if (helpParam)
		{
			// Set the parameter
			System.out.println("IMPRIMIR AYUDA ");
	        System.out.println(Constants.HELP_COMMAND_OUTPUT);
	        System.exit(0);
		} 

	}

	/**
	 * Method checkingProfessor
	 * @param professorParameters
	 */
	private void checkingProfessor(String professorParameters,Configuration configuration)
	{
		if (professorParameters != null && !professorParameters.isEmpty())
		{
			// Set the parameter

			// smartPhone.setSerialNumber(Integer.valueOf(this.getCmdStringOption(this.serialNumberOption)));
			System.out.println(this.getMultiWordCmdOption(this.professorParameters));
			configuration.setProfessor(professorParameters);
		} 
	}

	/**
	 * Method checkingAdmin
	 * @param isAdmin
	 */
	private void checkingAdmin(String isAdmin,Configuration configuration)
	{
		if (isAdmin != null && !isAdmin.isEmpty())
		{
			// Set the parameter
			System.out.println(this.getMultiWordCmdOption(this.isAdmin));
			configuration.setIsAdmin(Boolean.parseBoolean(isAdmin));
		} 
	}

	/**
	 * Method checkingDescription
	 * @param descriptionParameters
	 */
	private void checkingDescription(String descriptionParameters,Configuration configuration)
	{
		if (descriptionParameters != null && !descriptionParameters.isEmpty())
		{
			// Set the parameter
			System.out.println(this.getMultiWordCmdOption(this.descriptionParameters));
			configuration.setDescription(descriptionParameters);
		}
	}

	/**
	 * Method checkingClassRoom
	 * @param classRoomParameters
	 */
	private void checkingClassRoom(String classRoomParameters,Configuration configuration)
	{
		if (classRoomParameters != null && !classRoomParameters.isEmpty())
		{
			// Set the parameter
			System.out.println(this.getMultiWordCmdOption(this.classRoomParameters));
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
	private String getMultiWordCmdOption(final Option option)
	{
		String outcome = null;

		String[] optionValues = this.commandLine.getOptionValues(option.getOpt());

		if (optionValues != null && optionValues.length > 0)
		{
			// Join the values into a single string
			outcome = String.join(" ", optionValues);
		}

		return outcome;
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
		} else if (this.commandLine.hasOption(option.getLongOpt()))
		{
			outcome = this.commandLine.getOptionValue(option.getLongOpt()).trim();
		}

		return outcome;
	}
}
