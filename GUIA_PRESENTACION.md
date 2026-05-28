# 🎓 GUÍA DE PRESENTACIÓN DEL PROYECTO
**Grupo 8 - HotelBooking App**

---

## 📊 Estructura de la Presentación (15 minutos)

### 1. INTRODUCCIÓN (1 minuto)
```
"Buenos días, somos el Grupo 8 y presentamos HotelBooking,
un sistema de gestión de usuarios para reservas de hoteles."

Mostrar:
- Logo/pantalla de splash
- Nombre del proyecto
```

---

### 2. OBJETIVO DEL PROYECTO (1 minuto)

**Explicar:**
- Aplicación Android nativa
- Integración con API REST (.NET)
- Base de datos MySQL
- Autenticación segura

**Mostrar diagrama:**
```
[Android App] ←→ [API .NET] ←→ [MySQL BD]
```

---

### 3. FUNCIONALIDADES IMPLEMENTADAS (3 minutos)

#### A. Splash Screen (30 segundos)
**Pantalla**: SplashActivity  
**Duración**: 2 segundos  
**Función**: Redirección automática según sesión  

```
Demostrar:
1. Abrir app
2. Mostrar splash (2 segundos)
3. Redirecciona a Login o Home según estado
```

#### B. Registro de Usuario (1 minuto)
**Campos**:
- Cédula
- Nombre  
- Apellido
- Celular
- Correo
- Contraseña + Confirmar

**Validaciones**:
- Campos obligatorios
- Formato de correo
- Contraseña ≥ 6 caracteres
- Contraseñas coinciden

```
Demostración:
1. Clic en "Registrarse"
2. Llenar formulario con NUEVOS datos (no duplicar)
   Cédula: 9876543210
   Nombre: Pedro
   Apellido: Rodríguez
   Celular: 3001111111
   Correo: pedro@example.com
   Contraseña: 123456
   Confirmar: 123456
3. Clic "Registrar"
4. Mostrar: ✅ "Registro exitoso"
5. Vuelve a pantalla de login
```

#### C. Autenticación/Login (1 minuto)
**Campos**:
- Correo
- Contraseña

**Validaciones**:
- Campos no vacíos
- Correo válido
- Verificación en BD

```
Demostración:
1. Ingresar credenciales de prueba:
   Correo: juan@example.com
   Contraseña: 123456
2. Clic "Login"
3. Mostrar: "Validando..." (ProgressBar)
4. Redirige a HomeActivity
```

#### D. Página Principal/Home (1 minuto)
**Información mostrada**:
- Bienvenida personalizada
- Nombre del usuario
- Email
- Cédula
- Celular

```
Demostración:
1. Mostrar pantalla HomeActivity
2. Leer: "¡Bienvenido, Juan!"
3. Mostrar todos los datos del usuario
4. Explicar: "Los datos vienen de la BD a través de la API"
```

#### E. Logout (30 segundos)
**Función**: Cerrar sesión segura

```
Demostración:
1. Clic "Cerrar Sesión"
2. Mostrar diálogo: "¿Estás seguro?"
3. Clic "Sí"
4. Vuelve a Login
5. Confirmar: "Sesión está limpia"
```

---

### 4. ARQUITECTURA TÉCNICA (4 minutos)

#### A. Backend (.NET) (2 minutos)

**Mostrar en navegador**: `http://localhost:5000/swagger`

**Explicar endpoints**:

1. **POST /api/auth/register**
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

2. **POST /api/auth/login**
```json
{
  "correo": "juan@example.com",
  "contrasena": "123456"
}
```

**Pruebas en Swagger**:
1. Clic en POST /api/auth/register
2. Clic "Try it out"
3. Cambiar datos (usar nuevos para no duplicar)
4. Clic "Execute"
5. Mostrar respuesta exitosa (200)

Repetir con /api/auth/login

#### B. Base de Datos (1 minuto)

**Mostrar estructura**:
```sql
USE HotelBookingDB;
SHOW TABLES;
DESCRIBE Usuarios;
SELECT * FROM Usuarios;
```

**Explicar**:
- Tabla Usuarios con campos requeridos
- Índices en Correo y Cédula (para búsquedas rápidas)
- Contraseñas hasheadas con BCrypt
- Campos timestamp (FechaRegistro, ActualizadoEn)

#### C. Frontend Android (1 minuto)

**Mostrar estructura en Android Studio**:
```
ui/
  ├── splash/
  ├── login/
  ├── register/
  └── home/
data/
  ├── network/ (Retrofit)
  ├── model/   (DTOs)
  └── repository/
utils/
  └── SessionManager (SharedPreferences)
```

**Explicar MVVM**:
- ViewModel + LiveData
- Repository pattern
- Separación de capas

---

### 5. SEGURIDAD (2 minutos)

**Mostrar implementación**:

#### A. Contraseñas
```
Código backend:
string hash = BCrypt.Net.BCrypt.HashPassword(request.Contrasena);
bool esValido = BCrypt.Net.BCrypt.Verify(contrasena, usuario.Contrasena);
```
✅ Never storing plain text passwords

#### B. Validaciones
```
Cliente (Android):
- Patrón EMAIL_ADDRESS
- Longitud mínima
- Campos obligatorios

Servidor (.NET):
- DataAnnotations
- Búsqueda de duplicados
- ModelState validation
```

#### C. Prevención de Duplicados
```
✅ Correo único (índice UNIQUE en BD)
✅ Cédula única (índice UNIQUE en BD)
✅ Validación doble (cliente + servidor)
```

#### D. CORS
```csharp
options.AddPolicy("AllowAll", policy =>
    policy.AllowAnyOrigin().AllowAnyMethod().AllowAnyHeader());
```
✅ Permite conexión desde Android

---

### 6. DEMO EN VIVO (2 minutos)

**Escenario 1: Registro Fallido (Email duplicado)**
```
1. Ir a Registrarse
2. Usar email de usuario existente: juan@example.com
3. Llenar otros campos
4. Clic Registrar
5. Mostrar error: "El correo ya está registrado"
```

**Escenario 2: Login Fallido (Contraseña incorrecta)**
```
1. Volver a Login
2. Ingresar: juan@example.com
3. Contraseña: 999999 (incorrecta)
4. Clic Login
5. Mostrar error: "Credenciales incorrectas"
```

**Escenario 3: Flujo Completo (Exitoso)**
```
1. Registrar nuevo usuario
2. Login con las nuevas credenciales
3. Ver HomeActivity con datos
4. Logout
5. Verificar que vuelve a Login
```

---

## 📋 CHECKLIST ANTES DE PRESENTAR

### Verificaciones Previas
- [ ] MySQL ejecutándose
- [ ] Servidor .NET en puerto 5000
- [ ] Swagger accesible: http://localhost:5000/swagger
- [ ] Emulador/dispositivo conectado
- [ ] App compilada y lista
- [ ] BASE_URL configurada correctamente
- [ ] Datos de prueba en BD (juan@example.com)
- [ ] Credenciales escritas en papel (por si acaso)

### Preparación de Pantalla
- [ ] Abrir Android Studio con emulador/dispositivo listo
- [ ] Abrir navegador en Swagger
- [ ] Abrir MySQL o Workbench
- [ ] Tener PowerPoint o presentación visual
- [ ] Laptop conectada a proyector

### Documentación Lista
- [ ] README.md impreso o en mano
- [ ] Script SQL disponible
- [ ] Código fuente en USB
- [ ] Capturas de pantalla

---

## 🎬 SCRIPT DE PRESENTACIÓN

### Introducción
```
"Hola, somos el grupo 8. Presentamos HotelBooking, una aplicación 
de autenticación y gestión de usuarios para un sistema de reserva 
de hoteles.

La solución integra tres capas:
- Frontend: Android nativo en Kotlin
- Backend: API REST en .NET
- Datos: MySQL

Vamos a demostrar todas las funcionalidades..."
```

### Durante Demo
```
"Ven cómo la app:
1. Muestra splash screen al iniciarse
2. Permite registrarse con validaciones
3. Previene duplicados consultando la BD
4. Autentica con credenciales cifradas
5. Guarda sesión en el dispositivo
6. Muestra datos del usuario en home
7. Permite logout seguro"
```

### Cierre
```
"La arquitectura está dividida en capas para:
- Mantenimiento fácil
- Escalabilidad
- Reutilización de código
- Testing automatizado

Tecnologías usadas:
- Kotlin: Seguridad de tipos, moderno
- .NET: Framework robusto
- MySQL: Base de datos fiable
- BCrypt: Seguridad en contraseñas
- MVVM: Arquitectura profesional

¿Preguntas?"
```

---

## 🎯 PUNTOS CLAVE PARA ENFATIZAR

1. **Seguridad**
   - ✅ Contraseñas hasheadas
   - ✅ Validaciones dobles
   - ✅ Prevención de SQL injection

2. **Experiencia de Usuario**
   - ✅ UI intuitiva
   - ✅ Mensajes de error claros
   - ✅ Loading indicators
   - ✅ Confirmaciones importantes

3. **Arquitectura**
   - ✅ Separación de responsabilidades
   - ✅ MVVM pattern
   - ✅ Repository pattern
   - ✅ Principios SOLID

4. **Integración**
   - ✅ Retrofit para comunicación
   - ✅ Gson para serialización
   - ✅ SharedPreferences para persistencia
   - ✅ Coroutines para asincronía

---

## 📹 RECOMENDACIONES PARA FILMAR

Si necesitas grabar video:

1. **Calidad**: 1080p mínimo
2. **Velocidad**: Grabar más lento para ver bien
3. **Audio**: Explicar mientras demuestras
4. **Edición**: Quitar esperas innecesarias
5. **Subtítulos**: Si presentarás a grupo grande

---

## ⏱️ DISTRIBUCIÓN DE TIEMPO (15 minutos)

```
0:00 - 1:00   Introducción
1:00 - 4:00   Funcionalidades (1 min cada una)
4:00 - 8:00   Arquitectura técnica
8:00 - 10:00  Seguridad
10:00 - 13:00 Demo en vivo
13:00 - 15:00 Preguntas + Cierre
```

---

## 🎁 EXTRAS PARA IMPRESIONAR

1. **Mostrar logs del servidor**
   - Ver en consola de .NET cada request/response
   - Explicar las validaciones ejecutándose

2. **Mostrar BD en tiempo real**
   - Ejecutar SELECT antes y después de registrar
   - Mostrar datos nuevos siendo guardados

3. **Manejo de errores**
   - Desconectar internet y mostrar error graceful
   - Mostrar validaciones rechazando entrada inválida

4. **Performance**
   - Mostrar ProgressBar durante llamadas
   - Explicar uso de Coroutines para no bloquear UI

---

## 📸 CAPTURAS RECOMENDADAS

Tomar screenshots de:
- [ ] Splash Screen
- [ ] Pantalla de Login
- [ ] Pantalla de Registro
- [ ] Mensaje de error (duplicado)
- [ ] Mensaje de éxito
- [ ] HomeActivity
- [ ] Swagger con endpoints
- [ ] Terminal de servidor
- [ ] MySQL con datos

---

## 🏁 RECORDAR

✅ Hablar claro y pausado  
✅ Mantener contacto visual  
✅ No apresurar la demo  
✅ Permitir preguntas  
✅ Estar preparado para preguntas técnicas  
✅ Mostrar seguridad en el conocimiento  
✅ Mencionar desafíos que superaron  
✅ Explicar decisiones de diseño  

---

**¡Listo para presentar con confianza! 🚀**
