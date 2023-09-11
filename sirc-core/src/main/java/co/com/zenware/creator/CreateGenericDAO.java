package co.com.zenware.creator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CreateGenericDAO {

	private static String ENTITIES_PATH = "./src/main/java/co/gov/movilidadbogota/core/entity/";
	private static String DAO_PATH = "./src/main/java/co/gov/movilidadbogota/core/dao/";

	public static void createDAO() {
		try {
			Files.walk(Paths.get(ENTITIES_PATH)).forEach(filePath -> {
				if (Files.isRegularFile(filePath)) {
					try (BufferedReader br = new BufferedReader(new FileReader(filePath.toFile()))) {

						String sCurrentLine;

						while ((sCurrentLine = br.readLine()) != null) {
							// System.out.println(sCurrentLine);
							if (sCurrentLine.contains("public class")) {
								String[] lines = sCurrentLine.split(" ");
								String nameClass = lines[2];
								String nameDao = nameClass.replace("Entity", "");
								String separator = System.getProperty("line.separator");

								StringBuffer dao = new StringBuffer();
								dao.append("package co.gov.movilidadbogota.core.dao;");
								dao.append(separator);
								dao.append(separator);
								dao.append("import org.springframework.stereotype.Repository;");
								dao.append(separator);
								dao.append("import co.gov.movilidadbogota.core.entity." + nameClass + ";");
								dao.append(separator);
								dao.append(separator);
								dao.append("@Repository");
								dao.append(separator);
								dao.append("public class " + nameDao + "DAO extends AbstractDAO<" + nameClass + "> {");
								dao.append(separator);
								dao.append(separator);
								dao.append("	public " + nameDao + "DAO() {");
								dao.append(separator);
								dao.append("		super(" + nameClass + ".class);");
								dao.append(separator);
								dao.append("	}");
								dao.append(separator);
								dao.append("}");

								// System.out.println(dao);

								FileWriter fichero = null;
								PrintWriter pw = null;
								try {
									fichero = new FileWriter(DAO_PATH + nameDao + "DAO.java");
									pw = new PrintWriter(fichero);
									pw.println(dao);
								} catch (Exception e) {
									e.printStackTrace();
								} finally {
									try {
										if (null != fichero)
											fichero.close();
									} catch (Exception e2) {
										e2.printStackTrace();
									}
								}

							}
						}

					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		createDAO();
	}

}
