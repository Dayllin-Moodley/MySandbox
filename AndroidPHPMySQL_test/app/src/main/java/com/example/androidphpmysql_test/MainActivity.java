package com.example.androidphpmysql_test;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextUsername, editTextEmail, edittextPassword;
    private Button buttonRegister;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextEmail=(EditText) findViewById(R.id.editTextEmail);
        editTextUsername=(EditText) findViewById(R.id.editTextUsername);
        edittextPassword=(EditText) findViewById(R.id.editTextPassword);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        progressDialog = new ProgressDialog(this);

        buttonRegister.setOnClickListener(this);
    }

    private void registerUser(){
        final String email = editTextEmail.getText().toString().trim();
        final String username = editTextUsername.getText().toString().trim();
        final String password = edittextPassword.getText().toString().trim();

        progressDialog.setMessage("Registering user...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("username",username);
                params.put("email",email);
                params.put("password",password);

                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onClick(View v) {
        if(v == buttonRegister){
            registerUser();
        }
    }
}