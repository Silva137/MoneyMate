package pt.isel.moneymate.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.isel.moneymate.R
import pt.isel.moneymate.background.poppins


@Composable
fun LoginScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Image(
            painter = painterResource(id = R.drawable.login_background),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()

        )
        Box(
            modifier = Modifier.fillMaxSize().padding(bottom = 50.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            BlurBox()
        }
    }
}

@Composable
fun LoginFields(
    onSignUpRequest : () -> Unit = {},
    onLoginRequest : (String, String) -> Unit = { _, _ -> },
    onForgotPasswordRequest : () -> Unit= {}
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    val isFormValid by derivedStateOf { username.isNotBlank() && password.length >= 7 }

    Column(
        Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        Text(text = "Log in", fontSize = 48.sp, color = Color.White, fontFamily = poppins)
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Spacer(modifier = Modifier.weight(1f))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = username,
                onValueChange = { username = it },
                label = { Text(text = "Username", color = Color.White) },
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "Password", color = Color.White) },
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(
                            imageVector = if (isPasswordVisible) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                            contentDescription = "Password Toggle",
                            tint = Color.White
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {},
                enabled = isFormValid,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = "Log In")
            }
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = {}) { Text(text = "Sign Up", color = Color.White) }
                TextButton(onClick = { }) { Text(text = "Forgot Password?", color = Color.White) }
            }
        }
    }
}



@Composable
fun BlurBox() {
    Box(
        modifier = Modifier
            .width(width = 350.dp)
            .height(height = 500.dp)
            .border(
                BorderStroke(
                    width = 1.dp,
                    color = Color.White,
                ),
                shape = RoundedCornerShape(48.dp)
            )
            .background(
                brush = Brush.linearGradient(
                    0f to Color.White.copy(alpha = 0.7f),
                    0.95f to Color.White.copy(alpha = 0.1f),
                    start = Offset(20.75f, 52.5f),
                    end = Offset(429.46f, 850f)
                ),
                shape = RoundedCornerShape(48.dp)
            )
    ){
        LoginFields()
    }
}

@Preview
@Composable
fun LoginScreenPreview(){
    LoginScreen()
}




