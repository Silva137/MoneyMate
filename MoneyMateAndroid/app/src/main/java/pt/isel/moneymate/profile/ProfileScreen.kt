package pt.isel.moneymate.profile

import android.app.Dialog
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.launch
import pt.isel.moneymate.R
import pt.isel.moneymate.background.poppins
import pt.isel.moneymate.theme.dialogBackground


@Composable
fun ProfileScreen(
    username : String?,
    onAddButtonClick: (String) -> Unit = {},
    onCreateSharedWalletClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Image(
            painter = painterResource(id = R.drawable.home_background),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(modifier = Modifier) {
            if (username != null) {
                ProfImg(username)
            }
            BalanceTexts()
            AddWallet(onAddButtonClick = onAddButtonClick)
            ProfileButton(icon = R.drawable.shared_icon, text = "Create Shared Wallet", onClick = onCreateSharedWalletClick)
            Spacer(modifier = Modifier.height(20.dp))
            ProfileButton(icon = R.drawable.settings_icon, text = "Settings", onClick = onSettingsClick)
        }
    }
}

@Composable
fun ProfImg(username : String) {
    var imageUri: Any? by remember { mutableStateOf(R.drawable.icon_profile) }
    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) {
        if (it != null) {
            Log.d("PhotoPicker", "Selected URI: $it")
            imageUri = it
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }
    Row(
        modifier = Modifier.padding(30.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(140.dp)
                .clickable {
                    photoPicker.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }
                .clip(CircleShape)
                .background(Color.White)
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUri)
                    .crossfade(enable = true)
                    .build(),
                contentDescription = "Avatar Image",
                contentScale = ContentScale.Crop,
            )
            Image(
                painter = painterResource(id = R.drawable.profile_frame),
                contentDescription = "frame",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Text(
            modifier = Modifier.padding(16.dp),
            text = "Hi, \n$username!",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = poppins
        )
    }

}

@Composable
fun BalanceTexts(
    balance: String = "1.524"
){
    Text(
        modifier = Modifier.padding(start = 30.dp),
        text = "Total Balance",
        color = Color.White,
        fontSize = 20.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = poppins
    )
    Text(
        modifier = Modifier.padding(start = 30.dp),
        text = "$$balance",
        color = Color.White,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = poppins
    )
}


@Composable
fun AddWallet(onAddButtonClick: (String) -> Unit) {
    var showDialog by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(id = R.drawable.add_wallet),
            contentDescription = "Add Wallet",
            modifier = Modifier
                .clickable { showDialog = true }
                .size(150.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.wallet_image),
            contentDescription = "Wallet",
            modifier = Modifier.size(250.dp)
        )
    }

    if (showDialog) {
        CustomDialog(
            onDismiss = { showDialog = false },
            onSaveWallet = onAddButtonClick
        )
    }
}

@Composable
fun CustomDialog(
    onDismiss: () -> Unit,
    onSaveWallet: (String) -> Unit
) {
    var walletName by remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            dismissOnClickOutside = true,
            dismissOnBackPress = true
        )
    ) {
        Surface(
            color = dialogBackground,
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                verticalArrangement = Arrangement.spacedBy(25.dp)
            ) {
                Text(
                    text = "Create Wallet",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontFamily = poppins,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = walletName,
                    onValueChange = { walletName = it },
                    label = { Text(text = "Wallet Name", color = Color.White) },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        textColor = Color.White
                    ),
                )

                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            onSaveWallet(walletName)
                            onDismiss()
                        },
                        enabled = walletName.isNotBlank()
                    ) {
                        Text("Create")
                    }
                }
            }
        }
    }
}


@Composable
fun ProfileButton(icon: Int? = null, text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth().height(70.dp)
            .padding(horizontal = 30.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.profile_button),
            contentDescription = "Expenses",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
                .clickable { onClick() }
        )
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            icon?.let {
                Icon(
                    painter = painterResource(id = it),
                    contentDescription = "Category Icon",
                    modifier = Modifier.size(40.dp),
                    tint = Color.White
                )
            }
            Text(
                text = text,
                color = Color.White,
                fontSize = 20.sp,
                fontFamily = poppins,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}


@Preview
@Composable
fun ProfilePicPreview() {
    ProfileScreen("abc")
}


