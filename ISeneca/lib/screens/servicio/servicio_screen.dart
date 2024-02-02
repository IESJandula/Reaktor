import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';

class ServicioScreen extends StatelessWidget {
  const ServicioScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("BAÑO"),
      ),
      body: ListView(
        children: [
          GestureDetector(
            onTap: () => Navigator.pushNamed(context, "servicio_es_screen"),
            child: const Card(
              child: ListTile(
                leading: FaIcon(FontAwesomeIcons.doorOpen),
                title: Text('Entrada/Salida'),
              ),
            ),
          ),
          GestureDetector(
            onTap: () =>
                Navigator.pushNamed(context, "servicio_informes_screen"),
            child: const Card(
              child: ListTile(
                leading: FaIcon(FontAwesomeIcons.solidFolder),
                title: Text('Informes'),
              ),
            ),
          ),
        ],
      ),
    );
  }
}
