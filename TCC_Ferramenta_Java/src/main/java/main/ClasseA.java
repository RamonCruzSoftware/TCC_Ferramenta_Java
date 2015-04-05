package main;

import org.apache.logging.log4j.*;


public class ClasseA {

	static final Logger logg= LogManager.getLogger(ClasseA.class.getName());
	
	public ClasseA()
	{
	
		logg.debug("DEBUG");
		logg.info("INFO");
		logg.error("ERROR");
		logg.fatal("FATAL");
	}
}
