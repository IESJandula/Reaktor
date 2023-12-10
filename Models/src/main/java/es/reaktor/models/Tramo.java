package es.reaktor.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tramo
{
	private int num_tr;
	private int numero_dia;
	private String hora_inicio;
	private String hora_final;
}