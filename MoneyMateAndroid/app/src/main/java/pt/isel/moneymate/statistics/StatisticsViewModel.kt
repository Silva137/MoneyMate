package pt.isel.moneymate.statistics

import androidx.lifecycle.ViewModel
import pt.isel.moneymate.services.MoneyMateService
import pt.isel.moneymate.session.SessionManager

class StatisticsViewModel(
    private val moneymateService: MoneyMateService,
    private val sessionManager: SessionManager
) : ViewModel() {

}