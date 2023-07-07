package pt.isel.moneymate.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.isel.moneymate.theme.incomeGreen

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> DropdownButton(
    modifier: Modifier = Modifier,
    items: List<T>,
    label: String,
    selectedIndex: Int = -1,
    onItemSelected: (index: Int, item: T) -> Unit,
    onFetchItems: () -> Unit = {},
    selectedItemToString: (T) -> String = { it.toString() }
) {
    var isExpanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = isExpanded,
        onExpandedChange = { isExpanded = it }
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxSize(),
            value = items.getOrNull(selectedIndex)?.let { selectedItemToString(it) } ?: "",
            onValueChange = { },
            label = { Text(text = label, color = Color.White, fontSize = 18.sp) },
            shape = RoundedCornerShape(12.dp),
            enabled = true,
            trailingIcon = { Icon(Icons.Filled.ArrowDropUp, "trailingIcon", tint = Color.White) },
            readOnly = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White,
                textColor = Color.White
            )
        )

        // Transparent clickable surface on top of OutlinedTextField
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp)
                .clip(RoundedCornerShape(12.dp))
                .clickable(enabled = true) {
                    isExpanded = true
                    onFetchItems()
                },
            color = Color.Transparent,
        ) { }

        ExposedDropdownMenu(
            modifier = Modifier.background(Color.DarkGray),
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
            content = {
                if(items.size > 4){
                    Box(Modifier.height(200.dp)) {
                        Column(
                            Modifier.verticalScroll(rememberScrollState()),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            items.forEachIndexed { index, item ->
                                DropdownMenuItem(
                                    onClick = {
                                        onItemSelected(index, item)
                                        isExpanded = false
                                    }
                                ) {
                                    Text(
                                        text = selectedItemToString(item),
                                        color = if (index == selectedIndex) incomeGreen else Color.White
                                    )
                                }
                            }
                        }
                    }
                }
                else{
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items.forEachIndexed { index, item ->
                            DropdownMenuItem(
                                onClick = {
                                    onItemSelected(index, item)
                                    isExpanded = false
                                }
                            ) {
                                Text(
                                    text = selectedItemToString(item),
                                    color = if (index == selectedIndex) incomeGreen else Color.White
                                )
                            }
                        }
                    }
                }
            }
        )
    }
}


@Preview
@Composable
fun CustomDropdownMenuPreview() {
    val items = listOf("Item 1", "Item 2", "Item 3", "Item 4")
    var selectedIndex by remember { mutableStateOf(-1) }

    Surface(
        color = Color.DarkGray
    ) {
        DropdownButton(
            items = items,
            label = "Select an item",
            selectedIndex = selectedIndex,
            onItemSelected = { index, _ ->
                selectedIndex = index
            },
            selectedItemToString = { it }
        )
    }
}