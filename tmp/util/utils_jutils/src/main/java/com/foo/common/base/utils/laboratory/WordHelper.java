package com.foo.common.base.utils.laboratory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.w3c.dom.Document;

import com.foo.common.base.utils.FooUtils;

/**
 * 对word2003的处理直接废弃好了，没有意义。
 * 
 * @author think
 *
 */
public class WordHelper {

	private final static org.slf4j.Logger logger = org.slf4j.LoggerFactory
			.getLogger(WordHelper.class);

	public static void main(String[] args) throws Exception {
		// HWPFDocumentCore wordDocument = WordToHtmlUtils
		// .loadDoc(new FileInputStream(
		// "D:\\tmp\\xUtils\\tenmalife-api.doc"));

		File myFile = new File("D:\\tmp\\xUtils\\edu.docx");
		String extension = FilenameUtils.getExtension(myFile.getName());
		if (extension.equals("doc")) {

			HWPFDocument wordDocument = new HWPFDocument(
					new FileInputStream(myFile));

			WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
					DocumentBuilderFactory.newInstance().newDocumentBuilder()
							.newDocument());

			wordToHtmlConverter.setPicturesManager(new PicturesManager() {
				public String savePicture(byte[] content,
						PictureType pictureType, String suggestedName,
						float widthInches, float heightInches) {
					// return "test/" + suggestedName;
					return "" + suggestedName;
				}
			});

			wordToHtmlConverter.processDocument(wordDocument);

			// List<Picture> pics = wordDocument.getPicturesTable().getAllPictures();
			// if (pics != null) {
			// for (int i = 0; i < pics.size(); i++) {
			// Picture pic = (Picture) pics.get(i);
			// // logger.info("file name is:{}", pic.suggestFullFileName());
			// pic.writeImageContent(new FileOutputStream(
			// "D:\\tmp\\xUtils\\" + pic.suggestFullFileName()));
			// }
			// }

			Document htmlDocument = wordToHtmlConverter.getDocument();

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			DOMSource domSource = new DOMSource(htmlDocument);

			StreamResult streamResult = new StreamResult(out);

			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer serializer = tf.newTransformer();
			serializer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			serializer.setOutputProperty(OutputKeys.INDENT, "yes");
			serializer.setOutputProperty(OutputKeys.METHOD, "html");
			serializer.transform(domSource, streamResult);
			out.close();

			String result = new String(out.toByteArray());
			System.out.println(result);

		} else if (extension.equals("docx")) {
			XWPFDocument doc = new XWPFDocument(new FileInputStream(myFile));
			List<XWPFParagraph> paras = doc.getParagraphs();
			String paragraphText;
			String tmpImageName;
			findAllParagraphsLoop: for (XWPFParagraph para : paras) {
				if (para.getRuns() == null || para.getRuns().size() < 1) {
					continue;
				}

				findImgLoop: for (XWPFRun xwpfRun : para.getRuns()) {
					if (xwpfRun.getEmbeddedPictures().size() > 0) {
						tmpImageName = FooUtils.generateUUID() + ".png";
						logger.info("find and write img of name:{}",
								tmpImageName);
						FileUtils.writeByteArrayToFile(
								new File("D:\\tmp\\xUtils\\bak\\"
										+ tmpImageName),
								xwpfRun.getEmbeddedPictures().get(0)
										.getPictureData().getData());

						break findImgLoop;
					}
				}

				paragraphText = para.getParagraphText().trim();
				if (paragraphText.equals("")) {
					continue findAllParagraphsLoop;
				}
				logger.info("para text is:{}", paragraphText);
			}
		} else {

		}
	}

}
