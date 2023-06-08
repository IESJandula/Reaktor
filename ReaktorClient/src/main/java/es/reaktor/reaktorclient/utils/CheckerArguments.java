package es.reaktor.reaktorclient.utils;

/**
 * - CLASS -
 * This class is used to check the arguments of the application
 */
public class CheckerArguments
{



    /**
     * - METHOD -
     * This method is used to check the arguments of the application
     * @param args Arguments of the application
     * @return boolean with the result of the check
     */
    public boolean checkArguments(String[] args)
    {
        // if the length is correct and the content is correct and the repetitions are correct and the obligations are correct
        boolean result = checkContent(args) && checkRepetitions(args) && checkObligations(args);

        if (args.length > 0)
        {
            // if the argument is -h and not contains more arguments
            if (args[0].equalsIgnoreCase(Constants.HELP_PARAMETERS) && args.length == 1)
            {
                this.actionHelp();
            }
        }

        return result;
    }


    /**
     * - METHOD -
     * This method is used to check the content of the arguments of the application
     * @param args Arguments of the application
     * @return if the content is correct
     */
    private boolean checkContent(String[] args)
    {
        // In case of not obtain arguemnts, pass
        if (args.length > 0)
        {
            // if the argument is -h and not contains more arguments
            if (args[0].equalsIgnoreCase(Constants.HELP_PARAMETERS) && args.length == 1)
            {
                return true;
            }
            // In case of not contains -h in the first arguments check the rest of the arguments that are not -h
            else
            {
                for (String arg : args)
                {
                    if (arg.equalsIgnoreCase(Constants.HELP_PARAMETERS))
                    {
                        return false;
                    }
                }
            }

            for (String arg : args)
            {
                // if the argument is not -a or -p or -d or -admin
                if (!arg.equalsIgnoreCase(Constants.CLASSROOM_PARAMETERS) && !arg.equalsIgnoreCase(Constants.PROFESSOR_PARAMETERS) && !arg.equalsIgnoreCase(Constants.DESCRIPTION_PARAMETERS) && !arg.equalsIgnoreCase(Constants.HELP_PARAMETERS) && !arg.equalsIgnoreCase(Constants.IS_ADMIN_PARAMETERS))
                {
                    // if the argument is not a number
                    if (!arg.matches("[0-9]+"))
                    {
                        // if the argument is not a letter
                        if (!arg.matches("[a-zA-Z]+"))
                        {
                            // if the argument is not a space
                            if (arg.matches(" "))
                            {
                                return false;
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

    /**
     * - METHOD -
     * This method is used to check the repetitions of the arguments of the application
     * @param args Arguments of the application
     * @return if the repetitions are correct
     */
    private boolean checkRepetitions(String[] args)
    {
        // initialize the number of repetitions to 0
        int numberOfRepetitions = 0;

        // for each letter of the allowed arguments
        for (String letter : Constants.ALLOWED_ARGUMENTS)
        {
            // for each argument
            for (String arg : args)
            {
                // if the letter is equal to the argument
                if (letter.equalsIgnoreCase(arg))
                {
                    numberOfRepetitions++;
                }
            }

            // if the number of repetitions is more than 1 return false
            if (numberOfRepetitions > 1)
            {
                return false;
            }

            numberOfRepetitions = 0;
        }

        // if the number of repetitions is 0 or 1 return true
        return true;
    }

    /**
     * - METHOD -
     * This method is used to check the obligations of the arguments of the application
     * @param args Arguments of the application
     * @return if the obligations are correct
     */
    private boolean checkObligations(String[] args)
    {
        // if the length is more than 0
        if (args.length > 0)
        {
            // if the first argument is not -a or -p or -h return false
            if (!args[0].equalsIgnoreCase(Constants.CLASSROOM_PARAMETERS) && !args[0].equalsIgnoreCase(Constants.PROFESSOR_PARAMETERS) && !args[0].equalsIgnoreCase(Constants.HELP_PARAMETERS) && !args[0].equalsIgnoreCase(Constants.DESCRIPTION_PARAMETERS) && !args[0].equalsIgnoreCase(Constants.IS_ADMIN_PARAMETERS))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * This method is used to print the help of the command line
     */
    public void actionHelp()
    {
        System.out.println(Constants.HELP_COMMAND_OUTPUT);
        System.exit(0);
    }
}
