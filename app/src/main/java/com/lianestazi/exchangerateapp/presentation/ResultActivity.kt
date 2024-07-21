package com.lianestazi.exchangerateapp.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.lianestazi.exchangerateapp.R
import com.lianestazi.exchangerateapp.presentation.dialogs.APIErrorDialog
import com.lianestazi.exchangerateapp.presentation.dialogs.InternetErrorDialog
import com.lianestazi.exchangerateapp.presentation.managers.ConnectionManager
import org.koin.androidx.viewmodel.ext.android.viewModel

class ResultActivity : AppCompatActivity() {

    private val vm by viewModel<ResultViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_result)

        //Проверка на интернет соединение
        val connectionManager = ConnectionManager(applicationContext)
        if (!connectionManager.checkConnection()){
            //Alert dialog с ошибкой подключения
            InternetErrorDialog().show(supportFragmentManager, "INTERNET_DIALOG")
        }else{
            vm.errorMessagePublic.observe(this){
                if (it != null){
                    //Окошко ошибки с API вылазит здесь
                    APIErrorDialog().show(supportFragmentManager, "API_DIALOG")
                }
            }

            val fromCurr: String = intent.getStringExtra("from").toString()
            val toCurr: String = intent.getStringExtra("to").toString()
            val amount: Double = intent.getDoubleExtra("amount", 0.0)

            vm.convert(fromCurr, toCurr, amount)

            val amountFromTextView = findViewById<TextView>(R.id.amount_from)
            val valueToTextView = findViewById<TextView>(R.id.value_to)

            val s1 = "$amount $fromCurr"
            amountFromTextView.text = s1

            vm.valuePublic.observe(this){
                val s2 = "$it $toCurr"
                valueToTextView.text = s2
            }

            val convertNextButton = findViewById<Button>(R.id.convert_next_button)
            convertNextButton.setOnClickListener {
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}