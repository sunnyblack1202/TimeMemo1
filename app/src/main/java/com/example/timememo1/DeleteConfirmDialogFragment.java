package com.example.timememo1;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class DeleteConfirmDialogFragment extends DialogFragment {

    String _memoTitle;
    int _memoId;
    int _activity;

    Activity _parentActivity;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        _memoTitle = getArguments().getString("memoTitle", "");
        _memoId = getArguments().getInt("memoId", -1);

        _activity = getArguments().getInt("activity", 0);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        String message = _memoTitle + getString(R.string.dialog_msg_delete);

        builder.setTitle(R.string.dialog_title_delete);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.dialog_btn_ok, new DialogButtonClickListener());
        builder.setNegativeButton(R.string.dialog_btn_ng, new DialogButtonClickListener());

        AlertDialog dialog = builder.create();
        return dialog;
    }

    private class DialogButtonClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            String msg = "";

            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    delete();
                    msg = _memoTitle + getString(R.string.dialog_ok_delete_toast);

                    if (_activity == -5){
                        _parentActivity.finish();
                    } else if(_activity == -3) {
                        //_parentActivity.
                    }

                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    //TODO
                    // キャンセル
                    msg = getString(R.string.dialog_ng_delete_toast);
                    break;
            }

            Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
        }
    }

    //削除
    public void delete() {
        _parentActivity = getActivity();

        TMDatabaseHelper helper = new TMDatabaseHelper(_parentActivity);

        try (SQLiteDatabase db = helper.getWritableDatabase()){
            db.delete(TMDatabaseContract.TimememoContent.TABLE_NAME,
                    TMDatabaseContract.TimememoContent._ID + " = ? ",
                    new String[] {String.valueOf(_memoId)});
        }
    }
}
