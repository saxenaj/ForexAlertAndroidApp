package com.forexalert.gcm.forexalert;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;


public class MainActivity extends Activity {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG ="mainactivity";

    Button register;
    Intent intent;

    Spinner convertfromSpinner;
    Spinner converttoSpinner;

    EditText forexrate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        convertfromSpinner = (Spinner) findViewById(R.id.convert_from);
        ArrayAdapter<CharSequence> adapterfrom = ArrayAdapter.createFromResource(this,
        R.array.convert_from, android.R.layout.simple_spinner_item);
        adapterfrom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        convertfromSpinner.setAdapter(adapterfrom);
        convertfromSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());

        converttoSpinner = (Spinner) findViewById(R.id.convert_to);
        ArrayAdapter<CharSequence> adapterto = ArrayAdapter.createFromResource(this,
        R.array.convert_to, android.R.layout.simple_spinner_item);
        adapterto.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        converttoSpinner.setAdapter(adapterto);
        converttoSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());

        forexrate = (EditText) findViewById(R.id.forex_rate);


        register= (Button)findViewById(R.id.regis);
        intent = new Intent(this, RegistrationIntentService.class);
        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                System.out.println("Clicking Jatin");
                if (checkPlayServices()) {
                    //String exch=String.valueOf(convertfromSpinner.getSelectedItem())+String.valueOf(converttoSpinner.getSelectedItem());
                    String forexvalue=String.valueOf(forexrate.getText());
                    intent.putExtra("forexalert",forexvalue);

                    // Start IntentService to register this application with GCM.

               /* Toast.makeText(MainActivity.this,"OnClickListener : " +"\nSpinner 1 : " + String.valueOf(convertfromSpinner.getSelectedItem()) +
                                "\nSpinner 2 : " + String.valueOf(converttoSpinner.getSelectedItem()) + "\nForex : " + forexrate.getText()
                        ,Toast.LENGTH_SHORT).show(); */

                    Toast.makeText(MainActivity.this, "Rate Alert is Set for" + forexrate.getText()
                            , Toast.LENGTH_SHORT).show();

                      startService(intent);


                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }



}
