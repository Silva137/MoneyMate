package pt.isel.moneymate.statistics

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import pt.isel.moneymate.services.category.models.CategoryBalanceDTO
import pt.isel.moneymate.services.category.models.CategoryDTO
import pt.isel.moneymate.services.users.models.UserDTO

class StatisticsActivity: ComponentActivity() {
    companion object {
        fun navigate(context: Context) {
            with(context) {
                val intent = Intent(this, StatisticsActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StatisticsScreen(
                categoriesBalancePos = listOf(
                    CategoryBalanceDTO(CategoryDTO(1, "Food", UserDTO(1,"silva","silva")), 100),
                    CategoryBalanceDTO(
                        CategoryDTO(2, "Transport",
                            UserDTO(1,"silva","silva")
                        ), 200),
                    CategoryBalanceDTO(CategoryDTO(3, "Entertainment", UserDTO(1,"silva","silva")), 300),
                    CategoryBalanceDTO(CategoryDTO(4, "Other", UserDTO(1,"silva","silva")), 400),
                ),
                categoriesBalanceNeg = listOf(
                    CategoryBalanceDTO(CategoryDTO(1, "Food", UserDTO(1,"silva","silva")), -100),
                    CategoryBalanceDTO(
                        CategoryDTO(2, "Transport",
                            UserDTO(1,"silva","silva")
                        ), -200),
                    CategoryBalanceDTO(CategoryDTO(3, "Entertainment", UserDTO(1,"silva","silva")), -300),
                    CategoryBalanceDTO(CategoryDTO(4, "Other", UserDTO(1,"silva","silva")), -400),
                )
            )
        }
    }
}