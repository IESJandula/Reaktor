# MODELS: Proyecto que guarda todos los modelos guardados en bbdd o usados en clases java de REAKTOR

# Descripción del proyecto

Models es un proyecto cuya finalidad es guardar objetos e información en lenguaje java para utilizarlos en otros proyectos como puede ser los servidores o el cliente

# Componentes o modelos

<ol>
<li>El fichero más importante es Computer.java ya que guarda toda la información necesaria para gestionar un ordenador desde reaktor</li>
<br>
<li>El siguiente es HardwareComponent que es una clase padre que engloba a todas las demás exceptuando Stratus.java y se encarga de guardar información de los componentes de un ordenador</li>
<br>
<li>El siguiente es Actions.java ubicado dentro de la carpeta monitoring que guarda todas las peticiones que se pueden hacer dentro de un ordenador tales como apagado, reinicio, instalación, etc</li>
<br>
<li>El siguiente es Status.java que guarda la información de las tareas pendientes o realizadas sobre un ordenador como apagado, reinicio, instalación, etc</li>
<br>
<li>Por ultimo encontramos la carpeta DTO t la carpeta Id que son las entidades o tablas que se guardaran dentro de la base de datos, estas entidades hacen referencia a los componentes que posee el ordenador que se sube al cliente</li>
<br>
</ol>

# Créditos

Los modelos que se guardan en la base de datos fueron creados por 

- [Alejandro Aljarilla Castro](https://github.com/Aljarilla11)
- [Neil Hernández Salvador](https://www.linkedin.com/in/neilhdez/)

Los demás fueron creados por los alumnos de 2DAM bajo la supervisión del profesor D. Francisco Benítez Chico

- [Francisco Benítez Chico](https://www.linkedin.com/in/franciscobenitezchico/)
