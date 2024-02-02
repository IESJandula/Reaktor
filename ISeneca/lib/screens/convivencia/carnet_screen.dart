import 'package:flutter/material.dart';

class CardScreen extends StatelessWidget 
{
  const CardScreen({super.key});

  @override
  Widget build(BuildContext context) 
  {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Carnet e Incidencias'),
      ),
      body: const _CardScreenView(),
    );
  }
}

class _CardScreenView extends StatefulWidget {
  const _CardScreenView();

  @override
  State<_CardScreenView> createState() => _CardScreenViewState();
}
enum Transportation { eso, fpb, bachillerato, cfgs, defecto }

class _CardScreenViewState extends State<_CardScreenView>{

  
    final ExpansionTileController controller = ExpansionTileController();

   bool isDeveloper = true;
  
  Transportation selectedTransportation = Transportation.defecto;
  bool unoesoa = false;
  bool unoesob = false;
  bool unoesoc = false;
  bool dosesoa = false;
  bool dosesob = false;
  bool dosesoc = false;
  bool tresesoa = false;
  bool tresesob = false;
  bool tresesoc = false;
  bool cuatroesoa = false;
  bool cuatroesob = false;
  bool cuatroesoc = false;
  bool mec1 = false;
  bool mec2 = false;
  bool dam1 = false;
  bool dam2 = false;
  bool daw1 = false;
  bool daw2 = false;
  bool guia1a = false;
  bool guia1b = false;
  bool guia2 = false;
  bool fpb1 = false;
  bool fpb2 = false;
  bool unobacha = false;
  bool unobachb = false;
  bool unobachc = false;
  bool dosbacha = false;
  bool dosbachb = false;
  bool dosbachc = false;
  

  @override
  Widget build(BuildContext context) {
    return ListView(
      physics: const ClampingScrollPhysics(),
      children: [
      
        ExpansionTile(
          title: const Text('Etapa educativa'),
          children: [
            RadioListTile(
              title: const Text('ESO'),
              subtitle: const Text('Educacion Secundaria Obligatoria'),
              value: Transportation.eso,
              groupValue: selectedTransportation,
              onChanged: (value) => setState(() {
                
                selectedTransportation = Transportation.eso;
                return ExpansionTileController.of(context).collapse();
                
              }),
              
            ),
            RadioListTile(
              title: const Text('FPB'),
              subtitle: const Text('Formacion Profesional Basica'),
              value: Transportation.fpb,
              groupValue: selectedTransportation,
              onChanged: (value) => setState(() {
                selectedTransportation = Transportation.fpb;
              }),
            ),
            RadioListTile(
              title: const Text('Bachillerato'),
              subtitle: const Text('No saben donde se meten'),
              value: Transportation.bachillerato,
              groupValue: selectedTransportation,
              onChanged: (value) => setState(() {
                selectedTransportation = Transportation.bachillerato;
              }),
            ),
            RadioListTile(
              title: const Text('CFGS'),
              subtitle: const Text('Ciclos formativos de grado superior'),
              value: Transportation.cfgs,
              groupValue: selectedTransportation,
              onChanged: (value) => setState(() {
                selectedTransportation = Transportation.cfgs;
              }),
            ),
          ],
          
        ),
        if(selectedTransportation != Transportation.defecto) 
          if(selectedTransportation == Transportation.eso)
              ExpansionTile(
                  title: const Text('Cursos'),
                  children: [
                
                CheckboxListTile(
                  title: const Text('1ESO A'),
                  value: unoesoa,
                  onChanged: (value) => setState(() {
                    unoesoa = !unoesoa;
                  }),
                ),
                CheckboxListTile(
                  title: const Text('1ESO B'),
                  value: unoesob,
                  onChanged: (value) => setState(() {
                    unoesob = !unoesob;
                  }),
                ),
                CheckboxListTile(
                  title: const Text('1ESO C'),
                  value: unoesoc,
                  onChanged: (value) => setState(() {
                    unoesoc = !unoesoc;
                  }),
                ),
                CheckboxListTile(
                  title: const Text('2ESO A'),
                  value: dosesoa,
                  onChanged: (value) => setState(() {
                    dosesoa = !dosesoa;
                  }),
                ),
                CheckboxListTile(
                  title: const Text('2ESO B'),
                  value: dosesob,
                  onChanged: (value) => setState(() {
                    dosesob = !dosesob;
                  }),
                ),
                CheckboxListTile(
                  title: const Text('2ESO C'),
                  value: dosesoc,
                  onChanged: (value) => setState(() {
                    dosesoc = !dosesoc;
                  }),
                ),
                CheckboxListTile(
                  title: const Text('3ESO A'),
                  value: tresesoa,
                  onChanged: (value) => setState(() {
                    tresesoa = !tresesoa;
                  }),
                ),
                CheckboxListTile(
                  title: const Text('3ESO B'),
                  value: tresesob,
                  onChanged: (value) => setState(() {
                    tresesob = !tresesob;
                  }),
                ),
                CheckboxListTile(
                  title: const Text('3ESO C'),
                  value: tresesoc,
                  onChanged: (value) => setState(() {
                    tresesoc = !tresesoc;
                  }),
                ),
                CheckboxListTile(
                  title: const Text('4ESO A'),
                  value: cuatroesoa,
                  onChanged: (value) => setState(() {
                    cuatroesoa = !cuatroesoa;
                  }),
                ),
                CheckboxListTile(
                  title: const Text('4ESO B'),
                  value: cuatroesob,
                  onChanged: (value) => setState(() {
                    cuatroesob = !cuatroesob;
                  }),
                ),
                CheckboxListTile(
                  title: const Text('4ESO C'),
                  value: cuatroesoc,
                  onChanged: (value) => setState(() {
                    cuatroesoc = !cuatroesoc;
                  }),
                ),                
                ],
                ),
                if(selectedTransportation == Transportation.fpb)
                ExpansionTile(
                  title: const Text('Cursos'),
                  children: [
                CheckboxListTile(
                  title: const Text('1 F.P.B'),
                  value: fpb1,
                  onChanged: (value) => setState(() {
                    fpb1 = !fpb1;
                  }),
                ),
                CheckboxListTile(
                  title: const Text('2 F.P.B'),
                  value: fpb2,
                  onChanged: (value) => setState(() {
                    fpb2 = !fpb2;
                  }),
                ),            
                ],
                ),
                if(selectedTransportation == Transportation.bachillerato)
                ExpansionTile(
                  title: const Text('Cursos'),
                  children: [
                CheckboxListTile(
                  title: const Text('1BACH A'),
                  value: unobacha,
                  onChanged: (value) => setState(() {
                    unobacha = !unobacha;
                  }),
                ),
                CheckboxListTile(
                  title: const Text('1BACH B'),
                  value: unobachb,
                  onChanged: (value) => setState(() {
                    unobachb = !unobachb;
                  }),
                ),
                CheckboxListTile(
                  title: const Text('1BACH C'),
                  value: unobachc,
                  onChanged: (value) => setState(() {
                    unobachc = !unobachc;
                  }),
                ),
                CheckboxListTile(
                  title: const Text('2BACH A'),
                  value: dosbacha,
                  onChanged: (value) => setState(() {
                    dosbacha = !dosbacha;
                  }),
                ),
                CheckboxListTile(
                  title: const Text('2BACH B'),
                  value: dosbachb,
                  onChanged: (value) => setState(() {
                    dosbachb = !dosbachb;
                  }),
                ),
                CheckboxListTile(
                  title: const Text('2BACH C'),
                  value: dosbachc,
                  onChanged: (value) => setState(() {
                    dosbachc = !dosbachc;
                  }),
                ),               
                ],
                ),
                if(selectedTransportation == Transportation.cfgs)
                 ExpansionTile(
                  title: const Text('Cursos'),
                  children: [
                CheckboxListTile(
                  title: const Text('1DAM'),
                  value: dam1,
                  onChanged: (value) => setState(() {
                    dam1 = !dam1;
                  }),
                ),
                CheckboxListTile(
                  title: const Text('2DAM'),
                  value: dam2,
                  onChanged: (value) => setState(() {
                    dam2 = !dam2;
                  }),
                ),
                CheckboxListTile(
                  title: const Text('1DAW'),
                  value: daw1,
                  onChanged: (value) => setState(() {
                    daw1 = !daw1;
                  }),
                ),
                CheckboxListTile(
                  title: const Text('2DAW'),
                  value: daw2,
                  onChanged: (value) => setState(() {
                    daw2 = !daw2;
                  }),
                ),
                CheckboxListTile(
                  title: const Text('1MEC'),
                  value: mec1,
                  onChanged: (value) => setState(() {
                    mec1 = !mec1;
                  }),
                ),
               
                CheckboxListTile(
                  title: const Text('2MEC'),
                  value: mec2,
                  onChanged: (value) => setState(() {
                    mec2 = !mec2;
                  }),
                ),
                 
                 CheckboxListTile(
                  title: const Text('1GUI A'),
                  value:   guia1a,
                  onChanged: (value) => setState(() {
                    guia1a = !guia1a;
                  }),
                ),
                CheckboxListTile(
                  title: const Text('1GUI B'),
                  value:   guia1b,
                  onChanged: (value) => setState(() {
                    guia1b = !guia1b;
                  }),
                ),
                CheckboxListTile(
                  title: const Text('2GUI'),
                  value:   guia2,
                  onChanged: (value) => setState(() {
                    guia2 = !guia2;
                  }),
                ),
                  
                  ]
                )
      ],
    );
  }

}