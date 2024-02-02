import 'dart:convert';

class MayoresResponse {
  MayoresResponse({
    required this.results,
  });

  List<Mayor> results;

  factory MayoresResponse.fromJson(String str) =>
      MayoresResponse.fromMap(json.decode(str));

  factory MayoresResponse.fromMap(Map<String, dynamic> json) => MayoresResponse(
        results: List<Mayor>.from(json["results"].map((x) => Mayor.fromMap(x))),
      );
}

class Mayor {
  Mayor({
    required this.idAlumno,
    required this.apellidosNombre,
    required this.curso,
    required this.fecInic,
    required this.fecFin,
    required this.aula,
  });

  String idAlumno;
  String apellidosNombre;
  String curso;
  String fecInic;
  String fecFin;
  String aula;

  factory Mayor.fromJson(String str) => Mayor.fromMap(json.decode(str));

  factory Mayor.fromMap(Map<String, dynamic> json) => Mayor(
        idAlumno: json["id_alumno"],
        apellidosNombre: json["apellidos_nombre"],
        curso: json["curso"],
        fecInic: json["fec_inic"],
        fecFin: json["fec_fin"],
        aula: json["aula"],
      );
}
