# TestingPortCom

## Descripción

Aplicación Java de escritorio para probar, visualizar y registrar datos de dispositivos conectados por puertos serie COM/RS232.
permite establecer comunicaciones seriales, configurar parámetros de puerto, recibir datos en tiempo real y exportar resultados a archivos de texto o Excel.

## Características principales

- Conexión a dispositivos mediante puertos COM.
- Configuración de baudios, bits de datos, paridad, stop bits y control de flujo.
- Comunicación y envio de información entre puertos COM.
- Visualización de datos en tiempo real.
- Exportación a archivo de texto o Excel.

## Estructura del proyecto

```text
src/
├── main/
│   ├── java/
│   │   └── com/ingecode/
│   │       ├── clases/
│   │       ├── controladores/
│   │       └── principal/
│   └── resources/
│       ├── recursos/
│       └── vistas/
├── test/
└── pom.xml
```

## Requisitos

- Java JDK 11 o superior
- Maven 3.8 o superior
- Microsoft Office (opcional)

## Dependencias principales

- JavaFX 11.0.2: interfaz gráfica
- jSerialComm 2.10.4: comunicación serial
- Apache POI 5.2.5: exportación a Excel

## Ejecutar el proyecto

### Desde Eclipse

1. Importa el proyecto en Eclipse.
2. Actualiza las dependencias con Maven.
3. Ejecuta la clase principal `com.ingecode.principal.Main`.

### Desde Maven

```bash
mvn clean javafx:run
```

Para forzar la actualización de dependencias:

```bash
mvn clean javafx:run -U
```

## Generar un JAR ejecutable con Maven

```bash
mvn clean package
```

El artefacto recomendado se generará en `target/` con nombre similar a:

```text
TestingPortCom-0.0.1-SNAPSHOT-jar-with-dependencies.jar
```

### Ejecutar el JAR generado por Maven

```bash
java -jar target/TestingPortCom-0.0.1-SNAPSHOT-jar-with-dependencies.jar
```

## Generar un JAR desde Eclipse

1. Haz clic derecho sobre el proyecto.
2. Selecciona `Export`.
3. Elige `Java > Runnable JAR file`.
4. Selecciona `Launch configuration` con el `Main` del proyecto.
5. Establece `Export destination`.
6. En `Library handling`, selecciona `Package required libraries into generated JAR`.
7. Haz clic en `Finish`.

### Ejecutar el JAR generado por Eclipse

```bash
java -jar TestingPortCOM.jar
```

## Notas importantes

- El JAR recomendado para ejecución es el que incluye las dependencias (`*-jar-with-dependencies.jar`).
- La clase principal es `com.ingecode.principal.Main`.
- El proyecto usa recursos FXML desde `src/main/resources/vistas/`.
- Al usar maven nos ahorramos el paso de establecer los VM Arguments de JavaFX.

## Crear un ejecutable .exe

Puedes generar un archivo ejecutable .exe para tu aplicación siguiendo estos pasos:

1. Exporta el proyecto desde Eclipse o maven generarndo el archivo .JAR
2. Abre Launch4j.
3. En la pestaña Basic:
    - Selecciona el archivo .jar exportado.
    - En Output file, coloca la misma ruta del .jar, pero con extensión .exe.
    - Si deseas, puedes asignar un icono .ico al ejecutable.
4. En la pestaña JRE:
    - Define Min JRE version como 1.8.0 o superior.
    - Deja Max JRE version vacío.
    > VM Arguments: se deja vacio.
5. Haz clic en el botón de la tuerca para generar el archivo.
6. Selecciona la carpeta donde se guardará el .exe y confirma con Save.
7. Prueba el ejecutable. Si funciona correctamente, puedes comprimir la carpeta completa y compartirla.

> Se recomienda guardar el `.exe` junto con los archivos extraídos del `.jar` para que todo quede bien organizado.

## Crear un instalador con Inno Setup 6.3.3

Puedes generar un instalador para tu aplicación con Inno Setup siguiendo estos pasos:

1. Abre Inno Setup 6.3.3.
2. Ve a `File` y selecciona `New`.
3. En la ventana emergente, haz clic en `Next`.
4. Configura los datos básicos del proyecto:
   - `Application name`: PesajePro
   - `Application version`: 1.0.0
   - `Application publisher`: nombre de tu empresa o nickname
   - `Application website`: opcional (sitio web, GitHub o dejar vacío)
5. Haz clic en `Next`.
   > En la sección `Application folder` no es necesario modificar nada.
6. Haz clic en `Next`.
   > En `Application files`:
   - `Application main executable file`: selecciona el archivo `.exe` generado previamente con Launch4j.
   - `Allow user to start the application after setup has finished`: activado.
   - `The application doesn't have a main executable file`: desactivado.
   - `Other application files`: haz clic en `Add Folder` y selecciona la carpeta donde se encuentra extraído tu proyecto Java.
7. Haz clic en `Next`.
   > En `Application file associations` no es necesario modificar nada.
8. Haz clic en `Next`.
   > En `Application shortcuts` no es necesario modificar nada.
9. Haz clic en `Next`.
   > En `Application documentation` no es necesario modificar nada.
   - `License file`: puedes crear un archivo de licencia.
   - `Information file shown before installation`: puedes agregar un archivo con información que se mostrará antes de instalar.
   - `Information file shown after installation`: puedes agregar un archivo con información que se mostrará después de instalar.
10. Haz clic en `Next`.
    > En `Setup install mode` no es necesario modificar nada.
11. Haz clic en `Next`.
    > En `Application registry keys and values` no es necesario modificar nada.
12. En `Setup languages`, selecciona los idiomas en los que deseas mostrar la información del instalador: Inglés y Español. Luego haz clic en `Next`.
13. En `Compiler settings`:
    - `Custom compiler output folder`: elige una carpeta nueva donde se generará el instalador.
    - `Compiler output base file name`: asigna un nombre, por ejemplo `PesajePro Installer`.
    - `Custom setup icon file`: selecciona un archivo `.ico` para el instalador.
    - `Setup password`: opcional. Puedes agregar una contraseña para proteger la instalación.
14. Haz clic en `Next`.
    > En `Inno Setup compiler` no es necesario modificar nada.
15. Haz clic en `Next`.
16. Haz clic en `Finish`.
17. Aparecerá un mensaje que dice `Would you like to compile the new script now?`.
    - Haz clic en `Yes`.
    - Vuelve a confirmar con `Yes`.
    - Se generará un archivo `.iss`. Guárdalo en la misma carpeta del instalador y dale un nombre.
18. El proceso puede tardar unos minutos. Cuando termine, en la consola de Inno Setup aparecerá un mensaje en verde con la palabra `Finished` junto con la hora y la fecha.
19. Ya puedes usar tu instalador.

## Lo que aprendí con este proyecto

- Creción de proyectos de aplicaciones de escritorio Java con Maven.
- Comunicación serial con Java.
- Integración de interfaces gráficas con JavaFX.
- Lectura y exportación de datos a Excel.
- Empaquetado de aplicaciones Java con eclipse y maven
- Generar ejecutables .exe e instaladores.

## Autor

Rodrigo Cantor Vásquez
