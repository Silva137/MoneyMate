package pt.isel.moneymate

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import pt.isel.moneymate.background.poppins
import pt.isel.moneymate.domain.Category
import pt.isel.moneymate.theme.Purple200
import pt.isel.moneymate.theme.dialogBackground
import pt.isel.moneymate.theme.incomeGreen

@Composable
fun CategoriesScreen(
    userCategories: List<Category> = emptyList(),
    systemCategories : List<Category> = emptyList(),
    onEditCategoryClick: (categoryId: Int, updatedCategoryName: String) -> Unit = { _, _ -> },
    onDeleteCategoryClick:  (categoryId: Int) -> Unit = { _ -> },
    onCreateCategoryClick: (categoryName: String) -> Unit = { _ -> },
) {
    var showPopupEditCategory by remember { mutableStateOf(false) }
    var showPopupCreateCategory by remember { mutableStateOf(false) }
    var selectedCategoryIndex by remember { mutableStateOf(0) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Image(
            painter = painterResource(id = R.drawable.home_background),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 25.dp, top = 15.dp, bottom = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                Text(
                    text = "Categories",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontFamily = poppins,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start
                )

                Button(
                    onClick = { showPopupCreateCategory = true },
                    modifier = Modifier.padding(end = 25.dp),
                    colors = ButtonDefaults.buttonColors(incomeGreen),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = "+",
                        color = Color.White,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }


            }
            CategoryList(
                userCategories = userCategories,
                systemCategories = systemCategories,
                onEditClick = {
                    selectedCategoryIndex = it
                    showPopupEditCategory = true
                }
            )
        }
    }

    if (showPopupCreateCategory) {
        CreateCategoryPopup(
            onCreateCategoryClick = onCreateCategoryClick,
            onDismiss = { showPopupCreateCategory = false }
        )
    }

    if (showPopupEditCategory) {
        EditCategoryPopup(
            category = userCategories[selectedCategoryIndex],
            onEditCategoryClick = onEditCategoryClick,
            onDeleteCategoryClick = onDeleteCategoryClick,
            onDismiss = { showPopupEditCategory = false }
        )
    }
}

@Composable
fun CreateCategoryPopup(
    onCreateCategoryClick: (categoryName: String) -> Unit = { _ -> },
    onDismiss: () -> Unit
) {
    var categoryName by remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = { onDismiss()},
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
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Create Category",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontFamily = poppins,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = categoryName,
                    onValueChange =  { if (it.length <= 18) categoryName = it.take(18) },
                    label = { Text(text = "Category name", color = Color.White, fontSize = 18.sp) },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        textColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Button(
                        onClick = {
                            onCreateCategoryClick(categoryName)
                            onDismiss()
                        },
                        enabled = categoryName.isNotBlank(),
                    ) {
                        Text("Create")
                    }
                }
            }
        }
    }
}

@Composable
fun EditCategoryPopup(
    category: Category,
    onEditCategoryClick: (categoryId: Int, updatedCategoryName: String) -> Unit = { _, _ -> },
    onDeleteCategoryClick:  (categoryId: Int) -> Unit = { _ -> },
    onDismiss: () -> Unit
) {
    var categoryName by remember { mutableStateOf(category.name) }

    Dialog(
        onDismissRequest = { onDismiss()},
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
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Edit Category",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontFamily = poppins,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = categoryName,
                    onValueChange = { if (it.length <= 18) categoryName = it.take(18) },
                    label = { Text(text = "Category name", color = Color.White, fontSize = 18.sp) },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        textColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Button(
                        onClick = {
                            onEditCategoryClick(category.id, categoryName)
                            onDismiss()
                        },
                        enabled = true,
                    ) {
                        Text("Save")
                    }
                    Button(
                        onClick = {
                            onDeleteCategoryClick(category.id)
                            onDismiss()
                        },
                        enabled = true,
                    ) {
                        Text("Delete")
                    }
                }
            }
        }
    }
}


@Composable
fun CategoryList(
    userCategories: List<Category>,
    systemCategories : List<Category>,
    onEditClick: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize().padding(top = 20.dp, bottom = 85.dp, start = 15.dp, end = 15.dp)
            .background(
                color = dialogBackground.copy(alpha = 0.9f),
                shape = RoundedCornerShape(24.dp)
            )
    ) {
        LazyColumn {
            item {
                Text(
                    text = "User Categories",
                    color = Purple200,
                    fontSize = 20.sp,
                    fontFamily = poppins,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
            itemsIndexed(userCategories) { index, category ->
                CategoryItem(
                    category = category,
                    index = index,
                    onEditClick = onEditClick
                )
                if (index < userCategories.size - 1) {
                    Divider(
                        color = Purple200,
                        thickness = 1.dp,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                    )
                }
            }

            item {
                Text(
                    text = "System Categories",
                    color = Purple200,
                    fontSize = 20.sp,
                    fontFamily = poppins,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
            itemsIndexed(systemCategories) { index, category ->
                CategoryItem(
                    category = category,
                    index = -1,
                    onEditClick = onEditClick
                )
                if (index < systemCategories.size - 1) {
                    Divider(
                        color = Purple200,
                        thickness = 1.dp,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryItem(category: Category, onEditClick: (Int) -> Unit, index: Int) {
    val clickableModifier = if (index >= 0) {
        Modifier.clickable { onEditClick(index) }
    } else {
        Modifier
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(24.dp))
            .then(clickableModifier),
        color = Color.Transparent,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = category.name,
                color = Color.White,
                fontSize = 16.sp,
                fontFamily = poppins,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(1f)
            )
            if (index >= 0) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null,
                    tint = Color.White,
                )
            }
        }
    }
}