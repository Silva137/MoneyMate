package pt.isel.moneymate.utils

import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.time.LocalDate
import java.time.YearMonth
import java.util.*

@Suppress("UNCHECKED_CAST")
fun <T> viewModelInit(block: () -> T) =
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return block() as T
        }
    }

fun getCurrentMonthRange(): Pair<LocalDate, LocalDate> {
    val calendar = Calendar.getInstance()
    val currentYear = calendar.get(Calendar.YEAR)
    val currentMonth = calendar.get(Calendar.MONTH) + 1 // Calendar.MONTH is zero-based

    val firstDayOfMonth = LocalDate.of(currentYear, currentMonth, 1)
    val lastDayOfMonth = YearMonth.of(currentYear, currentMonth).atEndOfMonth()

    return Pair(firstDayOfMonth, lastDayOfMonth)
}

fun getCurrentYearRange(): Pair<LocalDate, LocalDate> {
    val currentYear = LocalDate.now().year
    val firstDayOfYear = LocalDate.of(currentYear, 1, 1)
    val lastDayOfYear = LocalDate.of(currentYear, 12, 31)
    return Pair(firstDayOfYear, lastDayOfYear)
}


