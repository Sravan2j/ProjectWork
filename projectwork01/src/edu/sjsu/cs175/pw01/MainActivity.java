package edu.sjsu.cs175.pw01;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Quiz quiz = new Quiz();


        InputStream in;
        try {
            in = getAssets().open("events.xml");
            quiz.readFromXml(in);    
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ArrayAdapter<Question> adapter = new ArrayAdapter<Question>(
                MainActivity.this, R.layout.custom_layout, quiz.getQuestions()) {
            @Override
            public View getView(final int position, View v, ViewGroup parent) {

                LayoutInflater inflater = LayoutInflater.from(getContext());
                Question q = getItem(position);                                
                if (v == null) v = inflater.inflate(R.layout.custom_layout, null);                                
                TextView textView = (TextView) v.findViewById(R.id.textView2);
                textView.setText(q.getText());
                TextView textView1 = (TextView) v.findViewById(R.id.textView1);
                textView1.setText(q.getSTime());
                TextView textView2 = (TextView) v.findViewById(R.id.textView3);
                textView2.setText(q.getETime());
                Button button1 = (Button) v.findViewById(R.id.button1);
                button1.setOnClickListener( new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Calendar cal = Calendar.getInstance(); 
                        Log.i("pw01",position+"");
                        Question ques = quiz.getQuestions().get(position);

                        //if (Build.VERSION.SDK_INT >= 14) {
                        //Assuming the selectedGridDate="11-August-2013";
                        /*                   long startTime = getMilliSeconds(selectedGridDate) + 1000 * 60 * 60;  
                            long endTime = getMilliSeconds(selectedGridDate) + 1000 * 60 * 60 * 2;
                            Intent intent = new Intent(Intent.ACTION_INSERT).setData(Events.CONTENT_URI).
                                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,startTime)
                                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME,endTime)
                                .putExtra(Events.TITLE, "Yoga")
                                .putExtra(Events.DESCRIPTION, "Group class")
                                .putExtra(Events.EVENT_LOCATION, "The gym")
                                .putExtra(Events.AVAILABILITY, Events.AVAILABILITY_BUSY)
                                .putExtra(Intent.EXTRA_EMAIL, "owan@example.com,trevor@example.com");
                             startActivity(intent);*/

                        //} else {
                        //Log.i("pw01","entered");
                        String h=ques.getSTime().substring(11);  
                        
                        String[] h1=h.split(":");  

                        int hour=Integer.parseInt(h1[0]);  
                        int minute=Integer.parseInt(h1[1]);  
                        int second=Integer.parseInt(h1[2]);  
                        
                        
                        int stime = second + (60 * minute) + (3600 * hour);
                        h=ques.getETime().substring(11);  
                        Log.i("substring etime", h);
                        h1=h.split(":");  

                        hour=Integer.parseInt(h1[0]);  
                        minute=Integer.parseInt(h1[1]);  
                        second=Integer.parseInt(h1[2]);                          
                        int etime = second + (60 * minute) + (3600 * hour);
                        //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        Date startDate = null;
                        Date endDate = null;

                        try {
                            //startDate = (Date) formatter.parse(ques.getSTime().substring(0, 10));
                            //endDate = (Date) formatter.parse(ques.getETime().substring(0, 10));
                           startDate = (Date) formatter.parse(ques.getSTime());
                            endDate = (Date) formatter.parse(ques.getETime());

                        } catch (ParseException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }  
                        Log.i("Date", ques.getSTime().substring(0, 10));
                        Intent intent = new Intent(Intent.ACTION_INSERT);
                        intent.setType("vnd.android.cursor.item/event");
                        intent.putExtra("beginTime", startDate.getTime());
                        //intent.putExtra("allDay", false);
                        //intent.putExtra("rrule", "FREQ=DAILY");
                        intent.putExtra("endTime", endDate.getTime());
                        intent.putExtra("title", ques.getText());
                        try{
                            startActivity(intent);
                        }
                        catch(Exception E){
                            Toast toast = Toast.makeText(getApplicationContext(), "Device is not supporting"+E.getMessage(), Toast.LENGTH_LONG);
                            toast.show();
                        }
                        
                        
                     /*   String m_selectedCalendarId = "0";
                        
                        ContentValues l_event = new ContentValues();
                        l_event.put("calendar_id", m_selectedCalendarId);
                        l_event.put("title", ques.getText());
                        l_event.put("description", "This is a simple test for calendar api");
                        l_event.put("eventLocation", "@home");
                        //l_event.put("dtstart", startDate.getTime()+stime);
                        //l_event.put("dtend", endDate.getTime()+etime);
                        l_event.put("dtstart", startDate.getTime());
                        l_event.put("dtend", endDate.getTime());
                        l_event.put("allDay", 0);
                        //status: 0~ tentative; 1~ confirmed; 2~ canceled
                        l_event.put("eventStatus", 1);
                        //0~ default; 1~ confidential; 2~ private; 3~ public
                        
                        //l_event.put("visibility", 1);
                        //0~ opaque, no timing conflict is allowed; 1~ transparency, allow overlap of scheduling
                        //l_event.put("transparency", 0);
                        //0~ false; 1~ true
                        l_event.put("hasAlarm", 1);

                        l_event.put("eventTimezone", TimeZone.getDefault().getID());
                        Uri l_eventUri;
                        if (Build.VERSION.SDK_INT >= 8) {
                            l_eventUri = Uri.parse("content://com.android.calendar/events");
                        } else {
                            l_eventUri = Uri.parse("content://calendar/events");
                        }
                        Uri l_uri = getContentResolver().insert(l_eventUri, l_event);
                        Log.v("++++++test", l_uri.toString());
*/
                        //}                        
                    }
                });
                return v;                                
            }                            
        };

        ListView listView = (ListView) findViewById(R.id.listView1);
        listView.setAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
