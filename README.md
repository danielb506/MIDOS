# MIDOS
MS-DOS Emulator

"MIDOS" is an emulator of MS-DOS command-line written in Java for academic purposes in 2018 as the final project 
of the Compilers course in the Universidad Estatal a Distancia (Costa Rica). This application implements some of 
the techniques used by lexical and semantic analyzers when validating input from the user and responding by displaying 
error messages. Also, this application recreates persistent available memory when the user creates and removes 
directories and text files. The following are the implemented commands:

- CLS    Clears the screen
- VER	   Shows the version
- PROMPT	Changes appearance of command line (parameters $P, $G)
- DATE	  Shows current date
- TIME	  Shows current time
- MD 	   Creates a directory under the current one
- CD	    Changes the working directory
- RD	    Eliminates directory under the current one
- COPY	  Creates a text file on current directory (COPY	CON <filename>)
- TYPE	  Shows contents of text file
- DEL	   Deletes a file under the current directory
- REN	   Renames directory
- DIR	   Lists contents of current directory
- TREE	  Shows directory tree under current working directory 
- EXIT	  Exits the application


*********************************************
"MIDOS" es un emulador de la linea de comandos de MS-DOS escrito en Java con propósitos académicos en el año 2018 
siendo este el entregable final de un curso de Compiladores en la Universidad Estatal a Distancia. Este programa 
implementa algunas de las técnicas empleadas por los analizadores léxicos y sintácticos de los compiladores al validar 
input del usuario y responder mostrando mensajes de error. También, esta aplicacion recrea memoria disponible persistente 
cuando el usuario crea y elimina directorios y archivos de texto. Los comandos implementados se muestran a continuación:


- CLS	   Limpia la pantalla
- VER	   Muestra la “versión” del sistema operativo MIDOS
- PROMPT	Cambia la apariencia de la línea de comando (parametros $P, $G)
- DATE	  Muestra la fecha actual
- TIME	  Muestra la hora actual
- MD 	   Crea un directorio bajo el directorio actual
- CD	    Cambia el directorio actual por otro
- RD	    Borra un directorio bajo el directorio actual
- COPY	  Crea un archivo bajo el directorio actual (COPY	CON archivo)
- TYPE	  Muestra el contenido del archivo
- DEL	   Borra un archivo dentro del directorio actual
- REN	   Cambia el nombre de un directorio
- DIR	   Muestra el contenido (directorios y archivos) del directorio actual
- TREE	  Muestra todos los subdirectorios que contiene 
- EXIT	  Sale del programa
