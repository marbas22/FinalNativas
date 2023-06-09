package com.example.finalnativas.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import androidx.appcompat.app.AlertDialog;

import com.example.finalnativas.R;

public class MenuHelper {
    private Context context;

    public MenuHelper(Context context) {
        this.context = context;
    }

    public void attachMenuToButton(Button button, int menuResId, final OnMenuItemClickListener listener) {
        // Asigna un listener de clic al botón para mostrar el menú emergente
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(button, menuResId, listener);
            }
        });
    }

    private void showPopupMenu(View view, int menuResId, final OnMenuItemClickListener listener) {
        // Crea un objeto PopupMenu y lo muestra en la posición del botón
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(menuResId, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Notifica al listener cuando se hace clic en un elemento del menú
                if (listener != null) {
                    return listener.onMenuItemClick(item);
                }
                return false;
            }
        });
        popupMenu.show();
    }

    public void showExitConfirmationDialog(final OnExitConfirmedListener exitConfirmedListener) {
        // Muestra un cuadro de diálogo de confirmación para salir
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.exit_confirmation_title)
                .setMessage(R.string.exit_confirmation_message)
                .setPositiveButton(R.string.exit_confirmation_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Notifica al listener cuando se confirma la salida
                        if (exitConfirmedListener != null) {
                            exitConfirmedListener.onExitConfirmed();
                        }
                    }
                })
                .setNegativeButton(R.string.exit_confirmation_cancel, null)
                .show();
    }

    public interface OnMenuItemClickListener {
        // Interfaz para manejar los eventos de clic en los elementos del menú
        boolean onMenuItemClick(MenuItem item);
    }

    public interface OnExitConfirmedListener {
        // Interfaz para manejar el evento de confirmación de salida
        void onExitConfirmed();
    }
}
