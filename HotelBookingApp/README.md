# 🏨 HotelBooking App – Grupo 8
## Proyecto Académico – Desarrollo Móvil

---

## Arquitectura
```
Android (Kotlin) ──Retrofit──► .NET Web API ──EF Core──► MySQL
```

---

## 📁 Estructura del proyecto Android

```
HotelBookingApp/
└── app/src/main/
    ├── AndroidManifest.xml
    ├── java/com/hotelbooking/app/
    │   ├── activities/
    │   │   ├── SplashActivity.kt      ← pantalla de carga
    │   │   ├── LoginActivity.kt       ← inicio de sesión
    │   │   ├── RegisterActivity.kt    ← registro de usuario
    │   │   └── HomeActivity.kt        ← página principal
    │   ├── models/
    │   │   └── Models.kt              ← data classes (request/response)
    │   ├── network/
    │   │   ├── AuthApiService.kt      ← endpoints Retrofit
    │   │   └── RetrofitClient.kt      ← cliente HTTP singleton
    │   └── utils/
    │       └── SessionManager.kt      ← gestión de sesión (SharedPreferences)
    └── res/
        ├── layout/
        │   ├── activity_login.xml
        │   ├── activity_register.xml
        │   └── activity_home.xml
        └── values/
            ├── colors.xml
            ├── strings.xml
            └── themes.xml
```

---

## ⚙️ Configuración antes de correr

### 1. Base de datos MySQL
```sql
-- Ejecuta script_bd.sql en tu servidor MySQL
mysql -u root -p < script_bd.sql
```

### 2. Backend (.NET)
Edita `appsettings.json` del proyecto .NET con tu cadena de conexión:
```json
{
  "ConnectionStrings": {
    "DefaultConnection": "Server=localhost;Database=hotelbooking_db;User=root;Password=TU_PASSWORD;"
  }
}
```
Luego corre la API:
```bash
dotnet run
```
La API queda en `http://localhost:5000`

### 3. Android – URL de la API
Abre `RetrofitClient.kt` y ajusta la URL:

| Caso | URL |
|------|-----|
| Emulador Android Studio | `http://10.0.2.2:5000/` |
| Dispositivo físico (mismo WiFi) | `http://192.168.X.X:5000/` |

---

## 🔌 Endpoints consumidos

| Endpoint | Método | Body |
|----------|--------|------|
| `api/auth/register` | POST | `{cedula, nombre, apellido, celular, correo, contrasena}` |
| `api/auth/login` | POST | `{correo, contrasena}` |

---

## ✅ Funcionalidades implementadas

- [x] **Splash Screen** con redirección automática
- [x] **Registro** con todos los campos requeridos
- [x] **Validaciones en cliente** (campos vacíos, correo válido, contraseñas coinciden)
- [x] **Login** con correo y contraseña
- [x] **Manejo de errores** del backend (duplicados, credenciales incorrectas)
- [x] **Sesión persistente** con SharedPreferences
- [x] **Página principal** con mensaje de bienvenida y datos del usuario
- [x] **Logout** con confirmación y limpieza de sesión

---

## 🛠️ Dependencias Android (build.gradle)

```groovy
// Retrofit + Gson
implementation 'com.squareup.retrofit2:retrofit:2.9.0'
implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
implementation 'com.squareup.okhttp3:logging-interceptor:4.12.0'

// Coroutines
implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3'
implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.7.0'

// Material Design
implementation 'com.google.android.material:material:1.11.0'
```

---

## 👥 Grupo 8 – HotelBooking
Proyecto Académico | Desarrollo Móvil
