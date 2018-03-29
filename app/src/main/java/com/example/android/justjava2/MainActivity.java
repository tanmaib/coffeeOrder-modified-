package com.example.android.justjava2;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    //Boolean hasWhippedCreme, hasChocolate;
    static final String key1 = "orderTotal", key2 = "toppingsTotal", key3 = "orderSummary", key4 = "Focus";
    int noofcoffee = 0, orderTotal, toppingsTotal = 0, pricecoffee, priceCreamTopping, priceChocolateTopping;
    TextView quantityTextView, orderSummaryTextView;
    CheckBox whippedCreamCheckBox, chocolateCheckBox;
    EditText nameEditTextVIew;
    String orderSummary, Name;
    View focusedChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        whippedCreamCheckBox = (CheckBox) findViewById(R.id.whippedCream);
        //hasWhippedCreme = whippedCreamCheckBox.isChecked();
        chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate);
        //hasChocolate = chocolateCheckBox.isChecked();
        nameEditTextVIew = (EditText) findViewById(R.id.Name_editTextView);
        priceCreamTopping = 1;
        priceChocolateTopping = 2;
        pricecoffee = 5;
        noofcoffee = 2;
    }

    /**
     * This method is right before we change orientation, to preserve values
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);

        outState.putInt(key1, noofcoffee);
        outState.putInt(key2, toppingsTotal);
        outState.putString(key3, orderSummary);
        //outState.putString(key4,Name);
        focusedChild = (ScrollView) findViewById(R.id.rootView);
        focusedChild.requestFocus();
        outState.putInt(key4, focusedChild.getId());

    }

    /**
     * This method is called to display values in the UI which were saved
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);

        noofcoffee = savedInstanceState.getInt(key1);
        displayQuantity(noofcoffee);
        toppingsTotal = savedInstanceState.getInt(key2);
        orderSummary = savedInstanceState.getString(key3);
        displayMessage(orderSummary);
//        Name = savedInstanceState.getString(key4);
//        nameEditTextVIew.setText(Name.toString());
        int focusID = savedInstanceState.getInt(key4, View.NO_ID);
        focusedChild = findViewById(focusID);
        focusedChild.requestFocus();
    }

    /**
     * This method is called when the + button is clicked
     */
    public void increment(View view) {

        if (noofcoffee < 100) {

            noofcoffee = 1 + noofcoffee;
            displayQuantity(noofcoffee);
        } else if (noofcoffee >= 100) {

            //Context context = getApplicationContext();
            CharSequence text = "You cannot have more than 100 coffees.";
            int duration = Toast.LENGTH_SHORT;

            Toast.makeText(this, text, duration).show();
        }
    }

    /**
     * This method is called when the - button is clicked
     */
    public void decrement(View view) {

        /*if(noofcoffee == 1){
            return;
        }*/

        if (noofcoffee > 1) {
            noofcoffee = noofcoffee - 1;
            displayQuantity(noofcoffee);
        } else if (noofcoffee <= 1) {

            //Context context = getApplicationContext();
            CharSequence text = "You cannot have less than 1 coffee.";
            int duration = Toast.LENGTH_SHORT;

            Toast.makeText(this, text, duration).show();
        }
    }

    /**
     * This method creats a map intent
     */
    /*public void showMap(Uri geoLocation) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }*/

    /**
     * This method creats a Email intent
     */
    public void composeEmail(String subject, String body) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        //intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        //intent.putExtra(Intent.EXTRA_STREAM, attachment);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method is called when the order button is clicked
     */
    public void submitOrder(View view) {

        String orderSummary = createOrderSummary();
        displayMessage(orderSummary);

        // String Subject = getResources().getString(R.string.order_summary_email_subject, Name);
        //composeEmail(Subject, orderSummary);
        //showMap(Uri.parse("geo:47.6,-122.3"));
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
                    toppingsTotal += priceCreamTopping;
                else
                    // No whipped cream
                    toppingsTotal -= priceCreamTopping;
                break;
            case R.id.chocolate:
                if (checked)
                    // Put some chocolate on the coffee
                    toppingsTotal += priceChocolateTopping;
                else
                    // No chocolate
                    toppingsTotal -= priceChocolateTopping;
                break;
        }
    }

    /**
     * Calculates the price of the order based on the current quantity.
     *
     * @return the Total price
     */
    private int calculatePrice() {
       /*private int calculatePrice(boolean addWhippedCreme, boolean addChocolate) {

        if(addWhippedCreme){
           pricecoffee += priceCreamTopping;
        }else if (addChocolate){
            pricecoffee += priceChocolateTopping;
        }else{
            //Do Nothing
        }
        return pricecoffee*noofcoffee;
        }*/
        orderTotal = noofcoffee * pricecoffee;
        orderTotal += (toppingsTotal * noofcoffee);
        return (orderTotal);
    }

    /**
     * This method generates the summary of the order
     */
    private String createOrderSummary() {

        //hasWhippedCreme = whippedCreamCheckBox.isChecked();
        //hasChocolate = chocolateCheckBox.isChecked();
        Boolean checkBox1 = whippedCreamCheckBox.isChecked();
        Boolean checkBox2 = chocolateCheckBox.isChecked();
        Name = nameEditTextVIew.getText().toString();
        Resources res = getResources();
        orderSummary = res.getString(R.string.Output, Name, checkBox1, checkBox2, noofcoffee, calculatePrice());
        return orderSummary;
        //calculatePrice(hasWhippedCreme,hasChocolate);
    }

}
