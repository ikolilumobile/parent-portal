package com.ikolilu.ikolilu.portal.ui.wardService.wardOptionInfo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.ikolilu.ikolilu.portal.R;

public class PaymentActivity extends AppCompatActivity {

    private String billName;
    private String billAmount;

    private EditText eBillName;
    private EditText eBillAmount;
    private Spinner ePaymethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        eBillName = (EditText) findViewById(R.id.bill_item);
        eBillAmount = (EditText) findViewById(R.id.bill_amount);
        ePaymethod = (Spinner) findViewById(R.id.bill_method);

        Bundle bundle = getIntent().getExtras();
        billName = bundle.getString("billName", null);
        billAmount = bundle.getString("billAmount", null);

        // setting the views
        eBillName.setText(billName);
        eBillAmount.setText(billAmount);

        loadSpinner();
    }

    private void loadSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.payment_method, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        ePaymethod.setAdapter(adapter);
    }
}
