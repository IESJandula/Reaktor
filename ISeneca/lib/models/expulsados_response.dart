import 'dart:convert';

class ExpulsadosResponse {
  ExpulsadosResponse({
    required this.results,
  });

  List<Expulsado> results;

  factory ExpulsadosResponse.fromJson(String str) =>
      ExpulsadosResponse.fromMap(json.decode(str));

  factory ExpulsadosResponse.fromMap(Map<String, dynamic> json) =>
      ExpulsadosResponse(
        results: List<Expulsado>.from(
            json["results"].map((x) => Expulsado.fromMap(x))),
      );
}

class Expulsado {
  Expulsado({
    required this.idAlumno,
    required this.apellidosNombre,
    required this.curso,
    required this.fecInic,
    required this.fecFin,
  });

  String idAlumno;
  String apellidosNombre;
  String curso;
  String fecInic;
  String fecFin;

  factory Expulsado.fromJson(String str) => Expulsado.fromMap(json.decode(str));

  factory Expulsado.fromMap(Map<String, dynamic> json) => Expulsado(
        idAlumno: json["id_alumno"],
        apellidosNombre: json["apellidos_nombre"],
        curso: json["curso"],
        fecInic: json["fec_inic"],
        fecFin: json["fec_fin"],
      );
}
