package pt.isel.moneymate.utils

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

data class BottomSheetItem(val title: String)

@OptIn(ExperimentalMaterialApi::class)
@ExperimentalFoundationApi
@Composable
fun BottomSheet(bottomSheetItems: List<BottomSheetItem>) {
    val selectedItems = remember { mutableStateListOf<Int>() }

    val coroutineScope = rememberCoroutineScope()

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                onClick = {
                    if (bottomSheetScaffoldState.bottomSheetState.isExpanded) {
                        coroutineScope.launch {
                            bottomSheetScaffoldState.bottomSheetState.collapse()
                        }
                    }
                }
            )
    ) {
        BottomSheetScaffold(
            scaffoldState = bottomSheetScaffoldState,
            sheetShape = RoundedCornerShape(topEnd = 30.dp),
            sheetContent = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFF8E2DE2),
                                    Color(0xFF4A00E0)
                                )
                            )
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    for (item in bottomSheetItems) {
                        val index = bottomSheetItems.indexOf(item)
                        val isSelected = selectedItems.contains(index)

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .clickable {
                                    if (isSelected) {
                                        selectedItems.remove(index)
                                    } else {
                                        selectedItems.clear()
                                        selectedItems.add(index)
                                    }
                                }
                        ) {
                            RadioButton(
                                selected = isSelected,
                                onClick = {
                                    if (isSelected) {
                                        selectedItems.remove(index)
                                    } else {
                                        selectedItems.clear()
                                        selectedItems.add(index)
                                    }
                                },
                                modifier = Modifier.padding(start = 16.dp)
                            )
                            Text(
                                text = item.title,
                                modifier = Modifier.padding(start = 8.dp),
                                color = Color.White
                            )
                        }
                    }
                }
            },
            sheetPeekHeight = 0.dp,
            topBar = {
                TopAppBar(
                    title = { Text("Bottom Sheet Demo") },
                    backgroundColor = Color.White,
                    contentColor = Color.Blue
                )
            }
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Button(
                    modifier = Modifier.padding(20.dp),
                    onClick = {
                        coroutineScope.launch {
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        }
                    }
                ) {
                    Text(text = "Click to show Bottom Sheet")
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun PreviewBottomSheetDemo() {
    val bottomSheetItems = listOf(
        BottomSheetItem(title = "Notification"),
        BottomSheetItem(title = "Mail"),
        BottomSheetItem(title = "Scan"),
        BottomSheetItem(title = "Edit"),
        BottomSheetItem(title = "Favorite"),
        BottomSheetItem(title = "Settings")
    )

    BottomSheet(bottomSheetItems)
}
