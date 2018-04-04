package com.example.elric.myapplication;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.nfc.Tag;
import android.nfc.tech.NfcA;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.elriczhan.basecore.utils.LogUtil;

import java.io.IOException;
import java.nio.charset.Charset;

public class NFC_Activity extends AppCompatActivity implements NfcAdapter.CreateNdefMessageCallback,
        NfcAdapter.OnNdefPushCompleteCallback {
    private static final int MESSAGE_SENT = 1;
    private NfcAdapter mNfcAdapter;
    private TextView mInfoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_);
        mInfoText = (TextView) findViewById(R.id.mInfoText);


//        NfcManager manager = new NfcManager();
//
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter == null) {
            Toast.makeText(this, "not support NFC", Toast.LENGTH_LONG).show();
        } else {
            mNfcAdapter.setOnNdefPushCompleteCallback(this, this);
            mNfcAdapter.setNdefPushMessageCallback(this, this); //注册NDEF回调消息
            Toast.makeText(this, "good NFC", Toast.LENGTH_LONG).show();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                mNfcAdapter.enableReaderMode(NFC_Activity.this, new NfcAdapter.ReaderCallback() {
                    @Override
                    public void onTagDiscovered(Tag tag) {
                        LogUtil.e("-------------------***********************");
                        LogUtil.e(tag.toString());
                        LogUtil.e(tag.getId() + "");
                        LogUtil.e(tag.getTechList().toString());
                        String[] techList = tag.getTechList();
                        for (String string : techList) {
                            LogUtil.e(string + " ??");
                        }
                        mInfoText.setText(tag.toString());
                        try {
                            NfcA.get(tag).transceive(NfcA.get(tag).getAtqa());
                            LogUtil.e(" down ???????????????????????????????????????????????????????????");

                        } catch (IOException e) {
                            e.printStackTrace();
                            LogUtil.e(e.toString() + " ???????????????????????????????????????????????????????????");
                        }
                        LogUtil.e("-------------------***********************");
                    }
                }, NfcAdapter.FLAG_READER_NFC_A, new Bundle());
            }


            LogUtil.e(mNfcAdapter.isNdefPushEnabled() + "  enable??");
        }
//        NdefMessage message = new NdefMessage();
//        mNfcAdapter.setNdefPushMessage();

//        NfcFCardEmulation.getInstance(mNfcAdapter).enableService()
//        NfcF.get("asd").transceive()
//        NfcA.get("asd").transceive()
    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
        Time time = new Time();
        time.setToNow();
        String text = ("Beam me up!\n\n" +
                "Beam Time: " + time.format("%H:%M:%S"));
        mInfoText.setText(text);
        NdefMessage msg = new NdefMessage(
                new NdefRecord[]{createMimeRecord(
                        "application/com.example.android.beam", text.getBytes())
                });
        return msg;
    }

    @Override
    public void onNdefPushComplete(NfcEvent arg0) {
        // A handler is needed to send messages to the activity when this
        // callback occurs, because it happens from a binder thread
        mHandler.obtainMessage(MESSAGE_SENT).sendToTarget();
    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_SENT:
                    Toast.makeText(getApplicationContext(), "Message sent!", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
            processIntent(getIntent());
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        // onResume gets called after this to handle the intent
        setIntent(intent);
    }

    /**
     * Parses the NDEF Message from the intent and prints to the TextView
     */
    void processIntent(Intent intent) {
        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(
                NfcAdapter.EXTRA_NDEF_MESSAGES);
        // only one message sent during the beam
        NdefMessage msg = (NdefMessage) rawMsgs[0];
        // record 0 contains the MIME type, record 1 is the AAR, if present
        mInfoText.setText(new String(msg.getRecords()[0].getPayload()));
    }

    /**
     * Creates a custom MIME type encapsulated in an NDEF record
     *
     * @param mimeType
     */
    public NdefRecord createMimeRecord(String mimeType, byte[] payload) {
        byte[] mimeBytes = mimeType.getBytes(Charset.forName("US-ASCII"));
        NdefRecord mimeRecord = new NdefRecord(
                NdefRecord.TNF_MIME_MEDIA, mimeBytes, new byte[0], payload);
        return mimeRecord;
    }


    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // If NFC is not available, we won't be needing this menu
        if (mNfcAdapter == null) {
            return super.onCreateOptionsMenu(menu);
        }
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                Intent intent = new Intent(Settings.ACTION_NFCSHARING_SETTINGS);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
