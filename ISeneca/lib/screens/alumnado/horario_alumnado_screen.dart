import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:iseneca/providers/providers.dart';

class HorarioAlumnadoScreen extends StatelessWidget {
  const HorarioAlumnadoScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final alumnadoProvider = Provider.of<AlumnadoProvider>(context);
    final listadoAlumnos = alumnadoProvider.listadoAlumnos;

    return Scaffold(
      appBar: AppBar(
        title: const Text("HORARIOS ALUMNOS"),
      ),
      body: Center(
        child: ListView.builder(
            itemCount: listadoAlumnos.length,
            itemBuilder: (BuildContext context, int index) {
              return GestureDetector(
                onTap: () {
                  Navigator.pushNamed(
                      context, "horario_detalles_alumnado_screen",
                      arguments: index);
                },
                child: ListTile(
                  title: Text(listadoAlumnos[index].curso),
                ),
              );
            }),
      ),
    );
  }
}
