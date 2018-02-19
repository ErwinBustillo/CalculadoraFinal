package com.edu.uninorte.calculadorav2;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.Gravity;
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

    private String[] operacionVector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        main = (LinearLayout) findViewById(R.id.mainLayout);
        count = 0;
        Intent intent = getIntent();
        if (savedInstanceState == null){
            operation =(String) intent.getStringExtra("operaciones");
        }
        else{
            operation=savedInstanceState.getString("operaciones");
        }

        nuevaOperacion=operation;
        operacionVector = new String[operation.length()];
        generarLayout();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("operaciones",nuevaOperacion);
        super.onSaveInstanceState(outState);
    }

    public void generarLayout(){

        for (int i=0; i<=operation.length()-1;i++){
            LinearLayout newLayout = new LinearLayout(this);
            newLayout.setOrientation(LinearLayout.HORIZONTAL);

            //PARAMETROS DEL LINEAR LAYOUT
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            //params.weight = 1.0f;
            params.gravity = Gravity.LEFT;

            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            params.weight = 1.0f;
            params.gravity = Gravity.RIGHT;
            //

            final TextView numberText = new TextView(this);
            numberText.setId(i);
            numberText.setText(" "+operation.substring(i,i+1));
            numberText.setTextSize(30.0f);
            numberText.setLayoutParams(params);
            numberText.setTextColor(Color.BLACK);


            Button bt = new Button(this);
            bt.setText("Edit "+operation.substring(i,i+1));
            bt.setTag(operation.substring(i,i+1));
            bt.setBackgroundResource(R.drawable.button_style);
            bt.setTextColor(Color.WHITE);
            bt.setLayoutParams(params2);
            operacionVector[i]=operation.substring(i,i+1);

            final int finalI = i;
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //int id = (int) view.getTag();
                    //EditText e = (EditText) view.findViewById(id);
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("Cambiar termino");
                    builder.setIcon(R.mipmap.ic_launcher);
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
                            operacionVector[finalI]=numberText.getText().toString();
                            nuevaOperacion = "";
                            for (int j=0; j<operacionVector.length;j++){
                                nuevaOperacion+=operacionVector[j];
                            }

                           // nuevaOperacion+=numberText.getText().toString();

                            //operation=changeCharInPosition(count,(char)numberText.getText(),operation);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //nuevaOperacion+=numberText.getText().toString();
                            dialog.cancel();
                        }
                    });

                    builder.show();

                }
            });

            newLayout.addView(numberText);
            newLayout.addView(bt);
            main.addView(newLayout);
            count ++;
        }

        Button buttonGoBack = new Button(this);
        buttonGoBack.setText("Go Back ");
        buttonGoBack.setTag(count+1);
        buttonGoBack.setBackgroundResource(R.drawable.button_style);
        buttonGoBack.setTextColor(Color.WHITE);
        main.addView(buttonGoBack);
        buttonGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent returnIntent = new Intent();
                returnIntent.putExtra("operaciones",nuevaOperacion);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });
    }

}
