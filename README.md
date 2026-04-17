# 🚀 Sistema de Mensajería Cloud (FCM)

Este proyecto es una implementación completa de **Firebase Cloud Messaging (FCM)** que integra un cliente móvil Android y un servidor de despacho de notificaciones en Node.js.

## 📋 Descripción
El sistema permite la comunicación en tiempo real entre un servidor y dispositivos móviles. La aplicación Android genera un token único de registro, el cual es utilizado por el servidor para enviar notificaciones personalizadas con alta prioridad.

---

## 🛠️ Tecnologías Utilizadas

### Cliente (App Android)
- **Lenguaje**: Kotlin
- **UI**: Material Design 3 (Android Jetpack)
- **Firebase**: Firebase Messaging SDK (BoM 33.9.0)
- **Patrones**: BroadcastReceiver para actualizaciones UI en tiempo real.

### Servidor (Backend)
- **Entorno**: Node.js
- **Librerías**: Express, Firebase Admin SDK, Body-parser.
- **UI**: Interfaz Web con diseño Glassmorphism (HTML5/CSS3).

---

## 🔧 Configuración e Instalación

### 1. Requisitos Previos
- Android Studio Ladybug o superior.
- Node.js instalado (v14 o superior).
- Un proyecto en [Firebase Console](https://console.firebase.google.com/).

### 2. Configuración de la App Android
1.  Descarga el archivo `google-services.json` desde la consola de Firebase.
2.  Colócalo en la carpeta `app/` del proyecto.
3.  Sincroniza el proyecto con Gradle y ejecútalo en un dispositivo físico o emulador con Google Play Services.

### 3. Configuración del Servidor Node.js
1.  Ve a la consola de Firebase -> Configuración del proyecto -> Cuentas de servicio.
2.  Genera una nueva clave privada y descárgala como `serviceAccountKey.json`.
3.  Coloca este archivo en la **raíz del repositorio** (fuera de la carpeta server).
4.  Desde la terminal, accede a la carpeta del servidor:
    ```bash
    cd server
    npm install
    ```
5.  Inicia el servidor:
    ```bash
    node app.js
    ```

---

## 📲 Cómo usar el sistema
1.  Abre la App en tu dispositivo Android.
2.  Copia el **Token de Registro FCM** que aparece en la pantalla principal.
3.  Abre un navegador en `http://localhost:3000`.
4.  Pega el Token, escribe un título y un mensaje.
5.  Presiona **"Transmitir Mensaje"** y verifica la llegada de la notificación en tu celular.

---

## 🔒 Seguridad (Archivos Ignorados)
Por razones de seguridad, los siguientes archivos de configuración sensible están excluidos del repositorio de GitHub mediante `.gitignore`:
- `app/google-services.json`
- `serviceAccountKey.json`
- `node_modules/`

*Cada desarrollador deberá proveer sus propias llaves de Firebase para ejecutar el proyecto.*

---

**Desarrollado por:** JGL & CZG
**Materia:** Programación Móvil II
**Fecha:** Abril 2026
