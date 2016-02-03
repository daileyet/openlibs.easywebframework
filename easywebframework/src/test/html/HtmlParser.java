package html;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class HtmlParser {

	private static final String TARGET_TABLE_TAG = "apex_ir_html_export";
	private final String htmlContent = "<html><head>Test</head><body><P>Test.</P></body></html>";

	public void parse(List<String[]> container, String[] heads)
			throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder builder = null;
		builderFactory.setIgnoringComments(true);
		builderFactory.setIgnoringElementContentWhitespace(true);
		builderFactory.setValidating(false);
		builder = builderFactory.newDocumentBuilder();
		builder.setErrorHandler(new ErrorHandler() {

			@Override
			public void warning(SAXParseException exception)
					throws SAXException {
				System.out.println("Warning:" + exception);
			}

			@Override
			public void fatalError(SAXParseException exception)
					throws SAXException {
				System.out.println("Fatal Error:" + exception);
			}

			@Override
			public void error(SAXParseException exception) throws SAXException {
				System.out.println("Error:" + exception);
			}
		});
		InputSource source = new InputSource(new StringReader(htmlContent));
		source = new InputSource(new FileInputStream(new File("R:\\test.html")));
		Document document = builder.parse(source);

		Element targetTable = getTargetDataTable(document);
		//
		System.out.println(targetTable.getElementsByTagName("tbody")
				.getLength());
		// Element targetTbody = (Element) targetTable.getElementsByTagName(
		// "tbody").item(0);
		// //
		// NodeList trs = targetTbody.getElementsByTagName("tr");
		// for (int index = 0; index < trs.getLength(); index++) {
		// Element tr = (Element) trs.item(index);
		// NodeList tds = tr.getElementsByTagName("td");
		// for (int c = 0; c < tds.getLength(); c++) {
		// Element td = (Element) tds.item(c);
		// System.out.println(td.getNodeValue());
		// }
		// }
	}

	private Element getTargetDataTable(Document document) {
		NodeList nodes = document.getElementsByTagName("table");
		for (int index = 0; index < nodes.getLength(); index++) {
			Element table = (Element) nodes.item(index);
			if (TARGET_TABLE_TAG.equalsIgnoreCase(table.getAttribute("class"))) {
				return table;
			}
		}
		return null;
	}

	public static void main(String[] args) throws SAXException, IOException,
			ParserConfigurationException {
		HtmlParser instance = new HtmlParser();
		List<String[]> container = new ArrayList<String[]>();
		String[] heads = {};
		instance.parse(container, heads);
	}
}
