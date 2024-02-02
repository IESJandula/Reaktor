import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:iseneca/models/horario_response.dart';
import 'package:iseneca/providers/providers.dart';

class HorarioDetallesAlumnadoScreen extends StatelessWidget {
  const HorarioDetallesAlumnadoScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final index = ModalRoute.of(context)!.settings.arguments as int;
    final alumnadoProvider = Provider.of<AlumnadoProvider>(context);
    final listadoAlumnos = alumnadoProvider.listadoAlumnos;

    //Definimos el estilo de texto para la leyenda de alumnos
    const textStyle = TextStyle(fontWeight: FontWeight.bold, fontSize: 17);

    return Scaffold(
      appBar: AppBar(
        title: Text(
          "HORARIO DE ${listadoAlumnos[index].curso} ",
          style: const TextStyle(fontSize: 16),
        ),
      ),
      body: Container(
          decoration: const BoxDecoration(color: Colors.white),
          child: Column(
            children: [
              Container(
                color: Colors.blue,
                child: Table(
                  border: TableBorder.all(style: BorderStyle.solid),
                  children: [
                    diasSemana(),
                    diaHorario(context, index, 0),
                    diaHorario(context, index, 1),
                    diaHorario(context, index, 2),
                    diaHorario(context, index, 3),
                    diaHorario(context, index, 4),
                    diaHorario(context, index, 5),
                  ],
                ),
              ),
              Expanded(
                  child: Container(
                alignment: Alignment.topLeft,
                padding: const EdgeInsets.only(top: 25, left: 10),
                child: const Column(
                  children: [
                    Text(
                      "ASIGNATURAS\n\nALG - Álgebra\nBIO - Biologia\nEDU - Educación Física\nINF - Informática\nING - Inglés\nMAT - Matemáticas\nTEC - Tecnología",
                      style: textStyle,
                    ),
                  ],
                ),
              ))
            ],
          )),
    );
  }

  TableRow diasSemana() {
    return TableRow(children: [
      Container(),
      const Text("L", textAlign: TextAlign.center),
      const Text("M", textAlign: TextAlign.center),
      const Text("X", textAlign: TextAlign.center),
      const Text("J", textAlign: TextAlign.center),
      const Text("V", textAlign: TextAlign.center),
    ]);
  }

  TableRow diaHorario(BuildContext context, int index, int horaDia) {
    final horario = [
      "8:00 a 9:00",
      "9:00 a 10:00",
      "10:00 a 11:00",
      "11:00 a 12:00",
      "12:00 a 13:00",
      "13:00 a 14:00"
    ];
    return TableRow(decoration: const BoxDecoration(color: Colors.white), children: [
      Container(
          color: Colors.blue,
          child: Text(
            horario[horaDia],
            style: const TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
          )),
      devolverClase(context, index, horaDia, 0),
      devolverClase(context, index, horaDia, 1),
      devolverClase(context, index, horaDia, 2),
      devolverClase(context, index, horaDia, 3),
      devolverClase(context, index, horaDia, 4),
    ]);
  }

  Widget devolverClase(
      BuildContext context, int index, int horaDia, int numDia) {
    final alumnadoProvider = Provider.of<AlumnadoProvider>(context);
    final listadoHorario = alumnadoProvider.listadoHorarios;
    final listadoAlumnos = alumnadoProvider.listadoAlumnos;
    List<HorarioResult> listadoHorarioClase = [];

    List<String> dias = ["L", "M", "X", "J", "V"];
    List<int> horas = [8, 9, 10, 11, 12, 13];

    String asignatura = "";
    String aula = "";

    for (int i = 0; i < listadoHorario.length; i++) {
      if (listadoHorario[i].curso == listadoAlumnos[index].curso) {
        listadoHorarioClase.add(listadoHorario[i]);
      }
    }

    for (int i = 0; i < listadoHorarioClase.length; i++) {
      for (int j = 0; j < 5; j++) {
        if (listadoHorarioClase[i].dia.substring(0, 1) == dias[numDia]) {
          for (int k = 0; k < 6; k++) {
            if (int.parse(listadoHorarioClase[i].hora.split(":")[0]) ==
                horas[horaDia]) {
              asignatura = listadoHorarioClase[i].asignatura;
              aula = listadoHorarioClase[i].aulas;
            }
          }
        }
      }
    }

    // for (int i = 0; i < listadoHorarioClase.length; i++) {
    //   debugPrint(
    //       "Dia: ${listadoHorarioClase[i].dia}. Hora: ${listadoHorarioClase[i].hora}, asignatura: ${listadoHorarioClase[i].asignatura}");
    // }

    return Column(children: [
      Text(
        asignatura.toUpperCase().substring(0, 3),
        textAlign: TextAlign.center,
        style: const TextStyle(
          fontWeight: FontWeight.bold,
          fontSize: 15,
        ),
      ),
      Text(
        aula.toUpperCase(),
        style: const TextStyle(fontWeight: FontWeight.bold),
      )
    ]);
  }
}
