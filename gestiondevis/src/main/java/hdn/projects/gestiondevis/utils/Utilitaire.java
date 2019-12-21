/**
 * 
 */
package hdn.projects.gestiondevis.utils;

import java.util.StringJoiner;

import org.slf4j.Logger;

/**
 * @author Gandalf
 *
 */
public class Utilitaire {
	
	/**
	 * @param logger
	 * @param nomServiceName
	 * @param methodName
	 * @param object
	 */
	public static void loguer(Logger logger, String nomServiceName, String methodName, Object object) {
		StringJoiner sj = new StringJoiner(" / ", "\n*****************************************\n", "\n*****************************************\n");
		sj.add(nomServiceName)
		.add(methodName)
		.add(object != null ? object.toString() : "");
		logger.debug(sj.toString());
	}

}
