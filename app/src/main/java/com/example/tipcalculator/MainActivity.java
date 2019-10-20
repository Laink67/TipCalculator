package com.example.tipcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    // Форматировщики денежных сумм и процентов
    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat = NumberFormat.getPercentInstance();

    private double billAmount = 0.0; // Сумма счёта, введённая пользователем
    private double percent = 0.15; // Исходный процент чаевых
    private TextView amountTextView; // Для отформатированной суммы счета
    private TextView percentTextView; // Для вывода процента чаевых
    private TextView tipTextView; // Для вывода вычисленных чаевых
    private TextView totalTextView; // Для вычисленной общей суммы
    private EditText amountEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        amountTextView = findViewById(R.id.amountTextView);
        percentTextView = findViewById(R.id.percentTextView);
        tipTextView = findViewById(R.id.tipTextView);
        totalTextView = findViewById(R.id.totalTextView);

        tipTextView.setText(currencyFormat.format(0)); // Заполнить 0
        totalTextView.setText(currencyFormat.format(0)); // Заполнить 0

        // Назначение слушателя TextWatcher для amountEditText
        amountEditText = findViewById(R.id.editText);
        amountEditText.addTextChangedListener(amountEditTextWatcher);

        // Назначение слушателя OnSeekBarChangeListener для percentSeekBar
        SeekBar percentSeekBar = findViewById(R.id.percentSeekBar);
        percentSeekBar.setOnSeekBarChangeListener(seekBarListener);
    }

    private void calculate() {
        // Форматирование процентов и вывод в percentTextView
        percentTextView.setText(percentFormat.format(percent));

        // Вычисление чаевых и общей суммы
        double tip = billAmount * percent;
        double total = billAmount + tip;

        // Вывод чаевых и общей суммы
        tipTextView.setText(currencyFormat.format(tip));
        totalTextView.setText(currencyFormat.format(total));
    }

    // Для изменения состояния SeekBar
    private final SeekBar.OnSeekBarChangeListener seekBarListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            percent = progress / 100.0; // Назначение чаевых в соотвествии с SeekBar
            calculate(); // Вычисление чаевых и общей суммы
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    // Объект слушателя для событий изменения текста в EditText
    private final TextWatcher amountEditTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Получить счёт и вывести в формате денежной суммы
            try {
                billAmount = Double.parseDouble(s.toString()) / 100.0;
                amountTextView.setText(currencyFormat.format(billAmount));
            } catch (NumberFormatException exp) {
                amountTextView.setText("");
                billAmount = 0.0;
            }

            calculate();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

}
