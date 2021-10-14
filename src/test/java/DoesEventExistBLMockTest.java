
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import businessLogic.BLFacadeImplementation;
import dataAccess.DataAccess;
import domain.Event;
import exceptions.EventAlreadyExist;
import exceptions.EventFinished;
import utility.TestUtilityFacadeImplementation;

public class DoesEventExistBLMockTest { 
	
	//Para trabajar sin Mockito
	DataAccess da = new DataAccess(false);
	private  BLFacadeImplementation sut= new BLFacadeImplementation(da);
	TestUtilityFacadeImplementation testBL = new TestUtilityFacadeImplementation();
	
	
	
	@Test
	@DisplayName("Test2: Event Existed")
	public void test2() throws EventAlreadyExist, ParseException {
		String eventText = "Real-Barsa";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date oneDate = sdf.parse("05/10/2021");
		
		//Mockito.doThrow(EventAlreadyExist.class).when(dataAccess).createEvent(s1, d1);
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
			//Mockito.doReturn(e).when(dataAccess).createEvent(s1, d1);
			assertEquals(e, sut.createEvent(eventText, oneDate));
		}catch(Exception e) {
			fail();
		}
	}
}