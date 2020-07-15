package by.siminski.services;

import by.siminski.model.catalog.CatalogItem;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;

@Service
public class DocumentParseService {

    @Autowired
    CatalogItemService catalogItemService;

    private static final String DEFAULT_CATALOG_FILE_NAME = "catalog.docx";
    private static final String DIRECTORY_TO_UP_DOWNLOAD = "/download/";

    public void parseCatalog() {
        try {
            Path path = new ClassPathResource(DIRECTORY_TO_UP_DOWNLOAD + DEFAULT_CATALOG_FILE_NAME).getFile().toPath();
            FileInputStream fileInputStream = new FileInputStream(path.toString());
            XWPFDocument document = new XWPFDocument(OPCPackage.open(fileInputStream));
            Iterator bodyElementIterator = document.getBodyElementsIterator();
            while (bodyElementIterator.hasNext()) {
                IBodyElement element = (IBodyElement) bodyElementIterator.next();
                if ("TABLE".equalsIgnoreCase(element.getElementType().name())) {
                    XWPFTable table = (XWPFTable) element;
                    for (int row = 1; row < table.getRows().size(); row++) {
                        CatalogItem catalogItem = new CatalogItem();
                        catalogItem.setName(table.getRow(row).getCell(1).getText());
                        catalogItem.setGroupName(table.getRow(0).getCell(0).getText());
                        catalogItemService.addCatalogItem(catalogItem);
                    }
                }
            }
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }
    }
}
