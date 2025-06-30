# 🤖 Local LLM Chat App (Android, MediaPipe, Kotlin)

This is an Android app built using Jetpack Compose and MediaPipe's GenAI API. It allows users to run a small local LLM (e.g., Gemma 3 or the new Gemma 3n) on-device and interact with it through a chat interface — no internet required!

---

## 📱 Features

- 🔌 **Local model support**: Load `.task` files from device storage.
- 💬 **Chat UI**: User messages and LLM responses in real time.
- 📜 **Markdown support**: Assistant replies are rendered with formatting.
- 📂 **File picker**: Easily select a model file using Storage Access Framework.

---

## 🧩 Tech Stack

- **Kotlin + Jetpack Compose** — modern declarative UI.
- **MediaPipe GenAI Tasks API** — for running LLM inference.
- **Android ViewModel & Coroutines** — for state and async handling.

---


## 📦 Setup Instructions 

### 1. Clone this repo

```bash
git clone https://github.com/your-username/local-llm-app.git
cd local-llm-app
```

### 2. Open the Project in Android Studio

- Make sure you're using **Android Studio Meerkat** or newer.
- Open the project and let **Gradle sync** complete.
- Wait for the project to **build successfully** before continuing.

---

### 3. Enable USB Debugging on Your Phone

- Go to your phone's **Settings > About phone**
- Tap **Build number** 7 times to enable **Developer Options**
- Then go to **Developer Options** and enable **USB Debugging**

---

### 4. Run the App on Your Phone

- Connect your phone to your PC via USB
- Use Android Studio to **install and run** the app directly on your device  
  **OR**  
- Simply **download the APK** from the link below and install it manually:  
  [📦 Download APK](https://github.com/Rithik-101/local-llm-chat/releases/download/v1.0/local_llm.apk)

---

### 5. Download a Compatible Model (.task file)

You'll need a `.task` model file that is compatible with MediaPipe's GenAI API.

✅ **Recommended**:  
Use the **Gemma 3n LiteRT version** from Kaggle:  
[🔗 Download Gemma 3n](https://www.kaggle.com/models/google/gemma-3n)

---

### 6. Select the Model Before Starting a Chat

Once the app is running:
- Tap the **“Select Model File”** button
- Choose the `.task` file you downloaded
- You're now ready to start chatting with your local LLM!

---