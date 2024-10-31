package com.example.eventapp2;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ArrayList<Event> eventsList = new ArrayList<>();
    ArrayAdapter<Event> adapter;
    ListView listView;
    Button btnAddEvent;
    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.events_list);
        btnAddEvent = findViewById(R.id.add_event);

        // Initialize adapter and set it to the ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, eventsList);
        listView.setAdapter(adapter);

        btnAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddEventDialog();

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event selectedEvent = eventsList.get(position);
                showEditEventDialog(selectedEvent, position); // Call the method with the event and its position
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete Event")
                        .setMessage("Are you sure you want to delete this event?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // User confirmed to delete the event
                                deleteEvent(position);
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return true; // Indicate that the callback consumed the long click
            }
        });
    }

    public void showAddEventDialog() {
        // Create a Builder for the alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add New Event");

        // Set up the input fields (title, description, date)
        final EditText inputTitle = new EditText(this);
        inputTitle.setHint("Title");
        final EditText inputDescription = new EditText(this);
        inputDescription.setHint("Description");
        final EditText inputDate = new EditText(this);
        inputDate.setHint("Date (e.g., 01/01/2023)");

        // Lay out the input fields vertically
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(inputTitle);
        layout.addView(inputDescription);
        layout.addView(inputDate);

        builder.setView(layout);

        // Set up the buttons
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String title = inputTitle.getText().toString();
                String description = inputDescription.getText().toString();
                String dateString = inputDate.getText().toString();
                Date date = null;

                // Parse the date string
                try {
                    date = dateFormatter.parse(dateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                    // Show error message to the user or handle date parse error appropriately
                    Toast.makeText(MainActivity.this, "Invalid date format. Please use dd/MM/yyyy", Toast.LENGTH_LONG).show();
                    return; // Exit the handler early
                }

                // Create a new event with the given details
                Event newEvent = new Event(title, description, date);
                eventsList.add(newEvent);
                adapter.notifyDataSetChanged(); // Refresh ListView

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Show the AlertDialog
        builder.show();
    }


    public void showEditEventDialog(final Event eventToEdit, final int eventIndex) {
        // Code for showing "Edit Event" dialog

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Event");

        // Set up the input fields (title, description, date) with existing event data
        final EditText inputTitle = new EditText(this);
        inputTitle.setText(eventToEdit.getTitle());
        final EditText inputDescription = new EditText(this);
        inputDescription.setText(eventToEdit.getDescription());
        final EditText inputDate = new EditText(this);
        inputDate.setText(eventToEdit.getDate());

        // Lay out the input fields vertically
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(inputTitle);
        layout.addView(inputDescription);
        layout.addView(inputDate);

        builder.setView(layout);

        // Set up the buttons
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Update event details with user input
                eventToEdit.setTitle(inputTitle.getText().toString());
                eventToEdit.setDescription(inputDescription.getText().toString());
                eventToEdit.setDate(inputDate.getText().toString());
                eventsList.set(eventIndex, eventToEdit); // Replace event at the given index
                adapter.notifyDataSetChanged(); // Notify ListAdapter about the changes
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Show the AlertDialog
        builder.show();

    }

    public void deleteEvent(int position) {
        eventsList.remove(position);
        adapter.notifyDataSetChanged();
    }

}
