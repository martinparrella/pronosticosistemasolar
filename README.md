# Aplicación de pronóstico del clima en una galaxia lejana
Proyecto encargado de pronosticar el clima en el sistema solar de una galaxia lejana.<br/>
El clima puede ser de "sequia", "lluvioso (con picos de lluvia)", "optimo" o normal.

## Supuestos y consideraciones para el desarrollo de la aplicación
* Según los datos proporcinados, se asume como clima inicial, un período de "sequia", es decir que los planetas estan alineados verticalmente al sol, y el sol se encuenta en el Punto(0, 0) del eje cartesiano.
* Según los datos proporcinados respecto a la velocidad angular (grados/dia), y considerando que los planetas giran a velocidad constante, realizando un movimiento circular uniforme, podemos calcular el período que tardarán cada uno de ellos en girar alrededor del sol:<br/>
``` 
Si el planeta "Ferengi" se desplaza 1grados/día, tardará 360 días en dar la vuelta alrededor del sol.
Si el planeta "Betasoide" se desplaza 3 grados/día, tardará 120 días en dar la vuelta alrededor del sol.
Si el planeta "Vulcano" se desplaza 5 grados/día, tardará 72 días en dar la vuelta alrededor del sol.
``` 
* De acuero al análisis anterior, deducimos que un ciclo durará 360 días. A partir del día 361 los pronósticos se repiten año tras año. 
* Los días se cuentan desde el día 0 (día inicial) hasta el mismo día dentro de 10 años (día final)
* Si la alineación de planetas, no pertenece a ninguno de los períodos definidos en el enunciado, el clima es considerado com "normal".

## Prerequisitos
La aplicación fue construida con los siguientes tecnologías:
* [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [Maven](https://maven.apache.org/download.cgi) (al menos 3.5)
* [SpringBoot](https://spring.io/) (Version 2.1.6.RELEASE)
* [git command line tool](https://help.github.com/articles/set-up-git)

### Opcionales
Opcionalmente, si desean revisar el codigo o actualizar la aplicación en GCP, se recomienda instalar:  
* [Spring Tools Suite](https://spring.io/tools) (IDE Eclipse utilizado para desarrollo)
* [Google Cloud SDK](https://cloud.google.com/sdk/) (gcloud command line tool)

## Acceso WEB
Además de la API, se puede acceder al sitio web, para obtener el pronostico del clima:  
https://meli-galaxia.appspot.com/  
Accediendo al sitio, se puede consultar la cantidad de días de cierto clima (sequia, lluvia, optimo, normal) y el día con pico máximo de lluvia para los próximos 10 años. O sea, esto **contesta las preguntas del enunciado del problema, que eran**:
Predecir en los próximos 10 años:
1. ¿Cuántos períodos de sequía habrá?  
2. ¿Cuántos períodos de lluvia habrá y qué día será el pico máximo de lluvia?  
3. ¿Cuántos períodos de condiciones óptimas de presión y temperatura habrá?  

## Acceso y uso de la API
La API ofrece los siguientes recursos. 
En todos los casos se recomienda setear en los Headers del llamado GET `Key: Accept - Value: application/json`  

#### Despliegue en Google Cloud con App Engine Standard
La API se encuentra hosteada en GCP [App Engine Standard](https://cloud.google.com/appengine/docs/standard/java/) y como almacenamiento usa base de datos `MySQL 2.ª gen. 5.7` en [Google Cloud MySQL](https://cloud.google.com/sql/docs/mysql/quickstart).
Para acceder a la API se ofrecen los siguientes recursos:
* Inicio:  
  https://meli-galaxia.appspot.com/api/sistemasolar/pronostico/  
    
* Consultar durante los próximos 10 años, el clima de un día. Esto es lo que requería el enuncunciado del problema, que era **generar una API REST la cual devuelve en formato JSON la condición climática del día consultado**: <br/>
  https://meli-galaxia.appspot.com/api/sistemasolar/pronostico/clima?dia=0  <br/>


| Campo | Descripcion | 
| ----- | --------- | 
| dia   | El día puede ser 0 (dia inicial) y todos los valores intermedios hasta el 3653 (dia final, que es la cantidad de dias que hay desde la fecha actual hasta 10 años a futuro) |   

Response
```
dia=0     => { "dia":0,"clima":"sequia" }  
dia=566   => { "dia":0,"clima":"lluvia" }  
dia=3653  => { "dia":0,"clima":"normal" }  
dia=3680  => { "fecha": "04-08-2019 04:02:04",
               "status": 404,
               "statusMessage": "NOT_FOUND",
               "error": "com.meli.pronostico.sistemasolar.exceptions.DiaNotFoundException",
               "message": "El parametro dia no es valido. Los valores permitidos para el parametro dia son numeros enteros entre 0 y 3653",
               "path": "/api/sistemasolar/pronostico/clima"
             }  
```

* Consultar la cantidad de días de cierto clima (sequia, lluvia, optimo, normal) y el día o días con pico máximo de lluvia para los próximos 10 años. Esta es otra manera de acceder a la respuesta del problema, o sea que esto tambien **contesta las preguntas del enunciado del problema mencionadas anteriormente**: <br/>
  https://meli-galaxia.appspot.com/api/sistemasolar/pronostico/en-una-decada/cantidad-diasxclima-con-pico-lluvia  

Response
```
{
    "pronostico_cant_dias_x_clima": [
        {
            "clima": "sequia",
            "cantidad_dias": 41
        },
        {
            "clima": "normal",
            "cantidad_dias": 2425
        },
        {
            "clima": "lluvia",
            "cantidad_dias": 1187
        },
        {
            "clima": "optimo",
            "cantidad_dias": 0
        }
    ],
    "picos_maximo_de_lluvia": [
        {
            "dia": 72,
            "mensaje": "El perímetro del triangulo del día es: 6263.197468741897"
        },
        {
            "dia": 108,
            "mensaje": "El perímetro del triangulo del día es: 6263.197468741897"
        },
        {
            "dia": 252,
            "mensaje": "El perímetro del triangulo del día es: 6263.197468741897"
        },
        .
        * Mas resultados aquí. No se muestran x un tema de espacio en el README
        .
        {
            "dia": 3528,
            "mensaje": "El perímetro del triangulo del día es: 6263.197468741897"
        }
    ]
}
```

## Instrucciones instalación local
Para analizar el código o ejecutar la aplicación localmente se deben seguir los siguientes pasos:
Antes de comenzar, descargar el código fuente
```
git clone https://github.com/martinparrella/pronosticosistemasolar.git
```
#### Ejecución desde linea de comandos CMD
* Desde la linea de comandos, ubicarse en el directorio donde se haya descargado el proyecto y ejecutar:<br/>
`mvnw -DskipTests spring-boot:run`
* Desde el browser o con algún cliente REST (Postman, SoapUI o alguna extensión de tipo rest client de Google Chrome) hacer un GET a la URL 
http://localhost:8080/api/sistemasolar/pronostico/  

#### Ejecución desde el IDE Eclipse
* Desde Spring Tools Suite importar como proyecto Maven.  `File -> Import -> Maven -> Existing Maven project`
* Sobre el proyecto ejecutar `Run As -> Maven Install` y luego ejecutar `Run As -> Spring Boot App` 
* Desde el browser o con algún cliente REST (Postman, SoapUI o alguna extensión de tipo rest client de Google Chrome) hacer un GET a la URL 
http://localhost:8080/api/sistemasolar/pronostico/  

#### Configuraciones disponibles
Los siguientes parámetros de configuración de la aplicación se encuentran disponibles en el archivo de configuración  `src\main\resources\application.properties`. El mismo se pueden editar ante cambios en los datos de entrada con el cuenta actualmente el servicio de pronóstico. Ante cambios de configuración se debe realizar un deploy de la aplicación.  
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

#### Despliegue local (Pruebas)
La API se puede desplegar localmente y acceder a los siguientes recursos:
* Inicio:  
  http://localhost:8080/api/sistemasolar/pronostico/
* Consultar durante los próximos 10 años, el clima de un día: <br/>
  http://localhost:8080/api/sistemasolar/pronostico/clima?dia=0
* Consultar la cantidad de días de cierto clima (sequia, lluvia, optimo, normal) y el día con pico máximo de lluvia para los próximos 10 años:  
  http://localhost:8080/api/sistemasolar/pronostico/en-una-decada/cantidad-diasxclima-con-pico-lluvia


## Documentación de referencia
#### Física y matemática
* [Leyes de Kepler](https://www.fisicalab.com/apartado/leyes-kepler#contenidos)
* [Movimiento Circular Uniforme](https://www.fisicalab.com/apartado/caracteristicas-mcu#contenidos)
* [Distancia entre dos puntos](http://geoutc.blogspot.com/2012/12/22-distancia-entre-dos-puntos.html)
* [Puntos colineales](https://www.youtube.com/watch?v=Bz6PrepV0Mo)
* [Verificar si un punto pertenece a una recta](https://www.unprofesor.com/matematicas/comprobar-si-un-punto-pertenece-a-una-recta-181.html) 
* [verificar si un punto 0.0 esta dentro de un triangulo](https://www.dokry.com/1885) 

#### Desarrollo
* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Reference doc](https://docs.spring.io/spring-cloud-gcp/docs/1.1.0.M3/reference/htmlsingle/#_spring_resources)
* [Reference doc](https://docs.spring.io/spring-cloud-gcp/docs/1.1.0.M3/reference/htmlsingle/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)



