package com.ipath;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final EditText source = findViewById(R.id.source);
        source.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        clearEditText(source);
                    }
                }
        );

        final EditText destination = findViewById(R.id.destination);
        destination.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        clearEditText(destination);
                    }
                }
        );

        Bundle parent_data = getIntent().getExtras();

        Object src_data = parent_data.get("setsrc");
        if(src_data!=null)
            source.setText(src_data.toString());
    }

    private void clearEditText(EditText edit){
        edit.setText("");
    }
}
