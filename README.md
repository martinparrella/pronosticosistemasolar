# Aplicacion de pronostico del clima en una galaxia lejana
Proyecto encargado de predecir el pronostico del clima en el sistema solar de una galaxia lejana.

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

#### Ejecución desde linea de comandos CMD
* Descargar el código fuente de mi Github
* Desde la linea de comandos y en el directorio donde se haya descargado el proyecto, ejecutar: `mvnw -DskipTests spring-boot:run`
* Desde el browser o con algún cliente REST (Postman, SoapUI o alguna extensión de tipo rest client de Google Chrome) hacer un GET a la URL 
http://localhost:8080/api/pronostico/sistemasolar/

#### Ejecución desde el IDE Eclipse
* Descargar el código fuente de mi Github
* Desde Spring Tools Suite importar como proyecto Maven.
* Sobre el proyecto ejecutar `Run As --> Maven Install` y luego ejecutar `Run As --> Spring Boot App` 
* Desde el browser o con algún cliente REST (Postman, SoapUI o alguna extensión de tipo rest client de Google Chrome) hacer un GET a la URL 
http://localhost:8080/api/pronostico/sistemasolar/

#### Configuracines disponibles
Los siguientes parámetros de configuración de la aplicación se encuentran en el archivo `src\main\resources\application.properties` y se pueden editar.
* El ciclo dura 360 dias, luego los pronosticos se repiten 
  DEFAULT: `dias.repite.ciclo.orbital=360`

* Tiempo de muestreo para pronosticar el periodo del clima. Tener en cuenta que si se quiere tener un mayor tiempo de muestreo, el tiempo de procesamiento del JOB que realiza la carga inicial va a ser MAYOR. 
`Si se pretende una frecuencia de muestreo por dia, el valor debe ser 1 (DEFAULT)`
`Si se pretende una frecuencia de muestreo por hora, el valor debe ser 0.0416 (Equivalente a 1/24)`
`Si se pretende una frecuencia de muestreo por minuto, el valor debe ser XXX (Equivalente a 1/1440)` 
`Si se pretende una frecuencia de muestreo por segundo, el valor debe ser YYY (Equivalente a 1/86400)`
  DEFAULT: `frecuencia.muestreo=1`

* Años a futuro para los cuales se quiere pronosticar
  DEFAULT: `pronosticar.aniosfuturos=10`

* Frecuencia en que se ejecuta el Job. 86400000 milisegundos es equivale a un día.
  DEFAULT: `frecuencia.ejecucion.job.pronosticarClima=86400000`


## Uso de la API
Según desde donde se acceda a la API, ofrecemos los siguientes recursos.
#### Despliegue local
La API se puede desplegar localmente y acceder a los siguientes recursos:
* Inicio: 
  http://localhost:8080/api/pronostico/sistemasolar/
* Consultar el clima de un día: 
  http://localhost:8080/api/pronostico/sistemasolar/clima?dia=1

#### Despliegue en Google Cloud con App Engine Standard
La API se encuentra hosteada en GCP `App Engine Standar` y como almacenamiento usa base de datos `MySQL 2.ª gen. 5.7` en `Google Cloud MySQL`.
Para acceder a la API se ofrecen los siguientes recursos:
* Inicio: 
  https://meli-galaxia.appspot.com/api/pronostico/sistemasolar/

* Consultar durante los proximos 10 años, el clima de un día: 
  https://meli-galaxia.appspot.com/api/pronostico/sistemasolar/clima?dia=3653

* Consultar durante los proximos 10 años, la cantidad de días que van haber con un determinado clima:
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

