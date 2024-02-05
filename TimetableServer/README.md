# TIMEABLE: Proyecto encargado de gestionar los horarios de los profesores y las incidencias de los alumnos en el centro IES Jandula

# Descripcion

Timeable server es un servidor que se lanzará dentro del propio instituto IES Jandula que proporcionará a los profesores una ayuda a la hora de gestionar las incidencias de los alumnos, este proyecto guarda lógica que debe de hacer el servidor cada vez que un profesor realiza una llamada, la parte del cliente se encuentra en el proyecto ISeneca

Está realizado en java utilizando la tecnología de springboot que actúa como servidor para recibir peticiones
# Arranque del proyecto

Para arrancar el proyecto necesitamos usar el IDE Eclipse, una vez que ejecutemos el IDE ubicamos el proyecto que se encuentra en 

```
C:\Users\casaj\git\Reaktor\TimetableServer\src\main\java\es\iesjandula\reaktor\timetable_server
```

Y ejecutamos el fichero TimetableApplication.java

# Peticiones del servidor

Este proyecto guarda muchas peticiones ya sea por parte de administración como por parte del profesorado:

<ul>
    <li>Envío de fichero xml con información del profesorado, este fichero ( con estructura reglada ) contiene la información de los profesores, qué horas disponen diariamente, las asignaturas que imparten en esas horas y los días qye las imparten, esta petición solo pueden acceder administradores del centro</li>
    <br>
    <li>Envío de fichero csv con información adicional de los profesores, se añade un correo y roles (docente,conserje,administrador) esta petición solo pueden acceder administradores del centro</li>
    <br>
    <li>Envío de fichero csv con información de los alumnos, se guarda su nombre y apellido, dni, email y telefono del tutor esta petición solo pueden acceder administradores del centro</li>
    <br>
    <li>Acceso al rol de un profesor usando su email</li>
    <br>
    <li>Acceso al nombre y apellidos de todos los profesores para reportar una incidencia</li>
    <br>
    <li>Acceso al nombre y apellidos de todos los alumnos para reportar una incidencia</li>
    <br>
    <li>Acceso al horario y clase de la que se encuentra el profesor en el momento de hacer la incidencia</li>
    <br>
    <li>Acceso al horario, clase y teléfono del tutor de la que se encuentra el alumno en el momento de hacer la incidencia </li>
    <br>
    <li>Anotación de cuando un alumno va y viene al baño, esta petivión sirve para prevenir fraudes por parte de los alumnos en las instalaciones</li>
    <br>
    <li>Acceso a todas las incidencias posibles ya sean negativas o positivas para rellenar información de la misma</li>
    <br>
    <li>Obtención de un pdf con el horario de la semana de un determinado profesor</li>
</ul>