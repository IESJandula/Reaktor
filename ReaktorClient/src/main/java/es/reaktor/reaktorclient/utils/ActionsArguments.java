package es.reaktor.reaktorclient.utils;

import es.reaktor.models.Configuration;
import es.reaktor.reaktorclient.utils.exceptions.ConstantsErrors;
import es.reaktor.reaktorclient.utils.exceptions.ReaktorClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * - CLASS -
 * This class is used to execute the action of the arguments
 */
@Service
public class ActionsArguments
{
    /** Attribute writeFiles*/
    @Autowired
    private WriteFiles writeFiles;

    /**
     * This method is used to execute the action of the arguments
     *
     * @param args Arguments of the command line
     */
    @Deprecated
    //THIS METHOD IS USED ON LAST VERSION WITHOUT APACHE COMMONS-CLI
    public void actionArguments(String[] args) throws ReaktorClientException
    {
        // If the first argument is "-a" or "-p" we execute the action
        writeConfiguration(args);
    }

    /**
     * Method writeConfiguration
     * @param args
     * @throws ReaktorClientException
     */
    public void writeConfiguration(String[] args) throws ReaktorClientException
    {
    	//THIS MAP IS USED BY THE LAST VERSION FROM REAKTOR WITHOUT APACHE COMMONS-CLI
    	//obtainsArguments() METHOD IS NOW DEPRECATED
        //Map<String, String> argumentsContent = obtainsArguments(args);

    	//Creation new configuration
    	Configuration configuration = new Configuration();
		try
		{	//Getting the configuration values from ParametersParser
			//Calling to ParametersParser for check arguments
			configuration=new ParametersParser().parse(args);
		} 
		catch (IllegalArgumentException excep)
		{
			//Exception if exist any error on argumetns
			excep.printStackTrace();
			throw new IllegalArgumentException(excep.toString());
			
		}
		
		//THAT IS THE ONLY WAY TO CREATE THE CONFIGURATION OBJECT ON THE LAST VERSION OF REAKTOR WITHOUT APACHE COMMONS-CLI
        //Configuration configuration = createConfiguration(argumentsContent);
		
		//Checking the configuration attribue values
        this.checkConfiguration(configuration);

        //Getting the configuration values and transform to JSON
        this.writeFiles.escribirResultadoJson(configuration);
    }

    /**
     * Method checkConfiguration
     * @param configuration
     */
    public void checkConfiguration(Configuration configuration)
    {
    	//If any attribute of configuration is null or empty string ("") , set "Unknow" string value.
    	
        if (configuration.getClassroom() == null || configuration.getClassroom().isEmpty())
        {
            configuration.setClassroom(Constants.UNKNOWN);
        }

        if (configuration.getProfessor() == null || configuration.getProfessor().isEmpty())
        {
            configuration.setProfessor(Constants.UNKNOWN);
        }

        if (configuration.getDescription() == null || configuration.getDescription().isEmpty())
        {
            configuration.setDescription(Constants.UNKNOWN);
        }

        if (configuration.getIsAdmin() == null)
        {
            configuration.setIsAdmin(false);
        }
    }

    /**
     * This method is used to obtain the arguments and the content of the arguments
     *
     * @param args Arguments of the command line
     * @return Map with the arguments and the content of the arguments
     */
    @Deprecated
    private Map<String, String> obtainsArguments(String[] args)
    {

        // Convert the array of arguments to a list
        List<String> argsList = List.of(args);

        // String: Letter, Integer: Index of the option
        Map<String, Integer> argsIndex = new HashMap<>();

        // String: Letter, String: Content of the option
        Map<String, String> argsAllContent;

        // Extract the arguments and the indices of the arguments
        extractedArguments(argsList, argsIndex);

        // Create a list of the entry map
        List<Map.Entry<String, Integer>> entryArrayList = new ArrayList<>(argsIndex.entrySet());

        // Order the list by the value of the map
        entryArrayList.sort(Map.Entry.comparingByValue());

        // Check if the arguments content is correct
        checkerContentArguments(entryArrayList);

        // Extract the content of the arguments
        argsAllContent = extractedInformationOfArguments(argsList, entryArrayList);

        return argsAllContent;
    }

    /**
     * This method is used to check the content of the arguments
     *
     * @param argsList              List of arguments
     * @param entryOptionsArguments List of options arguments and indices
     */
    @Deprecated
    private Map<String, String> extractedInformationOfArguments(List<String> argsList, List<Map.Entry<String, Integer>> entryOptionsArguments)
    {

        Map<String, String> argsAllContent = new HashMap<>();

        List<String> content;
        // Si solo tiene un argumento
        if (entryOptionsArguments.size() == 1)
        {
            // Si el argumento es el primero, cogemos el contenido desde el siguiente indice hasta el final
            content = argsList.subList(entryOptionsArguments.get(0).getValue() + 1, argsList.size());

            // Almacenamos en el mapa el contenido del argumento
            argsAllContent.put(entryOptionsArguments.get(0).getKey(), String.join(" ", content));
        }
        // Si tiene mas de un argumento
        else if (entryOptionsArguments.size() > 1)
        {
            // Recorremos la lista de argumentos
            for (int i = 0; i < entryOptionsArguments.size() - 1; i++)
            {
                // Nos quedamos con el contenido del argumento
                content = argsList.subList(entryOptionsArguments.get(i).getValue() + 1, entryOptionsArguments.get(i + 1).getValue());

                // Almacenamos en el mapa el contenido del argumento con su propio argumento
                argsAllContent.put(entryOptionsArguments.get(i).getKey(), String.join(" ", content));
            }

            // Nos quedamos con el contenido del ultimo argumento ya que no tiene argumento siguiente
            content = argsList.subList(entryOptionsArguments.get(entryOptionsArguments.size() - 1).getValue() + 1, argsList.size());

            argsAllContent.put(entryOptionsArguments.get(entryOptionsArguments.size() - 1).getKey(), String.join(" ", content));
        }

        return argsAllContent;
    }


    /**
     * Method createConfiguration
     * @param argsAllContent
     * @return
     */
    @Deprecated
    private Configuration createConfiguration(Map<String, String> argsAllContent)
    {
        Configuration configuration = new Configuration();

        if (argsAllContent.containsKey(Constants.CLASSROOM_PARAMETERS))
        {
            configuration.setClassroom(argsAllContent.get(Constants.CLASSROOM_PARAMETERS));
        }

        if (argsAllContent.containsKey(Constants.PROFESSOR_PARAMETERS))
        {
            configuration.setProfessor(argsAllContent.get(Constants.PROFESSOR_PARAMETERS));
        }

        if (argsAllContent.containsKey(Constants.DESCRIPTION_PARAMETERS))
        {
            configuration.setDescription(argsAllContent.get(Constants.DESCRIPTION_PARAMETERS));
        }

        if (argsAllContent.containsKey(Constants.IS_ADMIN_PARAMETERS))
        {
            configuration.setIsAdmin(Boolean.parseBoolean(argsAllContent.get(Constants.IS_ADMIN_PARAMETERS)));
        }

        return configuration;
    }

    /**
     * This method is used to extract the arguments and the content of the arguments
     *
     * @param argsContent Map with the options arguments and the index of the arguments
     */
    @Deprecated
    private void checkerContentArguments(List<Map.Entry<String, Integer>> argsContent)
    {
        // loop to check if the arguments content is empty
        for (int i = 0; i < argsContent.size() - 1; i++)
        {
            // if the index current argument options, is equal to the next index argument options
            if (argsContent.get(i).getValue() + 1 == argsContent.get(i + 1).getValue())
            {
                throw new IllegalArgumentException(ConstantsErrors.ERROR_ARGUMENTS_NOT_FOUND);
            }
        }
    }

    /**
     * This method is used to extract the arguments and the index of the arguments
     *
     * @param arguments  Arguments of the command line
     * @param argsIndice Map for storage the arguments and the index of the arguments
     */
    @Deprecated
    private static void extractedArguments(List<String> arguments, Map<String, Integer> argsIndice)
    {
        // if contains the argument "-a"
        if (arguments.contains(Constants.CLASSROOM_PARAMETERS))
        {
            // we add the argument and the index of the argument
            argsIndice.put(arguments.get(arguments.indexOf(Constants.CLASSROOM_PARAMETERS)), arguments.indexOf(Constants.CLASSROOM_PARAMETERS));
        }
        // if contains the argument "-p"
        if (arguments.contains(Constants.PROFESSOR_PARAMETERS))
        {
            // we add the argument and the index of the argument
            argsIndice.put(arguments.get(arguments.indexOf(Constants.PROFESSOR_PARAMETERS)), arguments.indexOf(Constants.PROFESSOR_PARAMETERS));
        }
        // if contains the argument "-d"
        if (arguments.contains(Constants.DESCRIPTION_PARAMETERS))
        {
            // we add the argument and the index of the argument
            argsIndice.put(arguments.get(arguments.indexOf(Constants.DESCRIPTION_PARAMETERS)), arguments.indexOf(Constants.DESCRIPTION_PARAMETERS));
        }

        // if contains the argument "-admin"
        if (arguments.contains(Constants.IS_ADMIN_PARAMETERS))
        {
            // we add the argument and the index of the argument
            argsIndice.put(arguments.get(arguments.indexOf(Constants.IS_ADMIN_PARAMETERS)), arguments.indexOf(Constants.IS_ADMIN_PARAMETERS));
        }
    }
}
