var ancho = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
var alto = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;

function ampliarImagen() {
    var imagen = document.getElementById("imagenAmpliada");
    var insertar = document.getElementById("imagenInsertada");

    if (imagen.style.width === "175px") {
        insertar.innerHTML = `<div class="contenedor"><img src="../img/Prueba.png"  onclick="ampliarImagen()" id="imagenMostrada" style="pointer-events: all; "></div>`;
        imagen.style.width = "176px";
    } 
    else{
        imagen.style.width = "175px"; // Restaura el tamaño original
        insertar.innerHTML = `<img src="../img/Prueba.png" style="width: 80%; visibility: hidden;">`;
    }
}

function ampliarImagen2() {
    var imagen = document.getElementById("imagenAmpliada1");
    var insertar = document.getElementById("imagenInsertada");

    if (imagen.style.width === "175px") {
        insertar.innerHTML = `<div class="contenedor"><img src="../img/Prueba1.png"  onclick="ampliarImagen2()" id="imagenMostrada1" style=" pointer-events: all; "></div>`;
        imagen.style.width = "176px";
    } 
    else{
        imagen.style.width = "175px"; // Restaura el tamaño original
        imagen.style.height = "110px";
        insertar.innerHTML = `<img src="../img/Prueba1.png" style="width: 80%; visibility: hidden;">`;
    }
}

function ampliarImagen3() {
    var imagen2 = document.getElementById("imagenAmpliada2");
    var insertar = document.getElementById("imagenInsertada");

    if (imagen2.style.width === "175px") {
        insertar.innerHTML = `<div class="contenedor"><img src="../img/Prueba2.png"  onclick="ampliarImagen3()" id="imagenMostrada2" style=" pointer-events: all; "></div>`;
        imagen2.style.width = "176px";
    } 
    else{
        imagen2.style.width = "175px"; // Restaura el tamaño original
        imagen2.style.height = "110px";
        insertar.innerHTML = `<img src="../img/Prueba2.png" style="width: 80%; visibility: hidden;">`;
    }
}

function obtenerDimensionesPantalla() {
    var ancho = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
    var alto = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
    
    return "Las dimensiones de la ventana son: " + ancho + " x " + alto;
}

// Ejemplo de uso:
console.log(obtenerDimensionesPantalla());  // Muestra las dimensiones en la consola del navegador