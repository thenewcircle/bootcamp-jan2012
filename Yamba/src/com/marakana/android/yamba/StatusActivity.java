package com.marakana.android.yamba;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class StatusActivity extends Activity implements OnClickListener {
	Button buttonUpdate;
	EditText editStatus;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status);
        
        // Find views
        buttonUpdate = (Button) findViewById(R.id.button_update);
        editStatus = (EditText)findViewById(R.id.edit_status);
        
        // Add button listener
        buttonUpdate.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		Log.d("Yamba", "Status update button clicked");
	}
}