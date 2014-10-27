package com.example.androidhelloworld;

import java.util.Locale;

import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener
{
	private final static String email_default_subject = "Hello world!";
	private final static String email_default_text = "Hello world!";
	
	private TextToSpeech tts;
	
	private EditText additionBox1;
	private EditText additionBox2;
	private Button additionButton;
	
	private EditText urlEdit;
	private EditText emailEdit;
	private EditText voiceEdit;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Text-To-Speech
		tts = new TextToSpeech(this, null);
		tts.setLanguage(Locale.GERMAN);
		
		//Eingabefeld Zahl1, Zahl2 + Add-Button
		additionBox1 = (EditText)findViewById(R.id.additionEdit1);
		additionBox2 = (EditText)findViewById(R.id.additionEdit2);
		additionButton = (Button)findViewById(R.id.additionButton);
		additionButton.setOnClickListener(this);
		
		urlEdit = (EditText)findViewById(R.id.urlEdit);
		((Button)findViewById(R.id.urlButton)).setOnClickListener(this);
		
		emailEdit = (EditText)findViewById(R.id.emailEdit);
		((Button)findViewById(R.id.emailButton)).setOnClickListener(this);
		
		voiceEdit = (EditText)findViewById(R.id.voiceEdit);
		((Button)findViewById(R.id.voiceButton)).setOnClickListener(this);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings)
			return true;
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onDestroy()
	{
		if (tts != null)
		{
			tts.stop();
			tts.shutdown();
			tts = null;
		}
		super.onDestroy();
	}
	
	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.additionButton:
			additionButton.setText(Long.toString(
							Long.parseLong(additionBox1.getText().toString()) + Long.parseLong(additionBox2.getText().toString())
									));
			break;
		case R.id.urlButton:
			Uri webpage = Uri.parse(urlEdit.getText().toString());
			startActivity(new Intent(Intent.ACTION_VIEW, webpage));
			break;
		case R.id.emailButton:
			Intent intent = new Intent(Intent.ACTION_SEND);
			intent.setType(HTTP.PLAIN_TEXT_TYPE);
			intent.putExtra(Intent.EXTRA_EMAIL, new String[] {emailEdit.getText().toString()});
			intent.putExtra(Intent.EXTRA_SUBJECT, email_default_subject);
			intent.putExtra(Intent.EXTRA_TEXT, email_default_text);
			startActivity(intent);
			break;
		case R.id.voiceButton:
			tts.speak(voiceEdit.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
			break;
		}
	}
}
