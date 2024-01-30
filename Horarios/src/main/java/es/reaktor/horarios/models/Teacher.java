package es.reaktor.horarios.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author David Martinez
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teacher
{
	/** Attribute name*/
	private String name;
	
	/** Attribute lastName*/
	private String lastName;
	
	/** Attribute email*/
	private String email;
	
	/** Attribute telephoneNumber*/
	private String telephoneNumber;
	
	/** Attribute roles*/
	private List<Rol> roles;
}
