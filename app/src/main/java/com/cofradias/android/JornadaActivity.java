package com.cofradias.android;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cofradias.android.model.adapter.JornadaAdapter;
import com.cofradias.android.model.help.Constants;
import com.cofradias.android.model.object.Cofradia;
import com.cofradias.android.model.object.Evento;
import com.cofradias.android.model.object.EventoCabecera;
import com.cofradias.android.model.object.Jornada;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.Calendar;

/**
 * Created by alaria on 17/05/2016.
 */
public class JornadaActivity extends AppCompatActivity implements JornadaAdapter.JornadaClickListener{

    private static final String LOG_TAG = JornadaActivity.class.getSimpleName();
    private ProgressBar spinner;
    private RecyclerView mRecyclerView;
    private JornadaAdapter mJornadaAdapter;
    private Jornada jornada;
    private EventoCabecera selectedEventoCabecera, mEventoCabecera;
    private Calendar beginTime;
    private static final int REQUEST_CALENDAR_ACCESS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evento_activity);

        spinner = (ProgressBar)findViewById(R.id.evento_progress_bar);
        spinner.setVisibility(View.VISIBLE);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_evento);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        mJornadaAdapter = new JornadaAdapter(this);
        mRecyclerView.setAdapter(mJornadaAdapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_evento);
        toolbar.setLogo(R.drawable.logo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Firebase myFirebaseRef = new Firebase(Constants.ConfigFireBase.FIREBASE_URL + Constants.ConfigFireBase.FIREBASE_CHILD_EVENTOS);
        myFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    mJornadaAdapter.addJornada(dataSnapshot.getValue(Jornada.class));
                    mJornadaAdapter.notifyDataSetChanged();
                }

                spinner.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id==android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }

    @Override
    public void onClick(int position) {
        Evento selectedEvento = mJornadaAdapter.getSelectedEvento(position);
        Firebase myFirebaseRef = new Firebase(Constants.ConfigFireBase.FIREBASE_URL + Constants.ConfigFireBase.FIREBASE_CHILD_COFRADIAS);
        Query queryRef = myFirebaseRef.orderByChild("id_cofradia").equalTo(selectedEvento.getId_cofradia());
        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Cofradia selectedCofradia = dataSnapshot.getValue(Cofradia.class);

                    Intent intent = new Intent(JornadaActivity.this, DetailCofradiaActivity.class);
                    intent.putExtra(Constants.REFERENCE.COFRADIA, selectedCofradia);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });
    }

    @Override
    public void onClickCabecera(int position) {

        selectedEventoCabecera = mJornadaAdapter.getSelectedEventoCabecera(position);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.WRITE_CALENDAR)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.READ_CALENDAR)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_CALENDAR,
                            android.Manifest.permission.WRITE_CALENDAR},
                    REQUEST_CALENDAR_ACCESS);

            return;
        } else {
            checkEventCalendar(selectedEventoCabecera);
        }
    }


    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case REQUEST_CALENDAR_ACCESS: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), R.string.permiso_calendar_ok, Toast.LENGTH_SHORT).show();

                    checkEventCalendar(selectedEventoCabecera);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.permiso_calendar_ko, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void checkEventCalendar(EventoCabecera eventoCabecera){

        mEventoCabecera = eventoCabecera;

        String input_date = mEventoCabecera.getFechaCabecera();
        String[] items1 = input_date.split("/");
        final String day = items1[0];
        final String month = items1[1];
        final String year = items1[2];

        Firebase myFirebaseRef = new Firebase(Constants.ConfigFireBase.FIREBASE_URL + Constants.ConfigFireBase.FIREBASE_CHILD_EVENTOS);
        Query queryRef = myFirebaseRef.orderByChild("fecha").equalTo(input_date);
        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    jornada = dataSnapshot.getValue(Jornada.class);

                    for (int i = 0; i< jornada.getEvento().size(); i++) {
                        String input_time = jornada.getEvento().get(i).getHora();
                        String[] items1 = input_time.split(":");
                        String hour = items1[0];
                        String minutes = items1[1];

                        beginTime = Calendar.getInstance();
                        beginTime.set(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day),
                                Integer.parseInt(hour), Integer.parseInt(minutes));

                        Calendar beginTime2 = Calendar.getInstance();
                        beginTime2.set(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day)-1);
                        long begin = beginTime2.getTimeInMillis();
                        beginTime2.set(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day)+1);
                        long end = beginTime2.getTimeInMillis();
                                String[] proj = new String[]{
                                        CalendarContract.Instances.TITLE,
                                        CalendarContract.Instances._ID,
                                        CalendarContract.Instances.EVENT_ID};
                        Cursor cursor = CalendarContract.Instances
                                .query(getContentResolver(), proj, begin, end);
                                //.query(getContentResolver(), proj, begin, end, jornada.getEvento().get(i).getTextoEvento());

                        if (cursor.getCount() > 0) {
                            boolean existe = false;
                            cursor.moveToFirst();
                            for (int x=0; x < cursor.getCount();++x) {
                                String titulo = cursor.getString(cursor.getColumnIndex("TITLE"));

                                if (titulo.equals(jornada.getEvento().get(i).getTextoEvento())){
                                    existe = false;
                                    break;
                                } else{
                                    existe = true;
                                }
                                cursor.moveToNext();
                            }

                            if (existe) {
                                writeEventInCalendar(i);
                            } else {
                                Toast.makeText(getApplicationContext(), R.string.event_calendar_existe, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            writeEventInCalendar(i);
                        }
                        cursor.close();
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });
    }

    private void writeEventInCalendar (int position) {

        ContentValues calendarEvent = new ContentValues();
        calendarEvent.put("calendar_id", 1);
        calendarEvent.put("title", jornada.getEvento().get(position).getTextoEvento());
        calendarEvent.put("description", jornada.getEvento().get(position).getTextoEvento()
                + "\r\n" + getString(R.string.event_calendar_descripcion)
                + "\r\n" + jornada.getEvento().get(position).getOrganizador());
        //calendarEvent.put("eventLocation", "School");
        calendarEvent.put("dtstart", beginTime.getTimeInMillis());
        calendarEvent.put("dtend", beginTime.getTimeInMillis());
        calendarEvent.put("allDay", 0); //0~ false, 1~ true
        calendarEvent.put("eventStatus", 1); // 0~ tentative, 1~ confirmed, 2~ canceled
        calendarEvent.put("eventTimezone", "Europe/Madrid");
        calendarEvent.put("hasAlarm", 1); // 0~ false, 1~ true

        //calendarEvent.put("rrule", "FREQ=YEARLY");
        //calendarEvent.put("visibility", 3); // 0~ default, 1~ confidential, 2~ private, 3~ public
        //calendarEvent.put("transparency", 0); // You can control whether an event consumes time opaque (0) or transparent (1)

        /***************** Event: Meeting(without alert) Adding Attendies to the meeting *******************/
        //calendarEvent.put("attendeeName", "xxxxx"); // Attendees name
        //calendarEvent.put("attendeeEmail", "yyyy@gmail.com");// Attendee Email id
        //calendarEvent.put("attendeeRelationship", 0); // 1~ Relationship_Attendee, 0~ Relationship_None,
        // 2~ Organizer, 3~ Performer, 4~ Speaker
        //calendarEvent.put("attendeeType", 0); // 0~ None, 1~ Optional, 2~ Required, 3~ Resource
        //calendarEvent.put("attendeeStatus", 0); // 0~ None, 1~ Accepted, 2~ Decline, 3~ Invited, 4~ Tentative

        Uri calendarEventUri;
        if (Build.VERSION.SDK_INT >= 8) {
            calendarEventUri = Uri.parse(getString(R.string.event_sdk1_url));
        } else {
            calendarEventUri = Uri.parse(getString(R.string.event_sdk2_url));
        }
        Uri eventUri = JornadaActivity.this.getContentResolver()
                .insert(calendarEventUri, calendarEvent);

        long eventID = 0;
        eventID = Long.parseLong(eventUri.getLastPathSegment());

        /***************** Event: Reminder(with alert) Adding reminder to event *******************/
        ContentValues reminderValues = new ContentValues();
        reminderValues.put("event_id", eventID);
        reminderValues.put("minutes", 60); // Default value of the system. Minutes is a integer
        reminderValues.put("method", 1); // 0~ Default, 1~ Alert, 2~ Email, 3~ SMS

        Uri calendarReminderUri;
        if (Build.VERSION.SDK_INT >= 8) {
            calendarReminderUri = Uri.parse(getString(R.string.reminder_sdk1_url));
        } else {
            calendarReminderUri = Uri.parse(getString(R.string.reminder_sdk2_url));
        }

        Uri reminderUri = JornadaActivity.this.getContentResolver()
                .insert(calendarReminderUri, reminderValues);

//        /***************** Event: Meeting(without alert) Adding Attendies to the meeting *******************/
//        String attendeuesesUriString = "content://com.android.calendar/attendees";
//
//        /******** To add multiple attendees need to insert ContentValues multiple times ***********/
//        ContentValues attendeesValues = new ContentValues();
//        attendeesValues.put("event_id", eventID);
//        attendeesValues.put("attendeeName", "xxxxx"); // Attendees name
//        attendeesValues.put("attendeeEmail", "yyyy@gmail.com"); // Attendee Email id
//        attendeesValues.put("attendeeRelationship", 0); // 1~ Relationship_Attendee, 0~ Relationship_None, 2~ Organizer, 3~ Performer, 4~ Speaker
//        attendeesValues.put("attendeeType", 0); // 0~ None, 1~ Optional, 2~ Required, 3~ Resource
//        attendeesValues.put("attendeeStatus", 0); // 0~ None, 1~ Accepted, 2~ Decline, 3~ Invited, 4~ Tentative
//
//        Uri calendarAttendeesUri;
//        if (Build.VERSION.SDK_INT >= 8) {
//            calendarAttendeesUri = Uri.parse("content://com.android.calendar/attendees");
//        } else {
//            calendarAttendeesUri = Uri.parse("content://calendar/attendees");
//        }
//
//        Uri attendeuesesUri = JornadaActivity.this.getContentResolver()
//              .insert(calendarAttendeesUri, attendeesValues);

        if (eventID > 0) {
            Toast.makeText(getApplicationContext(), R.string.event_calendar_ok, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), R.string.event_calendar_ko, Toast.LENGTH_SHORT).show();
        }
    }
}
