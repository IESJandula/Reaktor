package es.reaktor.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Grupo
{
	private int num_int_gr;
	private String abreviatura;
	private String nombre;
}
