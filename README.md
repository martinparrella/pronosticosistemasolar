# Aplicacion de pronostico del clima en una galaxia lejana
Proyecto encargado de predecir el pronostico del clima en el sistema solar de una galaxia lejana.

# Supuestos

## Datos de la aplicación
### Prerequisitos
Instalar los siguientes items si desean revisar o ejecutar la aplicación de forma local
* Java 8 or newer.
* Maven 3.3.9 
* SpringBoot <version>2.1.6.RELEASE</version>
* Puedes importar el proyecto dentro de tu IDE. Yo he utilizado: [Spring Tools Suite](https://spring.io/tools) (STS)
* git command line tool (https://help.github.com/articles/set-up-git)

### Instrucciones
Para analizar el código o ejecutar la aplicación localmente se deben seguir los siguientes pasos:
1- Descargar el código fuente de mi Github
2- Desde [Spring Tools Suite](https://spring.io/tools) (STS) importar como proyecto Maven.
3- Ejecutar mvn spring-boot:run
4- Con algún cliente REST (Postman, SoapUI o alguna extensión de tipo rest client de Google Chrome) hacer un GET a la URL 
http://localhost:8080/api/pronostico/sistemasolar/


### Configuración
Se ofrecen los siguientes parámetros de configuración de la aplicación. Estos datos se encuentran en el archivo application.properties y se pueden editar.
#### El ciclo dura 360 dias, luego los pronosticos se repiten 
* DEFAULT: dias.repite.ciclo.orbital=360

#### Tiempo entre muestreo y muestreo para pronosticar el periodo del clima. Tener en cuenta que si se quiere tener un mayor tiempo de muestreo, el tiempo de procesamiento del JOB que realiza la carga inicial va a ser MAYOR. 
- Si se pretende una frecuencia de muestreo por dia, el valor debe ser 1 (DEFAULT)
- Si se pretende una frecuencia de muestreo por hora, el valor debe ser 0.0416 (Equivalente a 1/24)
- Si se pretende una frecuencia de muestreo por minuto, el valor debe ser XXX (Equivalente a 1/1440) 
- Si se pretende una frecuencia de muestreo por segundo, el valor debe ser YYY (Equivalente a 1/86400)
* DEFAULT: frecuencia.muestreo=1

#### Años a futuro para los cuales se quiere pronosticar
* DEFAULT: pronosticar.aniosfuturos=10

#### Frecuencia en que se ejecuta el Job. 86400000 milisegundos es equivale a un día.
* DEFAULT: frecuencia.ejecucion.job.pronosticarClima=86400000

### Uso de la API
Según desde donde se acceda a la API, ofrecemos los siguientes recursos.
#### Despliegue local
La API se puede desplegar localmente y acceder a los siguientes recursos:
* [Inicio] (http://localhost:8080/api/pronostico/sistemasolar/)
* [Consultar el clima de un día] (http://localhost:8080/api/pronostico/sistemasolar/clima?dia=1)

#### Despliegue en Google Cloud con App Engine Standard
La API se encuentra desplegada en GCP "App Engine Standar" y usa como base de datos "Google Cloud GCP MySQL".
Para acceder a la API se ofrecen los siguientes recursos:
* [Inicio]
  https://meli-galaxia.appspot.com/api/pronostico/sistemasolar/
* [Consultar el clima de un día]: 
  https://meli-galaxia.appspot.com/api/pronostico/sistemasolar/clima?dia=3653
* [Consultar la cantidad de días que va haber de un clima durante los proximos 10 años]: 
  https://meli-galaxia.appspot.com/api/pronostico/sistemasolar/periodo?clima=sequia


## Documentación de referencia
### Desarrollo
* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Reference doc](https://docs.spring.io/spring-cloud-gcp/docs/1.1.0.M3/reference/htmlsingle/#_spring_resources)
* [Reference doc](https://docs.spring.io/spring-cloud-gcp/docs/1.1.0.M3/reference/htmlsingle/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Sample](https://github.com/spring-cloud/spring-cloud-gcp/tree/master/spring-cloud-gcp-samples/spring-cloud-gcp-storage-resource-sample)
* [Samples](https://github.com/spring-cloud/spring-cloud-gcp/tree/master/spring-cloud-gcp-samples)

### Fisicas y matemáticas
* [Leyes de Kepler]: https://www.fisicalab.com/apartado/leyes-kepler#contenidos
* [Movimiento Circular Uniforme]: https://www.fisicalab.com/apartado/caracteristicas-mcu#contenidos
* [Distancia entre dos puntos]: http://geoutc.blogspot.com/2012/12/22-distancia-entre-dos-puntos.html 
* [Puntos colineales (Demostracion que 3 puntos están en la misma recta)]: https://www.youtube.com/watch?v=Bz6PrepV0Mo
* []: 
* []: 