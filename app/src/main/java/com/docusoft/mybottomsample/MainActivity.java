package com.docusoft.mybottomsample;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import hu.scythe.droidwriter.DroidWriterEditText;
import windyzboy.github.io.customeeditor.CustomEditText;

public class MainActivity extends AppCompatActivity {

    DroidWriterEditText dwEdit;

    /* private static final int COLOR_REQUEST = 1337;
     private RichEditText editor = null;
     private ColorPickerOperation colorPickerOp = null;
     private RichEditor mEditor;*/
    private TextView mPreview, title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ToggleButton boldToggle = (ToggleButton) findViewById(R.id.BoldButton);
        ToggleButton italicsToggle = (ToggleButton) findViewById(R.id.ItalicsButton);
        ToggleButton underlinedToggle = (ToggleButton) findViewById(R.id.UnderlineButton);

        //View coolButton = findViewById(R.id.CoolButton);
        //View cryButton = findViewById(R.id.CryButton);

        Button clearButton = (Button) findViewById(R.id.ClearButton);
        Button GetButton = (Button) findViewById(R.id.GetButton);
        title = findViewById(R.id.title);

        dwEdit = (DroidWriterEditText) findViewById(R.id.DwEdit);
        dwEdit.setImageGetter(new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {
                Drawable drawable = null;

                try {
//                  if (source.equals("smiley_cool.gif")) {
//                        drawable = getResources().getDrawable(R.drawable.smiley_cool);
//                    } else if (source.equals("smiley_cry.gif")) {
//                        drawable = getResources().getDrawable(R.drawable.smiley_cry);
//                    } else {
//                        drawable = null;
//                    }

                    // Important
                    if (drawable != null) {
                        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    }
                } catch (Exception e) {
                    Log.e("DroidWriterTestActivity", "Failed to load inline image!");
                }
                return drawable;
            }
        });
        dwEdit.setSingleLine(false);
        dwEdit.setMinLines(10);
        dwEdit.setBoldToggleButton(boldToggle);
        dwEdit.setItalicsToggleButton(italicsToggle);
        dwEdit.setUnderlineToggleButton(underlinedToggle);
        dwEdit.setImageInsertButton(GetButton, "https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg");


        dwEdit.setClearButton(clearButton);

        dwEdit.setTextHTML("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "    <html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "    \t<head>\n" +
                "    \t\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /><title>\n" +
                "    \t\t</title>\n" +
                "    \t\t<style type=\"text/css\">\n" +
                "    \t\t\t.cs95E872D0{text-align:left;text-indent:0pt;margin:0pt 0pt 0pt 0pt}\n" +
                "    \t\t\t.cs9D249CCB{color:#000000;background-color:transparent;font-family:'Times New Roman';font-size:12pt;font-weight:normal;font-style:normal;}\n" +
                "    \t\t</style>\n" +
                "    \t</head>\n" +
                "    \t<body>\n" +
                "    \t\t<p class=\"cs95E872D0\"><span class=\"cs9D249CCB\">Sticky Note</span></p></body>\n" +
                "    </html>");

        GetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Spanned spannedText = dwEdit.getSpannedText();
                String textHTML = dwEdit.getTextHTML();
                Toast.makeText(MainActivity.this, textHTML, Toast.LENGTH_LONG).show();
                Log.d("MainActivity", "onClick: <html><head></head><body>" + textHTML + "</body></html>");
            }
        });

/*
        mEditor = (RichEditor) findViewById(R.id.richEditor);
        mEditor.setEditorHeight(200);
        mEditor.setEditorFontSize(22);
        mEditor.setEditorFontColor(Color.RED);
        //mEditor.setEditorBackgroundColor(Color.BLUE);
        //mEditor.setBackgroundColor(Color.BLUE);
        //mEditor.setBackgroundResource(R.drawable.bg);
        mEditor.setPadding(10, 10, 10, 10);
        //mEditor.setBackground("https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg");
        mEditor.setPlaceholder("Insert text here...");

        mPreview = (TextView) findViewById(R.id.preview);
        mEditor.insertImage("http://www.1honeywan.com/dachshund/image/7.21/7.21_3_thumb.JPG",
                "dachshund");

        mEditor.insertLink("https://github.com/wasabeef", "wasabeef");
        mPreview.onPreDraw();*/

    }


    private class LongOperation extends AsyncTask<String, Void, String> {
        ProgressDialog asyncDialog = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            // set message of the dialog
            asyncDialog.setMessage("Please wait processing");
            asyncDialog.setCancelable(false);
            // show dialog
            asyncDialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {


            beneficiaryItemTxn();


            return null;

        }

        @Override
        protected void onPostExecute(String result) {
            asyncDialog.dismiss();
            super.onPostExecute(result);


        }

    }


    public void beneficiaryItemTxn() {
        final String NAMESPACE = "http://Docusoft_Portal.org/";
        final String URL = "https://www.sharedocuments.co.uk/PortalUserServices.asmx?wsdl";
        final String SOAP_ACTION = "http://Docusoft_Portal.org/GetAccountCreditionals_Json";
        final String METHOD_NAME = "GetAccountCreditionals_Json";

        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("accid", "2001");
//            request.addProperty("email", "supoort@docusoft.net");
//            request.addProperty("password", "0g0CVwoe");
            request.addProperty("email", "desmond.palmer@docusoft.net");
            request.addProperty("password", "ZG9jdXNvZnQ=");

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            MarshalBase64 marshal = new MarshalBase64();
            marshal.register(envelope);

            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL, 150000);
            androidHttpTransport.call(SOAP_ACTION, envelope);

            if (envelope.getResponse() instanceof SoapObject) {
                SoapObject result = (SoapObject) envelope.getResponse();
                Log.d("MainActivity", "beneficiaryItemTxn: SoapObject: " + result.toString());
            } else {
                SoapPrimitive result = (SoapPrimitive) envelope.getResponse();
                Log.d("MainActivity", "beneficiaryItemTxn: SoapPrimitive: " + result.toString());
            }
        } catch (Exception e) {
//            exce = e.toString();
            e.printStackTrace();
        } finally {
        }
    }

}
