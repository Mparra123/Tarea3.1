package com.example.tarea3_convert;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Convert_Activity extends AppCompatActivity {

    //essential URL structure
    // API: MXN, CRC, EUR


    public static final String BASE_URL= "http://apilayer.net/api/live?access_key=39889281045c73f9da42205d4547f1e9&currencies=EUR,CRC,MXN&source=USD&format=1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert_);


        /*Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


        //Hot fix in order to run network operations on thread or as an asynchronous task.
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setUp();
    }


    public void setUp(){


        final Button mButton = (Button)findViewById(R.id.btnChange);

        final EditText mEditEUR = (EditText)findViewById(R.id.txtEU);

        final EditText mEditMXN = (EditText)findViewById(R.id.txtMXN);

        final EditText mEditCRC = (EditText)findViewById(R.id.txtCRC);


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //validating the internet connection

                final String EuroString = mEditEUR.getText().toString();// getting the value from the UI

                final String PesosString = mEditMXN.getText().toString();// getting the value from the UI

                final String colonesString = mEditCRC.getText().toString();// getting the value from the UI

                if(hasInternetAccess()==false){ // validating internet connection permissions.

                    Toast.makeText(getApplicationContext(),"Not internet connection sorry :(",Toast.LENGTH_SHORT).show();

                }else{

                    Toast.makeText(getApplicationContext(),"Internet connection successfully accomplish",Toast.LENGTH_SHORT).show();

                    try {


                        //mEditContent.setText(downloadContent(mEdit_Text.toString())); // we set the value that will get the text
                      // downloadContent(mEditUSA.getText().toString());

                        // convert in action

                        if(TextUtils.isEmpty(colonesString) != true){
                            convertToColones(colonesString);
                        }

                        if(TextUtils.isEmpty(EuroString)!=true){
                            convertToEuro(EuroString);
                        }


                        if(TextUtils.isEmpty(PesosString)!=true){
                            convertToMexico(PesosString);

                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),"Error downloading",Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }
        });

    }

    //validating the internet connection.
    private boolean hasInternetAccess() {

            ConnectivityManager cm = (ConnectivityManager)getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();

            return netInfo != null &&  netInfo.isConnectedOrConnecting();
    }

    //Passing the url in order to download it from internet.

    private String downloadContent () throws IOException, JSONException {
        String response="";
        URL urlToFetch = new URL(BASE_URL);
        HttpURLConnection urlConnection = (HttpURLConnection)urlToFetch.openConnection(); //opening the connection

        InputStream stream =urlConnection.getInputStream(); // will save the data
        response = readStrem(stream); //method that will read the data with a buffer
        urlConnection.disconnect(); //closing the connection

        //showing the json response
        System.out.println(response);
/*
//this was the old function

        JSONObject Response = new JSONObject(response);
        String success = Response.getString("success");// the response from json
        JSONObject currencies = Response.getJSONObject("quotes");
        Double colon = currencies.getDouble("USDCRC"); //will show the specific value Colones
        Double pesos = currencies.getDouble("USDMXN"); //will show the specific value Pesos Mexicanos
        Double euros = currencies.getDouble("USDEUR");//will show the specific value Euros

        convertToColones(colon);
        convertToMexico(pesos);
        convertToEuro(euros);
*/
        return response;
   //     System.out.println(colon);
    }

    //method to read the data from internet
    private String readStrem(InputStream stream) {

            Reader reader = new InputStreamReader(stream);
            BufferedReader buffer = new BufferedReader(reader);
            String response=""; // the response to show
            String chunkJustRead=""; // the data collected

            try{
                while((chunkJustRead = buffer.readLine()) != null){
                    response += chunkJustRead;
                }

            }catch (IOException e){
                e.printStackTrace();
            }


            return response;
    }


    /*
    * CONVERT DATA FROM EACH COUNTRY VALUE EDIT TEXT
    *
    * */


    public void convertToEuro(String txt) throws IOException, JSONException {


        //Json interaction
        JSONObject Response = new JSONObject(downloadContent());
        String success = Response.getString("success");// the response from json
        JSONObject currencies = Response.getJSONObject("quotes");
        Double euros = currencies.getDouble("USDEUR"); //will show the specific value Colones

      //  if( TextUtils.isEmpty(EuroString) != true){


            double EuroDouble = Double.parseDouble(txt); // Parsing the value to double from String
            double addstraction = euros * EuroDouble; // making the change

            Toast.makeText(getApplicationContext(),"The change for this amount will be : " + addstraction,Toast.LENGTH_LONG).show();

            System.out.println(addstraction);
        //}else{

          //  Toast.makeText(getApplicationContext(),"Error, please add a value",Toast.LENGTH_SHORT).show();
        //}

        Response=null;//testing in order to set the values to 0
    }

    public void convertToMexico(String txt) throws IOException, JSONException {


        //Json interaction
        JSONObject Response = new JSONObject(downloadContent());
        String success = Response.getString("success");// the response from json
        JSONObject currencies = Response.getJSONObject("quotes");
        Double pesos = currencies.getDouble("USDMXN"); //will show the specific value Colones


            double PesosDouble = Double.parseDouble(txt); // Parsing the value to double from String
            double addstraction = pesos * PesosDouble; // making the change

            Toast.makeText(getApplicationContext(),"The change for this amount will be : " + addstraction,Toast.LENGTH_LONG).show();

            System.out.println(addstraction);

        Response=null;
    }
    public void convertToColones(String txt) throws IOException, JSONException {

//Json interaction
        JSONObject Response = new JSONObject(downloadContent());
        String success = Response.getString("success");// the response from json
        JSONObject currencies = Response.getJSONObject("quotes");
        Double colon = currencies.getDouble("USDCRC"); //will show the specific value Colones

        double colonesDouble = Double.parseDouble(txt); // Parsing the value to double from String
        double addstraction = colon * colonesDouble; // making the change

        Toast.makeText(getApplicationContext(),"The change for this amount will be : " + addstraction,Toast.LENGTH_LONG).show();

        System.out.println(addstraction);

        Response=null;
    }



}
