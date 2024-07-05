package com.example.orgdon;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

// This is an important class which helps us do a lot of things in our project.

public class MyAdapter extends FirebaseRecyclerAdapter<model, MyAdapter.myViewHolder> {
    public MyAdapter(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull final myViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull final model model) {
        holder.name.setText(model.getName());
        holder.address.setText(model.getAddress());
        holder.donated.setText(model.getDonated());
        holder.age.setText(model.getAge());
        holder.blood.setText(model.getBlood());
        holder.hospital.setText(model.getHospital());


        // This part of the code is for when the call button is pressed when viewing the
        // list. This code takes the user to the phone application.
        holder.call.setOnClickListener(v -> {
            String call = model.getPhone();
            String s = "tel:" + call;
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(s));
            v.getContext().startActivity(intent);

        });

        // This is the layout that is shown on pressing the edit button.
        holder.edit.setOnClickListener(view -> {
            final DialogPlus dialogPlus = DialogPlus.newDialog(holder.name.getContext())
                    .setContentHolder(new ViewHolder(R.layout.dialogcontent))
                    .setExpanded(true, 2400)
                    .create();

            // If we want to update our database we click on the entry and press edit.
            // On pressing Edit, this part of our program get invoked. This next code rewrites
            // the values into the Text fields.

            View myView = dialogPlus.getHolderView();
            final TextInputEditText name = myView.findViewById(R.id.nameEdit);
            final TextInputEditText address = myView.findViewById(R.id.addressEdit);
            final TextInputEditText donated = myView.findViewById(R.id.donatedEdit);
            final TextInputEditText age = myView.findViewById(R.id.ageEdit);
            final TextInputEditText blood = myView.findViewById(R.id.bloodEdit);
            final TextInputEditText phone = myView.findViewById(R.id.phoneEdit);
            final TextInputEditText hospital = myView.findViewById(R.id.hospitalEdit);
            Button submit = myView.findViewById(R.id.usubmit);

            name.setText(model.getName());
            address.setText(model.getAddress());
            donated.setText(model.getDonated());
            age.setText(model.getAge());
            blood.setText(model.getBlood());
            phone.setText(model.getPhone());
            hospital.setText(model.getHospital());

            dialogPlus.show();

            // Pressing the submit button then updates the data to the Firebase database.

            submit.setOnClickListener(view1 -> {
                Map<String, Object> map = new HashMap<>();
                map.put("name", Objects.requireNonNull(name.getText()).toString());
                map.put("address", Objects.requireNonNull(address.getText()).toString());
                map.put("donated", Objects.requireNonNull(donated.getText()).toString());
                map.put("age", Objects.requireNonNull(age.getText()).toString());
                map.put("blood", Objects.requireNonNull(blood.getText()).toString());
                map.put("phone", Objects.requireNonNull(phone.getText()).toString());
                map.put("hospital", Objects.requireNonNull(hospital.getText()).toString());

                FirebaseDatabase.getInstance().getReference().child("donors")
                        .child(Objects.requireNonNull(getRef(position).getKey())).updateChildren(map)
                        .addOnSuccessListener(aVoid -> dialogPlus.dismiss())
                        .addOnFailureListener(e -> dialogPlus.dismiss());
            });

        });

        // This is the Delete button that is present beside the Edit button.

        holder.delete.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(holder.name.getContext());
            builder.setTitle("Delete Donors?");
            builder.setMessage("Are you sure?");

            builder.setPositiveButton("Yes", (dialogInterface, i) -> FirebaseDatabase.getInstance().getReference().child("donors")
                    .child(Objects.requireNonNull(getRef(position).getKey())).removeValue());

            builder.setNegativeButton("No", (dialogInterface, i) -> {

            });

            builder.show();
        });

    }


    // This part of the code is used to create the specific card view layout of the
    // donor's list that we view.
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new myViewHolder(view);
    }

    static class myViewHolder extends RecyclerView.ViewHolder {

        final Button edit;
        final Button delete;
        final ImageButton call;
        final TextView name;
        final TextView address;
        final TextView donated;
        final TextView age;
        final TextView blood;

        final TextView hospital;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameText);
            address = itemView.findViewById(R.id.address);
            donated = itemView.findViewById(R.id.lastDonated);
            age = itemView.findViewById(R.id.tvage);
            blood = itemView.findViewById(R.id.bloodgroup);
            hospital = itemView.findViewById(R.id.hospital);
            call = itemView.findViewById(R.id.phone);
            edit = itemView.findViewById(R.id.editButton);
            delete = itemView.findViewById(R.id.deleteButton);
        }
    }
}
