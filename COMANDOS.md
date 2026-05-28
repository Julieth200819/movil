# ⚙️ COMANDOS ÚTILES - REFERENCIA RÁPIDA

## 🚀 EJECUCIÓN RÁPIDA (copiar y pegar)

### 1️⃣ BASE DE DATOS

```bash
# Crear BD y tablas
mysql -u root -p < script_database.sql

# Verificar
mysql -u root -p
SHOW DATABASES;
USE HotelBookingDB;
SELECT * FROM Usuarios;
exit;
```

### 2️⃣ BACKEND .NET

```bash
# Navegar a carpeta
cd HotelBookingApi

# Restaurar dependencias
dotnet restore

# Compilar
dotnet build

# Ejecutar (MANTENER EJECUTÁNDOSE)
dotnet run

# En otra terminal - Probar API
curl http://localhost:5000/swagger
```

### 3️⃣ ANDROID

```bash
# Navegar a carpeta
cd HotelBookingApp/HotelBookingApp

# Limpiar
./gradlew clean

# Compilar
./gradlew build

# Instalar en emulador/dispositivo
./gradlew installDebug

# O con flag de depuración
./gradlew installDebug -d
```

---

## 🔧 CONFIGURACIÓN

### MySQL - Cambiar Credenciales
```
Archivo: HotelBookingApi/HotelBookingApi/appsettings.json

Cambiar esta línea:
"DefaultConnection": "server=localhost;port=3306;user=root;password=;database=HotelBookingDB"
                                                               ↑
                                                    Tu contraseña aquí
```

### Android - Cambiar URL de API
```kotlin
Archivo: HotelBookingApp/HotelBookingApp/app/src/main/java/com/example/hotelbooking/data/network/RetrofitClient.kt

Para Emulador:
private const val BASE_URL = "http://10.0.2.2:5000/"

Para Celular físico (cambia IP):
private const val BASE_URL = "http://192.168.1.100:5000/"
```

---

## 🧪 PRUEBAS (cURL/Postman)

### Registrar Usuario
```bash
curl -X POST http://localhost:5000/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "cedula": "9876543210",
    "nombre": "Pedro",
    "apellido": "López",
    "celular": "3001234567",
    "correo": "pedro@example.com",
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

### Swagger UI (visual)
```
http://localhost:5000/swagger
```

---

## 📊 VERIFICACIÓN

### ¿Todo funciona?
```bash
# BD
mysql -u root -p -e "USE HotelBookingDB; SELECT * FROM Usuarios;"

# Backend
curl http://localhost:5000/swagger

# Android
adb devices
```

### Puertos en uso
```bash
# Windows
netstat -an | findstr 5000
netstat -an | findstr 3306

# Mac/Linux
lsof -i :5000
lsof -i :3306
```

---

## 🧹 LIMPIAR (cuando falla algo)

```bash
# Backend
dotnet clean
dotnet restore
dotnet build

# Android
./gradlew clean
./gradlew build
./gradlew installDebug

# MySQL (si necesitas reiniciar)
mysql -u root -p -e "DROP DATABASE HotelBookingDB;"
mysql -u root -p < script_database.sql
```

---

## 🐛 SOLUCIONAR PROBLEMAS

### Conexión rechazada (MySQL)
```bash
# Iniciar MySQL (Windows)
mysql.server start

# Iniciar MySQL (Mac)
brew services start mysql

# Iniciar MySQL (Linux)
sudo service mysql start
```

### Puerto 5000 ocupado
```bash
# Windows
netstat -ano | findstr :5000
taskkill /PID {PID} /F

# Mac/Linux
lsof -ti:5000 | xargs kill -9
```

### Gradle falla
```bash
./gradlew clean
./gradlew build --debug
```

### .NET falla
```bash
dotnet clean
dotnet restore
dotnet tool update --global dotnet-ef
```

---

## 🚀 PIPELINE COMPLETO (copia todo)

```bash
#!/bin/bash

echo "=== HOTELBOOKING SETUP ==="

# 1. BD
echo "1. Configurando BD..."
mysql -u root -p < script_database.sql

# 2. Backend
echo "2. Iniciando backend..."
cd HotelBookingApi
dotnet restore
dotnet run &
DOTNET_PID=$!

# 3. Android
echo "3. Compilando Android..."
cd ../HotelBookingApp/HotelBookingApp
./gradlew build
./gradlew installDebug

echo "=== LISTO ==="
echo "Backend PID: $DOTNET_PID"
echo "Swagger: http://localhost:5000/swagger"
```

---

## 📱 EMULADOR

### Crear emulador
```bash
emulator -avd <nombre> &
```

### Listar emuladores
```bash
emulator -list-avds
```

### Conectar dispositivo
```bash
adb devices
adb connect <IP:5555>
```

### Permisos de depuración
```bash
adb shell pm grant com.example.hotelbooking android.permission.INTERNET
```

---

## 🔍 DEBUGGING

### Android Logcat
```bash
adb logcat | grep hotelbooking
adb logcat *:E | grep hotelbooking
```

### Inspeccionar BD
```bash
mysql -u root -p
USE HotelBookingDB;
SHOW TABLES;
DESCRIBE Usuarios;
SELECT * FROM Usuarios\G
```

### Inspeccionar API
```bash
# Todos los logs
curl -v http://localhost:5000/api/auth/login

# Con headers
curl -i -H "Content-Type: application/json" http://localhost:5000/api/auth/login
```

---

## 💾 GIT COMMANDS (si usas control de versión)

```bash
# Inicializar
git init
git add .
git commit -m "Initial commit: HotelBooking Grupo 8"

# Guardar cambios
git add .
git commit -m "description"

# Ver estado
git status
git log

# Ramas
git branch -a
git checkout -b feature-name
```

---

## 📦 INSTALAR DEPENDENCIAS FALTANTES

### Backend
```bash
dotnet add package BCrypt.Net-Next
dotnet add package Pomelo.EntityFrameworkCore.MySql
dotnet add package Microsoft.EntityFrameworkCore
dotnet add package Swashbuckle.AspNetCore
```

### Android
```gradle
// En build.gradle
dependencies {
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
    implementation 'com.google.code.gson:gson:2.10.1'
    implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1'
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1'
}
```

---

## 🔄 CI/CD QUICK (si necesitas)

```bash
# Test backend
cd HotelBookingApi
dotnet test

# Build release
dotnet publish -c Release

# Build APK Android
./gradlew assembleRelease
```

---

## 🎬 SCRIPT PARA PRESENTACIÓN

```bash
#!/bin/bash

echo "=== DEMOSTRACIÓN HOTELBOOKING ==="

# 1. Mostrar Swagger
echo "1. Abriendo Swagger..."
open http://localhost:5000/swagger
# O en Linux: xdg-open
# O en Windows: start

# 2. Mostrar BD
echo "2. Verificando BD..."
mysql -u root -p -e "USE HotelBookingDB; SELECT * FROM Usuarios;"

# 3. App está en emulador/dispositivo

echo "=== LISTO PARA DEMO ==="
```

---

## 📋 CHECKLIST DE COMANDOS ANTES DE PRESENTAR

```bash
# Ejecuta todos en orden
echo "Verificando..."

echo "1. BD..."
mysql -u root -p -e "USE HotelBookingDB; SELECT COUNT(*) FROM Usuarios;"

echo "2. Backend..."
curl -s http://localhost:5000/swagger > /dev/null && echo "OK" || echo "FALLA"

echo "3. Dispositivo..."
adb devices | grep -i "device"

echo "4. Puerto 5000..."
netstat -an | grep 5000

echo "✅ TODO LISTO"
```

---

## 🆘 EMERGENCIAS

### Backend no compila
```bash
dotnet clean
rm -rf bin obj
dotnet restore
dotnet build -v d
```

### Android no compila
```bash
./gradlew clean
rm -rf build/ .gradle/
./gradlew build --stacktrace
```

### BD corrupta
```bash
mysql -u root -p -e "DROP DATABASE HotelBookingDB;"
mysql -u root -p < script_database.sql
```

### App se cierra
```bash
adb logcat | grep FATAL
# Ver error en output
```

---

## ⏱️ TIEMPOS ESTIMADOS

```
Configurar BD:        2 min
Compilar Backend:     3 min
Compilar Android:     5 min
Ejecutar pruebas:     3 min
Total primer setup:   13 min

Ejecuciones siguientes: 30 segundos
```

---

## 🎓 AYUDA RÁPIDA

```
¿Qué comando ejecuto?
→ Copia de la sección correspondiente arriba

¿Qué si falla?
→ Ve a SOLUCIONAR PROBLEMAS

¿Qué necesito instalar primero?
→ Lee QUICK_START.md

¿Cómo pruebo la API?
→ Usa curl o Swagger (arriba)
```

---

## 📞 RESUMEN DE COMANDOS POR COMPONENTE

| Componente | Comando |
|-----------|---------|
| **MySQL** | `mysql -u root -p` |
| **BD** | `mysql -u root -p < script_database.sql` |
| **.NET** | `dotnet run` |
| **Android** | `./gradlew installDebug` |
| **Swagger** | `http://localhost:5000/swagger` |
| **Logcat** | `adb logcat` |

---

## 🏁 COMANDO FINAL (si todo falla)

```bash
# Limpia todo y empieza de cero
echo "Limpiando..."
rm -rf ~/.gradle 2>/dev/null
./gradlew clean
dotnet clean
mysql -u root -p -e "DROP DATABASE HotelBookingDB;" 2>/dev/null

echo "Instalando..."
mysql -u root -p < script_database.sql
./gradlew build
dotnet restore

echo "✅ LISTO"
```

---

**Guía Rápida de Comandos - Grupo 8** ✅
