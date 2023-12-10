package com.example.ezakat;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    private EditText weightEditText, typeEditText, valueEditText;
    private TextView resultTextView;
    private Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the toolbar
        myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(R.string.app_name);

        // Set up the Zakat calculation components
        weightEditText = findViewById(R.id.editTextWeight);
        typeEditText = findViewById(R.id.editTextType);
        valueEditText = findViewById(R.id.editTextValue);
        resultTextView = findViewById(R.id.textViewResult);

        Button calculateButton = findViewById(R.id.calculateButton);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateZakat();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_share) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Please use any application -https://github.com/Anisyuha1/ezakat.git");
            startActivity(Intent.createChooser(shareIntent, null));
            return true;
        } else if (item.getItemId() == R.id.item_about) {
            Intent aboutIntent = new Intent(this, AboutActivity.class);
            startActivity(aboutIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void calculateZakat() {
        String weightStr = weightEditText.getText().toString();
        String type = typeEditText.getText().toString();
        String valueStr = valueEditText.getText().toString();

        if (TextUtils.isEmpty(weightStr) || TextUtils.isEmpty(type) || TextUtils.isEmpty(valueStr)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double weight = Double.parseDouble(weightStr);
            double value = Double.parseDouble(valueStr);

            double totalValue = weight * value;
            double zakatPayableValue = (weight - getThreshold(type)) * value;
            double zakatAmount = 0.025 * zakatPayableValue;

            String result = "Zakat Payable Value: " + "RM" + zakatPayableValue +
                    "\nTotal Zakat: " + "RM" + zakatAmount;

            resultTextView.setText(result);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid input. Please enter valid numbers.", Toast.LENGTH_SHORT).show();
        }
    }

    private double getThreshold(String type) {
        return type.equalsIgnoreCase("keep") ? 85 : 200;
    }
}
