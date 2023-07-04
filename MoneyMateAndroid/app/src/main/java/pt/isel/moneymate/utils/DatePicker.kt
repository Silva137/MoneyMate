import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.commandiron.wheel_picker_compose.WheelDatePicker
import pt.isel.moneymate.background.poppins
import pt.isel.moneymate.theme.dialogBackground
import pt.isel.moneymate.theme.incomeGreen
import java.time.LocalDate


@Composable
fun DatePicker(
    onStartDateSelected: (LocalDate) -> Unit,
    onEndDateSelected: (LocalDate) -> Unit,
    startDate: LocalDate,
    endDate: LocalDate
) {

    var startDateValue by remember { mutableStateOf(startDate) }
    var endDateValue by remember { mutableStateOf(endDate) }
    var showPopupStartDate by remember { mutableStateOf(false) }
    var showPopupEndDate by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.padding(bottom = 30.dp, top = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = { showPopupStartDate = true },
            modifier = Modifier.width(150.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = dialogBackground),
            border = BorderStroke(3.dp, incomeGreen)
        ) {
            Text(
                text = "$startDateValue",
                fontSize = 18.sp,
                style = TextStyle(
                    fontFamily = poppins,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )
        }

        Icon(
            modifier = Modifier.size(35.dp),
            imageVector = Icons.Default.ArrowForward,
            contentDescription = "Arrow Right",
            tint = Color.White
        )

        Button(
            onClick = { showPopupEndDate = true },
            modifier = Modifier.width(150.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = dialogBackground),
            border = BorderStroke(3.dp, incomeGreen)
        ) {
            Text(
                text = "$endDateValue",
                fontSize = 18.sp,
                style = TextStyle(
                    fontFamily = poppins,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )
        }
    }



    if (showPopupStartDate) {
        DatePickerPopup(
            onDateSelected = {date ->
                onStartDateSelected(date)
                startDateValue = date
            },
            onDismiss = { showPopupStartDate = false },
            startValue = startDateValue
        )
    }

    if (showPopupEndDate) {
        DatePickerPopup(
            onDateSelected = {date ->
                onEndDateSelected(date)
                endDateValue = date
            },
            onDismiss = { showPopupEndDate = false },
            startValue = endDateValue
        )
    }
}

@Composable
fun DatePickerPopup(
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit,
    startValue: LocalDate
) {
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
            WheelDatePicker(
                modifier = Modifier.padding(16.dp),
                textColor = Color.White,
                textStyle = TextStyle(fontFamily = poppins, fontWeight = FontWeight.ExtraBold),
                size = DpSize(300.dp, 200.dp),
                startDate = startValue,
            ) { snappedDate ->
                onDateSelected(snappedDate)
            }
        }
    }
}

@Preview
@Composable
fun DateSelectionRowPreview() {
    var pickedStartDate by remember { mutableStateOf(LocalDate.now()) }
    var pickedEndDate by remember { mutableStateOf(LocalDate.now()) }

    DatePicker(
        onStartDateSelected = { pickedStartDate = it},
        onEndDateSelected = { pickedEndDate = it},
        startDate = pickedStartDate,
        endDate = pickedEndDate
    )
}