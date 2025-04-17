package viewmodel

import sk.upjs.activity.ZaznamenatCyklusActivity
import androidx.lifecycle.ViewModel
import sk.upjs.activity.PitnyRezimActivity
import sk.upjs.activity.R
import sk.upjs.activity.ZaznamenatAktivituActivity
import sk.upjs.activity.ZaznamenatPocityActivity
import sk.upjs.activity.ZaznamenatStravuActivity

class MenuZaznamenavaniaViewModel : ViewModel() {

    fun getDestinationForButton(buttonId: Int): Class<*>? {
        return when (buttonId) {
            R.id.button1 -> ZaznamenatAktivituActivity::class.java
            R.id.button2 -> ZaznamenatStravuActivity::class.java
            R.id.button4 -> ZaznamenatCyklusActivity::class.java
            R.id.button5 -> ZaznamenatPocityActivity::class.java
            R.id.button6 -> PitnyRezimActivity::class.java
            else -> null
        }
    }
}
