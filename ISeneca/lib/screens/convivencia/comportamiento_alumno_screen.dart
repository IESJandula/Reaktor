import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:iseneca/models/alumno_convivencia.dart';


List<AlumnoConvivencia> studentList = [
  AlumnoConvivencia(nombre: "David Martinez", curso: "2DAM"),
  AlumnoConvivencia(nombre: "Pablo Fernandez", curso: "2DAM"),
  AlumnoConvivencia(nombre: "Juan Fernandez", curso: "2DAM"),
  AlumnoConvivencia(nombre: "Javi Sanchez", curso: "2DAM"),
  AlumnoConvivencia(nombre: "Manuel Sanchez", curso: "2DAM"),
  AlumnoConvivencia(nombre: "Edu Martinez", curso: "1DAM"),
  AlumnoConvivencia(nombre: "Luisda Sanchez", curso: "1DAM"),
  AlumnoConvivencia(nombre: "Omar Martinez", curso: "1DAM"),
  AlumnoConvivencia(nombre: "Nerea Fernandez", curso: "1DAM"),
  AlumnoConvivencia(nombre: "David.J Sanchez", curso: "1DAM"),
  AlumnoConvivencia(nombre: "David.A Fernandez", curso: "3ESO"),
  AlumnoConvivencia(nombre: "David.F Sanchez", curso: "3ESO"),
  AlumnoConvivencia(nombre: "David.L Martinez", curso: "3ESO"),
  AlumnoConvivencia(nombre: "David.Y Alcalde", curso: "3ESO"),
  AlumnoConvivencia(nombre: "David.B 1Fernandez", curso: "3ESO"),
  AlumnoConvivencia(nombre: "David.C 2Fernandez", curso: "3ESO"),
  AlumnoConvivencia(nombre: "David.D 3Fernandez", curso: "3ESO"),
  AlumnoConvivencia(nombre: "David.E 4Fernandez", curso: "3ESO"),
  AlumnoConvivencia(nombre: "David.F 5Fernandez", curso: "3ESO"),
  AlumnoConvivencia(nombre: "David.G 6Fernandez", curso: "3ESO"),
  AlumnoConvivencia(nombre: "David.H 7Fernandez", curso: "3ESO"),
  AlumnoConvivencia(nombre: "David.I 8Fernandez", curso: "3ESO"),
  AlumnoConvivencia(nombre: "David.J 9Fernandez", curso: "3ESO"),
  AlumnoConvivencia(nombre: "David.K 10Fernandez", curso: "3ESO"),

];

List<String> hourList = [
  "Primera Hora",
  "Segunda Hora",
  "Tercera Hora",
  "Recreo",
  "Cuarta Hora",
  "Quinta Hora",
  "Sexta Hora",
];

List<String> derivationValues = ["Si", "No"];

List<String> tutorInformation = ["Si (Por telefono)", "Si (Por iPasen)","Si (Con nota manuscrita a devolver firmada)","No","Otro"];

List<String> sharedObservations = ["Si","No","Otro"];

List<String> professorList = [
  "Rafa",
  "Paco",
  "Manuel",
  "Rocio",
  "Juan",
  "Raquel",
  "Vicente",
];

List<String> alumnList = [
  "David",
  "Pablo",
  "Juan",
  "Manuel",
  "Javier",
  "Alejandro",
  "Carlos",
];

List<String> attitudePoints = [
  "-1 Gritar en el pasillo",
  "- 25 Pelearse ",
  "-25 Coger el Movil en clase",
  "-75 Agredir a un Profesor",
  "+25 Buen comportamiento",
  "+1 Tareas realizadas",
  "+1 Buena actitud",
];

class ComportamientoAlumnoScreen extends StatefulWidget {
  const ComportamientoAlumnoScreen({super.key});

  @override
  State<ComportamientoAlumnoScreen> createState() =>
      _ComportamientoAlumnoScreenState();
}

class _ComportamientoAlumnoScreenState
  extends State<ComportamientoAlumnoScreen> {
    
  bool initAnimation = false;
  @override
  void initState() {
    super.initState();
    Future.delayed(
      const Duration(milliseconds: 1),
      () {
        setState(() {
          initAnimation = true;
        });
      },
    );
  }

  DateTime dateTime = DateTime.now();
  String currentHour = hourList[0];
  String currentDerivationValue = derivationValues[0];
  String currentTutorInformationValue = tutorInformation[0];
  String currentSharedObservationValue = sharedObservations[0];
  TextEditingController tutorInformationController = TextEditingController();
  TextEditingController sharedObservationController = TextEditingController();
  TextEditingController descriptionController = TextEditingController();
  ScrollController scrollController = ScrollController();
  final GlobalKey<FormFieldState> _keyProfessor = GlobalKey();
  final GlobalKey<FormFieldState> _keyAlumn = GlobalKey();
  final GlobalKey<FormFieldState> _keyPoints = GlobalKey();
  final GlobalKey<FormFieldState> _keyCourseFilter = GlobalKey();
  String currentProfessor = "";
  AlumnoConvivencia currentAlumn = AlumnoConvivencia(nombre: "", curso: "");
  String currentPoints = "";
  String currentCourse = "";
  String onSelectThings ="👈";
  @override
  Widget build(BuildContext context) {
    List <String> courses = getCourseList(studentList);
    final boxShadowList = [
      BoxShadow(
        color: Colors.grey.withOpacity(0.5),
        offset: const Offset(
          5.0,
          5.0,
        ),
        blurRadius: 10.0,
        spreadRadius: 2.0,
      ), //BoxShadow
       BoxShadow(
        color: Colors.white.withOpacity(0.5),
      ), //BoxShadow
    ];
    final valueFormat = DateFormat("dd-MM-yyyy");
    final screenSize = MediaQuery.of(context).size;
    final theme = Theme.of(context);
    

    return Scaffold(
      appBar: AppBar(
        backgroundColor: theme.primaryColor,
        title: const Center(
            child: Text(
          "👨‍🎓 COMPORTAMIENTO ALUMNO 👨‍🎓",
          style: TextStyle(
              fontSize: 18, fontWeight: FontWeight.bold, fontStyle: FontStyle.italic),
        )),
      ),
      body: SingleChildScrollView(
        child: Center(
          // ---- COLUMN PRINCIPAL ----
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            mainAxisSize: MainAxisSize.max,
            children: [
              // --- ESPACIO ENTRE CONTAINERS Y APP BAR ---
              SizedBox(
                height: screenSize.height * 0.05,
              ),

              // --- CONTAINER PRINCIPAL DE FECHA, LIMITADO AL 95% DEL ANCHO DE PANTALLA ---
              AnimatedContainer(
                duration: const Duration(milliseconds: 1000),
                width: initAnimation ? screenSize.width * 0.95 : 250,
                curve: Curves.elasticOut,
                decoration: BoxDecoration(
                    boxShadow: boxShadowList,
                    borderRadius: const BorderRadius.all(Radius.circular(40)),
                    color: theme.secondaryHeaderColor),
                child:
                    // --- COLUMNA DE FECHA PARA HACER EL EFECTO DE CABECERA DE CONTAINER CON FONDO DE COLOR Y TEXTO ----
                    Column(
                  children: [
                    // --- CONTENEDOR PARA LA CABEZERA DE FECHA ---
                    Container(
                        width: double.infinity,
                        height: 30,
                        decoration: BoxDecoration(
                            borderRadius: const BorderRadius.vertical(
                                top: Radius.circular(40)),
                            color: theme.primaryColor),
                        child: Center(
                            child: Text(
                          " 📆 FECHA 📆 ",
                          style: TextStyle(
                              color: theme.secondaryHeaderColor,
                              fontSize: 18,
                              fontWeight: FontWeight.bold,
                              fontStyle: FontStyle.italic),
                        ))),
                    // --- ESPACIO DE 15 PX ---
                    createVerticalSeparator(15),

                    // --- ROW CON FECHA SELECCIONADA Y BOTON PARA CAMBIAR FECHA ---
                    Column(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: [
                          Text(
                            "📆 Fecha Seleccionada: ${valueFormat.format(dateTime)}",
                            style: const TextStyle(
                                fontSize: 18, fontWeight: FontWeight.bold),
                          ),
                          createVerticalSeparator(10),

                          // --- BOTON CAMBIAR FECHA ---
                          FilledButton.icon(
                            onPressed: () {
                              Container();
                              showCupertinoModalPopup(
                                  barrierColor: theme.secondaryHeaderColor.withOpacity(0.3),
                                  context: context,
                                  builder: (context) => Container(
                                    decoration: BoxDecoration(
                                      borderRadius: const BorderRadius.all(Radius.circular(40)),
                                      color: theme.secondaryHeaderColor.withOpacity(0.9)
                                    ),
                                    height: screenSize.height*0.5,
                                    child: CupertinoDatePicker(
                                          mode: CupertinoDatePickerMode.date,
                                          initialDateTime: DateTime.now(),
                                          onDateTimeChanged: (value) {
                                            setState(() {
                                              dateTime = value;
                                            });
                                          },
                                        ),
                                  ));
                            },
                            icon: const Icon(Icons.calendar_month),
                            label: const Text(
                              "Cambiar Fecha",
                              style: TextStyle(
                                  fontSize: 15, fontWeight: FontWeight.bold),
                            ),
                          ),
                          createVerticalSeparator(10),
                        ]),
                  ],
                ),
              ),

              // --- SEPARADOR ENTRE FECHA Y HORA ---
              createVerticalSeparator(25),

              // --- CONTENEDOR PRINCIPAL DE HORA 95% DEL ANCHO ---
              AnimatedContainer(
                duration: const Duration(milliseconds: 1000),
                width: initAnimation ? screenSize.width * 0.95 : 250,
                curve: Curves.elasticOut,
                decoration: BoxDecoration(
                    boxShadow: boxShadowList,
                    borderRadius: const BorderRadius.all(Radius.circular(40)),
                    color: theme.secondaryHeaderColor),
                child:
                    // --- COLUMNA PRINCIPAL DEL CONTENEDOR ---
                    Column(
                  children: [
                    // --- CONTENEDOR PARA LA CABEZERA DE HORA ---
                    Container(
                        width: double.infinity,
                        height: 30,
                        decoration: BoxDecoration(
                            borderRadius: const BorderRadius.vertical(
                                top: Radius.circular(40)),
                            color: theme.primaryColor),
                        child: Center(
                            child: Text(
                          " 🕘 HORA 🕘 ",
                          style: TextStyle(
                              color: theme.secondaryHeaderColor,
                              fontSize: 18,
                              fontWeight: FontWeight.bold,
                              fontStyle: FontStyle.italic),
                        ))),

                    // --- SECCION EXPANDIBLE CON LAS HORAS ---
                    ExpansionTile(
                      title: Text(
                        "Seleccionar Hora : 🕘 $currentHour 🕘 ",
                        style: const TextStyle(
                            fontSize: 16, fontWeight: FontWeight.bold),
                      ),
                      children: [
                        RadioListTile(
                          title: currentHour == hourList[0]
                              ? Text(
                                  "${hourList[0]}    👈 ",
                                  style: const TextStyle(
                                      fontSize: 20,
                                      color: Color.fromARGB(255, 0, 83, 3),
                                      fontWeight: FontWeight.bold),
                                )
                              : Text(hourList[0]),
                          value: hourList[0],
                          groupValue: currentHour,
                          onChanged: (value) {
                            setState(() {
                              currentHour = value.toString();
                            });
                          },
                        ),
                        RadioListTile(
                          title: currentHour == hourList[1]
                              ? Text(
                                  "${hourList[1]}    👈 ",
                                  style: const TextStyle(
                                      fontSize: 20,
                                      color: Color.fromARGB(255, 0, 83, 3),
                                      fontWeight: FontWeight.bold),
                                )
                              : Text(hourList[1]),
                          value: hourList[1],
                          groupValue: currentHour,
                          onChanged: (value) {
                            setState(() {
                              currentHour = value.toString();
                            });
                          },
                        ),
                        RadioListTile(
                          title: currentHour == hourList[2]
                              ? Text(
                                  "${hourList[2]}    👈 ",
                                  style: const TextStyle(
                                      fontSize: 20,
                                      color: Color.fromARGB(255, 0, 83, 3),
                                      fontWeight: FontWeight.bold),
                                )
                              : Text(hourList[2]),
                          value: hourList[2],
                          groupValue: currentHour,
                          onChanged: (value) {
                            setState(() {
                              currentHour = value.toString();
                            });
                          },
                        ),
                        RadioListTile(
                          title: currentHour == hourList[3]
                              ? Text(
                                  "${hourList[3]}    👈 ",
                                  style: const TextStyle(
                                      fontSize: 20,
                                      color: Color.fromARGB(255, 0, 83, 3),
                                      fontWeight: FontWeight.bold),
                                )
                              : Text(hourList[3]),
                          value: hourList[3],
                          groupValue: currentHour,
                          onChanged: (value) {
                            setState(() {
                              currentHour = value.toString();
                            });
                          },
                        ),
                        RadioListTile(
                          title: currentHour == hourList[4]
                              ? Text(
                                  "${hourList[4]}    👈 ",
                                  style: const TextStyle(
                                      fontSize: 20,
                                      color: Color.fromARGB(255, 0, 83, 3),
                                      fontWeight: FontWeight.bold),
                                )
                              : Text(hourList[4]),
                          value: hourList[4],
                          groupValue: currentHour,
                          onChanged: (value) {
                            setState(() {
                              currentHour = value.toString();
                            });
                          },
                        ),
                        RadioListTile(
                          title: currentHour == hourList[5]
                              ? Text(
                                  "${hourList[5]}    👈 ",
                                  style: const TextStyle(
                                      fontSize: 20,
                                      color: Color.fromARGB(255, 0, 83, 3),
                                      fontWeight: FontWeight.bold),
                                )
                              : Text(hourList[5]),
                          value: hourList[5],
                          groupValue: currentHour,
                          onChanged: (value) {
                            setState(() {
                              currentHour = value.toString();
                            });
                          },
                        ),
                        RadioListTile(
                          title: currentHour == hourList[6]
                              ? Text(
                                  "${hourList[6]}    👈 ",
                                  style: const TextStyle(
                                      fontSize: 20,
                                      color: Color.fromARGB(255, 0, 83, 3),
                                      fontWeight: FontWeight.bold),
                                )
                              : Text(hourList[6]),
                          value: hourList[6],
                          groupValue: currentHour,
                          onChanged: (value) {
                            setState(() {
                              currentHour = value.toString();
                            });
                          },
                        )
                      ],
                    ),
                  ],
                ),
              ),

              // --- SEPARADOR ENTRE HORA Y PROFESOR ---
              createVerticalSeparator(25),

              // --- CONTENEDOR DE PROFESOR ---
              AnimatedContainer(
                duration: const Duration(milliseconds: 1000),
                width: initAnimation ? screenSize.width * 0.95 : 270,
                curve: Curves.elasticOut,
                decoration: BoxDecoration(
                    boxShadow: boxShadowList,
                    borderRadius: const BorderRadius.all(Radius.circular(40)),
                    color: theme.secondaryHeaderColor),
                child:
                    // --- COLUMNA DE PROFESOR PARA HACER EL EFECTO DE CABECERA DE CONTAINER CON FONDO DE COLOR Y TEXTO ----
                    Column(
                  children: [
                    // --- CONTENEDOR PARA LA CABEZERA DE PROFESOR ---
                    Container(
                        width: double.infinity,
                        height: 30,
                        decoration: BoxDecoration(
                            borderRadius: const BorderRadius.vertical(
                                top: Radius.circular(40)),
                            color: theme.primaryColor),
                        child: Center(
                            child: Text(
                          " 👩‍🏫 PROFESOR 👩‍🏫 ",
                          style: TextStyle(
                              color: theme.secondaryHeaderColor,
                              fontSize: 18,
                              fontWeight: FontWeight.bold,
                              fontStyle: FontStyle.italic),
                        ))),

                    // --- ESPACIO DE 5 PX ---
                    createVerticalSeparator(5),

                    // --- COLUMNA INTERNA DE PROFESOR ---
                    Column(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: [
                          // --- CONTENEDOR PARA LIMITAR EL ANCHO DEL DROP DOWN AL 89%
                          SizedBox(
                            width: screenSize.width * 0.89,
                            child: DropdownButtonFormField(
                              key: _keyProfessor,
                              style: const TextStyle(
                                  color:Colors.black,fontSize: 15, fontWeight: FontWeight.bold),
                              borderRadius:
                                  const BorderRadius.all(Radius.circular(40)),
                              hint: const Text("Selecciona un Profesor    👈 ",
                                  style: TextStyle(
                                      fontSize: 18,
                                      fontWeight: FontWeight.bold)),
                              items: professorList
                                  .map((valueOfMap) => DropdownMenuItem(
                                        value: valueOfMap,
                                        child: Text(valueOfMap),
                                      ))
                                  .toList(),
                              onChanged: (value) {
                                currentProfessor=value.toString();
                              },
                            ),
                          ),

                          // --- ESPACIO DE 10 PX ---
                          createVerticalSeparator(10),
                        ]),
                  ],
                ),
              ),

              // --- SEPARADOR ENTRE PROFESOR Y ALUMNO ---
              createVerticalSeparator(25),

              // --- CONTENEDOR DE ALUMNO ---
              AnimatedContainer(
                duration: const Duration(milliseconds: 1000),
                width: initAnimation ? screenSize.width * 0.95 : 250,
                curve: Curves.elasticOut,
                decoration: BoxDecoration(
                    boxShadow: boxShadowList,
                    borderRadius: const BorderRadius.all(Radius.circular(40)),
                    color: theme.secondaryHeaderColor),
                child:
                    // --- COLUMNA DE ALUMNO PARA HACER EL EFECTO DE CABECERA DE CONTAINER CON FONDO DE COLOR Y TEXTO ----
                    Column(
                  children: [
                    // --- CONTENEDOR PARA LA CABEZERA DE ALUMNO ---
                    Container(
                        width: double.infinity,
                        height: 30,
                        decoration: BoxDecoration(
                            borderRadius: const BorderRadius.vertical(
                                top: Radius.circular(40)),
                            color: theme.primaryColor),
                        child: Center(
                            child: Text(
                          " 👨‍🎓 ALUMNO 👨‍🎓 ",
                          style: TextStyle(
                              color: theme.secondaryHeaderColor,
                              fontSize: 18,
                              fontWeight: FontWeight.bold,
                              fontStyle: FontStyle.italic),
                        ))),

                    // --- ESPACIO DE 5 PX ---
                    createVerticalSeparator(5),

                    // --- COLUMNA INTERNA DE ALUMNO ---
                    Column(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: [
                          // --- CONTENEDOR PARA LIMITAR EL ANCHO DEL DROP DOWN AL 89%
                          SizedBox(
                            width: screenSize.width * 0.89,
                            child: Column(
                              children: [

                                // SI NO HAY CURSO SELECCIONADO , MOSTRAR LA SELECCION DE TODOS LOS ALUMNOS
                                // SI HAY CURSO SELECCIONADO , MOSTRAR FILTRO POR CURSO
                                currentCourse=="" ? Column(
                                  children: [
                                    const Align(alignment: Alignment.topLeft, child: Text("Todos los alumnos",style: TextStyle(fontSize: 18,fontWeight: FontWeight.bold,),)),

                                    DropdownButtonFormField(
                                      key: _keyAlumn,
                                      style: const TextStyle(
                                          color:Colors.black,fontSize: 15, fontWeight: FontWeight.bold),
                                      borderRadius:
                                          const BorderRadius.all(Radius.circular(40)),
                                      hint: const Text("Selecciona un Alumno   👈 ",
                                          style: TextStyle(
                                              fontSize: 18,
                                              fontWeight: FontWeight.bold)),
                                      items: studentList
                                          .map((valueOfMap) => DropdownMenuItem(
                                                value: valueOfMap,
                                                child: Text("${valueOfMap.nombre} ${valueOfMap.curso}"),
                                              ))
                                          .toList(),
                                      onChanged: (value) {
                                        setState(() {
                                          currentAlumn=value!;
                                        });
                                      },
                                    ),
                                  ],
                                ):const Text(""),


                                createVerticalSeparator(20),

                                // FILTRO POR CURSOS
                                const Align(alignment: Alignment.topLeft, child: Text("Filtrar por curso",style: TextStyle(fontSize: 18,fontWeight: FontWeight.bold,),)),
                                DropdownButtonFormField(
                                  key: _keyCourseFilter,
                                  style: const TextStyle(
                                      color:Colors.black,fontSize: 15, fontWeight: FontWeight.bold),
                                  borderRadius:
                                      const BorderRadius.all(Radius.circular(40)),
                                  hint: const Text("Selecciona un Curso   👈 ",
                                      style: TextStyle(
                                          fontSize: 18,
                                          fontWeight: FontWeight.bold)),
                                  items: courses
                                      .map((valueOfMap) => DropdownMenuItem(
                                            value: valueOfMap,
                                            child: Text(valueOfMap.toString()),
                                          ))
                                      .toList(),
                                  onChanged: (value) {
                                    setState(() {
                                      currentCourse=value.toString();
                                    });
                                  },
                                ),


                                createVerticalSeparator(20),

                                // SI NO HAY CURSO , NADA (TEXT VACIO)
                                // SI HAY CRUSO , MOSTRAR LOS TEXTOS PARA LOS FILTROS
                                currentCourse==""? const Text(""):
                                Container(
                                  width: double.infinity,
                                  decoration: BoxDecoration(
                                    color: theme.primaryColor,
                                    borderRadius: const BorderRadius.all(Radius.circular(40))
                                  ),
                                  child: Center(
                                    child: Column(
                                      children: [
                                        Center(
                                          child: Text(" ⚠️  Alumnos filtrados ⚠️ ",
                                            style: TextStyle(fontSize: 18,fontWeight: FontWeight.bold,color: theme.secondaryHeaderColor),),
                                        ),
                                        Text("\nSeleccionado: ${currentAlumn.nombre} ${currentAlumn.curso }",style: TextStyle(color: theme.secondaryHeaderColor,fontSize: 14,fontWeight: FontWeight.bold),)
                                      ],
                                    ),
                                  ),
                                ),

                                // SI NO HAY CURSO , NADA (TEXT VACIO)
                                // SI HAY CRUSO , MOSTRAR EL BOTON DE LIMPIAR FILTRO Y LA LISTVIEW CON LOS ALUMNOS DE ESE CURSO
                                currentCourse=="" ? const Text(""):
                                Column(
                                  children: [
                                    createVerticalSeparator(10),
                                    Align(
                                      alignment: Alignment.topCenter,
                                      child: FilledButton(onPressed: () {
                                        setState(() {
                                          currentCourse="";
                                          _keyCourseFilter.currentState!.reset();
                                        });
                                      }, child: const Text("Limpiar filtro")),
                                    ),
                                  ],
                                ),
                                Container(
                                  // SI HAY CURSO PONER ALTURA 300 , SI NO HAY PONER 0
                                  decoration: BoxDecoration(
                                    color: Color.fromARGB(255, 165, 219, 248).withOpacity(0.5),
                                    borderRadius: BorderRadius.all(Radius.circular(15))
                                  ),
                                  height: currentCourse == "" ? 0: 300,
                                  child: currentCourse == "" ? const Text("")
                                  :ListView.builder(
                                    controller: scrollController,
                                    itemCount: getFilterAlumnList(studentList,currentCourse).length,
                                    itemBuilder:(context, index) {
                                      return Column(
                                        children: [
                                          createVerticalSeparator(15),
                                          Align(
                                            alignment: Alignment.topLeft,
                                            
                                            // SACAMOS EL ALUMNO SELECCIONADO CON GESTURE DETECTOR
                                            child: GestureDetector(
                                              onTap: () {
                                                setState(() {
                                                   currentAlumn= AlumnoConvivencia(nombre: getFilterAlumnList(studentList,currentCourse)[index].nombre, curso: getFilterAlumnList(studentList,currentCourse)[index].curso);
                                                });
                                               },
                                              child: MouseRegion(
                                                cursor: SystemMouseCursors.click,
                                                child:
                                                // SI EL ALUMNO ESTA SELECCIONADO (POR NOMBRE Y CURSO) SE RETALTARA EL TEXTO
                                                // DE LO CONTRARIO TEXTO SIN RESALTAR
                                                currentAlumn.nombre== getFilterAlumnList(studentList,currentCourse)[index].nombre &&
                                                currentAlumn.curso== getFilterAlumnList(studentList,currentCourse)[index].curso
                                                ? Center(child: Text("📌 ${getFilterAlumnList(studentList,currentCourse)[index].nombre}, ${getFilterAlumnList(studentList,currentCourse)[index].curso} $onSelectThings",style: const TextStyle(fontSize: 16,fontWeight: FontWeight.bold,color: Color.fromARGB(255, 0, 124, 4)),))
                                                :Center(child: Text("🎯 ${getFilterAlumnList(studentList,currentCourse)[index].nombre}, ${getFilterAlumnList(studentList,currentCourse)[index].curso}",style: const TextStyle(fontSize: 14,fontWeight: FontWeight.bold),)),
                                              )
                                             )),
                                        ],
                                      );
                                },) ,
                                ),
                                
                                
                              ],
                            ),
                          ),

                          // --- ESPACIO DE 10 PX ---
                          createVerticalSeparator(10),
                        ]),
                  ],
                ),
              ),

              // --- SEPARADOR ENTRE ALUMNO Y PUNTOS ACTITUD ---
              createVerticalSeparator(25),

              // --- CONTENEDOR DE PUNTOS ACTITUD ---
              AnimatedContainer(
                duration: const Duration(milliseconds: 1000),
                width: initAnimation ? screenSize.width * 0.95 : 250,
                curve: Curves.elasticOut,
                decoration: BoxDecoration(
                    boxShadow: boxShadowList,
                    borderRadius: const BorderRadius.all(Radius.circular(40)),
                    color: theme.secondaryHeaderColor),
                child:
                    // --- COLUMNA DE PUNTOS / ACTITUD PARA HACER EL EFECTO DE CABECERA DE CONTAINER CON FONDO DE COLOR Y TEXTO ----
                    Column(
                  children: [
                    // --- CONTENEDOR PARA LA CABEZERA DE PUNTOS ACTITUD ---
                    Container(
                        width: double.infinity,
                        height: 30,
                        decoration: BoxDecoration(
                            borderRadius: const BorderRadius.vertical(
                                top: Radius.circular(40)),
                            color: theme.primaryColor),
                        child: Center(
                            child: Text(
                          " ❌ PUNTOS / ACTITUD ✔️ ",
                          style: TextStyle(
                              color: theme.secondaryHeaderColor,
                              fontSize: 18,
                              fontWeight: FontWeight.bold,
                              fontStyle: FontStyle.italic),
                        ))),

                    // --- ESPACIO DE 5 PX ---
                    createVerticalSeparator(5),

                    // --- COLUMNA INTERIOR ---
                    Column(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: [
                          // --- CONTENEDOR PARA LIMITAR EL ANCHO DEL DROP DOWN AL 89%
                          SizedBox(
                            width: screenSize.width * 0.89,
                            child: DropdownButtonFormField(
                              key: _keyPoints,
                              style: const TextStyle(
                                  color:Colors.black,fontSize: 15, fontWeight: FontWeight.bold),
                              borderRadius:
                                  const BorderRadius.all(Radius.circular(40)),
                              hint: const Text("Selecciona Puntos   👈 ",
                                  style: TextStyle(
                                      fontSize: 18,
                                      fontWeight: FontWeight.bold)),
                              items: attitudePoints
                                  .map((valueOfMap) => DropdownMenuItem(
                                        value: valueOfMap,
                                        child: Text(valueOfMap),
                                      ))
                                  .toList(),
                              onChanged: (value) {
                                currentPoints=value.toString();
                              },
                            ),
                          ),

                          // --- ESPACIO DE 10 PX ---
                          createVerticalSeparator(10),
                        ]),
                  ],
                ),
              ),

              // --- SEPARADOR ENTRE PUNTOS ACTITUD Y DERIVACION AL AULA DE CONVIVENCIA---
              createVerticalSeparator(25),

              // --- CONTENEDOR DE PUNTOS ACTITUD ---
              AnimatedContainer(
                duration: const Duration(milliseconds: 1000),
                width: initAnimation ? screenSize.width * 0.95 : 250,
                curve: Curves.elasticOut,
                decoration: BoxDecoration(
                    boxShadow: boxShadowList,
                    borderRadius: const BorderRadius.all(Radius.circular(40)),
                    color: theme.secondaryHeaderColor),
                child:
                    // --- COLUMNA DE DERIVACION AL AULA DE CONVIVENCIA PARA HACER EL EFECTO DE CABECERA DE CONTAINER CON FONDO DE COLOR Y TEXTO ----
                    Column(
                  children: [
                    // --- CONTENEDOR PARA LA CABEZERA DE DERIVACION AL AULA DE CONVIVENCIA ---
                    Container(
                        width: double.infinity,
                        height: 30,
                        decoration: BoxDecoration(
                            borderRadius: const BorderRadius.vertical(
                                top: Radius.circular(40)),
                            color: theme.primaryColor),
                        child: Center(
                            child: Text(
                          " 👨‍👦 DERIVACION AL AULA DE CONVIVENCIA 👨‍👦 ",
                          style: TextStyle(
                              color: theme.secondaryHeaderColor,
                              fontSize: 17,
                              fontWeight: FontWeight.bold,
                              fontStyle: FontStyle.italic),
                        ))),

                    // --- ESPACIO DE 5 PX ---
                    createVerticalSeparator(5),

                    // --- COLUMNA INTERIOR ---
                    Column(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: [
                          RadioListTile(
                            title: currentDerivationValue == derivationValues[0]
                                ? Text(
                                    "${derivationValues[0]}    👈 ",
                                    style: const TextStyle(
                                        fontSize: 18,
                                        color: Color.fromARGB(255, 0, 83, 3),
                                        fontWeight: FontWeight.bold),
                                  )
                                : Text(derivationValues[0]),
                            value: derivationValues[0],
                            groupValue: currentDerivationValue,
                            onChanged: (value) {
                              setState(() {
                                currentDerivationValue = value.toString();
                              });
                            },
                          ),
                          RadioListTile(
                            title: currentDerivationValue == derivationValues[1]
                                ? Text(
                                    "${derivationValues[1]}    👈 ",
                                    style: const TextStyle(
                                        fontSize: 18,
                                        color: Color.fromARGB(255, 83, 0, 25),
                                        fontWeight: FontWeight.bold),
                                  )
                                : Text(derivationValues[1]),
                            value: derivationValues[1],
                            groupValue: currentDerivationValue,
                            onChanged: (value) {
                              setState(() {
                                currentDerivationValue = value.toString();
                              });
                            },
                          ),

                          // --- ESPACIO DE 10 PX ---
                          createVerticalSeparator(10),
                        ]),
                  ],
                ),
              ),

              // --- SEPARADOR ENTRE PUNTOS DERIVACION AL AULA DE CONVIVENCIA Y HAN SIDO INFORMADOS LOS TUTORES DEL ALUMNO---
              createVerticalSeparator(25),

              // --- CONTENEDOR DE HAN SIDO INFORMADOS LOS TUTORES DEL ALUMNO ---
              AnimatedContainer(
                duration: const Duration(milliseconds: 1000),
                width: initAnimation ? screenSize.width * 0.95 : 250,
                curve: Curves.elasticOut,
                decoration: BoxDecoration(
                    boxShadow: boxShadowList,
                    borderRadius: const BorderRadius.all(Radius.circular(40)),
                    color: theme.secondaryHeaderColor),
                child:
                    // --- COLUMNA DE HAN SIDO INFORMADOS LOS TUTORES DEL ALUMNO PARA HACER EL EFECTO DE CABECERA DE CONTAINER CON FONDO DE COLOR Y TEXTO ----
                    Column(
                  children: [
                    // --- CONTENEDOR PARA LA CABEZERA DE HAN SIDO INFORMADOS LOS TUTORES DEL ALUMNO ---
                    Container(
                        width: double.infinity,
                        height: 30,
                        decoration: BoxDecoration(
                            borderRadius: const BorderRadius.vertical(
                                top: Radius.circular(40)),
                            color: theme.primaryColor),
                        child: Center(
                            child: Text(
                          " 🙋‍♂️ ¿HAN SIDO INFORMADOS LOS TUTORES DEL ALUMNO? 🙋‍♂️ ",
                          style: TextStyle(
                              color: theme.secondaryHeaderColor,
                              fontSize: 13,
                              fontWeight: FontWeight.bold,
                              fontStyle: FontStyle.italic),
                        ))),

                    // --- ESPACIO DE 5 PX ---
                    createVerticalSeparator(5),

                    // --- COLUMNA INTERIOR ---
                    Column(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: [
                          RadioListTile(
                            title: currentTutorInformationValue == tutorInformation[0]
                                ? Text(
                                    "${tutorInformation[0]}    👈 ",
                                    style: const TextStyle(
                                        fontSize: 18,
                                        color: Color.fromARGB(255, 0, 83, 3),
                                        fontWeight: FontWeight.bold),
                                  )
                                : Text(tutorInformation[0]),
                            value: tutorInformation[0],
                            groupValue: currentTutorInformationValue,
                            onChanged: (value) {
                              setState(() {
                                currentTutorInformationValue = value.toString();
                              });
                            },
                          ),
                          RadioListTile(
                            title: currentTutorInformationValue == tutorInformation[1]
                                ? Text(
                                    "${tutorInformation[1]}    👈 ",
                                    style: const TextStyle(
                                        fontSize: 18,
                                        color: Color.fromARGB(255, 0, 83, 3),
                                        fontWeight: FontWeight.bold),
                                  )
                                : Text(tutorInformation[1]),
                            value: tutorInformation[1],
                            groupValue: currentTutorInformationValue,
                            onChanged: (value) {
                              setState(() {
                                currentTutorInformationValue = value.toString();
                              });
                            },
                          ),
                          RadioListTile(
                            title: currentTutorInformationValue == tutorInformation[2]
                                ? Text(
                                    "${tutorInformation[2]}    👈 ",
                                    style: const TextStyle(
                                        fontSize: 18,
                                        color: Color.fromARGB(255, 0, 83, 3),
                                        fontWeight: FontWeight.bold),
                                  )
                                : Text(tutorInformation[2]),
                            value: tutorInformation[2],
                            groupValue: currentTutorInformationValue,
                            onChanged: (value) {
                              setState(() {
                                currentTutorInformationValue = value.toString();
                              });
                            },
                          ),
                          RadioListTile(
                            title: currentTutorInformationValue == tutorInformation[3]
                                ? Text(
                                    "${tutorInformation[3]}    👈 ",
                                    style: const TextStyle(
                                        fontSize: 18,
                                        color: Color.fromARGB(255, 83, 3, 0),
                                        fontWeight: FontWeight.bold),
                                  )
                                : Text(tutorInformation[3]),
                            value: tutorInformation[3],
                            groupValue: currentTutorInformationValue,
                            onChanged: (value) {
                              setState(() {
                                currentTutorInformationValue = value.toString();
                              });
                            },
                          ),
                          RadioListTile(
                            title: currentTutorInformationValue == tutorInformation[4]
                                ? Text(
                                    "${tutorInformation[4]}    👈 ",
                                    style: const TextStyle(
                                        fontSize: 18,
                                        color: Color.fromARGB(255, 0, 83, 3),
                                        fontWeight: FontWeight.bold),
                                  )
                                : Text(tutorInformation[4]),
                            value: tutorInformation[4],
                            groupValue: currentTutorInformationValue,
                            onChanged: (value) {
                              setState(() {
                                currentTutorInformationValue = value.toString();
                              });
                            },
                          ),

                          currentTutorInformationValue == tutorInformation[4] ? 
                          SizedBox(
                            width: screenSize.width*0.89,
                            child: TextFormField(
                              controller: tutorInformationController,
                              decoration: const InputDecoration(
                                hintText: "Porfavor describa brevemente..."
                              ),
                            ),
                          )
                          :const SizedBox(),


                          // --- ESPACIO DE 10 PX ---
                          createVerticalSeparator(10),
                        ]),
                  ],
                ),
              ),




              // --- SEPARADOR ENTRE HAN SIDO INFORMADOS LOS TUTORES DEL ALUMNO Y SE HAN ENVIADO OBSERVACIONES COMPARTIDAS AL EQ.DOCENTE Y AL TUTOR---
              createVerticalSeparator(25),

              // --- CONTENEDOR DE SE HAN ENVIADO OBSERVACIONES COMPARTIDAS AL EQ.DOCENTE Y AL TUTOR ---
              AnimatedContainer(
                duration: const Duration(milliseconds: 1000),
                width: initAnimation ? screenSize.width * 0.95 : 250,
                curve: Curves.elasticOut,
                decoration: BoxDecoration(
                    boxShadow: boxShadowList,
                    borderRadius: const BorderRadius.all(Radius.circular(40)),
                    color: theme.secondaryHeaderColor),
                child:
                    // --- COLUMNA DE SE HAN ENVIADO OBSERVACIONES COMPARTIDAS AL EQ.DOCENTE Y AL TUTOR PARA HACER EL EFECTO DE CABECERA DE CONTAINER CON FONDO DE COLOR Y TEXTO ----
                    Column(
                  children: [
                    // --- CONTENEDOR PARA LA CABEZERA DE SE HAN ENVIADO OBSERVACIONES COMPARTIDAS AL EQ.DOCENTE Y AL TUTOR ---
                    Container(
                        width: double.infinity,
                        height: 36,
                        decoration: BoxDecoration(
                            borderRadius: const BorderRadius.vertical(
                                top: Radius.circular(40)),
                            color: theme.primaryColor),
                        child: Column(
                          children: [
                            Center(
                                child: Text(
                              " 📩 ¿SE HAN ENVIADO OBSERVACIONES ",
                              style: TextStyle(
                                  color: theme.secondaryHeaderColor,
                                  fontSize: 15,
                                  fontWeight: FontWeight.bold,
                                  fontStyle: FontStyle.italic),
                            )),
                            Center(
                                child: Text(
                              "COMPARTIDAS AL EQUIPO DOCENTE Y AL TUTOR? 📩 ",
                              style: TextStyle(
                                  color: theme.secondaryHeaderColor,
                                  fontSize: 15,
                                  fontWeight: FontWeight.bold,
                                  fontStyle: FontStyle.italic),
                            )),
                          ],
                        )),

                    // --- ESPACIO DE 5 PX ---
                    createVerticalSeparator(5),

                    // --- COLUMNA INTERIOR ---
                    Column(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: [
                          RadioListTile(
                            title: currentSharedObservationValue == sharedObservations[0]
                                ? Text(
                                    "${sharedObservations[0]}    👈 ",
                                    style: const TextStyle(
                                        fontSize: 18,
                                        color: Color.fromARGB(255, 0, 83, 3),
                                        fontWeight: FontWeight.bold),
                                  )
                                : Text(sharedObservations[0]),
                            value: sharedObservations[0],
                            groupValue: currentSharedObservationValue,
                            onChanged: (value) {
                              setState(() {
                                currentSharedObservationValue = value.toString();
                              });
                            },
                          ),
                          RadioListTile(
                            title: currentSharedObservationValue == sharedObservations[1]
                                ? Text(
                                    "${sharedObservations[1]}    👈 ",
                                    style: const TextStyle(
                                        fontSize: 18,
                                        color: Color.fromARGB(255, 97, 1, 1),
                                        fontWeight: FontWeight.bold),
                                  )
                                : Text(sharedObservations[1]),
                            value: sharedObservations[1],
                            groupValue: currentSharedObservationValue,
                            onChanged: (value) {
                              setState(() {
                                currentSharedObservationValue = value.toString();
                              });
                            },
                          ),
                          RadioListTile(
                            title: currentSharedObservationValue == sharedObservations[2]
                                ? Text(
                                    "${sharedObservations[2]}    👈 ",
                                    style: const TextStyle(
                                        fontSize: 18,
                                        color: Color.fromARGB(255, 0, 83, 3),
                                        fontWeight: FontWeight.bold),
                                  )
                                : Text(sharedObservations[2]),
                            value: sharedObservations[2],
                            groupValue: currentSharedObservationValue,
                            onChanged: (value) {
                              setState(() {
                                currentSharedObservationValue = value.toString();
                              });
                            },
                          ),
                          currentSharedObservationValue == sharedObservations[2] ? 
                          SizedBox(
                            width: screenSize.width*0.89,
                            child: TextFormField(
                              controller: sharedObservationController,
                              decoration: const InputDecoration(
                                hintText: "Porfavor describa brevemente..."
                              ),
                            ),
                          )
                          :const SizedBox(),


                          // --- ESPACIO DE 10 PX ---
                          createVerticalSeparator(10),
                        ]),
                  ],
                ),
              ),




              // --- SEPARADOR ENTRE HAN SIDO INFORMADOS LOS TUTORES DEL ALUMNO Y DESCRIPCION DEL MOTIVO DE LA INCIDENCIA---
              createVerticalSeparator(25),

              // --- CONTENEDOR DE DESCRIPCION DEL MOTIVO DE LA INCIDENCIA ---
              AnimatedContainer(
                duration: const Duration(milliseconds: 1000),
                width: initAnimation ? screenSize.width * 0.95 : 250,
                curve: Curves.elasticOut,
                decoration: BoxDecoration(
                    boxShadow: boxShadowList,
                    borderRadius: const BorderRadius.all(Radius.circular(40)),
                    color: theme.secondaryHeaderColor),
                child:
                    // --- COLUMNA DE DESCRIPCION DEL MOTIVO DE LA INCIDENCIA PARA HACER EL EFECTO DE CABECERA DE CONTAINER CON FONDO DE COLOR Y TEXTO ----
                    Column(
                  children: [
                    // --- CONTENEDOR PARA LA CABEZERA DE DESCRIPCION DEL MOTIVO DE LA INCIDENCIA ---
                    Container(
                        width: double.infinity,
                        height: 30,
                        decoration: BoxDecoration(
                            borderRadius: const BorderRadius.vertical(
                                top: Radius.circular(40)),
                            color: theme.primaryColor),
                        child: Center(
                            child: Text(
                          " 📝 DESCRIPCION DEL MOTIVO DE LA INCIDENCIA 📝 ",
                          style: TextStyle(
                              color: theme.secondaryHeaderColor,
                              fontSize: 15,
                              fontWeight: FontWeight.bold,
                              fontStyle: FontStyle.italic),
                        ))),

                    // --- ESPACIO DE 5 PX ---
                    createVerticalSeparator(5),

                    // --- COLUMNA INTERIOR ---
                    Column(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: [
                          SizedBox(
                            width: screenSize.width*0.89,
                            child: TextFormField(
                              controller: descriptionController,
                              decoration: const InputDecoration(
                                hintStyle: TextStyle(fontSize: 13),
                                hintText: "Porfavor describe claramente el motivo de la incidencia....."
                              ),
                            ),
                          ),
                          
                          // --- ESPACIO DE 10 PX ---
                          createVerticalSeparator(10),
                        ]),
                  ],
                ),
              ),



              // --- ESPACIO DE 10 PX ---
               createVerticalSeparator(30),


              SizedBox(
                width: screenSize.width*0.9,
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.start,
                  children: [
                    Container(
                      decoration: BoxDecoration(
                        borderRadius: const BorderRadius.all(Radius.circular(40)),
                        boxShadow: boxShadowList,
                        color: Colors.green
                      ),
                      child: FilledButton(onPressed: () {
                        printValues();
                          
                      },
                      style: const ButtonStyle(
                        backgroundColor: MaterialStatePropertyAll(Colors.green)
                      ), child: const Text("Enviar"),
                      ),
                    ),
                    const Spacer(),
                    Container(
                      decoration: BoxDecoration(
                        borderRadius: const BorderRadius.all(Radius.circular(40)),
                        boxShadow: boxShadowList,
                        color: Colors.red,
                      ),
                      child: FilledButton(onPressed: () {
                        resetDefaultValues();
                      },
                      style: const ButtonStyle(
                        backgroundColor: MaterialStatePropertyAll(Colors.red)
                      ), child: const Text("Borrar"),),
                    )
                  ],
                ),
              ),

              createVerticalSeparator(40),




            ],
          ),
        ),
      ),
    );
    
  }
  void resetDefaultValues(){
    setState(() {
      _keyPoints.currentState!.reset();
      if(currentCourse==""){
        _keyAlumn.currentState!.reset();
      }
      currentCourse= "";
      _keyProfessor.currentState!.reset();
      _keyCourseFilter.currentState!.reset();
      dateTime = DateTime.now();
      currentHour = hourList[0];
      currentDerivationValue = derivationValues[0];
      currentTutorInformationValue = tutorInformation[0];
      currentSharedObservationValue = sharedObservations[0];
      tutorInformationController = TextEditingController();
      descriptionController = TextEditingController();
      sharedObservationController = TextEditingController();
      currentProfessor="";
      currentAlumn= AlumnoConvivencia(nombre: "", curso: "");
      currentPoints="";
      
    });
  }
  List<AlumnoConvivencia> getFilterAlumnList(List<AlumnoConvivencia>alumnList,String currentCourse){

    List<AlumnoConvivencia> filterAlumnList=[];

    for (AlumnoConvivencia alumn in alumnList)
    {
      if(alumn.curso==currentCourse)
      {
        filterAlumnList.add(alumn);
      }
    }
    return filterAlumnList;
  }

  List<String> getCourseList(List<AlumnoConvivencia>listaAlumnos){
    Set <String> cursos = {};

    for (AlumnoConvivencia alumno in listaAlumnos)
    {
      cursos.add(alumno.curso);
    }
    List<String> listCourses = [...cursos];
    listCourses.sort();
    return listCourses;
  }

  createVerticalSeparator(cuantity) {
    return SizedBox(height: cuantity + 0.0);
  }
  
  
  printValues() {
    print(dateTime);
    print(currentHour);
    print(currentProfessor);
    print(currentAlumn.nombre+" "+currentAlumn.curso);
    print(currentPoints);
    print(currentDerivationValue);
    print(currentTutorInformationValue);
    print(currentSharedObservationValue);
    print(tutorInformationController.value.text);
    print(sharedObservationController.value.text);
    print(descriptionController.value.text);
   }
   
}
