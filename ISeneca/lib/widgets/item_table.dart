import 'package:flutter/material.dart';
import 'package:iseneca/widgets/widgets.dart';

class ItemTable extends StatelessWidget {
  const ItemTable({
    Key? key,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Table(
      children: [
        TableRow(children: [
          GestureDetector(
              onTap: () => Navigator.pushNamed(context, "alumnado_screen"),
              child: const SingleCard(
                  icon: "assets/sombrero.png", text: "Alumnado del centro")),
          GestureDetector(
            onTap: () => Navigator.pushNamed(context, "personal_screen"),
            child: const SingleCard(
                icon: "assets/profesor.png", text: "Personal del centro"),
          ),
          GestureDetector(
            onTap: () => Navigator.pushNamed(context, "convivencia_screen"),
            child: const SingleCard(icon: "assets/covid.png", text: "Convivencia"),
          ),
        ]),
        TableRow(children: [
          GestureDetector(
            onTap: () => Navigator.pushNamed(context, "dace_screen"),
            child: const SingleCard(icon: "assets/campana.png", text: "DACE"),
          ),
          GestureDetector(
            onTap: () => Navigator.pushNamed(context, "servicio_screen"),
            child: const SingleCard(icon: "assets/calendario.png", text: "Baño"),
          ),
          Container()
        ])
      ],
    );
  }
}
