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