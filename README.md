# UniGraoVerso
Un ejercicio increíble con un nombre terrible.

El objetivo de esta práctica es transformar una aplicación de escritorio básica en un sistema robusto de gestión de datos astronómicos utilizando JPA (Jakarta Persistence API) e Hibernate.

## Persistencia en dos bases de datos

El reto principal consiste en lograr que la aplicación sea capaz de persistir datos de forma intercambiable entre dos motores de base de datos (MySQL y SQLite) **sin modificar apenas el código fuente.**

Para ello, he decidido poner dos botones de inicio. Sentíos libre de modificar el mecanismo para intercambiar entre un sistema y otro.

Conviene saber que:
- MySQL es un **servidor** de base de datos. Funciona con comunicaciones cliente-servidor en el puerto 3306.
- SQLite es una versión muy portable de MySQL. La base de datos se guarda en un fichero .db, y el usuario no necesita instalar nada para poder ejecutar la aplicación localmente.

## La estructura de la plantilla

La plantilla tiene todo el *frontend* de la aplicación ya realizado. Sigue el estándar Modelo-Vista-Controlador.

Deberías crear tus clases propias para manejar la persistencia. Idealmente, basta con modificar 

## Entregar

Un .zip con este repositorio o un link a un repositorio online. Puedes hacer un fork en github si lo prefieres. No borres la carpeta .git