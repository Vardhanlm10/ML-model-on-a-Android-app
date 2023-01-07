package com.example.demo_ml_to_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    TextInputEditText Clump_thickness,Uniformity_Size,Uniformity_Shape,Marginal_Adhesion,Epithelial_Cell_Size,Bare_Nuclei,Bland_Chromatin,Normal_Nucleoli,Mitoses,Result;
    Button predict;
    String url = "https://ml-to-android-demo.herokuapp.com/predict";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Clump_thickness = findViewById(R.id.Clump_Thickness_t);
        Uniformity_Size = findViewById(R.id.Uniformity_Size_t);
        Uniformity_Shape = findViewById(R.id.Uniformity_Shape_t);
        Marginal_Adhesion = findViewById(R.id.Marginal_Adhesion_t);
        Epithelial_Cell_Size = findViewById(R.id.Epithelial_Cell_Size_t);
        Bare_Nuclei = findViewById(R.id.Bare_Nuclei_t);
        Bland_Chromatin = findViewById(R.id.Bland_Chromatin_t);
        Normal_Nucleoli = findViewById(R.id.Normal_Nucleoli_t);
        Mitoses = findViewById(R.id.Mitoses_t);
        Result = findViewById(R.id.Result_t);
        predict = findViewById(R.id.Submit);

        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // hit the API -> Volley
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String data = jsonObject.getString("Health Status");
                                    Result.setText(data);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }){

                    @Override
                    protected Map<String,String> getParams(){
                        Map<String,String> params = new HashMap<String,String>();
                        params.put("Clump_Thickness",Clump_thickness.getText().toString());
                        params.put("Uniformity_Size",Uniformity_Size.getText().toString());
                        params.put("Uniformity_Shape",Uniformity_Shape.getText().toString());
                        params.put("Marginal_Adhesion",Marginal_Adhesion.getText().toString());
                        params.put("Epithelial_Cell_Size",Epithelial_Cell_Size.getText().toString());
                        params.put("Bare_Nuclei",Bare_Nuclei.getText().toString());
                        params.put("Bland_Chromatin",Bland_Chromatin.getText().toString());
                        params.put("Normal_Nucleoli",Normal_Nucleoli.getText().toString());
                        params.put("Mitoses",Mitoses.getText().toString());



                        return params;
                    }

                };
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(stringRequest);
            }




    });
};
}