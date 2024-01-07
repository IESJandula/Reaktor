rem Asigna parámetros a variables
set "nombreRed=%~1"
set "tipoSeguridad=%~2"
set "methodEap=%~3"
set "methodAuth=%~4"
set "password=%~4"
rem Configuración del wifi
netsh wlan set hostednetwork mode=allow ssid=%nombreRed% key=%password%
netsh wlan set hostednetwork mode=allow keyMaterial=%tipoSeguridad%

rem Configuración del perfil de red
netsh wlan set profileparameter name=%nombreRed% keyMaterial=%tipoSeguridad%
netsh wlan set profileparameter name=%nombreRed% authentication=%methodAuth%
netsh wlan set profileparameter name=%nombreRed% eaptype=%methodEap%
netsh wlan set profileparameter name=%nombreRed% connectionmode=auto
rem Iniciar el punto de acceso (hotspot)
netsh wlan start hostednetwork
