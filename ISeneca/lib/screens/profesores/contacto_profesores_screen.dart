import 'dart:math';

import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:iseneca/models/centro_response.dart';
import 'package:iseneca/providers/providers.dart';
//import 'package:url_launcher/url_launcher.dart';
import 'package:url_launcher/url_launcher_string.dart';

class ContactoProfesoresScreen extends StatelessWidget {
  const ContactoProfesoresScreen({super.key});

  @override
  Widget build(BuildContext context) {
    final centroProvider = Provider.of<CentroProvider>(context);
    final listadoProfesores = centroProvider.listaProfesores;
    List<Profesor> listaOrdenadaProfesores = [];

    listaOrdenadaProfesores.addAll(listadoProfesores);

    listaOrdenadaProfesores.sort(((a, b) => a.nombre.compareTo(b.nombre)));

    return Scaffold(
      appBar: AppBar(
        title: const Text("CONTACTO"),
      ),
      body: Center(
        child: ListView.builder(
            itemCount: listaOrdenadaProfesores.length,
            itemBuilder: (BuildContext context, int index) {
              if (index == 0) {
                return GestureDetector(
                    onTap: () {
                      null;
                    },
                    child: Container());
              } else {
                return GestureDetector(
                  onTap: () {
                    _mostrarAlert(context, index, listaOrdenadaProfesores);
                  },
                  child: ListTile(
                    title: Text(listaOrdenadaProfesores[index].nombre),
                  ),
                );
              }
            }),
      ),
    );
  }
}

void _mostrarAlert(
    BuildContext context, int index, List<Profesor> listadoProfesores) {
  final int numeroTlf = (Random().nextInt(99999999) + 600000000);
  const String mailProfesor = "Correo@gmail.com";

  showDialog(
      context: context,
      barrierDismissible: true,
      builder: (context) {
        TextStyle textStyle = const TextStyle(fontWeight: FontWeight.bold);

        return AlertDialog(
          shape:
              RoundedRectangleBorder(borderRadius: BorderRadius.circular(20.0)),
          title: Text(listadoProfesores[index].nombre),
          content: Column(
            mainAxisSize: MainAxisSize.min,
            children: <Widget>[
              const Divider(
                color: Colors.black,
                thickness: 1,
              ),
              Row(
                children: [
                  Text(
                    "Correo: ",
                    style: textStyle,
                  ),
                  const Text(mailProfesor),
                  IconButton(
                    onPressed: () {
                      launchUrlString("mailto: $mailProfesor");
                    },
                    icon: const Icon(Icons.mail),
                    color: Colors.blue,
                  ),
                ],
              ),
              Row(
                children: [
                  Text(
                    "Teléfono: ",
                    style: textStyle,
                  ),
                  Text("$numeroTlf"),
                  IconButton(
                      onPressed: () {
                        launchUrlString("tel:$numeroTlf");
                      },
                      icon: const Icon(Icons.phone),
                      color: Colors.blue)
                ],
              )
            ],
          ),
          actions: [
            TextButton(
                onPressed: () => Navigator.pop(context), child: const Text("Cerrar")),
          ],
        );
      });
}

/*List<String> _averiguarHorario(BuildContext context, int tramo, int id_prof) {
  final centroProvider = Provider.of<CentroProvider>(context, listen: false);
  final listadoHorariosProfesores = centroProvider.listaHorariosProfesores;
  List<String> horario = [];

  for (int i = 0; i < listadoHorariosProfesores.length; i++) {
    if (int.parse(listadoHorariosProfesores[i].horNumIntPr) == id_prof) {
      debugPrint("id iguales");
      for (int j = 0; j < listadoHorariosProfesores[i].actividad.length; j++) {
        debugPrint("Tramo JSON: ${listadoHorariosProfesores[i].actividad[j].tramo}");
        debugPrint("Tramo: $tramo");

        if (int.parse(listadoHorariosProfesores[i].actividad[j].tramo) ==
            tramo) {
          debugPrint("bruh");
          horario.add(listadoHorariosProfesores[i].actividad[j].asignatura);

          horario.add(listadoHorariosProfesores[i].actividad[j].aula);

          debugPrint("Asignatura: " + horario[0]);
          debugPrint("Aula: " + horario[1]);
        }
      }
    }
  }

  return horario;
}*/
