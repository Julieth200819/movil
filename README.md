# 🏨 HotelBooking - Sistema de Gestión de Usuarios
**Grupo 8 - Proyecto Académico Desarrollo Móvil**

> Aplicación Android que permite gestionar usuarios mediante autenticación integrada con API REST .NET y MySQL

---

## 📋 Descripción General

Este proyecto implementa un sistema completo de autenticación para una aplicación móvil de reservas de hoteles. La solución integra:

- **Frontend**: Android (Kotlin) con Activities y Retrofit
- **Backend**: API REST en .NET con Entity Framework
- **Base de Datos**: MySQL con gestión de usuarios

---

## 🎯 Funcionalidades Principales

### 1. ✅ Registro (Register)
- Cédula, Nombre, Apellido, Celular, Correo, Contraseña
- Validaciones en cliente y servidor
- Prevención de duplicados (correo y cédula)
- Contraseñas hasheadas con BCrypt

### 2. ✅ Login
- Autenticación con correo y contraseña
- Validación contra MySQL
- Gestión de sesión persistente
- Manejo de errores

### 3. ✅ Página Principal
- Mensaje de bienvenida personalizado
- Visualización de datos del usuario
- Información del perfil

### 4. ✅ Logout
- Cierre de sesión seguro
- Limpieza de datos
- Confirmación antes de cerrar sesión

---

## 🏗️ Arquitectura del Proyecto

```
movil/
├── HotelBookingApi/               # Backend .NET
│   ├── Controllers/
│   │   └── AuthController.cs
│   ├── Models/
│   │   ├── Usuario.cs
│   │   └── DTOS/
│   │       └── AuthDTOs.cs
│   ├── Data/
│   │   └── AppDbContext.cs
│   ├── appsettings.json
│   └── Program.cs
│
├── HotelBookingApp/               # Frontend Android
│   ├── app/src/main/java/com/example/hotelbooking/
│   │   ├── ui/
│   │   │   ├── login/
│   │   │   ├── register/
│   │   │   └── home/
│   │   ├── data/
│   │   │   ├── network/
│   │   │   ├── model/
│   │   │   └── repository/
│   │   └── utils/
│   │       └── SessionManager.kt
│   └── build.gradle
│
├── script_database.sql            # Script SQL
└── README.md                       # Este archivo
```

---

## 🔌 API Endpoints

| Endpoint | Método | Request Body | Response |
|----------|--------|------|----------|
| `/api/auth/register` | POST | `{cedula, nombre, apellido, celular, correo, contrasena}` | `{mensaje}` |
| `/api/auth/login` | POST | `{correo, contrasena}` | `{id, nombre, apellido, correo, cedula, celular, mensaje}` |

---

## 📱 Configuración de Android

### Base URL Retrofit
```kotlin
// Para emulador:
private const val BASE_URL = "http://10.0.2.2:5000/"

// Para celular físico:
private const val BASE_URL = "http://<IP_PC>:5000/"
```

### Permisos Requeridos
```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

### Dependencias
- Retrofit 2
- Gson
- ViewModel
- SharedPreferences
- Coroutines

---

## 🗄️ Base de Datos

### Tabla Usuarios
```sql
CREATE TABLE Usuarios (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    Cedula VARCHAR(20) UNIQUE NOT NULL,
    Nombre VARCHAR(100) NOT NULL,
    Apellido VARCHAR(100) NOT NULL,
    Celular VARCHAR(20) NOT NULL,
    Correo VARCHAR(100) UNIQUE NOT NULL,
    Contrasena VARCHAR(255) NOT NULL,
    FechaRegistro DATETIME DEFAULT CURRENT_TIMESTAMP,
    ActualizadoEn DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB;
```

---

## 🚀 Instrucciones de Ejecución

### Servidor .NET

1. **Verificar cadena de conexión** en `appsettings.json`:
   ```json
   "ConnectionStrings": {
     "DefaultConnection": "server=localhost;port=3306;user=root;password=;database=HotelBookingDB"
   }
   ```

2. **Ejecutar las migraciones**:
   ```bash
   dotnet ef database update
   ```

3. **Ejecutar la API**:
   ```bash
   dotnet run
   ```
   La API estará disponible en `http://localhost:5000`

### Android App

1. **Configurar emulador** (API 24+)
2. **Actualizar BASE_URL** en `RetrofitClient.kt` según corresponda
3. **Build y Run**:
   ```bash
   gradle build
   gradle installDebug
   ```

---

## 🧪 Datos de Prueba

El script SQL incluye dos usuarios de prueba:

| Correo | Contraseña |
|--------|-----------|
| juan@example.com | 123456 |
| maria@example.com | abcdef |

**Nota**: Las contraseñas están hasheadas con BCrypt

---

## 🔒 Seguridad

✅ **Implementado:**
- Hash de contraseñas con BCrypt
- Validación de correos duplicados
- Validación de cédulas duplicadas
- Validaciones en cliente y servidor
- CORS habilitado en la API

---

## 📝 Validaciones

### Cliente (Android)
- Campos obligatorios
- Formato de correo válido
- Longitud mínima de contraseña (6 caracteres)
- Confirmación de contraseña en registro
- Patrón de cédula (si es requerido)

### Servidor (.NET)
- Modelos con DataAnnotations
- Búsqueda de duplicados en BD
- BCrypt para verificación de contraseñas

---

## 🎓 Tecnologías Utilizadas

### Backend
- **.NET 10** - Framework web
- **Entity Framework Core** - ORM
- **MySQL** - Base de datos
- **BCrypt.Net** - Seguridad de contraseñas
- **Swagger/OpenAPI** - Documentación

### Frontend
- **Kotlin** - Lenguaje
- **Android** - Plataforma
- **Retrofit** - Cliente HTTP
- **Gson** - Serialización JSON
- **MVVM** - Arquitectura
- **SharedPreferences** - Almacenamiento local

---

## 📂 Archivos Entregables

- ✅ Código Android (Kotlin)
- ✅ Código Backend (.NET)
- ✅ Script SQL
- ✅ Documentación (README.md)
- ✅ Capturas/Evidencias
- ✅ Presentación del proyecto

---

## 🐛 Solución de Problemas

### La app no se conecta a la API
- Verificar que la API está ejecutándose en `http://localhost:5000`
- Si usas emulador: asegurate que la BASE_URL sea `http://10.0.2.2:5000`
- Si usas celular físico: cambiar IP por la de tu PC en la red local

### Error "Usuario ya existe"
- Verificar que el correo no esté registrado
- Verificar que la cédula no esté registrada
- Limpiar datos de prueba si es necesario

### Credenciales incorrectas en login
- Verificar que el correo sea exacto (case-sensitive)
- Verificar que la contraseña sea exacta (case-sensitive)
- Si olvidaste la contraseña, usa los datos de prueba

---

## 👨‍💻 Autor

**Grupo 8** - Proyecto Académico  
Desarrollo Móvil - 2026

---

## 📄 Licencia

Proyecto académico - Uso educativo únicamente

---

**¡Proyecto completado y listo para presentación! 🎉**
