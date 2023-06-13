package pt.isel.moneymate.register
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
import pt.isel.moneymate.register.RegisterViewModel.AuthenticationState


@Composable
fun RegisterScreen(
    state: AuthenticationState,
    onLoginRequest : () -> Unit = {},
    onSignUpRequest : (String, String, String) -> Unit,
    onForgotPasswordRequest : () -> Unit= {},
    onSignUpSuccessful : () -> Unit
) {
    LaunchedEffect(state) {
        if (state == AuthenticationState.SUCCESS)
            onSignUpSuccessful()
    }


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
            BlurBox(onLoginRequest,onSignUpRequest,onForgotPasswordRequest)
        }
    }
}

@Composable
fun RegisterFields(
    onLoginRequest : () -> Unit = {},
    onSignUpRequest : (String, String,String) -> Unit,
    onForgotPasswordRequest : () -> Unit= {}
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    val isFormValid by derivedStateOf { username.isNotBlank() && password.length >= 6 && email.isNotBlank() }

    Column(
        Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        Text(text = "Sign up", fontSize = 48.sp, color = Color.White, fontFamily = poppins)
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
            Spacer(modifier = Modifier.weight(1f))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Email", color = Color.White) },
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
                onClick = {onSignUpRequest(username,password,email)},
                enabled = isFormValid,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = "Sign Up")
            }
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = {onLoginRequest()}) { Text(text = "Log in", color = Color.White) }
                TextButton(onClick = {onForgotPasswordRequest}) { Text(text = "Forgot Password?", color = Color.White) }
            }
        }
    }
}



@Composable
fun BlurBox(onLoginRequest : () -> Unit = {},
            onSignUpRequest : (String, String, String) -> Unit ,
            onForgotPasswordRequest : () -> Unit= {}) {
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
        RegisterFields(onLoginRequest,onSignUpRequest,onForgotPasswordRequest)
    }
}

@Preview
@Composable
fun RegisterScreenPreview(){
    RegisterScreen(onSignUpRequest = { _, _, _->}, state = AuthenticationState.IDLE, onSignUpSuccessful = {})
}




