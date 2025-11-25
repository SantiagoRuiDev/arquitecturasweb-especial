# 💡 Backend Entrega Especial

![Subdominios](/subdomains.png)

Desarrollo de servicios REST backend para una aplicación de renta de scooters electricos.

## 🚀 Necesario para un correcto funcionamiento
- Tener instalado [Java JDK 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html).
- Agregar las dependencias de [Maven](https://mvnrepository.com/)
- Docker para levantar la bd no-sql de MongoDB

## ⚙️ Consejos para testear el proyecto
Recomendamos configurar el archivo application.properties para un correcto funcionamiento del servicio. (Es nuestro caso ejecutamos este servicio con una base de datos local mediante XAMPP)

### Propiedades principales a configurar:
- **URL de conexión**: `jdbc:mysql://localhost:3306/scooter?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC`
- **Usuario**: `root`
- **Contraseña**: *(vacía por defecto, cambiar según tu entorno)*

## 📃 Microservicios
Los servicios mas destacados que ejecutan la logica requerida en los enunciados son los siguientes.

### **Chat Service**
* Expone una interfaz REST por la cual se puede enviar un prompt y realizar consultas.
* Por el momento solo hay dos consultas que trabajan de manera interna con los demas microservicios
* "¿Cual es el precio estimado de un viaje de la estacion 1 a la estacion 2?" (Ejemplo)
* "¿Cuanto he gastado desde el 11 de noviembre de 2025 hasta el 24 de noviembre de 2025?" (Ejemplo)

### **Auth Service**
* Permite registrarse e iniciar sesión para generar tokens y metodos de acceso.
* Forma parte de la seguridad en conjunto con el API Gateway

### **Payment Service**
* Gestiona los metodos de pago (MercadoPago, Visa Debito) que utilizan las cuentas para realizar recargas de creditos.
* Esto funciona como un Mock ya que al crear un metodo de pago se le asigna una cantidad de fondos que simulan ser el balance externo al servicio con el que pueden realizar operaciones 

### **Travel Service**
* Gestiona los viajes, inicia, finaliza, pausa y obtiene estadisticas de los viajes en la plataforma.
* Este servicio integra una base de datos no relacional, ya que creemos es la mas adecuada para un servicio de este estilo.

### **Scooter Service**
* Gestiona los monopatines y sus estados, genera estadisticas de uso.

> Creamos una colleción predefinida de consultas con Postman para agilizar el testing de los ejercicios. [Ver JSON](./Arquitecturas Integrador Especial.postman_collection.json)


##  Contacto
- Juan Manuel Santa Cruz - juanmasantax@gmail.com
- Rafael Iván Garcia - rafa.g6320@gmail.com
- Joel Kiehr - kiehrjoel@gmail.com
- Santiago Rui - srui@alumnos.exa.unicen.edu.ar