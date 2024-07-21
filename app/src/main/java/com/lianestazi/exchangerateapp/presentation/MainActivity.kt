package com.lianestazi.exchangerateapp.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.lianestazi.exchangerateapp.R
import com.lianestazi.exchangerateapp.presentation.dialogs.APIErrorDialog
import com.lianestazi.exchangerateapp.presentation.dialogs.InternetErrorDialog
import com.lianestazi.exchangerateapp.presentation.managers.ConnectionManager
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val vm by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        //Проверка на интернет соединение
        val connectionManager = ConnectionManager(applicationContext)
        if (!connectionManager.checkConnection()){
            //Alert dialog с ошибкой подключения
            InternetErrorDialog().show(supportFragmentManager, "INTERNET_DIALOG")
        } else{
            vm.errorMessagePublic.observe(this){
                if (it != null){
                    //Окошко ошибки с API вылазит здесь
                    APIErrorDialog().show(supportFragmentManager, "API_DIALOG")
                }
            }
            val spinnerFrom = findViewById<Spinner>(R.id.currency_spinner_from)
            val spinnerTo = findViewById<Spinner>(R.id.currency_spinner_to)
            val editTextAmount = findViewById<EditText>(R.id.amount_edit_text)
            val convertButton = findViewById<Button>(R.id.convert_button)

            vm.currenciesList.observe(this) { list ->
                val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item,list)
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerFrom.setAdapter(arrayAdapter)
                spinnerTo.setAdapter(arrayAdapter)
            }

            var fromCurr = ""
            var toCurr = ""
            spinnerFrom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    fromCurr = vm.currenciesList.value!!.get(position).short_code!!
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    fromCurr = ""
                }

            }

            spinnerTo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    toCurr = vm.currenciesList.value!!.get(position).short_code!!
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    toCurr = ""
                }

            }

            convertButton.setOnClickListener {
                if (editTextAmount.text.isEmpty()){
                    Toast.makeText(this,"Необходимо ввести сумму", Toast.LENGTH_SHORT).show()
                } else{
                    val intent = Intent(this,ResultActivity::class.java)
                    intent.putExtra("from",fromCurr)
                    intent.putExtra("to",toCurr)
                    val amountString = editTextAmount.text.toString()
                    val amountDouble = amountString.toDoubleOrNull()
                    if (amountDouble == null){
                        Toast.makeText(this,"Введите сумму в верном формате", Toast.LENGTH_SHORT).show()
                    } else{
                        intent.putExtra("amount", amountDouble)
                        startActivity(intent)
                    }
                }
            }
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}