package com.example.elric.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.bitpay.sdk.android.BitPayAndroid;
import com.bitpay.sdk.android.InvoiceActivity;
import com.bitpay.sdk.android.interfaces.InvoicePromiseCallback;
import com.bitpay.sdk.android.interfaces.PromiseCallback;
import com.bitpay.sdk.controller.BitPayException;
import com.bitpay.sdk.model.Invoice;
import com.elriczhan.basecore.utils.LogUtil;

/**
 * Created by xinshei on 2018/3/20.
 */

public class BitPayTestActivity extends AppCompatActivity {

    private Button test1;
    private Button test2;
    private Button test3;
    public static final String BITPAY_INVOICE = "BITPAY_INVOICE";
    public static final String MY_INVOICE= "MY_INVOICE";

    private com.bitpay.sdk.model.Invoice bitpayInvoice;
    private BitPayAndroid bitpay;
    private static final int PAYMENT_RESULT = 12;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitpay_test);

        test1 = findViewById(R.id.btn_test1);
        test2 = findViewById(R.id.btn_test2);
        test3 = findViewById(R.id.btn_test3);

        test1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String clientToken = "Ry2Q8fHgCUP7ognnZpnbP1";
                BitPayAndroid.withToken(clientToken, "https://test.bitpay.com/")
                        .then(new PromiseCallback<BitPayAndroid>() {
                            @Override
                            public void onSuccess(final BitPayAndroid bitpay) {
                                LogUtil.e("---------------success bit pay -------------");
                                LogUtil.e(bitpay.getPrivateKey() + " private key");
                                LogUtil.e(bitpay.getIdentity() + " identity");
                                LogUtil.e("---------------success bit pay -------------");
                                BitPayTestActivity.this.bitpay = bitpay;
                                bitpay.createNewInvoice(new com.bitpay.sdk.model.Invoice(0.0, "BTC")).then(new InvoicePromiseCallback() {
                                    @Override
                                    public void onSuccess(com.bitpay.sdk.model.Invoice invoice) {
                                        LogUtil.e("on success " + invoice.getToken());
                                        bitpayInvoice = invoice;
                                        Intent invoiceIntent = new Intent(BitPayTestActivity.this, InvoiceActivity.class);
                                        LogUtil.e(invoice.getPaymentUrls().getBIP21());
                                        invoiceIntent.putExtra(InvoiceActivity.INVOICE, invoice);
                                        invoiceIntent.putExtra(InvoiceActivity.CLIENT, bitpay);
//                                                                 runOnUiThread(new Runnable() {
//                                                                     @Override
//                                                                     public void run() {
//                                                                         dialog.dismiss();
//                                                                     }
//                                                                 });
                                        startActivityForResult(invoiceIntent, PAYMENT_RESULT);
                                    }

                                    @Override
                                    public void onError(BitPayException e) {
//                                                                 dialog.dismiss();
                                        e.printStackTrace();
                                        LogUtil.e("on error " + e.toString());

//                                                                 AlertDialog dialog = new AlertDialog.Builder(getApplicationContext()).setTitle("Creating Invoice").setMessage("Unable to create an invoice. Check your connection and token and try again.").create();
//                                                                 dialog.show();
                                    }
                                });
                            }

                            @Override
                            public void onError(BitPayException e) {
//                                                         dialog.dismiss();
                                e.printStackTrace();

//                                                         AlertDialog dialog = new AlertDialog.Builder(getApplicationContext()).setTitle("Creating BitPay Client").setMessage("Unable to connect to the server. Check your connection and token and try again.").create();
//                                                         dialog.show();
                            }
                        });

            }

        });


        test2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Invoice invoice = new Invoice(200.0, "BTC");
                invoice.setId("VWdWD3vxHY1mq71rQGHHtw");
                invoice.setBtcPrice("100");
                Intent invoiceIntent = new Intent(BitPayTestActivity.this, InvoiceActivity.class);
                invoiceIntent.putExtra(InvoiceActivity.INVOICE, invoice.getId());
                startActivity(invoiceIntent);

            }
        });


        test3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String clientToken = "VWdWD3vxHY1mq71rQGHHtw";
//                new BitPayAndroid.GetClientWithTokenTask() {
//                    @Override
//                    protected void onPostExecute(BitPayAndroid bitpay) {
//                        LogUtil.e(bitpay.getIdentity()+" " + bitpay.getPrivateKey());
//                    }
//                }.execute(clientToken);

                BitPayAndroid.withToken(clientToken, "https://test.bitpay.com/")
                        .then(new PromiseCallback<BitPayAndroid>() {
                            @Override
                            public void onSuccess(BitPayAndroid bitPayAndroid) {
                                LogUtil.e("asdasdas success");
                                Intent invoiceIntent = new Intent(BitPayTestActivity.this, InvoiceActivity.class);
//                                LogUtil.e(invoice.getPaymentUrls().getBIP21());
//                                invoiceIntent.putExtra(InvoiceActivity.INVOICE, invoice);
                                invoiceIntent.putExtra(InvoiceActivity.CLIENT, bitpay);
                                startActivityForResult(invoiceIntent, PAYMENT_RESULT);
                            }

                            @Override
                            public void onError(BitPayException e) {
                                LogUtil.e("asdasdas error " + e.toString());
                            }
                        });

            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BITPAY_INVOICE, bitpayInvoice);
        outState.putParcelable(MY_INVOICE, bitpayInvoice);

    }
}
