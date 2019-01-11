package com.ikolilu.ikolilu.portal.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ikolilu.ikolilu.portal.R;

/**
 * Created by Genuis on 19/06/2018.
 */

public class PayviaMomo extends AppCompatDialogFragment {
    private EditText mPhone;
    private Button mPayNow;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.acativity_payviamomo, null);

        builder.setView(view)
                .setTitle("Momo Payment")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Make Payment", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        //mPhone = (EditText) view.findViewById(R.id.momo_phone);
        //mPayNow =(Button) view.findViewById(R.id.pay_now);

        return builder.create();
    }

    private void sendMoneyAction() {

    }
}
