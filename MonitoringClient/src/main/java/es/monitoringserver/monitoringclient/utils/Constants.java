package es.monitoringserver.monitoringclient.utils;

import java.util.UUID;

/**
 * - CLASS -
 * In this class we storage the variables Constants 
 */
public final class Constants
{
    /**
     * - COMMONS CONSTANTS -
     * This variable is used to store the commons constants of the application
     */
    public static final String EMPTY_STRING /*...................*/ = "";
    public static final String UNKNOWN /*........................*/ = "Unknown\\"+ UUID.randomUUID();

    /**
     * - OUT PUT COMMAND -
     * This variable is used to store the output command in console
     */
    public static final String OUTPUT_MODEL_CONSOLE /*...........*/ = "Product";
    public static final String OUTPUT_GRAPHIC_CARD_ID_CONSOLE /*.*/ = "PNPDeviceID";
    public static final String OUTPUT_ID_SOUND_CARD_CONSOLE /*...*/ = "DeviceID";
    public static final String OUTPUT_RAM_ID_CONSOLE /*..........*/ = "BankLabel  PartNumber";

    /**
     * - OPERATING SYSTEM -
     * This variable is used to store the operating system of the application
     */
    public static final String OS_WINDOWS /*.....................*/ = "Windows";
    public static final String OS_LINUX /*.......................*/ = "Linux";

    /**
     *  UPDATED FOR APACHE CLI-VERSION IF YOU DONT USE APACHE CLI-VERSION put "-" on all paramters ("-h","-a"...)
     * - OPTIONS -
     * This variable is used to store the options of the application
     */
    public static final String HELP_PARAMETERS /*................*/ = "h";
    public static final String CLASSROOM_PARAMETERS /*...........*/ = "a";
    public static final String TROLLEY_PARAMETERS /*...........*/ = "t";
    public static final String ANDALUCIA_ID_PARAMETERS /*...........*/ = "aid";
    public static final String COMPUTER_NUMBER_PARAMETERS /*...........*/ = "cn";
    public static final String COMPUTER_SERIAL_NUMBER_PARAMETERS /*...........*/ = "sn";
    public static final String PROFESSOR_PARAMETERS /*...........*/ = "p";
    public static final String IS_ADMIN_PARAMETERS /*............*/ = "ad";
    
    /**
     *  USED ON THE APACHE COMMONS-CLI VERSION ONLY
     * - OPTIONS -
     *  The long version for the options 
     */
    public static final String HELP_PARAMETERS_LONG /*................*/ = "help";
    public static final String CLASSROOM_PARAMETERS_LONG /*...........*/ = "classroom";
    public static final String TROLLEY_PARAMETERS_LONG /*...........*/ = "trolley";
    public static final String ANDALUCIA_ID_PARAMETERS_LONG /*...........*/ = "andalucia";
    public static final String COMPUTER_NUMBER_PARAMETERS_LONG /*...........*/ = "computer";
    public static final String COMPUTER_SERIAL_NUMBER_PARAMETERS_LONG /*...........*/ = "serial";
    public static final String PROFESSOR_PARAMETERS_LONG /*...........*/ = "professor";
    public static final String IS_ADMIN_PARAMETERS_LONG /*............*/ = "admin";

    /**
     * - PATH FILES -
     * This variable is used to store the PATH of the Files in the application
     */
    public static final String PATH_CONFIG_FILE /*...............*/ = "./config.json";
    public static final String PATH_MALWARE_FILE /*..............*/ = "./malware.ps1";
    
    /**
     * - ARRAY -
     * This array is used to store the allowed arguments of the application
     */
    public static final String[] ALLOWED_ARGUMENTS  = {
        HELP_PARAMETERS, //         -h
        CLASSROOM_PARAMETERS, //    -a
        //DESCRIPTION_PARAMETERS, //  -d
        PROFESSOR_PARAMETERS, //    -p
        IS_ADMIN_PARAMETERS //      -admin
    };

    /**
     * - OUT PUT HELP -
     * This variable is used to store the help of the application
     */
    public static final String HELP_COMMAND_OUTPUT  =
        """
            -h: Show this help, this parameter only can be used alone
            the parameters -a and -p are obligatory, at least one of them must be used
        
            -a: Enter the classroom parameter
        
            -p: Enter the professor parameter
            
            -admin: Enter the admin parameter
        
        
            the parameter -d is optional, it can be used with the other parameters
        
            -d: Enter the description location parameter
        
            
            Example: "java -jar ReaktorClient.jar -h" For Help In The Application
            Example: "java -jar ReaktorClient.jar -a 2.11" For Enter The Classroom Parameter
            Example: "java -jar ReaktorClient.jar -p Cayetano" For Enter The Professor Parameter -admin
            Example: "java -jar ReaktorClient.jar -a 2.11 -p Cayetano" For Enter The Classroom And Professor Parameter 
            Example: "java -jar ReaktorClient.jar -a 2.11 -p Cayetano -d Second row, right-hand side second computer from the centre"
            Example: "java -jar ReaktorClient.jar -admin true -a 2.11 -p Cayetano -d Second row, right-hand side second computer from the centre"
        """;

}
