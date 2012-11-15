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
 * 
 * @author @author LukasBuchert <Lukas.Buchert@gmx.de>
 * @author Tobias Roeding <tobias@roeding.eu>
 */
public class PDF {

	private static LinkedList<ModuleInformation> configuration;
	private static UserData usr;
	private static Parameter params;

	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
			Font.BOLD | Font.UNDERLINE);
	private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
			Font.BOLD | Font.UNDERLINE);
	private static Font boldFont = new Font(Font.FontFamily.UNDEFINED, 12,
			Font.BOLD);

	private static int concentration_points = 0;
	private static int c_num = 0;
	private static int reaction_points = 0;
	private static int r_num = 0;
	private static int logic_points = 0;
	private static int l_num = 0;
	private static int math_points = 0;
	private static int m_num = 0;
	private static int total_points = 0;

	public PDF(Parameter params, UserData usr) {
		PDF.usr = usr;
		PDF.params = params;
		configuration = PDF.params.getConfiguration();
	}

	/**
	 * creates PDF at this path
	 * 
	 * @param path
	 *            path where pdf is created
	 */
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

	/**
	 * Fills the meta data
	 * 
	 * @param document
	 *            document metadata is added
	 */
	private static void addMetaData(Document document) {
		document.addTitle("StressYourself Outcome");
		document.addSubject("User: " + usr.getCurrentUserName());
		document.addKeywords("StressYourself,Outcome");
		document.addAuthor("StressYourself");
		document.addCreator("StressYourself");
	}

	/**
	 * adds the content to the pdf document
	 */
	private static void addContent(Document document) throws DocumentException {
		Paragraph preface = new Paragraph();
		addEmptyLine(preface, 1);

		preface.add(new Paragraph("Analysis      Proband: "
				+ usr.getCurrentUserName(), catFont));

		addEmptyLine(preface, 1);

		preface.add(new Paragraph("Testing Parameters", subFont));
		addEmptyLine(preface, 1);
		preface.add(new Paragraph("Difficulty: "
				+ params.getDifficulty().toString()));
		if (params.getAnnoyanceSetting()) {
			preface.add(new Paragraph("Annoyance: ON"));
		} else {
			preface.add(new Paragraph("Annoyance: OFF"));
		}
		addEmptyLine(preface, 1);

		// creates first table
		float[] colsWidth = { 2f, 5f, 1f };
		PdfPTable table = new PdfPTable(colsWidth);
		table.setWidthPercentage(95);
		addModulesToTable(table);
		table.setHorizontalAlignment(Element.ALIGN_LEFT);
		preface.add(table);

		addEmptyLine(preface, 2);

		preface.add(new Paragraph("Analysis", subFont));
		addEmptyLine(preface, 1);

		calculateTotalPoints();

		// creates second table
		PdfPTable table2 = new PdfPTable(2);
		table2.setWidthPercentage(50);

		PdfPCell c1 = new PdfPCell(new Phrase("Area"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table2.addCell(c1);

		c1 = new PdfPCell(new Phrase("Reached Percent"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table2.addCell(c1);

		table2.setHeaderRows(1);

		PdfPCell cc = new PdfPCell(new Phrase("Concentration"));
		cc.setHorizontalAlignment(Element.ALIGN_LEFT);
		table2.addCell(cc);

		cc = new PdfPCell(new Phrase(Integer.toString(concentration_points)));
		cc.setHorizontalAlignment(Element.ALIGN_CENTER);
		table2.addCell(cc);

		cc = new PdfPCell(new Phrase("Reaction"));
		cc.setHorizontalAlignment(Element.ALIGN_LEFT);
		table2.addCell(cc);

		cc = new PdfPCell(new Phrase(Integer.toString(reaction_points)));
		cc.setHorizontalAlignment(Element.ALIGN_CENTER);
		table2.addCell(cc);

		cc = new PdfPCell(new Phrase("Logic"));
		cc.setHorizontalAlignment(Element.ALIGN_LEFT);
		table2.addCell(cc);

		cc = new PdfPCell(new Phrase(Integer.toString(logic_points)));
		cc.setHorizontalAlignment(Element.ALIGN_CENTER);
		table2.addCell(cc);

		cc = new PdfPCell(new Phrase("Math"));
		cc.setHorizontalAlignment(Element.ALIGN_LEFT);
		table2.addCell(cc);

		cc = new PdfPCell(new Phrase(Integer.toString(math_points)));
		cc.setHorizontalAlignment(Element.ALIGN_CENTER);
		table2.addCell(cc);

		cc = new PdfPCell(new Phrase("Total", boldFont));
		cc.setHorizontalAlignment(Element.ALIGN_LEFT);
		table2.addCell(cc);

		cc = new PdfPCell(new Phrase(Integer.toString(total_points), boldFont));
		cc.setHorizontalAlignment(Element.ALIGN_CENTER);
		table2.addCell(cc);

		table2.setHorizontalAlignment(Element.ALIGN_LEFT);
		preface.add(table2);

		document.add(preface);

	}
	/**
	 * creates the first table with area, name and time
	 * @param table location of the table
	 */
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

			PdfPCell ct = new PdfPCell(new Phrase(
					Integer.toString(configuration.get(i).getTime())));
			ct.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(ct);

			addModulePoints(configuration.get(i).getArea(), configuration
					.get(i).getPoints());
		}
	}
	/**
	 * calculates the sum of area count
	 * @param area name of area
	 * @param points points to add
	 */
	private static void addModulePoints(String area, int points) {
		switch (area) {
		case "Concentration":
			concentration_points = (concentration_points * c_num + points)
					/ (c_num + 1);
			c_num++;
			break;
		case "Reaction":
			reaction_points = (reaction_points * r_num + points) / (r_num + 1);
			r_num++;
			break;
		case "Logic":
			logic_points = (logic_points * l_num + points) / (l_num + 1);
			l_num++;
			break;
		case "Math":
			math_points = (math_points * m_num + points) / (m_num + 1);
			m_num++;
			break;
		}
	}
	/**
	 * calculates the total points
	 */
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
	/**
	 * writes a number of empty lines in the document
	 * @param paragraph name of paragraph
	 * @param number of empty lines
	 */
	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

}
