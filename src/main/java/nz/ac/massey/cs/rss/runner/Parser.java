package nz.ac.massey.cs.rss.runner;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import nz.ac.massey.cs.sdc.parsers.ItemType;
import nz.ac.massey.cs.sdc.parsers.Rss;

public class Parser {



	public static void main(String[] args) throws Exception {


		BasicConfigurator.configure();
		Logger rssLogger = Logger.getLogger("rss");
		Logger channelLogger = Logger.getLogger("channel");
		Logger itemLogger = Logger.getLogger("item");
		rssLogger.setLevel(Level.OFF);
		channelLogger.setLevel(Level.OFF);
		itemLogger.setLevel(Level.OFF);		
		

		JAXBContext jc = JAXBContext.newInstance("nz.ac.massey.cs.sdc.parsers");
		Unmarshaller parser = jc.createUnmarshaller();
//		File file = new File("nzherald.xml");
		
//		& needs to be modified to &amp;
		String str = new String(Files.readAllBytes(Paths.get("nzherald.xml")));
		rssLogger.debug(str);
		String correctedXmlContent  = str.replace("h&o", "h&amp;o");
		rssLogger.debug("\n\n\n\n"+ correctedXmlContent);
		byte[] byteArray = correctedXmlContent.getBytes("UTF-8");
		ByteArrayInputStream correctedXML = new ByteArrayInputStream(byteArray);

		Rss rss = (Rss) parser.unmarshal(correctedXML);
		List<ItemType> articles = rss.getChannel().getItem(); // Assuming 'getArticle()' returns a List<Item>

		rssLogger.debug(rss.toString());
		channelLogger.debug("Title: " + rss.getChannel().getTitle());
		channelLogger.debug("Link: " + rss.getChannel().getLink());
		channelLogger.debug("Description " + rss.getChannel().getDescription());
		channelLogger.debug("Articles: " + rss.getChannel().getItem());
		channelLogger.debug("Total Articles: " + articles.size());

		
		
		for (ItemType article : articles) {
			itemLogger.debug("Title: " + article.getTitle());
			itemLogger.debug("Link: " + article.getLink());
			itemLogger.debug("Creator: " + article.getCreator());
			itemLogger.debug("Description: " + article.getDescription());
		}

		for (ItemType article : articles) {
			System.out.println("title: " + article.getTitle().strip());
			System.out.println("link: " + article.getLink().strip());
			System.out.println("description: " + article.getDescription().strip());
			System.out.println();
		}

	}

}
