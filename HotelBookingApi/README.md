# 🔧 HotelBooking API - Backend .NET
**Documentación Técnica - Grupo 8**

---

## 📋 Descripción

API REST desarrollada con .NET 10 que proporciona endpoints para:
- Registro de usuarios
- Autenticación (login)
- Gestión segura de contraseñas

---

## 🏗️ Estructura del Proyecto

```
HotelBookingApi/
├── Controllers/
│   └── AuthController.cs          # Endpoints de autenticación
├── Models/
│   ├── Usuario.cs                 # Modelo de entidad
│   └── DTOS/
│       └── AuthDTOs.cs            # Data Transfer Objects
├── Data/
│   └── AppDbContext.cs            # Contexto de EF Core
├── Program.cs                      # Configuración principal
├── appsettings.json               # Configuración
├── appsettings.Development.json    # Configuración desarrollo
└── HotelBookingApi.csproj         # Proyecto
```

---

## 🔌 Endpoints

### 1. POST /api/auth/register

**Descripción**: Registra un nuevo usuario

**Request Body**:
```json
{
  "cedula": "1023456789",
  "nombre": "Juan",
  "apellido": "Pérez",
  "celular": "3001234567",
  "correo": "juan@example.com",
  "contrasena": "123456"
}
```

**Response Success (200)**:
```json
{
  "mensaje": "Usuario registrado exitosamente."
}
```

**Response Error (409)**:
```json
{
  "mensaje": "El correo ya está registrado."
}
```

---

### 2. POST /api/auth/login

**Descripción**: Autentica un usuario

**Request Body**:
```json
{
  "correo": "juan@example.com",
  "contrasena": "123456"
}
```

**Response Success (200)**:
```json
{
  "id": 1,
  "nombre": "Juan",
  "apellido": "Pérez",
  "correo": "juan@example.com",
  "cedula": "1023456789",
  "celular": "3001234567",
  "mensaje": "Login exitoso"
}
```

**Response Error (401)**:
```json
{
  "mensaje": "Correo o contraseña incorrectos."
}
```

---

## 📊 Modelos de Datos

### Usuario (Entidad)
```csharp
public class Usuario
{
    public int Id { get; set; }
    public string Cedula { get; set; }
    public string Nombre { get; set; }
    public string Apellido { get; set; }
    public string Celular { get; set; }
    public string Correo { get; set; }
    public string Contrasena { get; set; }
}
```

### AuthDTOs (Request/Response)
```csharp
public class RegisterRequest
{
    [Required] public string Cedula { get; set; }
    [Required] public string Nombre { get; set; }
    [Required] public string Apellido { get; set; }
    [Required] public string Celular { get; set; }
    [Required] [EmailAddress] public string Correo { get; set; }
    [Required] [MinLength(6)] public string Contrasena { get; set; }
}

public class LoginRequest
{
    [Required] [EmailAddress] public string Correo { get; set; }
    [Required] public string Contrasena { get; set; }
}

public class LoginResponse
{
    public int Id { get; set; }
    public string Nombre { get; set; }
    public string Apellido { get; set; }
    public string Correo { get; set; }
    public string Cedula { get; set; }
    public string Celular { get; set; }
    public string Mensaje { get; set; }
}
```

---

## ⚙️ Configuración

### appsettings.json
```json
{
  "ConnectionStrings": {
    "DefaultConnection": "server=localhost;port=3306;user=root;password=;database=HotelBookingDB"
  },
  "Logging": {
    "LogLevel": {
      "Default": "Information"
    }
  },
  "AllowedHosts": "*"
}
```

### Cambios necesarios:
1. **user**: Cambiar `root` por tu usuario de MySQL
2. **password**: Cambiar por tu contraseña de MySQL
3. **database**: Asegurar que coincida con el nombre de la BD

---

## 🚀 Pasos para Ejecutar

### Requisitos Previos
- .NET 10 SDK
- MySQL Server
- Visual Studio o VS Code

### 1. Configurar Base de Datos
```bash
# Ejecutar script SQL
mysql -u root -p < script_database.sql

# Verifcar conexión
mysql -u root -p
USE HotelBookingDB;
SHOW TABLES;
```

### 2. Actualizar Cadena de Conexión
Modificar `appsettings.json` con tus credenciales de MySQL

### 3. Restaurar Dependencias
```bash
dotnet restore
```

### 4. Ejecutar Migraciones (si es necesario)
```bash
dotnet ef database update
```

### 5. Ejecutar la API
```bash
dotnet run
```

**Output esperado**:
```
info: Microsoft.Hosting.Lifetime[14]
      Now listening on: http://localhost:5000
```

---

## 🔒 Seguridad Implementada

### Validaciones
- ✅ DTOs con DataAnnotations
- ✅ Validación de campos obligatorios
- ✅ Validación de formato de correo
- ✅ Longitud mínima de contraseña

### Prevención de Duplicados
- ✅ Correo único en BD
- ✅ Cédula única en BD
- ✅ Validación en servidor

### Encriptación de Contraseñas
- ✅ BCrypt.Net para hash
- ✅ Comparación segura en login
- ✅ No se almacenan contraseñas en texto plano

### CORS
```csharp
builder.Services.AddCors(options =>
{
    options.AddPolicy("AllowAll", policy =>
        policy.AllowAnyOrigin().AllowAnyMethod().AllowAnyHeader());
});
```

---

## 🧪 Pruebas Manual con cURL o Postman

### Registrar Usuario
```bash
curl -X POST http://localhost:5000/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "cedula": "1023456789",
    "nombre": "Juan",
    "apellido": "Pérez",
    "celular": "3001234567",
    "correo": "juan@example.com",
    "contrasena": "123456"
  }'
```

### Login
```bash
curl -X POST http://localhost:5000/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "correo": "juan@example.com",
    "contrasena": "123456"
  }'
```

---

## 📦 Dependencias Principales

```xml
<ItemGroup>
    <PackageReference Include="Microsoft.EntityFrameworkCore" />
    <PackageReference Include="Pomelo.EntityFrameworkCore.MySql" />
    <PackageReference Include="BCrypt.Net-Next" />
    <PackageReference Include="Swashbuckle.AspNetCore" />
</ItemGroup>
```

---

## 🐛 Solución de Problemas

### Error: "Connection refused"
- Verificar que MySQL está ejecutándose
- Verificar credenciales en `appsettings.json`

### Error: "Database does not exist"
- Ejecutar el script SQL: `script_database.sql`
- Usar comando: `CREATE DATABASE HotelBookingDB;`

### Error: "The type initializer threw an exception"
- Limpiar cache: `dotnet clean`
- Restaurar dependencias: `dotnet restore`

---

## 📄 Swagger/OpenAPI

Para probar los endpoints en interfaz web:

1. Ejecutar: `dotnet run`
2. Abrir: `http://localhost:5000/swagger`
3. Probar endpoints directamente desde la UI

---

## 📝 Notas Finales

- El proyecto usa Entity Framework Core con MySQL
- Las contraseñas se hashean con BCrypt (algoritmo seguro)
- CORS está habilitado para permitir conexiones desde Android
- La API es stateless (cada request es independiente)
- Session se maneja en el cliente (Android) con SharedPreferences

---

**Documentación Backend - Grupo 8 ✅**
