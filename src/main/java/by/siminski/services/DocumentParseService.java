package by.siminski.services;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;

@Service
public class DocumentParseService {

    private static final String DEFAULT_CATALOG_FILE_NAME = "catalog.docx";
    private static final String DIRECTORY_TO_UP_DOWNLOAD = "/download/";

    public void parse() {
        try {
            Path path = new ClassPathResource(DIRECTORY_TO_UP_DOWNLOAD + DEFAULT_CATALOG_FILE_NAME).getFile().toPath();
            FileInputStream fileInputStream = new FileInputStream(path.toString());
            XWPFDocument document = new XWPFDocument(OPCPackage.open(fileInputStream));
            Iterator bodyElementIterator = document.getBodyElementsIterator();
            while (bodyElementIterator.hasNext()) {
                IBodyElement element = (IBodyElement) bodyElementIterator.next();
                if ("TABLE".equalsIgnoreCase(element.getElementType().name())) {
                    XWPFTable table = (XWPFTable) element;
//                    List<XWPFTable> tableList = element.getBody().getTables();
//                    for (XWPFTable table : tableList) {
                        System.out.println("Total Number of Rows of Table:" + table.getNumberOfRows());
                        for (int i = 0; i < table.getRows().size(); i++) {
                            for (int j = 0; j < table.getRow(i).getTableCells().size(); j++) {
                                System.out.println(table.getRow(i).getCell(j).getText());
                            }
                        }
//                    }
                }
            }
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }
    }
}
