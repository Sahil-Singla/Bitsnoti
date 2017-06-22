package example.com.bitsnoti;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


    public class NewEvent extends Activity {
    private static final String TAG = NewEvent.class.getSimpleName();
    private Button btncreateevent;
    private EditText inputtitle;
    private EditText inputdescription;
    private EditText inputdate;
    private EditText inputtime;
    private ProgressDialog pDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        inputtitle = (EditText) findViewById(R.id.title);
        inputdescription = (EditText) findViewById(R.id.description);
        inputdate = (EditText) findViewById(R.id.date);
        inputtime=(EditText) findViewById(R.id.time);
        btncreateevent = (Button) findViewById(R.id.btncreateevent);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Createevent Button Click event
        btncreateevent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String title = inputtitle.getText().toString().trim();
                String description = inputdescription.getText().toString().trim();
                String date = inputdate.getText().toString().trim();
                String time = inputtime.getText().toString().trim();

                if (!title.isEmpty() && !description.isEmpty() && !date.isEmpty() && !time.isEmpty()) {
                    registerEvent(title, description, date, time);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter all details!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

    }

    private void registerEvent(final String title, final String description,
                              final String date, final String time) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.URL_REGISTEREVENT, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        Toast.makeText(getApplicationContext(), "Event successfully created", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(
                                NewEvent.this,
                                Allevents.class);
                        startActivity(intent);
                        finish();
                   } else {
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("title", title);
                params.put("description", description);
                params.put("date", date);
                params.put("time",time);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
