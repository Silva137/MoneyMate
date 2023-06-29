package pt.isel.moneymate

import androidx.activity.ComponentActivity
import pt.isel.moneymate.services.MoneyMateService
import pt.isel.moneymate.session.SessionManager
import pt.isel.moneymate.utils.viewModelInit

open class MoneyMateActivity : ComponentActivity() {

    protected val dependenciesContainer by lazy { (application as DependenciesContainer) }

    protected inline fun <reified T > getViewModel(
        crossinline constructor: (
            battleshipsService: MoneyMateService,
            sessionManager: SessionManager
        ) -> T
    ) = viewModelInit {
        constructor(
            dependenciesContainer.moneymateService,
            dependenciesContainer.sessionManager
        )
    }
}