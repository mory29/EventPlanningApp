package com.example.eventapp2;


import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.test.core.app.ApplicationProvider;
import com.google.type.Date;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

public class MainActivityTest {

    @Mock
    private ListView mockListView;
    @Mock
    private Button mockButton;
    @Mock
    private ArrayAdapter<Event> mockAdapter;

    private MainActivity activity;
    private ArrayList<Event> eventList;
    private Object MockitoAnnotations;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        Context context = ApplicationProvider.getApplicationContext();
        activity = new MainActivity();
        eventList = new ArrayList<>();

        // Assume these views are available in the actual MainActivity.
        activity.listView = mockListView;
        activity.btnAddEvent = mockButton;
        activity.adapter = mockAdapter;
        activity.eventsList = eventList;
    }


    @Test
    public void addEvent_ShouldAddEventToList() {
        // Arrange
        Event newEvent = new Event("Test Event",
                "This is a test event",
                new Date());
        int initialSize = eventList.size();

        // Act
        eventList.add(newEvent);

        // Assert
        assertEquals(initialSize + 1, eventList.size());
        assertTrue(eventList.contains(newEvent));
    }

    @Test
    public void deleteEvent_ShouldRemoveEventFromList() {
        // Arrange
        Event eventToRemove = new Event("Event to Remove", "This event will be removed", new Date());
        eventList.add(eventToRemove);
        int indexToRemove = eventList.indexOf(eventToRemove);

        // Act
        eventList.remove(indexToRemove);

        // Assert
        assertFalse(eventList.contains(eventToRemove));
    }


}
