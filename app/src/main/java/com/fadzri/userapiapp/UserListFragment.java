package com.fadzri.userapiapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserListFragment extends Fragment{

    RecyclerView recyclerView;
    View view;
    List<User> users;
    FloatingActionButton floatingActionButton;
    private final String USER_URL = "https://felixstore.pythonanywhere.com/api/v1/users";
    UserAdapter userAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_list, container, false);
        recyclerView = view.findViewById(R.id.userList);
        users = new ArrayList<>();

        floatingActionButton = view.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_userListFragment_to_addUserFragment);
        });

        // Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, USER_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.
                        try {
                            JSONArray jsonArray = response.getJSONArray("users");
                            for(int i=0; i<=jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                User user = new User();
                                user.setId(jsonObject.getInt("id"));
                                user.setFirstName(jsonObject.getString("firstname"));
                                user.setLastName(jsonObject.getString("lastname"));
                                user.setEmail(jsonObject.getString("email"));

                                users.add(user);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                        userAdapter = new UserAdapter(view.getContext(), users);
                        recyclerView.setAdapter(userAdapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("That didn't work!");
            }
        });

        // Add the request to the RequestQueue.
        MySingleton.getInstance(view.getContext()).addToRequestQueue(jsonObjectRequest);

        return view;
    }

}