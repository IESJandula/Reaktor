import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:iseneca/providers/providers.dart';
//import 'package:url_launcher/url_launcher.Dart';
import 'package:intl/intl.dart';
import 'package:url_launcher/url_launcher_string.dart';

class DaceScreen extends StatefulWidget {
  const DaceScreen({Key? key}) : super(key: key);

  @override
  State<DaceScreen> createState() => _DaceScreenState();
}

class _DaceScreenState extends State<DaceScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: const Text("Actividades Extraescolares"),
        ),
        body: _lista());
  }

  Widget _lista() {
    final resultadosDace = Provider.of<DaceProvider>(context);

    //Ordenar fecha de menor a mayor
    resultadosDace.resultados.sort((a, b) => DateFormat('d/M/yyyy')
        .parse(a.fechaInicio)
        .compareTo(DateFormat('d/M/yyyy').parse(b.fechaInicio)));

    return ListView.builder(
      itemCount: resultadosDace.resultados.length,
      itemBuilder: (BuildContext context, int index) {
        //Solo muestra las actividades con fecha de inicio posterior a la fecha de hoy
        // if(DateFormat('d/M/yyyy').parse(resultadosDace.resultados[index].fechaInicio).isAfter(DateTime.now())){
        return Column(
          children: [
            GestureDetector(
              onTap: () {
                _launchUrlStringURL(resultadosDace.resultados[index]
                    .alumnos); //Enlace a documento con listado de alumnos
              },
              child: ListTile(
                leading: const Icon(Icons.add, color: Colors.blue),
                title: Text(resultadosDace.resultados[index].actividad),
                trailing: Text(resultadosDace.resultados[index].fechaInicio),
              ),
            ),
            const Divider()
          ],
        );
        // }
        // else{
        //   return Container();
        // }
      },
    );
  }

  _launchUrlStringURL(String url) async {
    if (await canLaunchUrlString(url)) {
      await launchUrlString(url);
    } else {
      throw 'Could not launch $url';
    }
  }
}
