

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dataAccess.DataAccess;
import domain.Event;
import exceptions.EventAlreadyExist;
import exceptions.EventFinished;
import exceptions.ExpiredDate;
import utility.TestUtilityFacadeImplementation;

public class CreateEventDATest {
	
	private DataAccess sut=  new DataAccess(false);
	private TestUtilityFacadeImplementation testBL = new TestUtilityFacadeImplementation();
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	private String s1="Real Sociedad-Real Madrid";
	private String s2="Real-Barsa";
	private Date d1;
	
	@BeforeEach
	public void setUp() {
		sut.open(false);
	}
	
	@AfterEach
	public void closes() {
		sut.close();
	}
	
	@Test
	@DisplayName("Test1: Expired Date for the event")
	public void test1() {
		try {
			d1 = sdf.parse("21/05/2019");
			assertThrows(EventFinished.class, () -> sut.createEvent(s1, d1));
		} catch (ParseException e) {
			fail("Error with parsing");
		}
	}
	
	@Test
	@DisplayName("Test2: No hay eventos en la fecha de eventDate")
	public void test2() {
		Event ev=null;
		try {
			d1=sdf.parse("21/12/2020");
			ev= sut.createEvent(s2, d1);
			assertEquals(ev.getDescription(), s2);
			assertEquals(ev.getEventDate(), d1);
		} catch (ParseException e) {
			fail();
		}catch (Exception e2){
			e2.printStackTrace();
		}finally {
			testBL.removeEvent(ev);
		}
		
	}
	@Test
	@DisplayName("Test3: Event Already Exists")
	public void test3(){
		try {
			d1=sdf.parse("17/11/2020");
			assertThrows(EventAlreadyExist.class, () -> sut.createEvent(s1, d1));
		}catch (ParseException e) {
			fail("Error with parsing");
		}
		
	}
	
	@Test
	@DisplayName("Test4: No Events given in the specified date")
	public void test4() {
		Event ev=null;
		try {
			d1=sdf.parse("21/12/2021");
			ev= sut.createEvent(s1, d1);
			assertEquals(ev.getDescription(), s1);
			assertEquals(ev.getEventDate(), d1);
		} catch (ParseException e) {
			fail();
		}catch (Exception e2){
			e2.printStackTrace();
		}finally {
			testBL.removeEvent(ev);
		}
		
	}
}
