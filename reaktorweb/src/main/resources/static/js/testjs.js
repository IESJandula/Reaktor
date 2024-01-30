function myFunction(elemento)
{
  document.getElementById("acciones-networking-des").style.display = "none";
  document.getElementById("acciones-bloqueopuertos-des").style.display = "none";
  document.getElementById("acciones-screenshot-des").style.display = "none";
  document.getElementById("acciones-id-junta-des").style.display = "none";
  document.getElementById("acciones-instalar-aplicacion-des").style.display = "none";
  document.getElementById("acciones-desistalar-des").style.display = "none";
  document.getElementById("acciones-script-des").style.display = "none";
  document.getElementById("acciones-navegador-des").style.display = "none";
  document.getElementById("acciones-apagar-des").style.display = "none";

  var descripcionElegida = elemento + "-des" ;
  
  var contenedorDescripcionElegida = document.getElementById(descripcionElegida);
  if (contenedorDescripcionElegida.style.display === "none")
  {
    contenedorDescripcionElegida.style.display = "block";
  }
  
  document.getElementById("acciones-networking-fil").style.display = "none";
  document.getElementById("acciones-bloqueopuertos-fil").style.display = "none";
  document.getElementById("acciones-screenshot-fil").style.display = "none";
  document.getElementById("acciones-id-junta-fil").style.display = "none";
  document.getElementById("acciones-instalar-aplicacion-fil").style.display = "none";
  document.getElementById("acciones-desistalar-fil").style.display = "none";
  document.getElementById("acciones-script-fil").style.display = "none";
  document.getElementById("acciones-navegador-fil").style.display = "none";
  document.getElementById("acciones-apagar-fil").style.display = "none";

  
  var filtroElegido = elemento + "-fil";
  var contenedorFiltroElegido = document.getElementById(filtroElegido);
  if (contenedorFiltroElegido.style.display === "none")
  {
    contenedorFiltroElegido.style.display = "block";
  }

}