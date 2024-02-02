import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:iseneca/models/models.dart';
import 'package:iseneca/providers/providers.dart';
//import 'package:url_launcher/url_launcher.dart';
import 'package:url_launcher/url_launcher_string.dart';

class ServicioInformesDetallesScreen extends StatelessWidget {
  const ServicioInformesDetallesScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final nombreParametro = ModalRoute.of(context)!.settings.arguments;
    final servicioProvider = Provider.of<ServicioProvider>(context);
    final alumnadoProvider = Provider.of<AlumnadoProvider>(context);
    final listaAlumnos = alumnadoProvider.listadoAlumnos;
    List<DatosAlumnos> alumno = [];

    for (int i = 0; i < listaAlumnos.length; i++) {
      if (listaAlumnos[i].nombre == nombreParametro) {
        alumno.add(listaAlumnos[i]);
      }
    }
    final listadoAlumnosDetalles = servicioProvider.listadoAlumnosServicio;

    List<String> fechas = [];

    for (int i = 0; i < listadoAlumnosDetalles.length; i++) {
      if (listadoAlumnosDetalles[i].nombreAlumno == nombreParametro) {
        fechas.add(
            "${listadoAlumnosDetalles[i].fechaEntrada} - ${listadoAlumnosDetalles[i].fechaSalida}");
      }
    }

    return Scaffold(
      appBar: AppBar(
        title: Text(nombreParametro.toString().toUpperCase()),
      ),
      body: SafeArea(
        child: Stack(children: [
          ListView.builder(
            itemCount: fechas.length,
            itemBuilder: (BuildContext context, int index) {
              return ListTile(
                title: Text(fechas[index]),
              );
            },
          ),
          Container(
              padding: const EdgeInsets.only(right: 20, bottom: 20),
              alignment: Alignment.bottomRight,
              child: FloatingActionButton(
                  onPressed: () => _mostrarAlert(context, alumno),
                  child: const Icon(Icons.person)))
        ]),
      ),
    );
  }

  void _mostrarAlert(BuildContext context, List<DatosAlumnos> alumno) {
    int numeroTlfAlumno = int.parse(alumno[0].telefonoAlumno);
    int numeroTlfPadre = int.parse(alumno[0].telefonoPadre);
    int numeroTlfMadre = int.parse(alumno[0].telefonoMadre);

    String mailAlumno = alumno[0].email;

    showDialog(
        context: context,
        barrierDismissible: true,
        builder: (context) {
          TextStyle textStyle = const TextStyle(fontWeight: FontWeight.bold);

          return AlertDialog(
            shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(20.0)),
            title: Text(alumno[0].nombre),
            content: Column(
              mainAxisSize: MainAxisSize.min,
              children: <Widget>[
                const Divider(
                  color: Colors.black,
                  thickness: 1,
                ),
                Row(
                  children: [
                    Text("Correo: ", style: textStyle),
                    Text(mailAlumno),
                    IconButton(
                        onPressed: () {
                          launchUrlString("mailto: $mailAlumno");
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
                    Text("$numeroTlfAlumno"),
                    IconButton(
                        onPressed: () {
                          launchUrlString("tel:$numeroTlfAlumno");
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
                    Text("$numeroTlfPadre"),
                    IconButton(
                        onPressed: () {
                          launchUrlString("tel:$numeroTlfPadre");
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
                    Text("$numeroTlfMadre"),
                    IconButton(
                        onPressed: () {
                          launchUrlString("tel:$numeroTlfMadre");
                        },
                        icon: const Icon(Icons.phone),
                        color: Colors.blue)
                  ],
                )
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
