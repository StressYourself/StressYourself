package de.dhbw.stress_yourself.outcome;

import java.io.FileOutputStream;
import java.util.LinkedList;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import de.dhbw.stress_yourself.params.*;

/**
 * creates the pdf outcome
 * @author  @author LukasBuchert <Lukas.Buchert@gmx.de>
 * @author Tobias Roeding <tobias@roeding.eu>
 */
public class PDF {

	private static LinkedList<ModuleInformation> configuration;
	private static UserData usr;
	private static Parameter params;

	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
			Font.BOLD);
	private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
			Font.BOLD);

	private static int concentration_points = 0;
	private static int reaction_points = 0;
	private static int logic_points = 0;
	private static int math_points = 0;
	private static int total_points = 0;

	public PDF(Parameter params, UserData usr) {
		PDF.usr = usr;
		PDF.params = params;
		configuration = PDF.params.getConfiguration();
	}

	public boolean createPDF(String path) {
		try {
			Document document = new Document();
			PdfWriter.getInstance(document,
					new FileOutputStream(path + usr.getCurrentUserName()
							+ "_analysis.pdf"));
			document.open();
			addMetaData(document);
			addContent(document);
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}

	
	private static void addMetaData(Document document) {
		document.addTitle("StressYourself Outcome");
		document.addSubject("User: " + usr.getCurrentUserName());
		document.addKeywords("StressYourself,Outcome");
		document.addAuthor("StressYourself");
		document.addCreator("StressYourself");
	}

	private static void addContent(Document document) throws DocumentException {
		Paragraph preface = new Paragraph();
		addEmptyLine(preface, 1);
		preface.add(new Paragraph("Analysis      Proband: "
				+ usr.getCurrentUserName(), catFont));

		addEmptyLine(preface, 1);

		preface.add(new Paragraph("Testparameter", subFont));
		addEmptyLine(preface, 1);
		preface.add(new Paragraph("Difficulty: "
				+ params.getDifficulty().toString()));
		addEmptyLine(preface, 1);
		PdfPTable table = new PdfPTable(3);
		addModulesToTable(table);
		table.setHorizontalAlignment(Element.ALIGN_LEFT);
		preface.add(table);

		addEmptyLine(preface, 2);

		preface.add(new Paragraph("Analysis", subFont));
		addEmptyLine(preface, 1);

		calculateTotalPoints();

		PdfPTable table2 = new PdfPTable(2);

		PdfPCell c12 = new PdfPCell(new Phrase("Area"));
		c12.setHorizontalAlignment(Element.ALIGN_CENTER);
		table2.addCell(c12);

		c12 = new PdfPCell(new Phrase("Reached Percent"));
		c12.setHorizontalAlignment(Element.ALIGN_CENTER);
		table2.addCell(c12);

		table2.setHeaderRows(1);

		table2.addCell("Concentration");
		table2.addCell(Integer.toString(concentration_points));
		table2.addCell("Reaction");
		table2.addCell(Integer.toString(reaction_points));
		table2.addCell("Logic");
		table2.addCell(Integer.toString(logic_points));
		table2.addCell("Math");
		table2.addCell(Integer.toString(math_points));
		table2.addCell("Total");
		table2.addCell(Integer.toString(total_points));

		table2.setHorizontalAlignment(Element.ALIGN_LEFT);
		preface.add(table2);

		document.add(preface);

	}

	private static void addModulesToTable(PdfPTable table) {

		PdfPCell c1 = new PdfPCell(new Phrase("Area"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Modulename"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Time"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		table.setHeaderRows(1);

		for (int i = 0; i < configuration.size(); i++) {
			table.addCell(configuration.get(i).getArea());
			table.addCell(configuration.get(i).getName());
			table.addCell(Integer.toString(configuration.get(i).getTime()));
			addModulePoints(configuration.get(i).getArea(), configuration
					.get(i).getPoints());
		}
	}

	private static void addModulePoints(String area, int points) {
		switch (area) {
		case "Concentration":
			concentration_points = concentration_points + points;
			break;
		case "Reaction":
			reaction_points = reaction_points + points;
			break;
		case "Logic":
			logic_points = logic_points + points;
			break;
		case "Math":
			math_points = math_points + points;
			break;
		}
	}

	private static void calculateTotalPoints() {
		int factor = 4;
		if (concentration_points == 0) {
			factor--;
		}
		if (reaction_points == 0) {
			factor--;
		}
		if (logic_points == 0) {
			factor--;
		}
		if (math_points == 0) {
			factor--;
		}
		if (factor > 0) {
			total_points = (concentration_points + reaction_points
					+ logic_points + math_points)
					/ factor;
		}
	}

	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

}
