# MONITORING CLIENT: Cliente del proyecto de reaktor para conectarse al servidor con un ordenador

# Descripción del proyecto

Monitoring client o servidor cliente es una de las partes del proyecto de reaktor que se encarga de recoger información de los ordenadores conectados y mandarlas a la base de datos para posteriormente su uso en servidor

Está desarrollado en java utilizando la tecnología de springboot para conectarse a una parte del servidor de reaktor que se encarga de mandar la información del ordenador que se ha conectado a una base de datos

# Arranque del proyecto

Para arrancar el proyecto es necesario lanzar el fichero de arranque del proyecto ubicado en ( dentro del proyecto )
```
.\Reaktor\MonitoringClient\src\main\java\es\monitoringserver\monitoringclient
```
Y al ejecutarlo tenemos que usar los siguientes argumentos:
<ul>
    <li>-admin "true" | "false"</li>
    <li>-classroom "valor"</li>
    <li>-t "valor"</li>
    <li>-andalucia "valor"</li>
    <li>-cn "valor"</li>
    <li>-p "valor"</li>
    <li>-sn "valor"</li>
</ul>

Los parametros valor se reemplaza por el valor real del ordenador a subir a a web y deben de ir entre comillas

Ejecución de prueba:


java .\Reaktor\MonitoringClient\src\main\java\es\monitoringserver\monitoringclient\MonitoringClientApplication.java -admin "true" -classroom "2 DAM" -t "Carrito test" -andalucia "AND-55DVX" -cn "5D" -p "Profesor" -sn "123456-DFG"


Todos los parametros son <b>obligatorios</b> si falta alguno el cliente no arranca

## Créditos

Este proyecto es una continuación del proyecto base Reaktor realizado por:

- [Alejandro Aljarilla Castro](https://github.com/Aljarilla11)
- [Francisco Benítez Chico](https://www.linkedin.com/in/franciscobenitezchico/)
- [Neil Hernández Salvador](https://www.linkedin.com/in/neilhdez/)

Los alumnos de 2DAM se encargaron de desarrollar esta parte del proyecto supervisado por el profesor D.Francisco Benítez Chico.