import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Event;
import exceptions.EventAlreadyExist;
import utility.TestUtilityDataAccess;

class DoesEventExistDATest {

	static DataAccess sut = new DataAccess(ConfigXML.getInstance().getDataBaseOpenMode().equals("initialize"));;
	static TestUtilityDataAccess testDA = new TestUtilityDataAccess();

	private Event ev;

	@Test
	@DisplayName("Test1:Event does Exist in the database")
	void test1() {

		try {
			// configure the state of the system (create object in the dabatase)
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate = sdf.parse("05/10/2022");
			String eventText = "Event Text";
			

			testDA.open();
			ev = testDA.createEvent(eventText, oneDate);
			testDA.close();

			// invoke System Under Test (sut) and Assert
			assertThrows(EventAlreadyExist.class, () -> sut.createEvent(eventText, oneDate));

		} catch (ParseException e) {
			fail("Event Already Exist");
		}

		// Remove the created objects in the database (cascade removing)
		testDA.open();
		boolean b = testDA.removeEvent(ev);
		System.out.println("Removed event " + b);
		testDA.close();

	}

	@Test
	@DisplayName("Test2:Event doesn't Exist in the database")
	// sut.createQuestion: The event has NOT one question with a queryText.
	void test2() throws EventAlreadyExist {
		try {

			// configure the state of the system (create object in the dabatase)
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate = sdf.parse("05/10/2022");
			String eventText = "Event Text";
			
			
			testDA.open();
			ev = testDA.createEvent(eventText, oneDate);
			testDA.close();

			try {
				// invoke System Under Test (sut)

				// verify the results returned
				assertNotNull(ev);
				assertEquals(eventText, ev.getDescription());
				assertEquals(oneDate, ev.getEventDate());
				
				
			} finally {
				// Remove the created objects in the database (cascade removing)
				testDA.open();
				boolean b = testDA.removeEvent(ev);
				testDA.close();
				System.out.println("Finally " + b);
			}
		} catch (ParseException e) {
			fail("It should be correct: check the date format");
		}

	}

}
