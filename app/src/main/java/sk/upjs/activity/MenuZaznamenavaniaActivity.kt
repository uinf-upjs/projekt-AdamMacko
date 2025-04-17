package sk.upjs.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import viewmodel.MenuZaznamenavaniaViewModel

class MenuZaznamenavaniaActivity : AppCompatActivity() {

    private val viewModel: MenuZaznamenavaniaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_zaznamenavania)

        val buttons = listOf(
            R.id.button1, R.id.button2, R.id.button4, R.id.button5, R.id.button6
        )

        buttons.forEach { buttonId ->
            findViewById<Button>(buttonId).setOnClickListener {
                viewModel.getDestinationForButton(buttonId)?.let { destination ->
                    startActivity(Intent(this, destination))
                }
            }
        }
    }
}
