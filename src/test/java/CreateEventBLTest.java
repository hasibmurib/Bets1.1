
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import dataAccess.DataAccess;
import domain.Event;
import exceptions.EventAlreadyExist;
import exceptions.EventFinished;
import exceptions.ExpiredDate;
import exceptions.QuestionAlreadyExist;
import utility.TestUtilityFacadeImplementation;

public class CreateEventBLTest { 
	
	
	DataAccess da = new DataAccess(false);
	private  BLFacadeImplementation sut= new BLFacadeImplementation(da);
	TestUtilityFacadeImplementation testBL = new TestUtilityFacadeImplementation();
	
	@Test
	@DisplayName("Test1: Expired Date")
	void createEventBLTest1() throws EventFinished {
		try {
			String eventText = "Real-Barsa";
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate = sdf.parse("05/10/2020");
			
			assertThrows(EventFinished.class, () -> sut.createEvent(eventText, oneDate));
			
			
		} catch (ParseException e) {
		fail("It should be correct: check the date format");
	}
	}
	
	@Test
	@DisplayName("Test2: Event Existed")
	public void test2() throws EventAlreadyExist, ParseException {
		String eventText = "Real-Barsa";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date oneDate = sdf.parse("05/10/2021");
		
		
		assertThrows(EventAlreadyExist.class, () -> sut.createEvent(eventText, oneDate));
	}

	@Test
	@DisplayName("Test3: Event doesn't Exist")
	public void test3() throws ParseException {
		String eventText = "Real-Barsa";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date oneDate = sdf.parse("05/10/2020");
		try {

			Event e = new Event(eventText,oneDate);
			testBL.createEvent(eventText, oneDate);
	
			assertEquals(e, sut.createEvent(eventText, oneDate));
		}catch(Exception e) {
			fail();
		}
	}
}