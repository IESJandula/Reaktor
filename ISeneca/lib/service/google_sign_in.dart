import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:provider/provider.dart';
import 'package:iseneca/main.dart';
import 'package:iseneca/providers/credenciales_provider.dart';
import 'package:iseneca/service/firebase_service.dart';

class GoogleSignIn extends StatefulWidget {
  const GoogleSignIn({Key? key}) : super(key: key);

  @override
  GoogleSignInState createState() => GoogleSignInState();
}

class GoogleSignInState extends State<GoogleSignIn> {
  bool isLoading = false;

  @override
  Widget build(BuildContext context) {
    final credencialesProvider = Provider.of<CredencialesProvider>(context);
    final lista = credencialesProvider.listaCredenciales;

    Size size = MediaQuery.of(context).size;

    return !isLoading
        ? Container(
            margin: const EdgeInsets.only(top: 15),
            width: size.width * 0.85,
            height: 55,
            child: OutlinedButton.icon(
              icon: const FaIcon(FontAwesomeIcons.google),
              onPressed: () async {
                setState(() {
                  isLoading = true;
                });
                FirebaseService service = FirebaseService();
                try {
                  await service.signInWithGoogle();

                  User? user = FirebaseAuth.instance.currentUser;
                  String? usuarioGoogle = user!.email;

                  String? nombreUsuarioGoogle = user.displayName;
                  bool existe = false;

                  for (int i = 0; i < lista.length; i++) {
                    debugPrint(lista[i].usuario);
                    if (lista[i].usuario == usuarioGoogle.toString()) {
                      existe = true;
                      Navigator.pushNamed(context, "main_screen",
                          arguments: nombreUsuarioGoogle);
                    }
                  }

                  if (!existe) {
                    _mostrarAlert(context);
                    logOut();
                  }
                } catch (e) {
                  if (e is FirebaseAuthException) {
                    debugPrint(e.message!);
                  }
                  if (e is PlatformException) {
                    logOut();
                  }
                }
                setState(() {
                  isLoading = false;
                });
              },
              label: const Text(
                "Accede a tu cuenta de Google",
                style:
                    TextStyle(color: Colors.blue, fontWeight: FontWeight.bold),
              ),
              style: ButtonStyle(
                  backgroundColor:
                      MaterialStateProperty.all<Color>(Colors.white),
                  side: MaterialStateProperty.all<BorderSide>(BorderSide.none)),
            ),
          )
        : Container(
            margin: const EdgeInsets.all(15), child: const CircularProgressIndicator());
  }
}

void _mostrarAlert(BuildContext context) {
  showDialog(
      context: context,
      barrierDismissible: true,
      builder: (context) {
        return AlertDialog(
          shape:
              RoundedRectangleBorder(borderRadius: BorderRadius.circular(20.0)),
          title: const Text("Error en la verificación"),
          content: const Column(
            mainAxisSize: MainAxisSize.min,
            children: <Widget>[
              Text("No existe ninguna cuenta con esas credenciales"),
            ],
          ),
          actions: [
            TextButton(
                onPressed: () => Navigator.pop(context), child: const Text("OK")),
          ],
        );
      });
}
