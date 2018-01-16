package com.example.sl.vistaracrew;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ordersActivity extends AppCompatActivity {

    ListView listView;
    Button extract;
    ArrayList<ContentFormat> arrayListEmailContent =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        //Log.d("*********","In MAIN 1");

        listView= (ListView)findViewById(R.id.content);
        extract= (Button) findViewById(R.id.btnextract);

        extract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchContent();

            }
        });

        arrayListEmailContent.add(new ContentFormat("ABC", "HEllo frie"));
        MyAdapter itemAdapter = new MyAdapter(ordersActivity.this, R.layout.list_item_content, arrayListEmailContent);
        listView.setAdapter(itemAdapter);

    }

    public void fetchContent(){

       String tag_string_req = "req_login";
       Log.d("*************","IN FETCH");

        StringRequest strReq = new StringRequest(Request.Method.POST,
                "http://192.168.0.23/vistara_api/fetchRequestForCrew.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("***********","ON RESPONSE");
                Log.d("request", "Request: " + response.toString());

                try {
                    JSONObject jo = new JSONObject(response);

                    Toast.makeText(ordersActivity.this, "weeee.."+response, Toast.LENGTH_SHORT).show();
                    Log.d("*****************", "Meow"+response);

                    boolean error = jo.getBoolean("error");
                    if (arrayListEmailContent.size()>0)
                        arrayListEmailContent.clear();

                    if(!error){

                        Toast.makeText(ordersActivity.this, "There is no error ", Toast.LENGTH_SHORT).show();

                        JSONArray jArr = jo.getJSONArray("request");

                        Toast.makeText(ordersActivity.this, "the json request is fetched", Toast.LENGTH_SHORT).show();

                        if (arrayListEmailContent.size()>0)
                            arrayListEmailContent.clear();

                        for (int i = 0; i < jArr.length(); i++) {
//
                            JSONObject eventObj = jArr.getJSONObject(i);
                            String sn = eventObj.getString("seat_no");
                            String rm= eventObj.getString("request_made");

                            sn= sn.trim();
                            rm= rm.trim();

                            arrayListEmailContent.add(new ContentFormat(sn, rm));

                            Log.d("********!!", String.valueOf(arrayListEmailContent));

                        }

                    }

                    else{
                        Toast.makeText(ordersActivity.this, "Error occurred in fetching. Get the error", Toast.LENGTH_SHORT).show();
                        // Error occurred in fetching. Get the error
                        // message
                        String errorMsg = jo.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_SHORT).show();
                    }

                    MyAdapter itemAdapter = new MyAdapter(ordersActivity.this, R.layout.list_item_content, arrayListEmailContent);
                    listView.setAdapter(itemAdapter);


                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                Log.d("ArrayList","size"+ arrayListEmailContent.size());
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("request", "Request List Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }

}
