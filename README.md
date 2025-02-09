# challenge-link
Esto es una solución propuesta al desafío de Link

## Arquitectura
### Diagrama de secuencia
https://github.com/freefrozztea/challenge-link/blob/develop/diagrama-de-secuencia.png?raw=true

## Decisiones Técnica
### Excepciones personalizados
Agrego excepciones personalizadas ya que pérmiten manejar errores específicos inherentes a la lógica de negocio de forma clara y organizada.

### Patrón Strategy
Uso el patrón Strategy para que el sistema pueda decidir la estrategia correcta según el tipo de transacción

### Generar un UUID para transactionId
Se genera un UUID al crear una transacción del lado del Back, decido que es mejor manejarlo de este lado ya que es más seguro

### Excepciones Globales
Se maneja centralizadamente las excepciones. Esto para devolver respuestas consistentes en caso de errores

### Validación de los campos de entrada

### Agrego paginación y filtrado
Ya que es requisito del desafío implemento Pageable de Spring Data

### Inyección de dependencias
Se maneja los services inyectando dependencias para poder cumplir con los principios SOLID

### Idempotencia y uso de Redis
Implemento un Servicio IdempotencyService para evitar transacciones duplicadas. Se usa Redis para poder almacenar en memoria dicha transacción

### Enums para currency y validaciones de tipo de cambio
Se implementa un enum para los tipos de moneda soportados y además se genera uns servicio para tratar de validar en caso de requerirlo a futuro

### Transaccionalidad
Se implementa @Transactional en el servicio para la transaccionalidad

### Implementacion de un model por cada tipo de transaccion
Esto complejiza la consulta de todas las transacciones de un usuario ya que habria que ver cada tabla
Pero lo mantuve así porque prioricé el no tener tantos campos con valores vacíos 

### Implementación de un DTO por cada tipo de transacción
Esto ayuda ya que el usuario va a llenar los campos de información según el tipo de transacción

### Uso de MapStruct para mapear
Ya que es un mapper que no es manual y evita errores manuales de mapeo

## Deuda técnica
Aún no implemento patrón Saga para manejar transacciones distribuidas
Aún no uso mensajería para garantizar la consistencia eventual --> Kafka
Falta de caché para mejorar el rendimiento en operaciones de lectura --> @Cacheable
No tengo un mecanismo para particionar datos por usuario o región
Aún no tengo las pruebas unitarias y de integración implementadas
Falta de documentación API --> Swagger u OpenAPI