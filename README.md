# UniGraoVerso
Un ejercicio increíble con un nombre terrible.

El objetivo de esta práctica es transformar una aplicación de escritorio básica en un sistema robusto de gestión de datos astronómicos utilizando JPA (Jakarta Persistence API) e Hibernate.

## Persistencia en dos bases de datos

El reto principal consiste en lograr que la aplicación sea capaz de persistir datos de forma intercambiable entre dos motores de base de datos (MySQL y SQLite) **sin modificar apenas el código fuente.**
Para ello, he decidido poner dos botones de inicio. Sentíos libre de modificar el mecanismo para intercambiar entre un sistema y otro.

**La aplicación final tiene que cargar datos desde ambos sistemas, y poder actualizar, añadir y eliminar planetas y sistemas solares.**


Conviene saber que:
- MySQL es un **servidor** de base de datos. Funciona con comunicaciones cliente-servidor en el puerto 3306.
- SQLite es una versión muy portable de MySQL. La base de datos se guarda en un fichero .db, y el usuario no necesita instalar nada para poder ejecutar la aplicación localmente.

## La estructura de la plantilla

La plantilla tiene todo el *frontend* de la aplicación ya realizado. Sigue el estándar Modelo-Vista-Controlador.
- Modelo: clases `Planet` y `SolarSystem`. Las clases que definen los datos y su estructura
- `UniGraoVerseController`: aquí se cargan y se centraliza el uso de datos
- `View`: consiste tanto en los archivos `.fxml` como en las clases `XViewController.java`. En estas, se maneja la lógica del frontend y se llama al controlador cuando se le requiere.
**Deberías crear tus clases propias para manejar la persistencia**

## Objetivos desglosados
Por si necesitas ir paso a paso:
- Añade al archivo `pom.xml` las dependencias necesarias de Hibernate, el driver de MySQL y SQLite
- Añade las etiquetas necesarias de Hibernate a las clases Planet y SolarSystem. Ten en cuenta que, si quieres que los planetas en SQL tengan una referencia a su SolarSystem correspondiente (recomendado), deberás añadir una referencia de cada Planet a su SolarSystem, junto a la lógica necesaria para esto.
- Crea el archivo `persistence.xml` en el directorio `resources/META-INF`. Deberás crear 2 unidades de persistencia
- Implementa las funcionalidades de persistencia. Es tu decisión editar y crear las clases necesarias para esto.
  - Nombres como `PlanetDAO` o `SolarSystemDAO` son bastantes adecuados. Estas clases técnicamente se consideran parte del model, aunque también puedes crear un package `DAO` para estas.
  - El `UniGraoVerseController` debe llamar a estas clases. Revisa todos los métodos que tiene, muchos de ellos deberán ser modificados.
  - En `PlayViewController` deberás editar el método `setupDeleteButton`. Puedes editar más métodos si lo crees conveniente, o si quieres añadir funcionalidades de manera opcional.
  - En `MainViewController` edita dos líneas para seleccionar la unidad de persistencia adecuada.
Edita 
## Entregar
- Un .zip con este repositorio o un link a un repositorio online. Puedes hacer un fork en github si lo prefieres. ¡No borres la carpeta `.git`!
- Un archivo `README.md` (borra/edita este) en el que expliques qué clases has creado, qué retos y problemas has tenido y las funcionalidades finales conseguidas.
- La base de datos exportada en formato tanto `.sql` (para MySQL) y el archivo `.db` (o como hayas decidido llamar a la base de datos SQLite)
- La base de datos exportada en formato tanto `.sql` (para MySQL) y el archivo `.db` (o como hayas decidido llamar a la base de datos SQLite)

## Problemas (y soluciones) comunes
¡Paciencia! Aquí iré subiendo los inconvenientes más importantes que encuentro.

- Para SQLite necesitamos la dependencia [Hibernate Community dialects](https://mvnrepository.com/artifact/org.hibernate.orm/hibernate-community-dialects). La versión tiene que coincidir con tu versión de Hibernate!. Además, en tu unidad de persistencia deberías incluir, entre otras, las siguientes _propierties_:
```xml
<property name="hibernate.connection.driver_class" value="org.sqlite.JDBC" />
<property name="hibernate.dialect" value="org.hibernate.community.dialect.SQLiteDialect"/>

```
Además, la url de SQLite tiene una estructura del tipo `jdbc:sqlite:universe.db`, siendo `universe.db` el fichero que almacenará la base de datos.

- Deberías borrar la generación de IDs cuanto antes. Este fragmento de código
```Java
		//TODO: borrar
        for (SolarSystem ss : solarSystems)
        {
            System.out.println("Loaded " + ss.getPlanets().size() + " planets " + " for " + ss.getName());

            List<Planet> planets = ss.getPlanets();
            for (int i = 0; i < planets.size(); i++)
            {
                planets.get(i).setId(i+1); // Id starts by 1
            }
        }
```
crea IDs repetidos para planetas de distintos sistemas solares. Esto te va a dar fallo al guardar en hibernate. Incluso te recomiendo borrar los métodos `setId()` para asegurarse que no asignamos los ids nosotros.

- Borra el archivo module-info.java! Este archivo **restringe** la comunicación entre dependencias, cosa que en nuestro caso nos dará más problemas que ventajas.

## Configuraciones Extra que hice
En la clase SolarSystem cambié el LiskedList al principio, para que fuera una List y luego se vovliera una linkedList

Planet.java

Anadi la anotacion @Entity para mapear la clase con la base de datos
Implemente un constructor vacio requerido por JPA
Configure la relacion @ManyToOne con SolarSystem
Anadi el campo solarSystem con @JoinColumn para la clave foranea

SolarSystem.java

Anadi la anotacion @Entity (estaba ausente en la version original)
Implemente un constructor vacio para JPA
Configure la relacion @OneToMany con Planet usando cascade
Estableci el mappedBy correcto para la relacion bidireccional


2. Implementacion de Clases DAO
   PlanetDAO.java
   Implemente las siguientes operaciones CRUD:

savePlanet() - Crear un nuevo planeta
loadPlanet() - Cargar planeta por ID
loadAllPlanets() - Cargar todos los planetas
updatePlanet() - Actualizar planeta existente
deletePlanet() - Eliminar planeta por ID

Cada metodo incluye:

Gestion correcta de transacciones
Manejo de errores con rollback
Cierre apropiado del EntityManager
Mensajes de log informativos

SolarSystemDAO.java
Implemente las siguientes operaciones CRUD:

saveSolarSystem() - Crear un nuevo sistema solar
loadSolarSystem() - Cargar sistema solar por ID
loadAllSolarSystems() - Cargar todos los sistemas solares
updateSolarSystem() - Actualizar sistema solar existente
deleteSolarSystem() - Eliminar sistema solar 


3. Refactorizacion del Controlador
   UniGraoVerseController.java
   Realice los siguientes cambios importantes:

Implemente el patron Singleton para garantizar una unica instancia
Anadi gestion del EntityManagerFactory y los DAOs
Implemente el metodo setPersistenceUnitName() para cambiar entre MySQL y SQLite
Refactorice loadSolarSystems() para cargar datos desde la base de datos
Elimine el metodo initializeDefaultData() que insertaba datos hardcodeados
Implemente completamente los metodos:

addSolarSystem() - Ahora persiste en la BD
updatePlanet() - Ahora actualiza en la BD
addPlanet() - Ahora persiste con relacion al sistema solar
removePlanet() - Ahora elimina de la BD
removeSolarSystem() - Ahora elimina de la BD con cascade


Anadi el metodo close() para liberar recursos
Anadi metodos estaticos getInstance() y resetInstance()


4. Actualizacion de las Vistas
   PlayViewController.java

Modifique initialize() para usar el patron Singleton del controlador
Implemente completamente el metodo setupDeleteButton():

Anadi validacion de seleccion
Implemente dialogo de confirmacion
Integre llamadas a removePlanet() y removeSolarSystem()
Anadi recarga automatica de tablas despues de eliminar
Implemente manejo de excepciones con mensajes al usuario



MainViewController.java

Implemente newLoginMySQL() con llamada a setPersistenceUnitName("unidad_planetas")
Implemente newLoginSQLite() con llamada a setPersistenceUnitName("unidad_sqlite")
Ahora el usuario puede elegir que base de datos usar antes de iniciar