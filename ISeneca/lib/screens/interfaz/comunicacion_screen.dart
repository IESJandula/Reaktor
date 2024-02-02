import 'package:flutter/material.dart';
import 'package:iseneca/widgets/lista_opciones.dart';

import '../screens.dart';

class ComunicacionScreen extends StatelessWidget {
  const ComunicacionScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        child: Stack(children: [
          const BackgroundPaginas(),
          Column(
            children: [
              Expanded(
                flex: 2,
                child: Container(
                  margin: const EdgeInsets.only(top: 20),
                  width: double.infinity,
                  alignment: Alignment.center,
                  child: const Text("Mensajes",
                      style: TextStyle(
                          fontSize: 60,
                          color: Colors.white,
                          fontFamily: 'ErasDemi')),
                ),
              ),
              Expanded(flex: 8, child: Container()),
              // Row(
              //   mainAxisAlignment: MainAxisAlignment.end,
              //   children: [
              //     ElevatedButton(
              //       style: ElevatedButton.styleFrom(
              //         maximumSize: Size.fromRadius(70),
              //         shape: CircleBorder(),
              //         onSurface: Colors.white,
              //         shadowColor: Colors.grey,
              //       ),
              //       onPressed: null,
              //       child: Icon(
              //         Icons.add_circle_outline_sharp,
              //         size: 70,
              //         color: Colors.blue,
              //       ),
              //     ),
              //   ],
              // )
            ],
          ),
          const ListaOpciones()
        ]),
      ),
    );
  }
}
