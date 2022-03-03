package mn.amra.lab2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {
    String[] items = { "Add", "Subtract", "Multiply", "Divide", };

    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItems;
    private static final String TAG = "MyActivity";
    private EditText textA;
    private EditText textB;
    private EditText textResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lab_layout);

        textA = findViewById(R.id.etValueA);
        textB = findViewById(R.id.etValueB);
        textResult = findViewById(R.id.etResult);

        Button addBtn = findViewById(R.id.btAdd);
        Button subBtn = findViewById(R.id.btSub);
        Button mulBtn = findViewById(R.id.btMulti);
        Button divBtn = findViewById(R.id.btDivide);

        autoCompleteTxt = findViewById(R.id.auto_complete_txt);
        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item, items);
        autoCompleteTxt.setAdapter(adapterItems);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = handleCalculate("ADD");
                textResult.setText(String.valueOf(result));
            }
        });

        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = handleCalculate("SUBTRACT");
                textResult.setText(String.valueOf(result));
            }
        });

        mulBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = handleCalculate("MULTIPLY");
                textResult.setText(String.valueOf(result));
            }
        });

        divBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(textB.getText().toString()) != 0) {
                    int result = handleCalculate("DIVIDE");
                    textResult.setText(String.valueOf(result));
                }
            }
        });

        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                String item = parent.getItemAtPosition(position).toString();
                int result = handleCalculate(item.toUpperCase(Locale.ROOT));
                if (result != Integer.MIN_VALUE)
                    textResult.setText(String.valueOf(result));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        int result = Integer.MIN_VALUE;
        switch (item.getItemId()) {
            case R.id.add_item:
                result = handleCalculate("ADD");
                textResult.setText(String.valueOf(result));
                return true;
            case R.id.subtract_item:
                result = handleCalculate("SUBTRACT");
                textResult.setText(String.valueOf(result));
                return true;
            case R.id.multiply_item:
                result = handleCalculate("MULTIPLY");
                textResult.setText(String.valueOf(result));
                return true;
            case R.id.divide_item:
                if (Integer.parseInt(textB.getText().toString()) != 0) {
                    result = handleCalculate("DIVIDE");
                    textResult.setText(String.valueOf(result));
                }
                return true;
            case R.id.square:
                Toast.makeText(getApplicationContext(), "Square", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.squareRoot:
                Toast.makeText(getApplicationContext(), "Square Root", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public int handleCalculate(String method) {
        int a = Integer.parseInt(textA.getText().toString());
        int b = Integer.parseInt(textB.getText().toString());
        int result = Integer.MIN_VALUE;
        try {
            switch(method) {
                case "ADD":
                    result = a + b;
                    break;
                case "SUBTRACT":
                    result = a - b;
                    break;
                case "MULTIPLY":
                    result = a * b;
                    break;
                case "DIVIDE":
                    if (b == 0) {
                        Toast.makeText(getApplicationContext(), "Can not divide number by 0", Toast.LENGTH_SHORT).show();
                        throw new IllegalArgumentException("Argument 'divisor' is 0");
                    } else {
                        result = a / b;
                    }
                    break;
            }
        } catch (Exception exception) {
            Log.e(TAG, exception.getMessage());
        }

        Log.i(TAG, "Lab => "+ method + ": " + a + " " + b + " " + (result));
        return result;
    }
}