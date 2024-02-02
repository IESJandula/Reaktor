import 'package:flutter/material.dart';
import 'package:iseneca/models/models.dart';
import 'package:iseneca/utils/utilidades.dart';

class AlumnadoProvider extends ChangeNotifier {
  List<DatosAlumnos> listadoAlumnos = [];
  List<HorarioResult> listadoHorarios = [];

  //Prueba Google Script ejecutado
  //https://script.google.com/macros/s/AKfycbyPsB_koj3MwkmRFn8IJU-k4sOP8nRfnHHKNNt9xov9INZ1VEsQbu96gDR8Seiz0oDGOQ/exec?spreadsheetId=11Y4M52bYFMCIa5uU52vKll2-OY0VtFiGK2PhMWShngg&sheet=Datos_Alumnado

  //hoja excel
  //https://docs.google.com/spreadsheets/d/14nffuLY-WILXuAQFMUWNEZIYK08WxI0g1_aK73Ths9Q/edit#gid=0

  AlumnadoProvider() {
    debugPrint("Alumnado Provider inicalizado");
    getAlumno();
    getHorario();
  }

  Future<List<String>> getCursos() async {
    const url =
        "https://script.google.com/macros/s/AKfycbyPsB_koj3MwkmRFn8IJU-k4sOP8nRfnHHKNNt9xov9INZ1VEsQbu96gDR8Seiz0oDGOQ/exec?spreadsheetId=11Y4M52bYFMCIa5uU52vKll2-OY0VtFiGK2PhMWShngg&sheet=Cursos";
    String jsonData = await Utilidades.getJsonData(url);
    jsonData = '{"results":$jsonData}';
    final cursosResponse = CursosResponse.fromJson(jsonData);
    List<String> nombres = [];

    for (int i = 0; i < cursosResponse.result.length; i++) {
      nombres.add(cursosResponse.result[i].cursoNombre);
    }
    return nombres;
  }

  Future<List<dynamic>> getAlumnos(String cursoABuscarAlumnos) async {
    const url =
        "https://script.google.com/macros/s/AKfycbyPsB_koj3MwkmRFn8IJU-k4sOP8nRfnHHKNNt9xov9INZ1VEsQbu96gDR8Seiz0oDGOQ/exec?spreadsheetId=14nffuLY-WILXuAQFMUWNEZIYK08WxI0g1_aK73Ths9Q&sheet=Datos_Alumnado";
    String jsonData = await Utilidades.getJsonData(url);
    jsonData = '{"results":$jsonData}';
    final cursosResponse = AlumnosResponse.fromJson(jsonData);
    List<dynamic> nombresAlumnos = [];
    for (int i = 0; i < cursosResponse.result.length; i++) {
      if (cursosResponse.result[i].curso == cursoABuscarAlumnos) {
        nombresAlumnos.add(cursosResponse.result[i].nombre);
      }
    }
    return nombresAlumnos;
  }

  getAlumno() async {
    const url =
        "https://script.google.com/macros/s/AKfycbyPsB_koj3MwkmRFn8IJU-k4sOP8nRfnHHKNNt9xov9INZ1VEsQbu96gDR8Seiz0oDGOQ/exec?spreadsheetId=14nffuLY-WILXuAQFMUWNEZIYK08WxI0g1_aK73Ths9Q&sheet=Datos_Alumnado";
    String jsonData = await Utilidades.getJsonData(url);
    jsonData = '{"results":$jsonData}';
    final cursosResponse = AlumnosResponse.fromJson(jsonData);
    listadoAlumnos = cursosResponse.result;
    notifyListeners();
  }

  getHorario() async {
    const url = "https://script.google.com/macros/s/AKfycbyPsB_koj3MwkmRFn8IJU-k4sOP8nRfnHHKNNt9xov9INZ1VEsQbu96gDR8Seiz0oDGOQ/exec?spreadsheetId=14nffuLY-WILXuAQFMUWNEZIYK08WxI0g1_aK73Ths9Q&sheet=Horarios";
    String jsonData = await Utilidades.getJsonData(url);
    jsonData = '{"results":$jsonData}';
    final cursosResponse = HorarioResponse.fromJson(jsonData);
    listadoHorarios = cursosResponse.result;
    notifyListeners();
  }
}

final datos = AlumnadoProvider();
