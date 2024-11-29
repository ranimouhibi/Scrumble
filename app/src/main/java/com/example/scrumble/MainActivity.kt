package com.example.scrumble
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.scrumble.ui.theme.ScrumbleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScrumbleTheme {
                ScrumbleGameScreen()
            }
        }
    }
}

@Composable
fun ScrumbleGameScreen() {
    // List of words to be scrambled
    val words = listOf("android", "compose", "scramble", "kotlin", "jetpack")
    var currentWordIndex by remember { mutableStateOf(0) }
    var score by remember { mutableStateOf(0) }
    var userInput by remember { mutableStateOf("") }
    var isCorrect by remember { mutableStateOf(false) }

    val currentWord = words[currentWordIndex].toCharArray().apply { shuffle() }.concatToString()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Unscramble", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier.padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = currentWord,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Text(
            text = "Unscramble the word using all the letters.",
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Input field for the user's guess
        OutlinedTextField(
            value = userInput,
            onValueChange = { userInput = it },
            label = { Text("Enter your word") }
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Buttons for submit and skip
        Button(
            onClick = {
                if (userInput.lowercase() == words[currentWordIndex].lowercase()) {
                    score += 10
                    isCorrect = true
                } else {
                    isCorrect = false
                }
                userInput = ""
            }
        ) {
            Text("Submit")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            if (currentWordIndex < words.size - 1) {
                currentWordIndex++
            } else {
                currentWordIndex = 0
            }
            userInput = ""
        }) {
            Text("Skip")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Score: $score", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(8.dp))

        if (isCorrect) {
            Text(text = "Correct!", color = MaterialTheme.colors.primary)
        } else {
            Text(text = "Try Again.", color = MaterialTheme.colors.error)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScrumbleGameScreenPreview() {
    ScrumbleTheme {
        ScrumbleGameScreen()
    }
}
