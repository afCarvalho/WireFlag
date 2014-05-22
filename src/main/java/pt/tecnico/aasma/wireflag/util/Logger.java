package pt.tecnico.aasma.wireflag.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.joda.time.DateTime;


public class Logger {
	private BufferedWriter logger;
	
	public Logger(String file, String header) throws IOException {
		this.logger = new BufferedWriter(new FileWriter(new File(file)));
		logger.write(":: LOGGER FOR " + header + " ::\n");
		logger.flush();
	}
	
	public void write(String message) {
		try {
			logger.write("[" + generateData() + "] " + message + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String generateData() {
		DateTime date = new DateTime();
		
		return date.getHourOfDay() + "h" + date.getMinuteOfHour() + "m" + date.getSecondOfMinute() + "s" + date.getMillisOfSecond();
	}
}
