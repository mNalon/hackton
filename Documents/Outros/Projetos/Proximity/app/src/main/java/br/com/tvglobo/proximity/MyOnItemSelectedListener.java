package br.com.tvglobo.proximity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

/**
 * Created by danielventura on 29/12/15.
 */
public  class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

    public void onItemSelected(AdapterView<?> parent,
                               View view, int pos, long id) {
        Toast.makeText(parent.getContext(), "Item is " +
                parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();
    }

    public void onNothingSelected(AdapterView parent) {
        // Do nothing.
    }
}
