import 'package:flutter/material.dart';
import 'package:iseneca/providers/expulsados_provider.dart';
import 'package:provider/provider.dart';



class SideMenu extends StatefulWidget {
  const SideMenu({Key? key}) : super(key: key);

  @override
  State<SideMenu> createState() => _SideMenuState();
}

class _SideMenuState extends State<SideMenu> {
  String seleccionCursos = ''; 
  String seleccionAula = ''; 

  @override
  Widget build(BuildContext context) {
    final expulsadosProvider = Provider.of<ExpulsadosProvider>(context);
    return NavigationDrawer(
      children: [
        ExpansionTile(
          title: const Text('CURSOS: '),
          subtitle: Text('Seleccionado: $seleccionCursos'),
          children: [
            RadioListTile(
              title: const Text('ESO'),
              value: 'ESO',
              groupValue: seleccionCursos,
              onChanged: (value) {
                setState(() {
                  seleccionCursos = 'ESO';
                });
              },
            ),
            RadioListTile(
              title: const Text('Bachillerato'),
              value: 'BACH',
              groupValue: seleccionCursos,
              onChanged: (value) {
                setState(() {
                  seleccionCursos = 'BACH';
                });
              },
            ),
            RadioListTile(
              title: const Text('Ciclos Formativos'),
              value: 'CICLOS',
              groupValue: seleccionCursos,
              onChanged: (value) {
                setState(() {
                  seleccionCursos = value!;
                  seleccionAula = expulsadosProvider.cursos[seleccionCursos]![0]; 
                });
              },
            ),
          ],
        ),
        ExpansionTile(
          title: const Text('AULAS: '),
          subtitle: Text('Seleccionado: $seleccionAula'),
          children:  seleccionCursos.isNotEmpty ? [
            ...expulsadosProvider.cursos[seleccionCursos]!.map((e) => RadioListTile(
              title: Text(e),
              value: e,
              groupValue: seleccionAula,
              onChanged: (value) {
                setState(() {
                  seleccionAula = value!;
                });
              },
            )),
          ] : [],
        ),

        Padding(
          padding: const EdgeInsets.all(8.0),
          child: ElevatedButton(
            onPressed: () => (context),
            child: const Text('Submit'), //IconData(189)),
          ),
        )
      ],
    );
  }
}
