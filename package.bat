@echo off
setlocal EnableDelayedExpansion

echo ==========================================
echo   Cybersecurity Inspector Builder
echo ==========================================
echo.

echo [1/4] Compilando proyecto...
call mvn clean package

if errorlevel 1 (
    echo.
    echo ERROR: Fallo la compilacion.
    pause
    exit /b 1
)

echo.
echo [2/4] Buscando el JAR...

set JAR=

for %%f in (target\*.jar) do (
    set JAR=%%~nxf
)

if "%JAR%"=="" (
    echo No se encontro el JAR.
    pause
    exit /b 1
)

echo Encontrado:
echo %JAR%

echo.
echo [3/4] Generando instalador...

if not exist installer mkdir installer

jpackage ^
--type exe ^
--input target ^
--dest installer ^
--name "Cybersecurity Inspector" ^
--main-jar %JAR% ^
--main-class com.unad.cybersecurityinspector.Launcher ^
--vendor "UNAD" ^
--app-version 1.0.0 ^
--win-menu ^
--win-shortcut ^
--win-dir-chooser

if errorlevel 1 (
    echo.
    echo ERROR generando instalador.
    pause
    exit /b 1
)

echo.
echo ==========================================
echo INSTALADOR GENERADO CORRECTAMENTE
echo ==========================================

pause