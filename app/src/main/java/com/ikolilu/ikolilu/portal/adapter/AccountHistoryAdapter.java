package com.ikolilu.ikolilu.portal.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ikolilu.ikolilu.portal.R;
import com.ikolilu.ikolilu.portal.dialogs.PayviaMomo;
import com.ikolilu.ikolilu.portal.helper.SchoolDAO;
import com.ikolilu.ikolilu.portal.model.AccountHistory;
import com.ikolilu.ikolilu.portal.network.networkStorage.AuthSharedPref;
import com.ikolilu.ikolilu.portal.network.networkStorage.GeneralPref;
import com.ikolilu.ikolilu.portal.ui.wardService.wardOptionInfo.BillFragments.FragmentPay;
import com.ikolilu.ikolilu.portal.ui.wardService.wardOptionInfo.PaymentActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;


/**
 * Created by Genuis on 10/04/2018.
 */

public class AccountHistoryAdapter extends RecyclerView.Adapter<AccountHistoryAdapter.AccountHistoryViewholder> {

    private Context mCtx;
    private List<AccountHistory> accountHistoryList;
    private PaymentActivity payActivity;
    FragmentManager fm;
    Context context;

    PayviaMomo payviaMomoDialog;
    GeneralPref generalPref;
    AuthSharedPref authSharedPref;
    EditText momoAmount, momoPhone;

    String itemName, credit, amount;
    private SchoolDAO schoolDAO;

    public AccountHistoryAdapter(Context mCtx, List<AccountHistory> accountHistoryList) {
        this.mCtx = mCtx;
        this.accountHistoryList = accountHistoryList;
    }

    @Override
    public AccountHistoryViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View v =  inflater.inflate(R.layout.history_bill_list_layout, null);
        return new AccountHistoryViewholder(v);
    }

    // #
    @Override
    public void onBindViewHolder(AccountHistoryViewholder holder, int position) {
        final AccountHistory accountHistory = accountHistoryList.get(position);
        payActivity = new PaymentActivity();

        generalPref = new GeneralPref(mCtx);
        authSharedPref = new AuthSharedPref(mCtx);

        fm = ((Activity) mCtx).getFragmentManager();

        holder.date.setText(accountHistory.getDate());
        holder.bill_cost.setText(accountHistory.getDebit());
        //holder.term.setText(accountHistory.getTerm().toUpperCase() + " TERM");
        //holder.aca_year.setText(accountHistory.getAcaYear());
        //holder.amount.setText("(" + accountHistory.getCur()+ ") " + accountHistory.getAmount());
        holder.cur.setText("(" + accountHistory.getCur()+ ") " + accountHistory.getAmount());
        //holder.debit.setText("");
        holder.credit.setText(accountHistory.getCredit());
        holder.desc.setText(accountHistory.getDesc());

        itemName = accountHistory.getDesc();

        if (accountHistory.getCredit() != null) {
            if (Float.parseFloat(accountHistory.getCredit()) < Float.parseFloat(accountHistory.getAmount())
                   &&  Float.parseFloat(accountHistory.getCredit()) != Float.parseFloat(accountHistory.getDebit())) {

                if(Float.parseFloat(accountHistory.getAmount()) != 0.00 &&
                        !accountHistory.getDebit().equals("0.00")) {
                    holder.debit.setText("PAY NOW");
                }else{
                    holder.debit.setText("");
                }

            }else{
                holder.debit.setText("");
            }
        }
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                credit = accountHistory.getCredit();
                amount = accountHistory.getAmount();

                GeneralPref  gp = new GeneralPref(mCtx);
                if (Float.parseFloat(credit) < Float.parseFloat(amount)) {
                    //Toast.makeText(mCtx, "You are owning!", Toast.LENGTH_SHORT).show();
//                    view = LayoutInflater.from(mCtx).inflate(R.layout.acativity_payviamomo, null);
//                    momoAmount = (EditText) view.findViewById(R.id.momo_amount);
//                    momoPhone  = (EditText) view.findViewById(R.id.momo_phone);
//                    momoAmount.setText(accountHistory.getAmount());
//                    paymentInitializer(view);

                    // Run the Shard prefernce

                    gp.setSelectedWardItems("bill_item", accountHistory.getDesc());
                    gp.setSelectedWardItems("bill_amount", accountHistory.getAmount());

                    FragmentPay fragmentPay = new FragmentPay();
                    setFragment(fragmentPay);
                }

                //gp.setSelectedWardItems("bill_item", "");
                //gp.setSelectedWardItems("bill_amount", "");
            }
        });

        holder.relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return false;
            }
        });
    }


    private void setFragment(android.support.v4.app.Fragment fragment){

        android.support.v4.app.FragmentManager fragmentManager = ((AppCompatActivity)mCtx).getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }


    public void paymentInitializer(final View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
        builder.setView(view)
                .setTitle("Payment")
                //.setMessage("Do you want to make this payment of Amount of ( " + accountHistory.getCur()+" ) "+  amount + "?")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Make Payment", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //Fire request
                        if (momoPhone.getText().toString().equals("")) {

                        }else {
                            Toast.makeText(mCtx, "Payment Initializing .. Please wait..", Toast.LENGTH_LONG).show();
                            paymentRequest(
                                    authSharedPref.getUserName(),
                                    //MSICheckers.getNetwork(momoPhone.getText().toString()),
                                    "MTN",
                                    momoPhone.getText().toString(),
                                    generalPref.getSelectedWardItems("ward_term"),
                                    generalPref.getSelectedWardItems("ward_aca_year"),
                                    generalPref.getSelectedWardItems("ward_code"),
                                    generalPref.getSelectedWardItems("ward_name"),
                                    generalPref.getSelectedWardItems("ward_class"),
                                    "00001-9",
                                    itemName,
                                    amount,
                                    generalPref.getSelectedWardItems("ward_school")
                            );
                        }
                    }
                });
        builder.setView(view);
        builder.show();
    }

    public void paymentRequest(
            String payeeName, String network, String msiNumber, String term, String acaYear,
            String wardId, String wardName, String wardClass, String billItem, String billItemName,
            String amount, String school
    ){
        String url = null;
        schoolDAO = new SchoolDAO(mCtx);
        Cursor cursor = schoolDAO.getSchoolByName(school);
        String schoolId = null;

        if (cursor.getCount() == 0){
            Log.d("Testing SQLite", "count: "+ cursor.getCount());
        }else{
            StringBuffer stringBuffer = new StringBuffer();
            while (cursor.moveToNext() || cursor == null){
                stringBuffer.append("code " + cursor.getString(2) + "\n");
                schoolId = cursor.getString(2);
            }
        }

        try {
            //payeeName = URLEncoder.encode(payeeName, "UTF-8");
            //network = URLEncoder.encode(network, "UTF-8");
            //msiNumber = URLEncoder.encode(msiNumber, "UTF-8");
            term = URLEncoder.encode(term, "UTF-8");
            //acaYear = URLEncoder.encode(acaYear, "UTF-8");
            wardId = URLEncoder.encode(wardId, "UTF-8");
            //wardName = URLEncoder.encode(wardName, "UTF-8");
            //wardClass = URLEncoder.encode(wardClass, "UTF-8");
            //billItem = URLEncoder.encode(billItem, "UTF-8");
            //billItemName = URLEncoder.encode(billItemName, "UTF-8");
            amount = URLEncoder.encode(amount, "UTF-8");
            school = URLEncoder.encode(school, "UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        msiNumber = "233" + msiNumber.substring(1);
        //url = "https://www.ikolilu.com:23000/MTNMomoPay?network="+network+"&msisdn="+msiNumber+"&szterm="+term+"&szacayear="+acaYear+"&studentid="+wardId+"&studentname="+wardName+"&szclass="+wardClass+"&billitem="+billItem+"&billitemname="+billItemName+"&szamount="+amount+"&payee="+payeeName+"&school=GSISCHOOL";
        url = "https://www.ikolilu.com:23000/MTNMomoPay?network="+network+"&msisdn="+msiNumber+"&szterm="+term+"&szacayear="+acaYear+"&studentid="+wardId+"&studentname=\""+wardName+"\"&szclass=\""+wardClass+"\"&billitem="+billItem+"&billitemname=\""+billItemName+"\"&szamount="+amount+"&payee=\""+payeeName+"\"&school="+schoolId;
        Log.d("paymentRequest", "paymentRequest: "+url);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(mCtx).add(request);
    }

    @Override
    public int getItemCount() {
        return (accountHistoryList != null) ? accountHistoryList.size() : 0;
    }

    class AccountHistoryViewholder extends RecyclerView.ViewHolder{
        TextView date, term, desc, aca_year, amount, cur, debit, credit, bill_cost;
        RelativeLayout relativeLayout;

        public AccountHistoryViewholder(View itemView) {
            super(itemView);

            date = (TextView) itemView.findViewById(R.id.date);
            //term = (TextView) itemView.findViewById(R.id.term);
            //aca_year = (TextView) itemView.findViewById(R.id.acayear);
            amount = (TextView) itemView.findViewById(R.id.total_amount);
            cur = (TextView) itemView.findViewById(R.id.cur);
            debit = (TextView) itemView.findViewById(R.id.debit);
            credit = (TextView) itemView.findViewById(R.id.credit);
            desc = (TextView) itemView.findViewById(R.id.desc);
            bill_cost = (TextView) itemView.findViewById(R.id.bill_cost);

            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.history_bill_box);
        }
    }
}
