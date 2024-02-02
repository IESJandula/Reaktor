import 'package:flutter/material.dart';
import 'package:iseneca/models/models.dart';
import 'package:iseneca/providers/alumnado_provider.dart';
import 'package:provider/provider.dart';

class ReflexionScreen extends StatelessWidget{

  const ReflexionScreen({super.key});
  
  @override
  Widget build(BuildContext context) {

    return Scaffold(
      appBar: AppBar(
        title: const Text("AULA DE REFLEXIÓN",style: TextStyle(color: Colors.white),),
        backgroundColor: Colors.blue,
      ),
      body: const _ReflexionScreen(),
    );
  }
}

class _ReflexionScreen extends StatefulWidget {
  const _ReflexionScreen();

  @override
  State<_ReflexionScreen> createState() => _ReflexionScreenState();
}

enum YesNo { si, no }

class _ReflexionScreenState extends State<_ReflexionScreen> {
  YesNo haceTareas = YesNo.si;
  YesNo buenComportamiento = YesNo.si;
  @override
  Widget build(BuildContext context) {

    
    final datosAlumnosProvider = Provider.of<AlumnadoProvider>(context);
    final listadoAlumnos = datosAlumnosProvider.listadoAlumnos;
    
    


    return ListView(
        children: [
          Padding(
            padding: const EdgeInsets.all(30.0),
            child: Column(
              children: [
                Container(
                  decoration: BoxDecoration(borderRadius: BorderRadius.circular(20),color: Colors.blue),
                  child: Column(
                    children: [
                      const SizedBox(height: 20,),
                       const Padding(
                         padding: EdgeInsets.all(20.0),
                         child: Text(
                            "Alumno en aula",
                            style: TextStyle(
                              fontWeight: FontWeight.bold,
                              fontSize: 30,
                              color: Colors.white,
                            ),
                          ),
                       ),
                      Padding(
                        padding: const EdgeInsets.fromLTRB(80, 20, 80, 40),
                        child: DropdownButtonFormField(
                          decoration: InputDecoration(filled: true,fillColor: Colors.white.withOpacity(0.70), hintText: "Elige", hintStyle: const TextStyle(color: Colors.black)),
                          borderRadius: BorderRadius.circular(10),
                          
                          items: listadoAlumnos.map((e){
                            return DropdownMenuItem(
                              value: e,
                              child: SizedBox(
                                width: double.infinity,
                                child: Text(
                                  "${e.nombre} / ${e.curso}",
                                  overflow: TextOverflow.ellipsis,
                                ),
                              ),
                            );
                          }).toList(),
                          onChanged: (DatosAlumnos? value) {},
                          isDense: true,
                          isExpanded: true,
                        ),
                      ),
                    ],
                  ),
                ),
                
                const SizedBox(height: 40),

                Container(
                  decoration: BoxDecoration(borderRadius: BorderRadius.circular(20),color: Colors.blue),
                  child: Column(
                    children: [
                      const SizedBox(height: 20,),
                       const Text(
                          "¿Hace tareas?",
                          style: TextStyle(
                            fontWeight: FontWeight.bold,
                            fontSize: 30,
                            color: Colors.white,
                          ),
                        ),
                      const SizedBox(height: 20),
                      Padding(
                        padding: const EdgeInsets.all(8.0),
                        child: Container(
                          decoration: BoxDecoration(borderRadius: const BorderRadius.only(bottomLeft: Radius.circular(20) , bottomRight: Radius.circular(20)), color:Colors.white.withOpacity(0.50)),
                          child: Padding(
                            padding: const EdgeInsets.fromLTRB(20, 20, 20, 40),
                            child: Column(
                              children: [
                                RadioListTile(
                                  title: const Text("Sí", style: TextStyle(color:Colors.black)),
                                  subtitle: const Text("El alumno ha realizado sus tareas correctamente", style: TextStyle(color:Colors.black)),
                                  value: YesNo.si, 
                                  groupValue: haceTareas, 
                                  onChanged: (value) => setState(() {
                                    haceTareas = YesNo.si;
                                  }),
                                ),
                                RadioListTile(
                                  title: const Text("No", style: TextStyle(color:Colors.black)),
                                  subtitle: const Text("El alumno no ha realizado sus tareas", style: TextStyle(color:Colors.black)),
                                  value: YesNo.no, 
                                  groupValue: haceTareas, 
                                  onChanged: (value) => setState(() {
                                    haceTareas = YesNo.no;
                                  }),
                                )
                              ],
                            ),
                            
                          ),
                        ),
                      ),
                    ],
                  ),
                ),

                const SizedBox(height: 40),
                
                Container(
                  decoration: BoxDecoration(borderRadius: BorderRadius.circular(20),color: Colors.blue),
                  child: Column(
                    children: [
                      const SizedBox(height: 20,),
                       const Text(
                          "¿Buen comportamiento?",
                          style: TextStyle(
                            fontWeight: FontWeight.bold,
                            fontSize: 25,
                            color: Colors.white,
                          ),
                        ),
                      const SizedBox(height: 20),
                      Padding(
                        padding: const EdgeInsets.all(8.0),
                        child: Container(
                          decoration: BoxDecoration(borderRadius: const BorderRadius.only(bottomLeft: Radius.circular(20) , bottomRight: Radius.circular(20)), color:Colors.white.withOpacity(0.50)),
                          child: Padding(
                            padding: const EdgeInsets.fromLTRB(20, 20, 20, 40),
                            child: Column(
                              children: [
                                RadioListTile(
                                  title: const Text("Sí", style: TextStyle(color:Colors.black)),
                                  subtitle: const Text("El alumno se ha portado correctamente", style: TextStyle(color:Colors.black)),
                                  value: YesNo.si, 
                                  groupValue: buenComportamiento, 
                                  onChanged: (value) => setState(() {
                                    buenComportamiento = YesNo.si;
                                  }),
                                ),
                                RadioListTile(
                                  title: const Text("No", style: TextStyle(color:Colors.black)),
                                  subtitle: const Text("El alumno no se ha portado correctamente", style: TextStyle(color:Colors.black)),
                                  value: YesNo.no, 
                                  groupValue: buenComportamiento, 
                                  onChanged: (value) => setState(() {
                                    buenComportamiento = YesNo.no;
                                  }),
                                )
                              ],
                            ),
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
                
                const SizedBox(height: 40),

                Container(
                  decoration: BoxDecoration(borderRadius: BorderRadius.circular(20),color: Colors.blue),
                  child: Column(
                    children: [
                      const SizedBox(height: 20,),
                       const Padding(
                         padding: EdgeInsets.only(top: 20, left: 20, right: 20),
                         child: Text(
                            "Profesor que lo ha enviado",
                            style: TextStyle(
                              fontWeight: FontWeight.bold,
                              fontSize: 25,
                              color: Colors.white,
                            ),
                          ),
                       ),
                       const Padding(
                         padding: EdgeInsets.only(bottom: 20),
                         child: Center(
                           child: Text(
                              "al aula de reflexión",
                              style: TextStyle(
                                fontWeight: FontWeight.bold,
                                fontSize: 25,
                                color: Colors.white,
                              ),
                            ),
                         ),
                       ),
                      Padding(
                        padding: const EdgeInsets.fromLTRB(80, 20, 80, 40),
                        child: DropdownButtonFormField(
                          decoration: InputDecoration(filled: true,fillColor: Colors.white.withOpacity(0.70), hintText: "Elige", hintStyle: const TextStyle(color: Colors.black)),
                          borderRadius: BorderRadius.circular(10),
                          
                          items: listadoAlumnos.map((e){
                            return DropdownMenuItem(
                              value: e,
                              child: SizedBox(
                                width: double.infinity,
                                child: Text(
                                  "${e.nombre} / ${e.curso}",
                                  overflow: TextOverflow.ellipsis,
                                ),
                              ),
                            );
                          }).toList(),
                          onChanged: (DatosAlumnos? value) {},
                          isDense: true,
                          isExpanded: true,
                        ),
                      ),
                    ],
                  ),
                ),

                const SizedBox(height: 40),

                Container(
                  decoration: BoxDecoration(borderRadius: BorderRadius.circular(20),color: Colors.blue),
                  child: Column(
                    children: [
                      const SizedBox(height: 20,),
                       const Padding(
                         padding: EdgeInsets.only(top: 20, left: 20, right: 20),
                         child: Text(
                            "Profesor que está en el",
                            style: TextStyle(
                              fontWeight: FontWeight.bold,
                              fontSize: 25,
                              color: Colors.white,
                            ),
                          ),
                       ),
                       const Padding(
                         padding: EdgeInsets.only(bottom: 20),
                         child: Center(
                           child: Text(
                              "aula de reflexión",
                              style: TextStyle(
                                fontWeight: FontWeight.bold,
                                fontSize: 25,
                                color: Colors.white,
                              ),
                            ),
                         ),
                       ),
                      Padding(
                        padding: const EdgeInsets.fromLTRB(80, 20, 80, 40),
                        child: DropdownButtonFormField(
                          decoration: InputDecoration(filled: true,fillColor: Colors.white.withOpacity(0.70), hintText: "Elige", hintStyle: const TextStyle(color: Colors.black)),
                          borderRadius: BorderRadius.circular(10),
                          
                          items: listadoAlumnos.map((e){
                            return DropdownMenuItem(
                              value: e,
                              child: SizedBox(
                                width: double.infinity,
                                child: Text(
                                  "${e.nombre} / ${e.curso}",
                                  overflow: TextOverflow.ellipsis,
                                ),
                              ),
                            );
                          }).toList(),
                          onChanged: (DatosAlumnos? value) {},
                          isDense: true,
                          isExpanded: true,
                        ),
                      ),
                    ],
                  ),
                ),

                const SizedBox(height: 40),

                Container(
                  decoration: BoxDecoration(borderRadius: BorderRadius.circular(20),color: Colors.blue),
                  child: Column(
                    children: [
                      const SizedBox(height: 40,),
                      const Text(
                          "Observaciones",
                          style: TextStyle(
                            fontWeight: FontWeight.bold,
                            fontSize: 30,
                            color: Colors.white,
                          ),
                        ),
                      const SizedBox(height: 20,),
                      Padding(
                        padding: const EdgeInsets.only(left: 80,right: 80,top: 0 ,bottom: 40),
                        child: TextFormField(
                          decoration: const InputDecoration(hintText: "Tu respuesta"),
                          cursorColor: Colors.black,
                          
                        ),
                      ),
                    ],
                  ),
                ),
                const SizedBox(height: 40),

                SizedBox(
                  height: 60,
                  width: 300,
                  child: ElevatedButton(onPressed: () {
                    
                  }, child: const Text("Enviar Formulario",style: TextStyle(fontSize: 20, color: Colors.blue),)),
                )
              ],
            ),
          )         
        ],
    );
  }
}
