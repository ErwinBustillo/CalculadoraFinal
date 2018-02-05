package com.edu.uninorte.calculadorav2;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    LinearLayout main;
    int count;

    String operation;
    private String temporal = "";
    private String nuevaOperacion ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        main = (LinearLayout) findViewById(R.id.mainLayout);
        count = 0;
        nuevaOperacion ="";
        Intent intent = getIntent();
        operation =(String) intent.getStringExtra("operaciones");
        generarLayout();
    }

    public void generarLayout(){

        for (int i=0; i<=operation.length()-1;i++){
            LinearLayout newLayout = new LinearLayout(this);
            newLayout.setOrientation(LinearLayout.HORIZONTAL);
            final TextView numberText = new TextView(this);
            numberText.setId(i);
            numberText.setText(operation.substring(i,i+1));

            Button bt = new Button(this);
            bt.setText("Edit "+operation.substring(i,i+1));
            bt.setTag(operation.substring(i,i+1));

            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //int id = (int) view.getTag();
                    //EditText e = (EditText) view.findViewById(id);
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("Cambiar termino");
                    // Set up the input
                    final EditText input = new EditText(view.getContext());
                    // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                    input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_NUMBER_VARIATION_NORMAL);
                    builder.setView(input);
                    // Set up the buttons
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            temporal = input.getText().toString();
                            numberText.setText(temporal.toString());
                            nuevaOperacion+=numberText.getText().toString();

                            //operation=changeCharInPosition(count,(char)numberText.getText(),operation);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            nuevaOperacion+=numberText.getText().toString();
                            dialog.cancel();
                        }
                    });

                    builder.show();

                }
            });
            nuevaOperacion += numberText.getText().toString();
            newLayout.addView(numberText);

            newLayout.addView(bt);

            main.addView(newLayout);
            count ++;
        }

        Button buttonGoBack = new Button(this);
        buttonGoBack.setText("Go Back ");
        buttonGoBack.setTag(count+1);
        main.addView(buttonGoBack);
        buttonGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast toastResult = Toast.makeText(view.getContext(), operation, Toast.LENGTH_SHORT);
                //toastResult.show();
                Intent returnIntent = new Intent();
                returnIntent.putExtra("operaciones",nuevaOperacion);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });
    }

}
