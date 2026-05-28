# 🚀 GUÍA RÁPIDA DE INSTALACIÓN Y EJECUCIÓN
**Grupo 8 - HotelBooking**

---

## ⚡ Quick Start (5 minutos)

### Paso 1: Preparar Base de Datos MySQL

```bash
# Abrir MySQL y ejecutar el script
mysql -u root -p < script_database.sql

# O manualmente:
# 1. Abrir MySQL Workbench o command line
# 2. Ejecutar cada comando del archivo: script_database.sql
# 3. Verificar que la BD se creó:
SHOW DATABASES;
USE HotelBookingDB;
SHOW TABLES;
```

**Verificar datos de prueba**:
```sql
SELECT * FROM Usuarios;
-- Debe mostrar 2 usuarios de prueba
```

---

### Paso 2: Ejecutar Servidor .NET

```bash
# Abrir terminal en la carpeta: HotelBookingApi

# 1. Restaurar dependencias
dotnet restore

# 2. Ejecutar la API
dotnet run

# Salida esperada:
# info: Microsoft.Hosting.Lifetime[14]
# Now listening on: http://localhost:5000
```

**Verificar que funciona**:
- Abrir navegador: `http://localhost:5000/swagger`
- Debe mostrar documentación de API

---

### Paso 3: Configurar Android

#### Opción A: Emulador

1. **Abrir Android Studio**
2. **AVD Manager** → Crear o seleccionar emulador (API 24+)
3. **Ejecutar emulador**
4. **RetrofitClient.kt** → Verificar `BASE_URL = "http://10.0.2.2:5000"`

#### Opción B: Celular Físico

1. **Conectar celular al PC** (USB o WiFi)
2. **Habilitar Depuración USB** en celular
3. **Obtener IP de la PC**:
   ```bash
   # Windows
   ipconfig
   # Buscar: IPv4 Address (ejemplo: 192.168.x.x)
   ```
4. **RetrofitClient.kt** → Cambiar `BASE_URL = "http://192.168.x.x:5000"`

---

### Paso 4: Compilar y Ejecutar Android

```bash
# Terminal en carpeta: HotelBookingApp

# 1. Build
./gradlew build

# 2. Run (abre app en emulador/dispositivo)
./gradlew installDebug

# O desde Android Studio:
# - Abrir el proyecto
# - Build → Build Bundle(s) / APK(s) → Build APK(s)
# - Run → Run 'app'
```

---

## ✅ Checklist de Verificación

- [ ] MySQL ejecutándose
- [ ] Base de datos "HotelBookingDB" creada
- [ ] Tabla "Usuarios" con datos de prueba
- [ ] Servidor .NET ejecutándose en puerto 5000
- [ ] Swagger accesible en `http://localhost:5000/swagger`
- [ ] Emulador/dispositivo Android conectado
- [ ] BASE_URL configurada correctamente en RetrofitClient.kt
- [ ] App compilada exitosamente

---

## 🧪 Primeras Pruebas

### Test 1: Registro
1. Abrir app
2. Clic en "Registrarse"
3. Llenar formulario con nuevos datos:
   ```
   Cédula: 9876543210
   Nombre: María
   Apellido: López
   Celular: 3159876543
   Correo: maria@example.com
   Contraseña: 654321
   Confirmar: 654321
   ```
4. Clic "Registrar"
5. ✅ Debe mostrar: "✅ Registro exitoso"
6. ✅ Vuelve a login

### Test 2: Login con datos de prueba
1. Clic "Login" (en home de registro)
2. Ingresar:
   ```
   Correo: juan@example.com
   Contraseña: 123456
   ```
3. Clic "Login"
4. ✅ Debe ir a HomeActivity
5. ✅ Debe mostrar: "¡Bienvenido, Juan!"

### Test 3: Logout
1. Desde HomeActivity
2. Clic "Cerrar Sesión"
3. Clic "Sí" en confirmación
4. ✅ Vuelve a LoginActivity
5. ✅ Sesión limpia (no vuelve a HomePage si reabre app)

---

## 🔧 Troubleshooting Rápido

| Problema | Solución |
|----------|----------|
| "Connection refused" | Iniciar MySQL y servidor .NET |
| "Database does not exist" | Ejecutar `script_database.sql` |
| App no se conecta a API | Verificar BASE_URL en RetrofitClient.kt |
| "El correo ya está registrado" | Usar email diferente |
| "Credenciales incorrectas" | Usar: juan@example.com / 123456 |
| Error "No module named..." | Ejecutar `dotnet restore` |
| Gradle error | Ejecutar `./gradlew clean` |

---

## 📱 URLs y Puertos

| Componente | URL | Puerto |
|-----------|-----|--------|
| API .NET | http://localhost:5000 | 5000 |
| Swagger | http://localhost:5000/swagger | 5000 |
| MySQL | localhost | 3306 |
| Emulador (desde PC) | 10.0.2.2 | (especificar en BASE_URL) |

---

## 📂 Estructura de Carpetas Importante

```
movil/
├── HotelBookingApi/          ← Servidor .NET
│   ├── Program.cs
│   ├── appsettings.json      ← Editar si necesitas cambiar BD
│   └── Controllers/
│
├── HotelBookingApp/          ← App Android
│   └── HotelBookingApp/
│       ├── build.gradle      ← Dependencias
│       └── app/src/main/java/com/example/hotelbooking/
│           └── data/network/
│               └── RetrofitClient.kt  ← Cambiar BASE_URL aquí
│
├── script_database.sql        ← Ejecutar en MySQL
└── README.md
```

---

## 🎬 Diagrama de Flujo Completo

```
Usuario abre app
    ↓
SplashActivity (espera 2s)
    ↓
¿Sesión activa?
    ├─ SÍ → HomeActivity
    └─ NO → LoginActivity
            ↓
            ¿Qué hacer?
            ├─ "Registrarse" → RegisterActivity
            │                  ↓ (si exitoso)
            │                  → Vuelve a LoginActivity
            │
            └─ "Login" → Valida credenciales
                         ↓
                         ¿Válidas?
                         ├─ SÍ → Guarda sesión → HomeActivity
                         └─ NO → Muestra error → Permanece en Login
                         
            HomeActivity
            ├─ Muestra datos del usuario
            └─ Botón "Cerrar Sesión"
               ↓
               Confirma logout
               ↓
               Limpia sesión → LoginActivity
```

---

## 🔐 Datos de Prueba Incluidos

```
Usuario 1:
- Correo: juan@example.com
- Contraseña: 123456
- Nombre: Juan Pérez
- Cédula: 1023456789

Usuario 2:
- Correo: maria@example.com
- Contraseña: abcdef
- Nombre: María García
- Cédula: 1098765432
```

---

## 💡 Tips Importantes

1. **Emulador lento?** → Usar celular físico o emulador con aceleración de hardware
2. **Cambiar BD?** → Editar `appsettings.json` en HotelBookingApi
3. **Celular físico?** → Asegúrate que PC y celular estén en misma red WiFi
4. **¿Olvidaste URL?** → Emulador siempre es `10.0.2.2`, celular es IP de PC
5. **¿Qué es BCrypt?** → Es el algoritmo para encriptar contraseñas (muy seguro)

---

## 📞 Soporte

Si algo no funciona:

1. **Revisar logs** del servidor .NET
2. **Revisar Logcat** en Android Studio
3. **Verificar conectividad** entre componentes
4. **Limpiar cache**: `gradle clean && dotnet clean`
5. **Reiniciar emulador/dispositivo**

---

## ✨ Ahora sí... ¡A Presentar! 🎉

```
1. BD configurada ✅
2. Servidor ejecutándose ✅
3. App compilada ✅
4. Datos de prueba listos ✅
5. ¡Listo para demostración! ✅
```

---

**¡Proyecto completado para Grupo 8! 🚀**
