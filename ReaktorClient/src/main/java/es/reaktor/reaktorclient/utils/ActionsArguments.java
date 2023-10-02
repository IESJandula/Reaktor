package es.reaktor.reaktorclient.utils;

import es.reaktor.models.Configuration;
import es.reaktor.reaktorclient.utils.exceptions.ReaktorClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * Method writeConfiguration
     * @param args
     * @throws ReaktorClientException
     */
    public void writeConfiguration(String[] args) throws ReaktorClientException
    {

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
			throw new IllegalArgumentException(excep.toString());
			
		}
		
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
}
