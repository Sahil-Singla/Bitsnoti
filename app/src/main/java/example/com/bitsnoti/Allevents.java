package example.com.bitsnoti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class Allevents extends AppCompatActivity {

    private ListView listView;
    private Button btnclublogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allevents);

        listView = (ListView) findViewById(R.id.listView);
        btnclublogin = (Button)findViewById(R.id.btnclublogin);
        sendRequest();
        btnclublogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intentclublogin = new Intent(Allevents.this,LoginActivity.class);
                startActivity(intentclublogin);
            }
        });
    }

    private void sendRequest(){

        StringRequest stringRequest = new StringRequest(AppConfig.URL_CHECKEVENTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        showJSON(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Allevents.this,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String json){
        ParseJSON pj = new ParseJSON(json);
        pj.parseJSON();
        CustomList cl = new CustomList(this, ParseJSON.titles,ParseJSON.descriptions,ParseJSON.dates,ParseJSON.times);
        listView.setAdapter(cl);
    }
}