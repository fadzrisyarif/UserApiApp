package com.fadzri.userapiapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddUserFragment extends Fragment {
    TextInputEditText firstNameInput, lastNameInput, emailInput;
    Button insertBtn;
    private final String url = "https://felixstore.pythonanywhere.com/api/v1/users/add";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_user, container, false);

        firstNameInput = view.findViewById(R.id.firstNameInput);
        lastNameInput = view.findViewById(R.id.lastNameInput);
        emailInput = view.findViewById(R.id.emailInput);
        insertBtn = view.findViewById(R.id.insertBtn);


        JSONObject object = new JSONObject();
        try {
            //input your API parameters
            object.put("firstname",firstNameInput.getText());
            object.put("lastname",lastNameInput.getText());
            object.put("email",emailInput.getText());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        insertBtn.setOnClickListener(v -> {
            // Request a string response from the provided URL.
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Navigation.findNavController(view).navigate(R.id.action_addUserFragment_to_userListFragment);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(view.getContext(), "That didn't work!", Toast.LENGTH_LONG).show();
                }
            });

            // Add the request to the RequestQueue.
            MySingleton.getInstance(view.getContext()).addToRequestQueue(jsonObjectRequest);
        });


        return view;
    }
}