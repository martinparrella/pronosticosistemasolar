# Aplicación de pronóstico del clima en una galaxia lejana
Proyecto encargado de pronosticar el clima en el sistema solar de una galaxia lejana.<br/>
El clima puede ser de "sequia", "lluvioso (con picos de lluvia)", "optimo" o normal.

## Supuestos y consideraciones para el desarrollo de la aplicación
* Según los datos proporcinados, se asume como momento inicial, un perìodo de "sequia", es decir que los planetas estan alineados verticalmente al sol, y el sol se encuenta en el Punto(0, 0) del eje cartesiano.
* Según los datos proporcinados respecto a la velocidad angular (grados/dia), y considerando que los planetas giran a velocidad constante, realizando un movimiento circular uniforme, podemos calcular el período que tardarán cada uno de ellos en girar alrededor del sol:<br/>
``` 
Si el planeta "Ferengi" se desplaza 1grados/día, tardará 360 días en dar la vuelta alrededor del sol.
Si el planeta "Betasoide" se desplaza 3 grados/día, tardará 120 días en dar la vuelta alrededor del sol.
Si el planeta "Vulcano" se desplaza 5 grados/día, tardará 72 días en dar la vuelta alrededor del sol.
``` 
* De acuero al análisis anterior, deducimos que un ciclo durará 360 días. A partir del día 361 los pronósticos se repiten año tras año.
* Si la alinación de planetas, no pertenece a ninguno de los períodos definidos en el enunciado, el clima es considerado com "normal".

## Prerequisitos
Instalar los siguientes items si desean revisar o ejecutar la aplicación de forma local
* [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [Maven](https://maven.apache.org/download.cgi) (al menos 3.5)
* [git command line tool](https://help.github.com/articles/set-up-git)

## Prerequisitos (Opcionales)
* [Google Cloud SDK](https://cloud.google.com/sdk/) (gcloud command line tool)
* [SpringBoot](https://spring.io/) (Version 2.1.6.RELEASE)
* [Spring Tools Suite](https://spring.io/tools) (IDE Eclipse utilizado para desarrollo)


## Instrucciones instalación
Para analizar el código o ejecutar la aplicación localmente se deben seguir los siguientes pasos:
Antes de comenzar, descargar el código fuente
```
git clone https://github.com/martinparrella/pronosticosistemasolar.git
```
#### Ejecución desde linea de comandos CMD
* Desde la linea de comandos, ubicarse en el directorio donde se haya descargado el proyecto y ejecutar:<br/>
`mvnw -DskipTests spring-boot:run`
* Desde el browser o con algún cliente REST (Postman, SoapUI o alguna extensión de tipo rest client de Google Chrome) hacer un GET a la URL 
http://localhost:8080/api/pronostico/sistemasolar/

#### Ejecución desde el IDE Eclipse
* Desde Spring Tools Suite importar como proyecto Maven.  `File -> Import -> Maven -> Existing Maven project`
* Sobre el proyecto ejecutar `Run As -> Maven Install` y luego ejecutar `Run As -> Spring Boot App` 
* Desde el browser o con algún cliente REST (Postman, SoapUI o alguna extensión de tipo rest client de Google Chrome) hacer un GET a la URL 
http://localhost:8080/api/pronostico/sistemasolar/

#### Configuraciones disponibles
Los siguientes parámetros de configuración de la aplicación se encuentran disponibles en el archivo de configuración  `src\main\resources\application.properties`. El mismo se pueden editar ante cambios en los datos de entrada con el cuenta actualmente el servicio de pronóstico.
* El ciclo dura 360 dias, luego los pronosticos se repiten  
  `dias.repite.ciclo.orbital=360`  
* Tiempo de muestreo para pronosticar el periodo del clima. Tener en cuenta que si se quiere tener un mayor tiempo de muestreo, el tiempo de procesamiento del JOB que realiza la carga inicial va a ser MAYOR.  
*XDIA: Si se pretende una frecuencia de muestreo por dia.*  
*XHORA: Si se pretende una frecuencia de muestreo por hora.*  
*XMINUTO: Si se pretende una frecuencia de muestreo por minuto.*  
*XSEGUNDO: Si se pretende una frecuencia de muestreo por segundo.*  
  (DEFAULT) `frecuencia.muestreo=XDIA`  
* Años a futuro para los cuales se quiere pronosticar  
  `pronosticar.aniosfuturos=10`  
* Frecuencia en que se ejecuta el JOB que pronostica el clima. 86400000 milisegundos es equivale a un día, o sea cada día corre el JOB  
  `frecuencia.ejecucion.job.pronosticarClima=86400000`


## Uso de la API
Según desde donde se acceda a la API, ofrecemos los siguientes recursos.
#### Despliegue local
La API se puede desplegar localmente y acceder a los siguientes recursos:
* Inicio:  
  http://localhost:8080/api/pronostico/sistemasolar/
* Consultar el clima de un día:  
  http://localhost:8080/api/pronostico/sistemasolar/clima?dia=1

#### Despliegue en Google Cloud con App Engine Standard
La API se encuentra hosteada en GCP [App Engine Standard](https://cloud.google.com/appengine/docs/standard/java/) y como almacenamiento usa base de datos `MySQL 2.ª gen. 5.7` en [Google Cloud MySQL](https://cloud.google.com/sql/docs/mysql/quickstart).
Para acceder a la API se ofrecen los siguientes recursos:
* Inicio:  
  https://meli-galaxia.appspot.com/api/pronostico/sistemasolar/
  Response body:
  
* Consultar durante los próximos 10 años, el clima de un día: <br/>
  https://meli-galaxia.appspot.com/api/pronostico/sistemasolar/clima?dia=3653
  Response body:
     { "dia":72,"clima":"LLUVIA" }
* Consultar durante los próximos 10 años, la cantidad de días que van haber con un determinado clima: <br/>
  https://meli-galaxia.appspot.com/api/pronostico/sistemasolar/periodo?clima=sequia


## Documentación de referencia
#### Desarrollo
* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Reference doc](https://docs.spring.io/spring-cloud-gcp/docs/1.1.0.M3/reference/htmlsingle/#_spring_resources)
* [Reference doc](https://docs.spring.io/spring-cloud-gcp/docs/1.1.0.M3/reference/htmlsingle/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Sample](https://github.com/spring-cloud/spring-cloud-gcp/tree/master/spring-cloud-gcp-samples/spring-cloud-gcp-storage-resource-sample)
* [Samples](https://github.com/spring-cloud/spring-cloud-gcp/tree/master/spring-cloud-gcp-samples)

#### Física y matemática
* [Leyes de Kepler](https://www.fisicalab.com/apartado/leyes-kepler#contenidos)
* [Movimiento Circular Uniforme](https://www.fisicalab.com/apartado/caracteristicas-mcu#contenidos)
* [Distancia entre dos puntos](http://geoutc.blogspot.com/2012/12/22-distancia-entre-dos-puntos.html)
* [Puntos colineales](https://www.youtube.com/watch?v=Bz6PrepV0Mo)
* [Verificar si un punto pertenece a una recta](https://www.unprofesor.com/matematicas/comprobar-si-un-punto-pertenece-a-una-recta-181.html) 
* [verificar si un punto 0.0 esta dentro de un triangulo](https://www.dokry.com/1885)

