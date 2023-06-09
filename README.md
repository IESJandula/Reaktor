# REAKTOR: Sistema de gestión de ordenadores en el centro educativo IES Jándula

## Descripción del Proyecto
El proyecto REAKTOR surge como respuesta a un problema de gestión de ordenadores en el centro educativo IES Jándula. Consiste en el desarrollo de una aplicación cliente-servidor utilizando Java y Spring Boot para proporcionar una solución integral y eficiente en la gestión de los equipos informáticos.

El objetivo principal de REAKTOR es recopilar y procesar información detallada sobre los componentes hardware de los ordenadores, como el disco duro, la memoria RAM, la CPU, las tarjetas de red, entre otros. Esta información se envía al servidor central, que la almacena y organiza para facilitar su acceso y gestión.

La aplicación cliente se encarga de extraer y procesar la información relevante de los ordenadores y enviarla al servidor. Por otro lado, el servidor es el núcleo del sistema y proporciona una interfaz web intuitiva y amigable para que los administradores puedan acceder a los datos y gestionar los equipos de manera eficiente. La interfaz web permite visualizar información detallada sobre los componentes hardware de los ordenadores, como el estado del disco duro, la capacidad de la memoria RAM, la velocidad de la CPU, entre otros. Además, ofrece funcionalidades como la detección de software no deseado y la emisión de alertas.

El proyecto REAKTOR se desarrolla en colaboración con los responsables del proyecto: Alejandro Aljarilla Castro y Neil Hernández Salvador, bajo la supervisión del profesor Francisco Benítez Chico.

## Tabla de Contenidos

- [Instalación](#instalación)
- [Uso](#uso)
- [Créditos](#créditos)


## Instalación
Para instalar y configurar el proyecto REAKTOR, se deben seguir los siguientes pasos:
1. Configurar el yaml del proyecto ReaktorClient en el que se especifica la IP a la que apuntan las llamadas en este caso de Reaktor. <br>

2. Configurar el fichero constants.ts del proyecto reaktorweb en el que se especifica la IP a la que apuntan las llamadas en este caso de Reaktor. <br>

3. Instalar [Docker](https://www.docker.com/)

4. Instalar npm [Node.js](https://nodejs.org/es/)

5. Instalar [Angular](https://angular.io/)

6. Realizar un build del proyecto web
```
cd reaktorweb
npm install
ng build
cd ..
```

7. Realizar el .jar de los proyectos java. <br>
```
mvn -f ./Reaktor/pom.xml clean package
mvn -f ./ReaktorClient/pom.xml clean package
```
8. Crear una carpeta e introducir la carpeta que se encuentra en el interior de la carpeta generada en el dist de reaktorweb.

- WINDOWS:
```
mkdir deploy
xcopy .\reaktorweb\dist .\deploy /E /I /H /Y
```

- LINUX:
```
mkdir deploy
cp -r ./reaktorweb/dist/ ./deploy/
```

9. Introducir el jar del Servidor en la carpeta deploy. <br>

- WINDOWS:
```
copy ".\Reaktor\target\Reaktor-0.0.1-SNAPSHOT-jar-with-dependencies.jar" ".\deploy\ReaktorServer.jar"
```

- LINUX:
```
cp -r ./Reaktor/target/Reaktor-0.0.1-SNAPSHOT-jar-with-dependencies.jar ./deploy/
```
10. Crear un archivo llamado nginx.conf en la carpeta deploy con el siguiente contenido. <br>
```
server {
    listen 80;
    server_name localhost;

    location / {
        root /usr/share/nginx/html;
        index index.html;
        try_files $uri $uri/ /index.html;
    }
}

```
11. Crear un Dockerfile en la carpeta deploy con el siguiente contenido. <br>
```
# Utiliza una imagen base de MySQL
FROM mysql:latest
ENV MYSQL_ROOT_PASSWORD=toor
ENV MYSQL_DATABASE=reaktor
EXPOSE 3306

# Utiliza una imagen base de Java
FROM openjdk:19
WORKDIR /app
COPY ReaktorServer.jar .
EXPOSE 8084
CMD ["java", "-jar", "ReaktorServer.jar"]

# Usa la imagen oficial de Nginx como base
FROM nginx
RUN rm /etc/nginx/conf.d/default.conf
COPY nginx.conf /etc/nginx/conf.d/
COPY reaktorweb /usr/share/nginx/html
RUN test -f /usr/share/nginx/html/index.html || echo "Archivo index.html no encontrado"
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
```
12. Ejecutar el siguiente comando para crear la imagen de Docker. <br>
```
docker build -t reaktor .
```

13. Ejecutar el siguiente comando para crear el contenedor de Docker. <br>
```
docker run -d -p 80:80 -p 8084:8084 --name reaktor reaktor
```

## Uso

## Créditos
El proyecto REAKTOR fue desarrollado por Alejandro Aljarilla Castro y Neil Hernández Salvador bajo la supervisión del profesor D.Francisco Benítez Chico.

- [Alejandro Aljarilla Castro](https://github.com/Aljarilla11)
- [Francisco Benítez Chico](https://www.linkedin.com/in/franciscobenitezchico/)
- [Neil Hernández Salvador](https://www.linkedin.com/in/neilhdez/)
