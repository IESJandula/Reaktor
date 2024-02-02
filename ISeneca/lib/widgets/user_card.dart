import 'package:flutter/material.dart';
import 'package:iseneca/main.dart';
import 'package:iseneca/service/services.dart';

Size size = Size.zero;

class UserCard extends StatelessWidget {
  const UserCard({
    Key? key,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    size = MediaQuery.of(context).size;

    return Container(
      decoration: BoxDecoration(
        borderRadius: BorderRadius.circular(10),
        color: Colors.white,
        boxShadow: const [
          BoxShadow(
              spreadRadius: 2,
              blurRadius: 0.7,
              color: Colors.grey,
              offset: Offset(0, 1))
        ],
      ),
      width: size.width * 0.95,
      height: size.height * 0.2,
      child: const Column(
        children: [
          _UsuarioOpciones(),
          _LineaBotones(),
        ],
      ),
    );
  }
}

class _UsuarioOpciones extends StatelessWidget {
  const _UsuarioOpciones({
    Key? key,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    User? user = FirebaseAuth.instance.currentUser;
    size = MediaQuery.of(context).size;

    return Expanded(
      flex: 7,
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Align(
            alignment: Alignment.centerLeft,
            child: Container(
              margin: EdgeInsets.only(left: 10, top: size.height * 0.005),
              child: Row(
                children: [
                  Text(
                    user!.displayName.toString(),
                    style:
                        const TextStyle(fontWeight: FontWeight.bold, fontSize: 18),
                  ),
                  Expanded(child: Container()),
                  IconButton(
                      onPressed: () {
                        Navigator.pushNamed(context, "/");
                        logOut();
                      },
                      icon: const Icon(Icons.logout))
                ],
              ),
            ),
          ),
          Align(
              alignment: Alignment.centerLeft,
              child: Container(
                margin: EdgeInsets.only(left: size.width * 0.015),
                child: Column(children: [
                  Text(" I.E.S. Jándula",
                      style: TextStyle(
                          color: Colors.blue[300],
                          fontSize: 16,
                          fontWeight: FontWeight.bold)),
                ]),
              )),
        ],
      ),
    );
  }
}

class _LineaBotones extends StatelessWidget {
  const _LineaBotones({
    Key? key,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    size = MediaQuery.of(context).size;

    return Expanded(
      flex: 3,
      child: Container(
        width: double.infinity,
        color: Colors.blue,
        child: Container(
          margin: EdgeInsets.symmetric(horizontal: size.width * 0.1),
          child: Row(
            crossAxisAlignment: CrossAxisAlignment.center,
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              GestureDetector(
                child: const Row(
                  children: [
                    Icon(
                      Icons.alarm,
                      color: Colors.white,
                    ),
                    Text("Avisos", style: TextStyle(color: Colors.white)),
                  ],
                ),
              ),
              Expanded(child: Container()),
              const VerticalDivider(
                color: Colors.white,
              ),
              Expanded(child: Container()),
              GestureDetector(
                child: const Row(
                  children: [
                    Icon(Icons.book, color: Colors.white),
                    Text("Bandeja de firmas",
                        style: TextStyle(
                          color: Colors.white,
                        ))
                  ],
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
