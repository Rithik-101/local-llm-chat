package com.example.local_llm.network

import android.content.Context
import android.net.Uri
import android.os.Environment
import com.google.mediapipe.tasks.genai.llminference.LlmInference
import com.google.mediapipe.tasks.genai.llminference.LlmInference.LlmInferenceOptions
import com.google.mediapipe.tasks.genai.llminference.LlmInferenceSession
import com.google.mediapipe.tasks.genai.llminference.LlmInferenceSession.LlmInferenceSessionOptions
import com.google.mediapipe.tasks.genai.llminference.GraphOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

object ChatApi {

    private var llm: LlmInference? = null
    private var initialized = false
    private var modelUri: Uri? = null

    fun setModelUri(uri: Uri, context: Context) {
        modelUri = uri
        initialized = false // Force re-initialization with new model
    }

    // Internal function to load model to cache from Uri
    private fun copyModelToCache(context: Context, uri: Uri): File {
        val cacheFile = File(context.cacheDir, "custom_model.task")
        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            FileOutputStream(cacheFile).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        } ?: throw IllegalArgumentException("Failed to open model Uri.")
        return cacheFile
    }

    private suspend fun init(context: Context) = withContext(Dispatchers.IO) {
        if (initialized) return@withContext

        val modelPath: String = if (modelUri != null) {
            copyModelToCache(context, modelUri!!).absolutePath
        } else {
            throw IllegalStateException("No model selected. Please select a .task model file before chatting.")
        }

        val options = LlmInferenceOptions.builder()
            .setModelPath(modelPath)
            .setMaxTokens(256)
            .build()

        llm = LlmInference.createFromOptions(context, options)
        initialized = true
    }

    suspend fun streamPromptResponse(
        context: Context,
        prompt: String,
        onChunk: (String) -> Unit
    ) = withContext(Dispatchers.IO) {
        try {
            if (!initialized) init(context)
            val model = llm ?: return@withContext onChunk("Error: LLM not initialized")

            val sessionOptions = LlmInferenceSessionOptions.builder()
                .setTopK(64)
                .setTopP(0.9f)
                .setTemperature(0.7f)
                .setGraphOptions(
                    GraphOptions.builder()
                        .setEnableVisionModality(false)
                        .build()
                )
                .build()

            val session = LlmInferenceSession.createFromOptions(model, sessionOptions)
            session.addQueryChunk(prompt)

            session.generateResponseAsync { partialResult, done ->
                onChunk(partialResult)
            }

        } catch (e: Exception) {
            onChunk("Error: ${e.message ?: "Model not loaded"}")
        }
    }
}
