# 📱 HotelBooking App - Android (Kotlin)
**Documentación Técnica - Grupo 8**

---

## 📋 Descripción

Aplicación Android nativa desarrollada en Kotlin que implementa:
- Pantalla de splash
- Registro de usuarios
- Autenticación
- Página principal con perfil de usuario
- Logout

---

## 🏗️ Arquitectura MVVM

```
app/src/main/java/com/example/hotelbooking/
│
├── ui/                          # UI Layer (Activities y Estados)
│   ├── splash/
│   │   └── SplashActivity.kt
│   ├── login/
│   │   ├── LoginActivity.kt
│   │   ├── LoginViewModel.kt
│   │   ├── LoginViewModelFactory.kt
│   │   └── LoginState.kt
│   ├── register/
│   │   ├── RegisterActivity.kt
│   │   ├── RegisterViewModel.kt
│   │   ├── RegisterViewModelFactory.kt
│   │   └── RegisterState.kt
│   └── home/
│       └── HomeActivity.kt
│
├── data/                        # Data Layer
│   ├── network/
│   │   ├── RetrofitClient.kt    # Cliente HTTP singleton
│   │   └── ApiService.kt        # Interface de endpoints
│   ├── model/                   # Data classes
│   │   ├── User.kt
│   │   ├── LoginRequest.kt
│   │   ├── LoginResponse.kt
│   │   ├── UserRequest.kt
│   │   └── ErrorResponse.kt
│   └── repository/
│       └── AuthRepository.kt    # Lógica de negocio
│
└── utils/                       # Utilidades
    ├── SessionManager.kt        # Gestión de sesión
    └── Validators.kt            # Validaciones
```

---

## 🎯 Flujo de Navegación

```
SplashActivity (2 segundos)
    │
    ├─→ Si está logueado → HomeActivity
    │
    └─→ Si NO está logueado → LoginActivity
        │
        ├─ Botón "Registrarse" → RegisterActivity
        │                         ├─ Si registro exitoso → vuelve a LoginActivity
        │                         └─ Si error → muestra Toast
        │
        └─ Botón "Login" → HomeActivity (si credenciales correctas)
           │
           └─ HomeActivity (Dashboard con Logout)
              │
              └─ Botón "Cerrar Sesión" → LoginActivity
```

---

## 📱 Pantallas

### 1. SplashActivity
**Propósito**: Pantalla de carga inicial

```kotlin
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Espera 2 segundos y redirecciona según sesión
        Handler(Looper.getMainLooper()).postDelayed({
            val destination = if (SessionManager.isLoggedIn(this)) {
                HomeActivity::class.java
            } else {
                LoginActivity::class.java
            }
            startActivity(Intent(this, destination))
            finish()
        }, 2000L)
    }
}
```

**Layout**: `activity_splash.xml`
- Logo/Ícono centrado
- Fondo con tema de hoteles
- Animación opcional

---

### 2. LoginActivity
**Propósito**: Autenticación de usuarios

**Campos**:
- Email (obligatorio, validación de formato)
- Contraseña (obligatorio, 6+ caracteres)

**Validaciones**:
- Campos no vacíos
- Correo válido (EmailAddress pattern)
- Contraseña mínimo 6 caracteres

**Acciones**:
- Botón "Login" → Llama a API y maneja respuesta
- Texto "Registrarse" → Va a RegisterActivity

```kotlin
private fun intentarLogin() {
    val correo = binding.etCorreo.text.toString().trim()
    val contrasena = binding.etContrasena.text.toString()
    
    // Validaciones
    if (correo.isEmpty() || contrasena.isEmpty()) {
        Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
        return
    }
    
    // Llamar a API vía Repository
    lifecycleScope.launch {
        val response = repository.login(correo, contrasena)
        if (response.isSuccessful) {
            SessionManager.saveSession(this@LoginActivity, response.body()!!)
            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
            finish()
        } else {
            Toast.makeText(this@LoginActivity, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
        }
    }
}
```

**Layout**: `activity_login.xml`
- Campo Email
- Campo Contraseña
- ProgressBar (durante conexión)
- Botón Login
- Texto "¿No tienes cuenta? Registrate aquí"

---

### 3. RegisterActivity
**Propósito**: Registro de nuevos usuarios

**Campos**:
- Cédula (obligatorio)
- Nombre (obligatorio)
- Apellido (obligatorio)
- Celular (obligatorio)
- Correo (obligatorio, validación)
- Contraseña (obligatorio, 6+ caracteres)
- Confirmar Contraseña (debe coincidir)

**Validaciones**:
- Todos campos obligatorios
- Correo válido
- Contraseña ≥ 6 caracteres
- Las contraseñas coinciden
- No se permiten duplicados (manejo de error del servidor)

**Acciones**:
- Botón "Registrar" → Llama a API
- Texto "Volver" → Regresa a LoginActivity

```kotlin
private fun intentarRegistro() {
    val cedula = binding.etCedula.text.toString().trim()
    val nombre = binding.etNombre.text.toString().trim()
    val apellido = binding.etApellido.text.toString().trim()
    val celular = binding.etCelular.text.toString().trim()
    val correo = binding.etCorreo.text.toString().trim()
    val contrasena = binding.etContrasena.text.toString()
    val confirmar = binding.etConfirmarContrasena.text.toString()
    
    // Validaciones
    if (cedula.isEmpty() || nombre.isEmpty() || apellido.isEmpty() ||
        celular.isEmpty() || correo.isEmpty() || contrasena.isEmpty()) {
        Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
        return
    }
    if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
        binding.etCorreo.error = "Correo no válido"
        return
    }
    if (contrasena.length < 6) {
        binding.etContrasena.error = "Mínimo 6 caracteres"
        return
    }
    if (contrasena != confirmar) {
        binding.etConfirmarContrasena.error = "Las contraseñas no coinciden"
        return
    }
    
    // Llamar a API
    lifecycleScope.launch {
        val response = repository.register(UserRequest(...))
        if (response.isSuccessful) {
            Toast.makeText(this@RegisterActivity, "✅ Registro exitoso", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this@RegisterActivity, "Error al registrar", Toast.LENGTH_SHORT).show()
        }
    }
}
```

**Layout**: `activity_register.xml`
- 6 campos de entrada (EditText)
- ProgressBar
- Botón Registrar
- Texto "Volver"

---

### 4. HomeActivity
**Propósito**: Dashboard del usuario autenticado

**Información mostrada**:
- Mensaje de bienvenida: "¡Bienvenido, [Nombre]!"
- Nombre completo
- Correo
- Cédula
- Celular
- Botón "Cerrar Sesión"

**Acciones**:
- Botón Logout → Muestra diálogo de confirmación
- Si confirma → Limpia sesión → Va a LoginActivity
- Si cancela → Permanece en HomeActivity

```kotlin
class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // Obtener datos de sesión
        val nombre = SessionManager.getNombre(this)
        val apellido = SessionManager.getApellido(this)
        val correo = SessionManager.getCorreo(this)
        val cedula = SessionManager.getCedula(this)
        val celular = SessionManager.getCelular(this)
        
        // Mostrar en UI
        binding.tvBienvenida.text = "¡Bienvenido, $nombre!"
        binding.tvNombreCompleto.text = "$nombre $apellido"
        binding.tvCorreo.text = correo
        binding.tvCedula.text = cedula
        binding.tvCelular.text = celular
        
        binding.btnLogout.setOnClickListener { mostrarConfirmacionLogout() }
    }
    
    private fun mostrarConfirmacionLogout() {
        AlertDialog.Builder(this)
            .setTitle("Cerrar sesión")
            .setMessage("¿Estás seguro que deseas cerrar sesión?")
            .setPositiveButton("Sí") { _, _ ->
                SessionManager.clearSession(this)
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
}
```

**Layout**: `activity_home.xml`
- Card/Layout para perfil
- TextView para cada dato
- Botón Logout

---

## 🔌 Integración con API

### RetrofitClient
```kotlin
object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:5000/"  // Emulador
    // Para celular físico: "http://<IP_PC>:5000/"
    
    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
```

### ApiService
```kotlin
interface ApiService {
    @POST("api/auth/register")
    suspend fun register(@Body request: UserRequest): Response<Unit>
    
    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}
```

### Repository
```kotlin
class AuthRepository(private val apiService: ApiService) {
    suspend fun register(userRequest: UserRequest): Response<Unit> {
        return apiService.register(userRequest)
    }
    
    suspend fun login(correo: String, contrasena: String): Response<LoginResponse> {
        return apiService.login(LoginRequest(correo, contrasena))
    }
}
```

---

## 💾 Gestión de Sesión

### SessionManager
```kotlin
object SessionManager {
    private const val PREF_NAME = "HotelBookingPrefs"
    private const val KEY_ID = "id"
    private const val KEY_NOMBRE = "nombre"
    private const val KEY_APELLIDO = "apellido"
    private const val KEY_CORREO = "correo"
    private const val KEY_CEDULA = "cedula"
    private const val KEY_CELULAR = "celular"
    private const val KEY_LOGGED = "isLogged"
    
    fun saveSession(context: Context, response: LoginResponse) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().apply {
            putInt(KEY_ID, response.id)
            putString(KEY_NOMBRE, response.nombre)
            putString(KEY_APELLIDO, response.apellido)
            putString(KEY_CORREO, response.correo)
            putString(KEY_CEDULA, response.cedula)
            putString(KEY_CELULAR, response.celular)
            putBoolean(KEY_LOGGED, true)
            apply()
        }
    }
    
    fun isLoggedIn(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(KEY_LOGGED, false)
    }
    
    fun clearSession(context: Context) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().clear().apply()
    }
}
```

---

## 🔐 Validaciones Implementadas

### Cliente (Android)
✅ Campos obligatorios  
✅ Formato de correo válido (Patterns.EMAIL_ADDRESS)  
✅ Longitud mínima de contraseña  
✅ Confirmación de contraseña  
✅ Validación de número de celular  

### Servidor (.NET)
✅ ModelState.IsValid  
✅ Duplicados (correo y cédula)  
✅ BCrypt para verificación  

---

## 📦 Dependencias (build.gradle)

```gradle
dependencies {
    // Retrofit
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
    
    // Gson
    implementation 'com.google.code.gson:gson:2.10.1'
    
    // ViewModel
    implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1'
    implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.6.1'
    
    // Activity
    implementation 'androidx.activity:activity-ktx:1.7.2'
    
    // Coroutines
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1'
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1'
    
    // Testing
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'
}
```

---

## 📝 AndroidManifest.xml

```xml
<manifest ...>
    <!-- Permisos -->
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    
    <application ...>
        <!-- SplashActivity como launcher -->
        <activity android:name=".ui.splash.SplashActivity" android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        
        <activity android:name=".ui.login.LoginActivity" />
        <activity android:name=".ui.register.RegisterActivity" />
        <activity android:name=".ui.home.HomeActivity" />
    </application>
</manifest>
```

---

## 🚀 Pasos para Ejecutar

### 1. Configuración Previa
- Android Studio
- Emulador (API 24+) o dispositivo físico
- Servidor .NET ejecutándose

### 2. Clonar/Abrir Proyecto
```bash
cd HotelBookingApp
```

### 3. Configurar BASE_URL
En `RetrofitClient.kt`:
- **Emulador**: `http://10.0.2.2:5000`
- **Celular físico**: `http://<IP_PC>:5000`

### 4. Build & Run
```bash
gradle build
gradle installDebug
```

O desde Android Studio:
- Build → Build Bundle(s) / APK(s)
- Run → Run 'app'

---

## 🧪 Pruebas

### Casos de Uso

#### Caso 1: Registro exitoso
1. Ir a pantalla de registro
2. Llenar todos los campos
3. Clic en "Registrar"
4. ✅ Debe mostrar "Registro exitoso"
5. ✅ Debe volver a LoginActivity

#### Caso 2: Email duplicado
1. Registrar usuario: juan@example.com
2. Intentar registrar otro con mismo email
3. ❌ Debe mostrar "El correo ya está registrado"

#### Caso 3: Login exitoso
1. Ingresar correo y contraseña válidos
2. Clic en "Login"
3. ✅ Debe ir a HomeActivity
4. ✅ Debe mostrar datos del usuario

#### Caso 4: Logout
1. Desde HomeActivity
2. Clic en "Cerrar Sesión"
3. Confirmar en diálogo
4. ✅ Debe volver a LoginActivity
5. ✅ Sesión debe estar limpia

---

## 🐛 Solución de Problemas

### "No se puede conectar a la API"
- Verificar que el servidor .NET está corriendo
- Verificar BASE_URL en RetrofitClient.kt
- Emulador: usar `10.0.2.2`, no `localhost`
- Celular: usar IP real de la PC (ej: `192.168.x.x`)

### "Credenciales incorrectas" al hacer login
- Verificar que el usuario existe en la BD
- Verificar que la contraseña es correcta
- Usar datos de prueba proporcionados en script SQL

### App se cierra (crash)
- Verificar que INTERNET permission está en AndroidManifest
- Revisar logcat para errores
- Limpiar build: `gradle clean`

---

## 📝 Notas Finales

✅ Arquitectura MVVM implementada  
✅ Validaciones en cliente y servidor  
✅ Manejo seguro de contraseñas  
✅ Sesión persistente con SharedPreferences  
✅ Uso de Coroutines para operaciones asincrónicas  
✅ Manejo de errores completo  
✅ UI responsive con ProgressBar  

---

**Documentación Android - Grupo 8 ✅**
