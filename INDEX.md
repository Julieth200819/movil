# 📚 ÍNDICE DEL PROYECTO - HotelBooking Grupo 8

> **Guía completa para navegar y ejecutar el proyecto de autenticación de usuarios**

---

## 🗂️ Estructura de Carpetas

```
movil/
├── 📄 README.md                    ← EMPIEZA AQUÍ (Visión general)
├── 📄 QUICK_START.md               ← Instalación en 5 minutos
├── 📄 GUIA_PRESENTACION.md         ← Cómo presentar el proyecto
├── 📄 CHECKLIST_ENTREGA.md         ← Verificación de completitud
├── 📄 INDEX.md (este archivo)      ← Mapa del proyecto
│
├── 📁 HotelBookingApi/             ← Backend .NET
│   ├── 📄 README.md                ← Documentación técnica
│   ├── 📄 script_database.sql      ← SQL para BD
│   ├── 📁 Controllers/
│   │   └── AuthController.cs
│   ├── 📁 Models/
│   │   ├── Usuario.cs
│   │   └── DTOS/AuthDTOs.cs
│   ├── 📁 Data/
│   │   └── AppDbContext.cs
│   ├── Program.cs
│   ├── appsettings.json            ← EDITAR: Credenciales MySQL
│   └── appsettings.Development.json
│
├── 📁 HotelBookingApp/             ← Frontend Android
│   └── HotelBookingApp/
│       ├── 📄 README.md            ← Documentación Android
│       ├── 📁 app/src/main/
│       │   ├── java/com/example/hotelbooking/
│       │   │   ├── 📁 ui/
│       │   │   │   ├── splash/     ← Pantalla de carga
│       │   │   │   ├── login/      ← Autenticación
│       │   │   │   ├── register/   ← Registro
│       │   │   │   └── home/       ← Dashboard
│       │   │   ├── 📁 data/
│       │   │   │   ├── network/    ← Retrofit + API
│       │   │   │   ├── model/      ← DTOs
│       │   │   │   └── repository/ ← Lógica
│       │   │   └── 📁 utils/
│       │   │       └── SessionManager.kt
│       │   ├── res/
│       │   │   ├── layout/         ← XML layouts
│       │   │   └── values/         ← Strings, colors
│       │   └── AndroidManifest.xml
│       ├── build.gradle            ← EDITAR: Dependencias
│       └── gradlew (build tool)
│
└── 📁 gradle/                      ← Configuración Gradle
    └── wrapper/
```

---

## 🚀 CÓMO EMPEZAR (Selecciona tu ruta)

### 🏃 Ruta Rápida (5 minutos)
```
1. Leer: QUICK_START.md
2. Ejecutar: script_database.sql en MySQL
3. Ejecutar: dotnet run (en HotelBookingApi)
4. Ejecutar: app Android
5. Probar: login/register
```

### 🚶 Ruta Completa (30 minutos)
```
1. Leer: README.md (visión general)
2. Leer: HotelBookingApi/README.md (entender backend)
3. Leer: HotelBookingApp/README.md (entender Android)
4. Configurar: BD, servidor, app
5. Ejecutar: todo
6. Probar: funcionalidades
```

### 🎓 Ruta de Estudio (1-2 horas)
```
1. Leer: README.md
2. Explorar: Código de AuthController.cs
3. Explorar: LoginActivity.kt y RegisterActivity.kt
4. Explorar: SessionManager.kt
5. Leer: Documentación técnica
6. Entender: Arquitectura MVVM
7. Entender: Flujo de datos
```

### 🎬 Ruta de Presentación (15 minutos)
```
1. Leer: GUIA_PRESENTACION.md
2. Verificar: CHECKLIST_ENTREGA.md
3. Preparar: Demos y explicaciones
4. Ejecutar: App y API
5. Presentar: Con confianza
```

---

## 📖 ARCHIVOS DE DOCUMENTACIÓN

### Generales
| Archivo | Propósito | Leer primero |
|---------|----------|-------------|
| **README.md** | Visión completa del proyecto | ✅ SÍ |
| **QUICK_START.md** | Instalación rápida | ✅ SÍ (si tienes prisa) |
| **GUIA_PRESENTACION.md** | Cómo presentar | ✅ Antes de presentar |
| **CHECKLIST_ENTREGA.md** | Verificación final | ✅ Antes de entregar |
| **INDEX.md** | Mapa de proyecto (este) | ℹ️ Referencia |

### Técnicos
| Archivo | Propósito | Para quién |
|---------|----------|-----------|
| **HotelBookingApi/README.md** | Backend técnico | Desarrolladores .NET |
| **HotelBookingApp/README.md** | Frontend técnico | Desarrolladores Android |
| **script_database.sql** | Base de datos | Administradores DB |

---

## 🔧 ARCHIVOS DE CONFIGURACIÓN A EDITAR

### 1. Backend - appsettings.json
**Ubicación**: `HotelBookingApi/HotelBookingApi/appsettings.json`

**Qué cambiar**:
```json
{
  "ConnectionStrings": {
    "DefaultConnection": "server=localhost;port=3306;user=root;password=;database=HotelBookingDB"
    //                                                          ↑ tu contraseña aquí
  }
}
```

### 2. Android - RetrofitClient.kt
**Ubicación**: `HotelBookingApp/HotelBookingApp/app/src/main/java/com/example/hotelbooking/data/network/RetrofitClient.kt`

**Qué cambiar**:
```kotlin
private const val BASE_URL = "http://10.0.2.2:5000/"  // Emulador
// Cambiar a:
private const val BASE_URL = "http://192.168.1.100:5000/"  // Celular físico
//                                         ↑ IP de tu PC
```

---

## 🗄️ BASE DE DATOS

### Crear BD
```bash
mysql -u root -p < HotelBookingApi/script_database.sql
```

### Verificar
```sql
USE HotelBookingDB;
SHOW TABLES;
SELECT * FROM Usuarios;  -- Debe mostrar 2 usuarios de prueba
```

### Credenciales de Prueba
```
Usuario 1:
- Email: juan@example.com
- Contraseña: 123456

Usuario 2:
- Email: maria@example.com
- Contraseña: abcdef
```

---

## ⚙️ EJECUCIÓN PASO A PASO

### Paso 1: Servidor .NET
```bash
cd HotelBookingApi
dotnet restore
dotnet run
# Debe funcionar en http://localhost:5000
# Swagger en http://localhost:5000/swagger
```

### Paso 2: App Android
```bash
cd HotelBookingApp/HotelBookingApp
./gradlew build
./gradlew installDebug
# O desde Android Studio: Run → Run 'app'
```

### Paso 3: Probar
```
1. Abre app
2. Clic "Registrarse" - Prueba nuevo usuario
3. Clic "Login" - Usa juan@example.com / 123456
4. Clic "Cerrar Sesión" - Prueba logout
```

---

## 🏗️ ARQUITECTURA

### Frontend (Android)
```
Activities
    ↓
ViewModels
    ↓
Repository
    ↓
RetrofitClient
    ↓
API .NET
```

### Backend (.NET)
```
HttpRequest
    ↓
AuthController
    ↓
AuthRepository/Service
    ↓
AppDbContext
    ↓
MySQL
```

### Flujo Completo
```
AndroidApp → Retrofit → .NET API → EF Core → MySQL
                ↓                                ↓
         (JSON Request)              (SQL Query)
                ↑                                ↓
         (JSON Response) ← Response ← Query Result
```

---

## 🔐 SEGURIDAD IMPLEMENTADA

✅ **Contraseñas**: BCrypt hash  
✅ **Validaciones**: Cliente + Servidor  
✅ **Duplicados**: Prevención en BD  
✅ **CORS**: Habilitado para Android  
✅ **Sesión**: No en servidor (sin estado)  

---

## 📱 PANTALLAS DE LA APP

### 1. SplashActivity
- **Archivo**: `ui/splash/SplashActivity.kt`
- **Duración**: 2 segundos
- **Función**: Redirecciona a Login o Home

### 2. LoginActivity
- **Archivo**: `ui/login/LoginActivity.kt`
- **Campos**: Correo, Contraseña
- **Validaciones**: Email válido, campos no vacíos
- **Acciones**: Login o ir a Registrarse

### 3. RegisterActivity
- **Archivo**: `ui/register/RegisterActivity.kt`
- **Campos**: Cédula, Nombre, Apellido, Celular, Correo, Contraseña (x2)
- **Validaciones**: Completas (duplicados, formato, longitud)
- **Acciones**: Registrar o Volver

### 4. HomeActivity
- **Archivo**: `ui/home/HomeActivity.kt`
- **Datos**: Bienvenida, nombre, email, cédula, celular
- **Acciones**: Logout

---

## 🔗 ENDPOINTS API

### POST /api/auth/register
```
Request:
{
  "cedula": "1023456789",
  "nombre": "Juan",
  "apellido": "Pérez",
  "celular": "3001234567",
  "correo": "juan@example.com",
  "contrasena": "123456"
}

Response (200):
{
  "mensaje": "Usuario registrado exitosamente."
}

Response (409):
{
  "mensaje": "El correo ya está registrado."
}
```

### POST /api/auth/login
```
Request:
{
  "correo": "juan@example.com",
  "contrasena": "123456"
}

Response (200):
{
  "id": 1,
  "nombre": "Juan",
  "apellido": "Pérez",
  "correo": "juan@example.com",
  "cedula": "1023456789",
  "celular": "3001234567",
  "mensaje": "Login exitoso"
}

Response (401):
{
  "mensaje": "Correo o contraseña incorrectos."
}
```

---

## 🧪 PRUEBAS RECOMENDADAS

### Test 1: Registro
```
1. Ir a Registrarse
2. Email nuevo: pedro@example.com
3. Rellenar todos los campos
4. Clic Registrar
5. ✅ Debe mostrar éxito
```

### Test 2: Duplicado
```
1. Intentar registrar con juan@example.com
2. ✅ Debe mostrar error "ya registrado"
```

### Test 3: Login
```
1. Correo: juan@example.com
2. Contraseña: 123456
3. Clic Login
4. ✅ Debe ir a HomeActivity
```

### Test 4: Logout
```
1. En HomeActivity clic "Cerrar Sesión"
2. Confirmar "Sí"
3. ✅ Vuelve a Login
```

---

## 🐛 SOLUCIÓN DE PROBLEMAS

| Error | Causa | Solución |
|-------|-------|----------|
| "Connection refused" | MySQL no ejecuta | `mysql.server start` |
| "Database does not exist" | BD no creada | Ejecutar script_database.sql |
| "Cannot reach API" | Servidor no ejecuta | `dotnet run` en HotelBookingApi |
| "Wrong BASE_URL" | Dirección incorrecta | 10.0.2.2 (emulador) o IP (celular) |
| "App crashes" | Permisos faltantes | Agregar INTERNET en AndroidManifest |
| "Login failure" | Credenciales incorrectas | Usar juan@example.com / 123456 |

---

## 📊 TECNOLOGÍAS USADAS

### Backend
- .NET 10
- Entity Framework Core
- MySql.EntityFrameworkCore
- BCrypt.Net-Next
- Swagger/OpenAPI

### Frontend
- Kotlin
- Android API 24+
- Retrofit 2
- Gson
- Coroutines
- ViewModel
- SharedPreferences

### Base de Datos
- MySQL
- InnoDB
- UTF-8 encoding

---

## 📚 REFERENCIAS RÁPIDAS

### Comando útiles

**MySQL**:
```bash
mysql -u root -p
SHOW DATABASES;
USE HotelBookingDB;
SELECT * FROM Usuarios;
```

**Backend**:
```bash
dotnet restore
dotnet run
dotnet clean
```

**Android**:
```bash
gradle build
gradle installDebug
gradle clean
```

---

## ✅ CHECKLIST PRE-PRESENTACIÓN

- [ ] BD configurada y con datos
- [ ] Servidor .NET corriendo en puerto 5000
- [ ] Swagger accesible
- [ ] App compilada
- [ ] BASE_URL correcta
- [ ] Emulador/dispositivo conectado
- [ ] Datos de prueba listos
- [ ] Presentación preparada

---

## 🎁 EXTRAS

### Para impresionar
- Mostrar logs del servidor
- Mostrar BD en tiempo real
- Explicar arquitectura MVVM
- Demostrar validaciones
- Hablar sobre seguridad
- Mostrar código limpio

### Documentos a mostrar
- README.md (general)
- HotelBookingApi/README.md (técnico)
- HotelBookingApp/README.md (técnico)
- GUIA_PRESENTACION.md (presentación)

---

## 🎯 CRONOGRAMA RECOMENDADO

```
Semana 1: Desarrollo
├─ Lunes: Setup inicial
├─ Martes: Código Android
├─ Miércoles: Código Backend
├─ Jueves: BD y testing
└─ Viernes: Documentación

Semana 2: Finalización
├─ Lunes: QA y bugs
├─ Martes: Documentación completa
├─ Miércoles: Preparar presentación
├─ Jueves: Ensayo presentación
└─ Viernes: Presentación final
```

---

## 📞 AYUDA RÁPIDA

**¿Dónde está...?**
- Code Android: `HotelBookingApp/HotelBookingApp/app/src/main/`
- Code Backend: `HotelBookingApi/`
- BD Script: `HotelBookingApi/script_database.sql`
- Docs: Archivos `.md` en raíz

**¿Cómo...?**
- Ejecutar: Ver QUICK_START.md
- Presentar: Ver GUIA_PRESENTACION.md
- Verificar: Ver CHECKLIST_ENTREGA.md
- Entender: Ver README.md

---

## 🏁 ESTADO DEL PROYECTO

```
✅ Backend: Completado
✅ Frontend: Completado
✅ Base de Datos: Completado
✅ Documentación: Completada
✅ Pruebas: Exitosas
✅ Listo para presentación
```

---

## 🎉 ¡PROYECTO LISTO!

Todos los componentes funcionan.  
Toda la documentación está lista.  
Estás preparado para presentar.  

**¡Adelante con el proyecto! 🚀**

---

**Grupo 8 - Proyecto Académico Desarrollo Móvil 2026**

*Última actualización: 2026-05-27*
