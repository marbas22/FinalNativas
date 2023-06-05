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
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(button, menuResId, listener);
            }
        });
    }

    private void showPopupMenu(View view, int menuResId, final OnMenuItemClickListener listener) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(menuResId, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (listener != null) {
                    return listener.onMenuItemClick(item);
                }
                return false;
            }
        });
        popupMenu.show();
    }

    public void showExitConfirmationDialog(final OnExitConfirmedListener exitConfirmedListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.exit_confirmation_title)
                .setMessage(R.string.exit_confirmation_message)
                .setPositiveButton(R.string.exit_confirmation_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (exitConfirmedListener != null) {
                            exitConfirmedListener.onExitConfirmed();
                        }
                    }
                })
                .setNegativeButton(R.string.exit_confirmation_cancel, null)
                .show();
    }

    public interface OnMenuItemClickListener {
        boolean onMenuItemClick(MenuItem item);
    }

    public interface OnExitConfirmedListener {
        void onExitConfirmed();
    }
}
