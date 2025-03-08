# App-Stripe

Una aplicación Spring Boot que integra la pasarela de pago Stripe con un frontend Vaadin.

## Descripción General

Este proyecto demuestra una aplicación de procesamiento de pagos construida con Spring Boot que utiliza Stripe como pasarela de pago y Vaadin para la interfaz de usuario. La aplicación permite a los usuarios realizar pagos a través de un proceso de checkout y visualizar los resultados del pago.

## Tecnologías

- Java 21
- Spring Boot 3.3.6
- Vaadin 24.5.5
- API de Stripe (v28.1.0)
- PostgreSQL
- Docker Compose
- Maven

## Estructura del Proyecto

```plaintext
├── src
│   ├── main
│   │   ├── frontend
│   │   │   ├── generated
│   │   │   ├── themes/my-app
│   │   │   └── index.html
│   │   ├── java/com/geovannycode/app
│   │   │   ├── domain
│   │   │   │   ├── model
│   │   │   │   │   ├── Currency.java
│   │   │   │   │   ├── PaymentEntity.java
│   │   │   │   │   ├── PaymentRequest.java
│   │   │   │   │   └── PaymentResponse.java
│   │   │   │   ├── PaymentRepository.java
│   │   │   │   └── PaymentService.java
│   │   │   ├── web
│   │   │   │   ├── exception
│   │   │   │   │   └── GlobalExceptionHandler.java
│   │   │   │   └── view
│   │   │   │       ├── CheckoutView.java
│   │   │   │       ├── MainLayout.java
│   │   │   │       └── ResultView.java
│   │   │   └── AppStripeApplication.java
│   │   └── resources
│   │       └── application.yaml
│   └── test
│       └── java/com/geovannycode/app
├── docker-compose.yaml
├── pom.xml
└── README.md
```

## Características

- Integración con la API de pagos de Stripe
- Frontend Vaadin responsive
- Flujo de procesamiento de pagos
- Gestión de entidades de pago con JPA
- Manejo global de excepciones
- Soporte de Docker Compose para desarrollo local

## Prerrequisitos

- JDK 21
- Maven
- Docker y Docker Compose (para desarrollo local)
- Cuenta de Stripe y claves API

## Comenzando

### Configuración

1. Clonar el repositorio
   ```bash
   git clone https://github.com/tuusuario/app-stripe.git
   cd app-stripe
   ```

2. Configurar tus claves API de Stripe en `application.yaml`
   ```yaml
   stripe:
     api-key: tu_clave_secreta_de_stripe
     webhook-secret: tu_secreto_de_webhook_de_stripe
   ```

3. Configurar los ajustes de tu base de datos PostgreSQL en `application.yaml` o utilizar la configuración de Docker Compose proporcionada.

### Ejecutando la Aplicación

#### Usando Maven

```bash
mvn spring-boot:run
```

#### Usando Docker Compose

```bash
docker-compose up
```

La aplicación estará disponible en `http://localhost:8080`

## Desarrollo

### Compilación para Producción

```bash
mvn clean package -Pproduction
```

### Ejecutando Pruebas

```bash
mvn test
```

## Endpoints

- `/` - Punto de entrada principal de la aplicación (UI de Vaadin)
- `/checkout` - Página de checkout para procesar pagos
- `/result` - Página que muestra el resultado del pago

## Arquitectura

![Diagrama_Flowchart.png](../../../../Desktop/Diagrama_Flowchart.png)

## Flujo de Pago



## Esquema de Base de Datos

La aplicación utiliza una base de datos PostgreSQL con la siguiente entidad principal:

- `PaymentEntity`: Almacena información de pago incluyendo estado, monto y moneda.


## Autor

Geovanny Code