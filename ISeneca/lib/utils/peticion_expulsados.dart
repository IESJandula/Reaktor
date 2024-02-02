
import 'package:iseneca/models/expulsados_response.dart';

class PeticionExpulsados{

  List<Expulsado> expulsados = [
    Expulsado(idAlumno: "1", apellidosNombre: "XXXXXXXXXXXXXXXXXXXXX", curso: "1ºESO B", fecInic: "18/10/2023", fecFin: "26-10-23"),
    Expulsado(idAlumno: "2", apellidosNombre: "XXXXXXXXXXXXXXXXXXXXX", curso: "1º FPB", fecInic: "31/10/23", fecFin: "8/11/23"),
    Expulsado(idAlumno: "3", apellidosNombre: "XXXXXXXXXXXXXXXXXXXXX", curso: "1ºESO B", fecInic: "13-11-23", fecFin: "8/11/23"),
    Expulsado(idAlumno: "4", apellidosNombre: "XXXXXXXXXXXXXXXXXXXXX", curso: "1ºESO B", fecInic: "31/10/23", fecFin: "28-11-23"),
    Expulsado(idAlumno: "5", apellidosNombre: "XXXXXXXXXXXXXXXXXXXXX", curso: "1ºESO B", fecInic: "08-11-23", fecFin: "24-11-23"),
    Expulsado(idAlumno: "6", apellidosNombre: "XXXXXXXXXXXXXXXXXXXXX", curso: "3º ESO A", fecInic: "14-11-23", fecFin: "01-12-23"),
    Expulsado(idAlumno: "7", apellidosNombre: "XXXXXXXXXXXXXXXXXXXXX", curso: "1º ESO C", fecInic: "14-11-23", fecFin: "28-11-23"),
  ];


  Future<List<Expulsado>> getExpulsados() async{

      await Future.delayed(const Duration(seconds: 2));

      return expulsados;

  }
}