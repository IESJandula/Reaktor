package es.reaktor.models;

/**
 * - CLASS -
 * This class is used to create the configuration of the classroom
 */
public class Configuration
{

    /**
     * - ATTRIBUTES -
     * This attributes are used to create the configuration of the classroom
     */
    private String classroom;

    /**
     * - ATTRIBUTES -
     * This attributes are used to create the configuration of the professor
     */
    private String professor;

    /**
     * - ATTRIBUTES -
     * This attributes are used to create the configuration of the description
     */
    private String description;

    /**
     * - ATTRIBUTES -
     * This attributes are used to create the configuration of the admin
     */
    private Boolean isAdmin;

    /**
     * - CONSTRUCTOR BY DEFAULT -
     * This constructor is used to create the configuration of the classroom by default
     */
    public Configuration()
    {
    }

    /**
     * - CONSTRUCTOR -
     * This constructor is used to create the configuration of the classroom
     * @param classroom Classroom of the configuration
     * @param professor Professor of the configuration
     * @param description Description of the configuration
     */
    public Configuration(String classroom, String professor, String description, Boolean isAdmin)
    {
        this.classroom = classroom;
        this.professor = professor;
        this.description = description;
        this.isAdmin = isAdmin;
    }

    /**
     *                      - GETTERS AND SETTERS -
     * This getters and setters are used to get and set the attributes of the configuration
     */

    /**
     * - METHOD GET -
     * This method is used to get the classroom of the configuration
     * @return Classroom of the configuration
     */
    public String getClassroom()
    {
        return this.classroom;
    }

    /**
     * - METHOD SET -
     * This method is used to set the classroom of the configuration
     * @param classroom Classroom of the configuration
     */
    public void setClassroom(String classroom)
    {
        this.classroom = classroom;
    }

    /**
     * - METHOD GET -
     * This method is used to get the professor of the configuration
     * @return Professor of the configuration
     */
    public String getProfessor()
    {
        return this.professor;
    }

    /**
     * - METHOD SET -
     * This method is used to set the professor of the configuration
     * @param professor Professor of the configuration
     */
    public void setProfessor(String professor)
    {
        this.professor = professor;
    }

    /**
     * - METHOD GET -
     * This method is used to get the description of the configuration
     * @return Description of the configuration
     */
    public String getDescription()
    {
        return this.description;
    }

    /**
     * - METHOD SET -
     * This method is used to set the description of the configuration
     * @param description Description of the configuration
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * - METHOD GET -
     * This method is used to get the admin of the configuration
     * @return
     */
    public Boolean getIsAdmin()
    {
        return isAdmin;
    }

    /**
     * - METHOD SET -
     * This method is used to set the admin of the configuration
     * @param iSadmin
     */
    public void setIsAdmin(Boolean iSadmin)
    {
        this.isAdmin = iSadmin;
    }
}
