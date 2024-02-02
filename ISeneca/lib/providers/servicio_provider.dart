import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:iseneca/models/servicio_response.dart';
import 'package:iseneca/utils/utilidades.dart';

class ServicioProvider extends ChangeNotifier {
  List<Servicio> listadoAlumnosServicio = [];

  //Google Script Lectura ejecutado
  //https://script.google.com/macros/s/AKfycbyPsB_koj3MwkmRFn8IJU-k4sOP8nRfnHHKNNt9xov9INZ1VEsQbu96gDR8Seiz0oDGOQ/exec?spreadsheetId=1u79XugcalPc4aPcymy9OsWu1qdg8aKCBvaPWQOH187I&sheet=Servicio

  //Google Script Escritura
  //https://script.google.com/macros/s/AKfycbz7ZwCTn2XXpXuPO2-m9tyR1sIC9lOMgvPPOsbDehda2NRoko871PvZvzF1jQnaq8Du/exec?spreadsheetId=1Jq4ihUzE5r4fqK9HHZQv1dg4AAgzdjPbGkpJRByu-Ds&sheet=Servicio&nombreAlumno=Alumno2&fechaEntrada=fecha2&fechaSalida=fecha2

  //Google Docs Baño
  //https://docs.google.com/spreadsheets/d/1u79XugcalPc4aPcymy9OsWu1qdg8aKCBvaPWQOH187I/edit#gid=0

  final _url = "script.google.com";
  final _apiEscritura =
      "macros/s/AKfycbz7ZwCTn2XXpXuPO2-m9tyR1sIC9lOMgvPPOsbDehda2NRoko871PvZvzF1jQnaq8Du/exec";
  final _idHoja = "1Jq4ihUzE5r4fqK9HHZQv1dg4AAgzdjPbGkpJRByu-Ds";
  final _hojaServicio = "Servicio";

  ServicioProvider() {
    debugPrint("Servicio Provider inicializado");
    getAlumnosServicio();

    notifyListeners();
  }

  getAlumnosServicio() async {
    const url = "https://script.google.com/macros/s/AKfycbyPsB_koj3MwkmRFn8IJU-k4sOP8nRfnHHKNNt9xov9INZ1VEsQbu96gDR8Seiz0oDGOQ/exec?spreadsheetId=1u79XugcalPc4aPcymy9OsWu1qdg8aKCBvaPWQOH187I&sheet=Servicio";
    String jsonData = await Utilidades.getJsonData(url);
    jsonData = '{"results":$jsonData}';
    final servicioResponse = ServicioResponse.fromJson(jsonData);
    listadoAlumnosServicio = servicioResponse.result;
    notifyListeners();
  }

  _setAlumnos(String baseurl, String api, String pagina, String hoja,
      String nombre, String fechaEntrada, String fechaSalida) async {
    final url = Uri.https(baseurl, api, {
      "spreadsheetId": pagina,
      "sheet": hoja,
      "nombreAlumno": nombre,
      "fechaEntrada": fechaEntrada,
      "fechaSalida": fechaSalida
    });

    await http.get(url);
  }

  setAlumnosServicio(
      String nombreAlumno, String fechaEntrada, String fechaSalida) {
    _setAlumnos(_url, _apiEscritura, _idHoja, _hojaServicio, nombreAlumno,
        fechaEntrada, fechaSalida);

    notifyListeners();
  }
}

final servicio = ServicioProvider();
