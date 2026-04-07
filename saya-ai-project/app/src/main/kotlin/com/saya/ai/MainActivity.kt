package com.saya.ai

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.*

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var textToSpeech: TextToSpeech
    private lateinit var userSpeechTextView: TextView
    private lateinit var assistantResponseTextView: TextView
    private lateinit var micButton: ImageButton

    private val REQUEST_RECORD_AUDIO_PERMISSION = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userSpeechTextView = findViewById(R.id.userSpeechTextView)
        assistantResponseTextView = findViewById(R.id.assistantResponseTextView)
        micButton = findViewById(R.id.micButton)

        // Request permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), REQUEST_RECORD_AUDIO_PERMISSION)
        } else {
            initializeSpeech()
        }

        textToSpeech = TextToSpeech(this, this)
    }

    private fun initializeSpeech() {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {
                userSpeechTextView.text = "Listening..."
            }

            override fun onBeginningOfSpeech() {}

            override fun onRmsChanged(rmsdB: Float) {}

            override fun onBufferReceived(buffer: ByteArray?) {}

            override fun onEndOfSpeech() {}

            override fun onError(error: Int) {
                userSpeechTextView.text = "Error listening. Restarting..."
                restartListening()
            }

            override fun onResults(results: Bundle?) {
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (!matches.isNullOrEmpty()) {
                    val text = matches[0].lowercase(Locale.getDefault())
                    userSpeechTextView.text = text
                    processSpeech(text)
                } else {
                    restartListening()
                }
            }

            override fun onPartialResults(partialResults: Bundle?) {}

            override fun onEvent(eventType: Int, params: Bundle?) {}
        })

        startWakeWordListening()
    }

    private fun startWakeWordListening() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)
            putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 3000)
            putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, 3000)
        }
        speechRecognizer.startListening(intent)
    }

    private fun processSpeech(text: String) {
        if (text.contains("saya")) {
            speak("Yes Ravi sir")
            startVoiceInput()
        } else {
            restartListening()
        }
    }

    private fun startVoiceInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)
        }
        speechRecognizer.startListening(intent)
        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {
                userSpeechTextView.text = "Listening for command..."
            }

            override fun onBeginningOfSpeech() {}

            override fun onRmsChanged(rmsdB: Float) {}

            override fun onBufferReceived(buffer: ByteArray?) {}

            override fun onEndOfSpeech() {}

            override fun onError(error: Int) {
                userSpeechTextView.text = "Error. Restarting wake word..."
                startWakeWordListening()
            }

            override fun onResults(results: Bundle?) {
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (!matches.isNullOrEmpty()) {
                    val command = matches[0].lowercase(Locale.getDefault())
                    userSpeechTextView.text = command
                    executeCommand(command)
                } else {
                    startWakeWordListening()
                }
            }

            override fun onPartialResults(partialResults: Bundle?) {}

            override fun onEvent(eventType: Int, params: Bundle?) {}
        })
    }

    private fun executeCommand(command: String) {
        when {
            command.contains("open whatsapp") -> {
                openApp("com.whatsapp")
            }
            command.contains("open youtube") -> {
                openApp("com.google.android.youtube")
            }
            command.contains("open chrome") -> {
                openApp("com.android.chrome")
            }
            command.contains("time") -> {
                val time = Calendar.getInstance().time.toString()
                speak("The current time is $time")
            }
            command.contains("hello") -> {
                speak("Hello! How can I help you?")
            }
            else -> {
                speak("Sorry, I didn’t understand")
            }
        }
        // After command, back to wake word
        startWakeWordListening()
    }

    private fun openApp(packageName: String) {
        try {
            val intent = packageManager.getLaunchIntentForPackage(packageName)
            if (intent != null) {
                startActivity(intent)
                speak("Opening ${packageName.split(".").last()}")
            } else {
                speak("App not found")
            }
        } catch (e: Exception) {
            speak("Unable to open app")
        }
    }

    private fun speak(text: String) {
        assistantResponseTextView.text = text
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    private fun restartListening() {
        speechRecognizer.stopListening()
        startWakeWordListening()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            textToSpeech.language = Locale.getDefault()
        } else {
            Toast.makeText(this, "TTS Initialization failed", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initializeSpeech()
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        speechRecognizer.destroy()
        textToSpeech.stop()
        textToSpeech.shutdown()
    }
}
