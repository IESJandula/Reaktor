import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:iseneca/providers/providers.dart';

class HorarioProfesoresScreen extends StatelessWidget {
  const HorarioProfesoresScreen({super.key});

  @override
  Widget build(BuildContext context) {
    final centroProvider = Provider.of<CentroProvider>(context);
    final listadoProfesores = centroProvider.listaProfesores;
    return Scaffold(
      appBar: AppBar(
        title: const Text("HORARIO"),
      ),
      body: Center(
        child: ListView.builder(
            itemCount: listadoProfesores.length,
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
                    Navigator.pushNamed(
                        context, "horario_profesores_detalles_screen",
                        arguments: index);
                  },
                  child: ListTile(
                    title: Text(listadoProfesores[index].nombre),
                  ),
                );
              }
            }),
      ),
    );
  }
}
