import 'package:flutter/material.dart';
import 'package:iseneca/main.dart';
//import 'package:url_launcher/url_launcher.dart';
import 'package:url_launcher/url_launcher_string.dart';

class ListaOpciones extends StatefulWidget {
  const ListaOpciones({super.key});

  @override
  State<ListaOpciones> createState() => _ListaOpcionesState();
}

class _ListaOpcionesState extends State<ListaOpciones> {
  bool esVisible = false;

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        Row(
          children: [
            Expanded(
              child: Container(),
            ),
            IconButton(
                onPressed: () {
                  launchUrlString("https://www.iesjandula.es");
                },
                icon: const Icon(
                  Icons.notifications,
                  color: Colors.white,
                )),
            IconButton(
              alignment: Alignment.centerRight,
              onPressed: () {
                setState(() {
                  esVisible = !esVisible;
                });
              },
              icon: const Icon(Icons.more_vert_sharp),
              color: Colors.white,
            ),
          ],
        ),
        Visibility(
          visible: esVisible,
          child: Row(
            children: [
              Expanded(child: Container()),
              SizedBox(
                  height: 400,
                  width: 220,
                  child: ListView(children: [
                    GestureDetector(
                      onTap: () {
                        Navigator.pop(context);
                        logOut();
                      },
                      child: const Card(
                        child: ListTile(
                          title: Text('Cerrar sesión'),
                        ),
                      ),
                    ),
                  ])),
            ],
          ),
        )
      ],
    );
  }
}
