package es.reaktor.reaktorclient.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.reaktor.models.Configuration;
import es.reaktor.reaktorclient.utils.exceptions.ConstantsErrors;
import es.reaktor.reaktorclient.utils.exceptions.ParametersParserException;
import es.reaktor.reaktorclient.utils.exceptions.ReaktorClientException;
import lombok.extern.slf4j.Slf4j;

/**
 * - CLASS -
 * This class is used to execute the action of the arguments
 */
@Service
@Slf4j
public class ActionsArguments
{
    /** Attribute writeFiles*/
    @Autowired
    private WriteFiles writeFiles;

    /**
     * Method writeConfiguration
     * @param args
     * @throws ReaktorClientException
     * @throws ParametersParserException
     */
    
    public void writeConfiguration(String[] args) throws ReaktorClientException,ParametersParserException
    {

    	//Creation new configuration
    	Configuration configuration = new Configuration();
		try
		{	//Getting the configuration values from ParametersParser
			//Calling to ParametersParser for check arguments
			configuration=new ParametersParser().parse(args);
		} 
		catch (ParametersParserException excep)
		{
			//Exception if exist any error on argumetns
			String errorString = ConstantsErrors.ERROR_PARSING_ARGUMENTS+" or "+ConstantsErrors.ERROR_ARGUMENTS_NOT_FOUND;
			log.error(errorString);
			throw new ParametersParserException(errorString,excep);
		}
		
		//Checking the configuration attribute values
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

        if (configuration.getTeacher() == null || configuration.getTeacher().isEmpty())
        {
            configuration.setTeacher(Constants.UNKNOWN);
        }

        if (configuration.getTrolley() == null || configuration.getTrolley().isEmpty())
        {
            configuration.setTrolley(Constants.UNKNOWN);
        }
        if (configuration.getAndaluciaId() == null || configuration.getAndaluciaId().isEmpty())
        {
            configuration.setAndaluciaId(Constants.UNKNOWN);
        }
        if (configuration.getComputerNumber() == null || configuration.getComputerNumber().isEmpty())
        {
            configuration.setComputerNumber(Constants.UNKNOWN);
        }

        if (configuration.getIsAdmin() == null)
        {
            configuration.setIsAdmin(false);
        }
    }
}
