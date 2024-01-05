package sg.edu.rp.c346.id22015131.l04_reservation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;



public class MainActivity extends AppCompatActivity {

    EditText name, phone, grpSize;
    DatePicker dp;
    TimePicker tp;
    CheckBox check;
    Button reset, submit;
    RadioGroup tableType;
    RadioButton smoke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        grpSize = findViewById(R.id.grpSize);
        dp = findViewById(R.id.datePicker);
        tp = findViewById(R.id.timePicker);
        check = findViewById(R.id.checkBox);
        reset = findViewById(R.id.reset);
        submit = findViewById(R.id.submit);
        tableType = findViewById(R.id.table);
        smoke = findViewById(R.id.idSmoke);


        /* Declare Default date & time */
        setDefault();

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDefault();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkFields()) {
                    String output = "Name: " + convertToText(name);
                    output += "\nTel: " +  convertToText(phone);
                    output += "\nGroup Size: " + convertToText(grpSize);
                    if (tableType.getCheckedRadioButtonId() == R.id.idSmoke) {
                        output += "\nTable Type: Smoking Area";
                    } else {
                        output += "\nTable Type: Non-smoking Area";
                    }
                    output += "\nDate: " + dp.getDayOfMonth() + "/" + dp.getMonth() + "/" + dp.getYear();
                    output += "\nTime: " + tp.getCurrentHour() + ":" + tp.getCurrentMinute();


                    Toast.makeText(MainActivity.this, output, Toast.LENGTH_LONG).show();


                } else {
                    Toast toast = Toast.makeText(MainActivity.this, "Please enter input fields", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        tp.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                int maxHour = 20;
                int minHour = 8;
                if (hourOfDay < minHour || hourOfDay > maxHour) {
                    if (hourOfDay - maxHour > minHour - hourOfDay ) {
                        tp.setCurrentHour(minHour);
                    } else {
                        tp.setCurrentHour(maxHour);
                    }
                }
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dp.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar calendar = Calendar.getInstance();
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    int month = calendar.get(Calendar.MONTH);
                    int currentYear = calendar.get(Calendar.YEAR);
                    if (day == dayOfMonth && month == monthOfYear && currentYear == year) {
                        dp.updateDate(year, monthOfYear, dayOfMonth+1);
                    }
                }
            });
        }

    }

    private void setDefault() {
        dp.updateDate(2023, 5, 1);
        tp.setCurrentHour(19);
        tp.setCurrentMinute(30);
        name.getText().clear();
        phone.getText().clear();
        grpSize.getText().clear();
        smoke.setChecked(true);
    }

    private String convertToText(EditText a) {
        return a.getText().toString();
    }

    private boolean checkFields() {
        /* Convert variables to text */
        String nameText = convertToText(name);
        String phoneText = convertToText(phone);
        String grpSizeText = convertToText(grpSize);

        if (nameText.isEmpty() && phoneText.isEmpty() && grpSizeText.isEmpty()) {
            name.setError("Name is required");
            phone.setError("Phone number is required");
            grpSize.setError("Group size is required");
            return false;
        } else if (nameText.isEmpty() && phoneText.isEmpty()) {
            name.setError("Name is required");
            phone.setError("Phone number is required");
            return false;
        } else if (nameText.isEmpty() && grpSizeText.isEmpty()) {
            name.setError("Name is required");
            grpSize.setError("Group Size is required");
            return false;
        } else if (phoneText.isEmpty() && grpSizeText.isEmpty()) {
            phone.setError("Phone number is required");
            grpSize.setError("Group Size is required");
            return false;
        } else if (phoneText.isEmpty()) {
            phone.setError("Phone number is required");
            return false;
        } else if (nameText.isEmpty()) {
            name.setError("Name is required");Toast.makeText(getApplicationContext(), "Name is required", Toast.LENGTH_SHORT).show();
            return false;
        } else if (grpSizeText.isEmpty()) {
            grpSize.setError("Group Size is required");
            return false;
        } else if(!check.isChecked()) {
            check.setError("Please check this field");
            return false;
        }
        return true;
    }
}