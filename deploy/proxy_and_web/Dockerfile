# Usa la imagen oficial de Nginx como base
FROM nginx
RUN rm /etc/nginx/conf.d/default.conf
COPY nginx.conf /etc/nginx/conf.d/
COPY reaktorweb /usr/share/nginx/html
RUN test -f /usr/share/nginx/html/index.html || echo "Archivo index.html no encontrado"
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]