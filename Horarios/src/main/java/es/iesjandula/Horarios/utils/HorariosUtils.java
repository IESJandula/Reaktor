package es.iesjandula.Horarios.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ProcessHandle.Info;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import es.iesjandula.Horarios.exceptions.HorarioError;
import es.iesjandula.Horarios.models.Hour;
import es.iesjandula.Horarios.models.RolReaktor;
import es.iesjandula.Horarios.models.xml.Grupo;
import es.iesjandula.Horarios.models.xml.InfoCentro;
import es.iesjandula.Horarios.models.xml.Profesor;
import es.iesjandula.Horarios.models.xml.TramoHorario;
import es.iesjandula.Horarios.models.xml.horarios.Actividad;
import jakarta.servlet.http.HttpSession;

/**
 * @author Alejandro Cazalla Pérez
 */
public class HorariosUtils {
	final static Logger logger = LogManager.getLogger();
	
	/**
	 * Public constructor
	 */
	public HorariosUtils() 
	{
		
	}
	


	public List<Profesor> parseCsvFile(MultipartFile file, HttpSession session) throws HorarioError {
		List<Profesor> profesores = new ArrayList<Profesor>(
				((InfoCentro) session.getAttribute("info")).getDatos().getProfesores().values());

		try {
			Scanner sc = new Scanner(file.getResource().getContentAsString(Charset.defaultCharset()));

			sc.nextLine();
			while (sc.hasNextLine()) {
				String[] parts = sc.nextLine().split(";");

				String nombre = parts[1] + ", " + parts[0];

				Profesor profesor = this.getIdProfesor(profesores, nombre);

				if (profesor != null) {

					String cuentaDeCorreo = parts[2];
					List<RolReaktor> listaDeRoles = getRoles(parts[3]);

					profesor.setCuentaDeCorreo(cuentaDeCorreo);
					profesor.setListaRoles(listaDeRoles);
				}

			}
			sc.close();
		} catch (IOException e) {
			String message = "Corrupted file";
			logger.error(message, e);
			throw new HorarioError(1, message, e);
		}

		return profesores;
	}

	private Profesor getIdProfesor(List<Profesor> profesores, String nombre) {

		int cont = 0;
		Profesor profesor = null;
		while (cont < profesores.size() && profesor == null) {

			if (profesores.get(cont).getNombre().equals(nombre)) {
				profesor = profesores.get(cont);
				System.out.println(nombre);
			}
			cont++;

		}

		return profesor;
	}

	private List<RolReaktor> getRoles(String part) {
		List<RolReaktor> lista = new ArrayList<RolReaktor>();

		String[] roles = part.split(",");

		for (String string : roles) {
			switch (string) {
			case "docente":
				lista.add(RolReaktor.docente);
				break;
			case "administrador":
				lista.add(RolReaktor.administrador);
				break;
			case "conserje":
				lista.add(RolReaktor.conserje);
				break;
			default:
				break;
			}
		}

		return lista;
	}

	public int getDiaByString(String dia) {
		int n = -1;
		switch (dia) 
		{
			case "lunes" -> n = 1;
			case "martes" -> n = 2;
			case "miercoles" -> n = 3;
			case "jueves" -> n = 4;
			case "viernes" -> n = 5;
		}
		return n;

	}
	
	public String getDiaByInt(int dia) {
		String n = "";
		switch (dia) 
		{
			case  1 -> n = "lunes";
			case  2 -> n = "martes";
			case  3 -> n = "miercoles";
			case  4 -> n = "jueves";
			case  5 -> n = "viernes";
		}
		return n;
	}
	
	public TramoHorario getTramo(Hour hour, String dia, InfoCentro info) throws HorarioError
	{
		int n = getDiaByString(dia);
		if(n < 0) 
			throw new HorarioError(1, "el dia es incorrecto", null);
		
		for(TramoHorario tramo : info.getDatos().getTramos().values())
		{
			if(tramo.getDia() == n && tramo.getHoraInicio().equals(hour.getStart()) && tramo.getHoraFinal().equals(hour.getEnd())) 
			{
				return tramo;
			}
		}
		
		throw new HorarioError(2, "la hora es incorrecto", null);
	}
	
	public void getInfoPdfFromProfesor(Profesor profesor, InfoCentro info)
	{
		try 
		{
			Document document = new Document();
			
			document.setPageSize(PageSize.A4.rotate());
			
			
			PdfWriter.getInstance(document, new FileOutputStream(profesor.getNombre()+ "_Horario.pdf"));
			
			document.open();
			
			PdfPTable pdfTable = new PdfPTable(8);
			pdfTable.setWidthPercentage(100f);
			
			
			List<TramoHorario> tramos = new ArrayList<TramoHorario>(info.getDatos().getTramos().values());
			pdfTable.addCell(this.createCell(""));
			for (int i = 0; i < 7 ; i++)
			{
				
				TramoHorario tramo = tramos.get(i);
				
				String horaInicio = tramo.getHoraInicio().getHours() + ":" + tramo.getHoraInicio().getMinutes();
				String horaFin = tramo.getHoraFinal().getHours() + ":" + tramo.getHoraFinal().getMinutes();
				
				pdfTable.addCell(this.createCell(horaInicio + "-" + horaFin));
				
			}
			
			pdfTable.completeRow();
			
			int n = 1;
			pdfTable.addCell(this.createCell("LUNES"));
			
			if (info.getHorarios().getHorariosProfesores().get(profesor)!= null)
			{
				info.getHorarios().getHorariosProfesores().get(profesor).sort(null);
			
			
			for(Actividad actividad : info.getHorarios().getHorariosProfesores().get(profesor)) 
			{
				if(actividad.getTramo().getDia() != n) {
					
					pdfTable.completeRow();
					
					n = actividad.getTramo().getDia();
					
					pdfTable.addCell(this.createCell(this.getDiaByInt(n)));
					
				}
				
				String content = actividad.getAsignatura().getNombre() + "\n" + actividad.getAula().getAbreviatura();
				
				pdfTable.addCell(this.createCell(content));
				
			}
			}
			document.add(pdfTable);
			document.close();
		}
		catch (FileNotFoundException | DocumentException exception)
		{
			String error = "ERROR FileNotFoundException OR DocumentException";
			logger.error(error);
		}
		
	}
	
	public void getInfoPdfFromGrupo(Grupo grupo, InfoCentro info)
	{
		try 
		{
			Document document = new Document();
			
			document.setPageSize(PageSize.A4.rotate());
			
			
			PdfWriter.getInstance(document, new FileOutputStream(grupo.getNombre()+ "_Horario.pdf"));
			
			document.open();
			
			PdfPTable pdfTable = new PdfPTable(8);
			pdfTable.setWidthPercentage(100f);
			
			
			List<TramoHorario> tramos = new ArrayList<TramoHorario>(info.getDatos().getTramos().values());
			pdfTable.addCell(this.createCell(""));
			for (int i = 0; i < 7 ; i++)
			{
				
				TramoHorario tramo = tramos.get(i);
				
				String horaInicio = tramo.getHoraInicio().getHours() + ":" + tramo.getHoraInicio().getMinutes();
				String horaFin = tramo.getHoraFinal().getHours() + ":" + tramo.getHoraFinal().getMinutes();
				
				pdfTable.addCell(this.createCell(horaInicio + "-" + horaFin));
				
			}
			
			pdfTable.completeRow();
			
			int n = 1;
			pdfTable.addCell(this.createCell("LUNES"));
			
			if (info.getHorarios().getHorariosGrupos().get(grupo)!= null)
			{
				info.getHorarios().getHorariosGrupos().get(grupo).sort(null);
			
			
			for(Actividad actividad : info.getHorarios().getHorariosGrupos().get(grupo)) 
			{
				if(actividad.getTramo().getDia() != n) {
					
					pdfTable.completeRow();
					
					n = actividad.getTramo().getDia();
					
					pdfTable.addCell(this.createCell(this.getDiaByInt(n)));
					
				}
				
				String content = actividad.getAsignatura().getNombre() + "\n" + actividad.getAula().getAbreviatura();
				
				pdfTable.addCell(this.createCell(content));
				
			}
			}
			document.add(pdfTable);
			document.close();
		}
		catch (FileNotFoundException | DocumentException exception)
		{
			String error = "ERROR FileNotFoundException OR DocumentException";
			logger.error(error);
		}
		
	}
	
	private PdfPCell createCell(String texto) {
		Font fontCell = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.BLACK);
		
		PdfPCell cell = new PdfPCell();
		
		cell.addElement(new Paragraph(texto,fontCell));
		
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
		return cell;
	}
	
}
