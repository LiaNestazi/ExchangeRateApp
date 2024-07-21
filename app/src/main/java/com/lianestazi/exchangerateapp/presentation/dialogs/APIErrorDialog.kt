package com.lianestazi.exchangerateapp.presentation.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class APIErrorDialog: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Ошибка получения данных")
                .setMessage("Попробуйте еще раз или зайдите позднее")
                .setPositiveButton("Еще раз"){ dialog, _ ->
                    //Перезапуск активити
                    dialog.dismiss()
                    requireActivity().recreate()
                }
                .setNegativeButton("Выйти"){ _, _ ->
                    //Выход из приложения
                    requireActivity().finishAffinity()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}