package com.ramon.testapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);

        recyclerView = findViewById(R.id.rv_main);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        reference = database.getReference("Data");
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Movie> firebaseRecyclerOptions =
            new FirebaseRecyclerOptions.Builder<Movie>()
                .setQuery(reference, Movie.class)
                .build();

        FirebaseRecyclerAdapter<Movie, ViewHolder> firebaseRecyclerAdapter =
            new FirebaseRecyclerAdapter<Movie, ViewHolder>(firebaseRecyclerOptions) {
                @Override
                protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Movie model) {

                    holder.setDetails(getApplicationContext(), model.getImage());
                    String trailerUrl = getItem(position).getTrailer();

                    holder.imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(MainActivity.this, PlayerActivity.class);
                            intent.putExtra("url", trailerUrl);
                            startActivity(intent);
                        }
                    });
                }

                @NonNull
                @Override
                public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.item,parent,false);

                    return new ViewHolder(view);
                }
            };

        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
}