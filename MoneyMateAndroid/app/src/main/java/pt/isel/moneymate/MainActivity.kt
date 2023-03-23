package pt.isel.moneymate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.isel.moneymate.background.poppins
import pt.isel.moneymate.loginbackground.LoginBackground
import pt.isel.moneymate.ui.theme.MoneyMateTheme
import pt.isel.moneymate.ui.theme.lightBlue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoneyMateTheme {
                var username by remember { mutableStateOf("") }
                var password by remember { mutableStateOf("") }
                var isPasswordVisible by remember { mutableStateOf(false) }
                val isFormValid by derivedStateOf { username.isNotBlank() && password.length >= 7 }

                LoginBackground()
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 350.dp),
                    shape = RoundedCornerShape(topStart = 60.dp, topEnd = 60.dp),
                    backgroundColor = Color.Transparent,
                ){
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Brush.verticalGradient(listOf(lightBlue, Color.White)))
                    ){
                        Column(
                            Modifier
                                .fillMaxSize()
                                .padding(32.dp)
                        ) {
                            Text(text = "Log in", fontSize = 32.sp)
                            Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                                Spacer(modifier = Modifier.weight(1f))
                                OutlinedTextField(
                                    modifier = Modifier.fillMaxWidth(),
                                    value = username,
                                    onValueChange = { username = it },
                                    label = { Text(text = "Username") },
                                    singleLine = true,
                                    trailingIcon = {
                                        if (username.isNotBlank())
                                            IconButton(onClick = { username = "" }) {
                                                Icon(imageVector = Icons.Filled.Clear, contentDescription = "")
                                            }
                                    }
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                OutlinedTextField(
                                    modifier = Modifier.fillMaxWidth(),
                                    value = password,
                                    onValueChange = { password = it },
                                    label = { Text(text = "Password") },
                                    singleLine = true,
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                                    trailingIcon = {
                                        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                                            Icon(
                                                imageVector = if (isPasswordVisible) Icons.Default.Face else Icons.Default.Info,
                                                contentDescription = "Password Toggle"
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
                                    TextButton(onClick = {}) {
                                        Text(text = "Sign Up")
                                    }
                                    TextButton(onClick = { }) {
                                        Text(text = "Forgot Password?", color = Color.Gray)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun Title(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 65.dp)
    ) {
        Text(
            text = stringResource(id =R.string.Login),
            color = Color.DarkGray,
            fontSize = 50.sp,
            fontFamily = poppins
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MoneyMateTheme {
    }
}