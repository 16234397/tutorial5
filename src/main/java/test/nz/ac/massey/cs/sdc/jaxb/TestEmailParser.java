package test.nz.ac.massey.cs.sdc.jaxb;

import static org.junit.Assert.*;
import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import nz.ac.massey.cs.sdc.parsers.Email;
import nz.ac.massey.cs.sdc.parsers.Participant;

import org.junit.Test;

/**
 * Test whether the jaxb-generated parser works correctly.
 * Run this with junit.
 * @author jens dietrich
 */
public class TestEmailParser {

	@Test
	public void test() throws Exception {
		JAXBContext jc = JAXBContext.newInstance("nz.ac.massey.cs.sdc.parsers");
		Unmarshaller parser = jc.createUnmarshaller();
		File file = new File("instance.xml");
		Email mail = (Email) parser.unmarshal(file);

		assertEquals(1,mail.getTo().size());
		Participant to = mail.getTo().get(0);
		assertEquals("staff@massey.ac.nz",to.getEmailAddress());
		assertEquals("John John",to.getDisplayName());

		assertEquals(1,mail.getCc().size());
		Participant cc = mail.getCc().get(0);
		assertEquals("students159251@massey.ac.nz",cc.getEmailAddress());
		assertEquals("Sarah Jonh",cc.getDisplayName());

		assertEquals(0,mail.getBcc().size());

		assertEquals("update",mail.getSubject());
		assertEquals("this lecture notes have been updated",mail.getBody());

	}

}