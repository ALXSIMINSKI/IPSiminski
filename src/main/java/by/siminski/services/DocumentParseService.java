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
                    System.out.println(table.getRow(0).getCell(0).getText());
                    for (int row = 1; row < table.getRows().size(); row++) {
                        for (int cell = 0; cell < table.getRow(row).getTableCells().size(); cell++) {
                            System.out.println(table.getRow(row).getCell(cell).getText());
                        }
                    }
                }
            }
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }
    }
}
