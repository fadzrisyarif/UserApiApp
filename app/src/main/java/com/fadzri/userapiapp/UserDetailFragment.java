package com.fadzri.userapiapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

public class UserDetailFragment extends Fragment {

    TextInputEditText firstNameField, lastNameField, emailField;
    Button updateBtn, deleteBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_detail, container, false);
        User user = UserDetailFragmentArgs.fromBundle(getArguments()).getUser();
        String UPDATE_URL = "https://felixstore.pythonanywhere.com/api/v1/users/update";
        String DELETE_URL = "https://felixstore.pythonanywhere.com/api/v1/users/delete";
        firstNameField = view.findViewById(R.id.firstNameField);
        firstNameField.setText(user.getFirstName());
        lastNameField = view.findViewById(R.id.lastNameField);
        lastNameField.setText(user.getLastName());
        emailField = view.findViewById(R.id.emailField);
        emailField.setText(user.getEmail());

        updateBtn = view.findViewById(R.id.updateBtn);
        deleteBtn = view.findViewById(R.id.deleteBtn);

        updateBtn.setOnClickListener(v -> {
            int id = UserDetailFragmentArgs.fromBundle(getArguments()).getUser().getId();

            JSONObject object = new JSONObject();
            try {
                //input your API parameters
                object.put("firstname",firstNameField.getText());
                object.put("lastname",lastNameField.getText());
                object.put("email",emailField.getText());
                object.put("id",id);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Request a string response from the provided URL.
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, UPDATE_URL, object,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Navigation.findNavController(view).navigate(R.id.action_userDetailFragment_to_userListFragment);
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


        deleteBtn.setOnClickListener(v -> {
            int id = UserDetailFragmentArgs.fromBundle(getArguments()).getUser().getId();
            JSONObject object1 = new JSONObject();
            try {
                //input your API parameters
                object1.put("id",id);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Request a string response from the provided URL.
            JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.POST, DELETE_URL, object1,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Navigation.findNavController(view).navigate(R.id.action_userDetailFragment_to_userListFragment);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(view.getContext(), "That didn't work!", Toast.LENGTH_LONG).show();
                }
            });

            // Add the request to the RequestQueue.
            MySingleton.getInstance(view.getContext()).addToRequestQueue(jsonObjectRequest1);

        });


        return view;
    }
}