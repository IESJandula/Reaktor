import 'package:flutter/material.dart';
import 'package:iseneca/screens/convivencia/menu_expulsados.dart';
//import 'package:url_launcher/url_launcher.dart';

class ScreenExpulsados extends StatelessWidget {
  const ScreenExpulsados({super.key});

  @override
  Widget build(BuildContext context) {
    
    return FutureBuilder(
        future: Future.delayed(const Duration(seconds: 2)),
        builder: (BuildContext context, AsyncSnapshot snapshot) {
          if (snapshot.connectionState == ConnectionState.done) {
            return const MenuExpulsados();
          } else {
            return Scaffold(
              appBar: AppBar(
                title: const Text('Expulsados'),
              ),
              body: const Center(child: CircularProgressIndicator()),
            );
          }
        });
  }
}
