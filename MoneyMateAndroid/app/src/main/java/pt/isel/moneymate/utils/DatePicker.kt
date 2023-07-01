import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.commandiron.wheel_picker_compose.WheelDatePicker
import pt.isel.moneymate.background.poppins
import pt.isel.moneymate.theme.dialogBackground
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

    Row(Modifier.padding(16.dp)) {
        Button(
            onClick = { showPopupStartDate = true },
            modifier = Modifier.weight(1f)
        ) {
            Text(text = "Start Date")
        }

        Spacer(modifier = Modifier.width(16.dp))

        Button(
            onClick = { showPopupEndDate = true },
            modifier = Modifier.weight(1f)
        ) {
            Text(text = "End Date")
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