

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
import exceptions.ExpiredDate;

public class CreateEventBLMockTest { 

	DataAccess dataAccess = Mockito.mock(DataAccess.class);
	BLFacade sut = new BLFacadeImplementation(dataAccess);
	
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}	

	@Test
	@DisplayName("Test1: Expired Date to add Event")
	public void test1(){
		Date d1;
		try {
			String s1 = "Real-Barsa";
			d1 = sdf.parse("21/05/2019");
			Mockito.doThrow(ExpiredDate.class).when(dataAccess).createEvent(s1, d1);
			assertThrows(ExpiredDate.class, () -> sut.createEvent(s1, d1));
		} catch (ParseException e) {
			fail("Error with parsing");
		}
	}

	@Test
	@DisplayName("Test2: Event Existed")
	public void test2() throws ExpiredDate, EventAlreadyExist {
		String s1 = "Getafe-Celta";
		try {
			Date d1 = sdf.parse("17/11/2020");
			Mockito.doThrow(EventAlreadyExist.class).when(dataAccess).createEvent(s1, d1);
			assertThrows(EventAlreadyExist.class, () -> sut.createEvent(s1, d1));
		} catch (ParseException e ) {
			e.printStackTrace();
		}
	}

	@Test
	@DisplayName("Test3: Add Event Successfully")
	public void test3() {
		String s1 = "Real-Barsa";
		try {
			Date d1 = sdf.parse("21/12/2020");
			Event e = new Event(s1,d1);
			Mockito.doReturn(e).when(dataAccess).createEvent(s1, d1);
			assertEquals(e, sut.createEvent(s1, d1));
		}catch(Exception e) {
			fail();
		}
	}
}
