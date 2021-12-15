package com.fadzri.userapiapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    LayoutInflater layoutInflater;
    List<User> users;

    public UserAdapter(Context context, List<User> users){
        this.layoutInflater = LayoutInflater.from(context);
        this.users = users;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.user_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        holder.firstName.setText(users.get(position).getFirstName());
        holder.lastName.setText(users.get(position).getLastName());
        holder.email.setText(users.get(position).getEmail());

        holder.itemView.setOnClickListener(v -> {
            User user = new User();
            user.setFirstName(users.get(position).getFirstName());
            user.setLastName(users.get(position).getLastName());
            user.setEmail(users.get(position).getEmail());
            user.setId(users.get(position).getId());
            UserListFragmentDirections.ActionUserListFragmentToUserDetailFragment actionUserListFragmentToUserDetailFragment = UserListFragmentDirections.actionUserListFragmentToUserDetailFragment(user);
            Navigation.findNavController(v).navigate(actionUserListFragmentToUserDetailFragment);
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView firstName, lastName, email;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            firstName = itemView.findViewById(R.id.firstName);
            lastName = itemView.findViewById(R.id.lastName);
            email = itemView.findViewById(R.id.email);

        }

    }

}
