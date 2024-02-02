import 'package:flutter/material.dart';
import 'package:iseneca/widgets/lista_opciones.dart';
import 'package:iseneca/widgets/widgets.dart';

class HomeScreen extends StatelessWidget {
  const HomeScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        child: Stack(children: [
          const Background(),
          ListView(
            children: const [
              Column(
                children: [
                  TitlePage(),
                  UserCard(),
                  ItemTable(),
                ],
              )
            ],
          ),
          const ListaOpciones()
        ]),
      ),
    );
  }
}
