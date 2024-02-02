import 'package:flutter/material.dart';
import 'package:iseneca/models/credenciales_response.dart';
import 'package:iseneca/utils/utilidades.dart';

class CredencialesProvider extends ChangeNotifier {
  //Script Google
  //https://script.google.com/macros/s/AKfycbyPsB_koj3MwkmRFn8IJU-k4sOP8nRfnHHKNNt9xov9INZ1VEsQbu96gDR8Seiz0oDGOQ/exec?spreadsheetId=1qREuUYht73nx_fS2dxm9m6qPs_uvBwsK74dOprmwdjE&sheet=Credenciales

  //Google Docs Credenciales
  //https://docs.google.com/spreadsheets/d/1qREuUYht73nx_fS2dxm9m6qPs_uvBwsK74dOprmwdjE/edit#gid=0

  List<Credenciales> listaCredenciales = [];

  CredencialesProvider() {
    debugPrint("Credenciales Provider inicializado");
    getCredencialesUsuario();
  }

  getCredencialesUsuario() async {
    const url = "https://script.google.com/macros/s/AKfycbyPsB_koj3MwkmRFn8IJU-k4sOP8nRfnHHKNNt9xov9INZ1VEsQbu96gDR8Seiz0oDGOQ/exec?spreadsheetId=1qREuUYht73nx_fS2dxm9m6qPs_uvBwsK74dOprmwdjE&sheet=Credenciales"; 
    String respuesta = await Utilidades.getJsonData(url);
    respuesta = '{"results":$respuesta}';
    final credencialesResponse = CredencialesResponse.fromJson(respuesta);
    listaCredenciales = credencialesResponse.results;
    notifyListeners();
  }
}
