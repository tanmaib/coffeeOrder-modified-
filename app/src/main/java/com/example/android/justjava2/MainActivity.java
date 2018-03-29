package com.example.android.justjava2;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    int noofcoffee = 0, orderTotal, toppingsTotal = 0, pricecoffee, priceTopping;
    TextView quantityTextView, orderSummaryTextView;
    CheckBox whippedCreamCheckBox, chocolateCheckBox;
    EditText nameEditTextVIew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        whippedCreamCheckBox = (CheckBox) findViewById(R.id.whippedCream);
        chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate);
        nameEditTextVIew = (EditText) findViewById(R.id.Name_editTextView);
        priceTopping = 1;
        pricecoffee = 5;
    }

    /**
     * This method is called when the + button is clicked
     */
    public void increment(View view) {

        noofcoffee = 1 + noofcoffee;
        displayQuantity(noofcoffee);
    }

    /**
     * This method is called when the - button is clicked
     */
    public void decrement(View view) {

        noofcoffee = noofcoffee - 1;
        displayQuantity(noofcoffee);
    }

    /**
     * This method is called when the order button is clicked
     */
    public void submitOrder(View view) {

        String orderSummary = createOrderSummary();
        displayMessage(orderSummary);
    }

    /**
     * This method displays the given quantity value on the screen
     */
    private void displayQuantity(int number) {

        //TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen
     */
    private void displayMessage(String message) {

        //TextView priceTextViewS = (TextView) findViewById(R.id.price_text_view);
        orderSummaryTextView.setText(message);
    }

    /**
     * This method is called when the check box is clicked
     */
    public void onCheckboxClicked(View view) {

        Boolean checked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.whippedCream:
                if (checked)
                    // Put some whipped cream on the coffee
                    toppingsTotal += (priceTopping * noofcoffee);
                else
                    // No whipped cream
                     toppingsTotal -= (priceTopping * noofcoffee);
                break;
            case R.id.chocolate:
                if (checked)
                    // Put some chocolate on the coffee
                    toppingsTotal += (priceTopping * noofcoffee);
                else
                    // No chocolate
                    toppingsTotal -= (priceTopping * noofcoffee);
                break;
        }
    }

    /**
     * Calculates the price of the order based on the current quantity.
     *
     * @return the Total price
     */
    private int calculatePrice() {

        orderTotal = noofcoffee * pricecoffee;
        orderTotal += toppingsTotal;
        return (orderTotal);
    }

    /**
     * This method generates the summary of the order
     */
    private String createOrderSummary() {

        Boolean checkBox1 = whippedCreamCheckBox.isChecked();
        Boolean checkBox2 = chocolateCheckBox.isChecked();
        String Name = nameEditTextVIew.getText().toString();
        Resources res = getResources();
        return res.getString(R.string.Output, Name, checkBox1, checkBox2, noofcoffee, calculatePrice());
    }

}
