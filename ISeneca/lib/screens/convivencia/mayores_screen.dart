import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:iseneca/models/models.dart';
import 'package:iseneca/providers/providers.dart';
//import 'package:url_launcher/url_launcher.dart';
import 'package:url_launcher/url_launcher_string.dart';

class MayoresScreen extends StatelessWidget {
  const MayoresScreen({super.key});

  @override
  Widget build(BuildContext context) {
    final mayoresProvider = Provider.of<ConvivenciaProvider>(context);
    final listadoMayores = mayoresProvider.listaMayores;

    final datosAlumnosProvider = Provider.of<AlumnadoProvider>(context);
    final listadoAlumnos = datosAlumnosProvider.listadoAlumnos;

    List<Mayor> listadoMayoresHoy = [];
    List<DatosAlumnos> cogerDatosMayores = [];

    // DateTime now = DateTime.now();

    for (int i = 0; i < listadoMayores.length; i++) {
      // debugPrint('Dentro del for');
      // final listaMayor = listadoMayores[i].fec_inic.split("-");
      // debugPrint(listaMayor);

      // if (int.parse(listaMayor[0]) == now.year &&
      //     int.parse(listaMayor[1]) == now.month &&
      //     int.parse(listaMayor[2]) == now.day) {
      listadoMayoresHoy.add(listadoMayores[i]);
      listadoMayoresHoy.sort((a, b) => b.fecFin.compareTo(a.fecFin));
      // debugPrint('Entra en el if');
      for (int i = 0; i < listadoMayoresHoy.length; i++) {
        // debugPrint(listadoMayoresHoy[i].apellidosNombre);
        // debugPrint(listadoMayoresHoy[i].fec_fin);
        // }
      }
    }

    for (int i = 0; i < listadoMayoresHoy.length; i++) {
      for (int j = 0; j < listadoAlumnos.length; j++) {
        if (listadoMayoresHoy[i].apellidosNombre == listadoAlumnos[j].nombre) {
          // debugPrint(listadoMayoresHoy[i].apellidosNombre);
          // debugPrint(listadoAlumnos[j].nombre);
          listadoAlumnos[j].email;
          listadoAlumnos[j].telefonoAlumno;
          listadoAlumnos[j].telefonoMadre;
          listadoAlumnos[j].telefonoPadre;

          cogerDatosMayores.add(listadoAlumnos[j]);
        }
      }
    }

    for (int j = 0; j < cogerDatosMayores.length; j++) {
      // debugPrint(cogerDatosMayores[j].email);
      // debugPrint(cogerDatosMayores[j].telefonoAlumno);
      // debugPrint(cogerDatosMayores[j].telefonoMadre);
      // debugPrint(cogerDatosMayores[j].telefonoPadre);
    }

    return Scaffold(
      appBar: AppBar(
        title: const Text('Mayores'),
      ),
      body: ListView.builder(
          itemCount: listadoMayoresHoy.length,
          itemBuilder: (BuildContext context, int index) {
            return GestureDetector(
              onTap: () {
                _mostrarAlert(
                    context, index, cogerDatosMayores, listadoMayoresHoy);
              },
              child: ListTile(
                title: Text(listadoMayoresHoy[index].apellidosNombre),
                trailing: Row(
                  mainAxisSize: MainAxisSize.min,
                  children: [
                    Text(listadoMayoresHoy[index].fecInic),
                    const Text(" - "),
                    Text(listadoMayoresHoy[index].fecFin)
                  ],
                ),
                subtitle: Text(listadoMayoresHoy[index].curso),
                leading: Text(listadoMayoresHoy[index].aula),
              ),
            );
          }),
    );
  }

  void _mostrarAlert(BuildContext context, int index,
      List<DatosAlumnos> cogerDatosMayores, List<Mayor> listadoMayoresHoy) {
    showDialog(
        context: context,
        barrierDismissible: true,
        builder: (context) {
          TextStyle textStyle = const TextStyle(fontWeight: FontWeight.bold);

          return AlertDialog(
            insetPadding: const EdgeInsets.symmetric(vertical: 10, horizontal: 10),
            shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(20.0)),
            title: Text(listadoMayoresHoy[index].apellidosNombre),
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
                    Text(cogerDatosMayores[index].email),
                    IconButton(
                        onPressed: () {
                          launchUrlString("mailto:${cogerDatosMayores[index].email}");
                        },
                        icon: const Icon(Icons.mail),
                        color: Colors.blue),
                  ],
                ),
                Row(
                  children: [
                    Text(
                      "Teléfono Alumno: ",
                      style: textStyle,
                    ),
                    Text(cogerDatosMayores[index].telefonoAlumno),
                    IconButton(
                        onPressed: () {
                          launchUrlString(
                              "tel:${cogerDatosMayores[index].telefonoAlumno}");
                        },
                        icon: const Icon(Icons.phone),
                        color: Colors.blue),
                  ],
                ),
                Row(
                  children: [
                    Text(
                      "Teléfono Madre: ",
                      style: textStyle,
                    ),
                    Text(cogerDatosMayores[index].telefonoMadre),
                    IconButton(
                        onPressed: () {
                          launchUrlString(
                              "tel:${cogerDatosMayores[index].telefonoMadre}");
                        },
                        icon: const Icon(Icons.phone),
                        color: Colors.blue),
                  ],
                ),
                Row(
                  children: [
                    Text(
                      "Teléfono Padre: ",
                      style: textStyle,
                    ),
                    Text(cogerDatosMayores[index].telefonoPadre),
                    IconButton(
                        onPressed: () {
                          launchUrlString(
                              "tel:${cogerDatosMayores[index].telefonoPadre}");
                        },
                        icon: const Icon(Icons.phone),
                        color: Colors.blue),
                  ],
                ),
              ],
            ),
            actions: [
              TextButton(
                  onPressed: () => Navigator.pop(context),
                  child: const Text("Cerrar")),
            ],
          );
        });
  }
}
