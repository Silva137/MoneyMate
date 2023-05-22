package pt.isel.moneymate

import androidx.lifecycle.ViewModel
import pt.isel.moneymate.services.MoneyMateService
import pt.isel.moneymate.session.SessionManager

abstract class MoneyMateViewModel(
    private val _moneyMateService: MoneyMateService,
    protected val sessionManager: SessionManager
) : ViewModel() {

    protected lateinit var moneymateService: MoneyMateService


}