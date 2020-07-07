package by.siminski.controller;

import by.siminski.utils.MediaTypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Controller
public class DownloadController {

    private static final String DEFAULT_PRICE_LIST_FILE_NAME = "prices.doc";
    private static final String DEFAULT_AGREEMENT_FILE_NAME = "agreement.doc";
    private static final String DIRECTORY_TO_DOWNLOAD = "/download";

    @Autowired
    private ServletContext servletContext;

    @GetMapping("/download/pl")
    public ResponseEntity<InputStreamResource> downloadPriceList(
            @RequestParam(defaultValue = DEFAULT_PRICE_LIST_FILE_NAME) String fileName, Model model) throws IOException {
        try {
            return buildResponseOnDownload(fileName);
        } catch (IOException e) {
            model.addAttribute("Error");
            return null;
        }
    }

    @GetMapping("/download/agreement")
    public ResponseEntity<InputStreamResource> downloadAgreement(
            @RequestParam(defaultValue = DEFAULT_AGREEMENT_FILE_NAME) String fileName,  Model model) throws IOException {
        try {
            return buildResponseOnDownload(fileName);
        } catch (IOException e) {
            model.addAttribute("Error");
            return null;
        }
    }

    private ResponseEntity<InputStreamResource> buildResponseOnDownload(String fileName) throws IOException {
        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, fileName);

        File file = new ClassPathResource(DIRECTORY_TO_DOWNLOAD + "/" + fileName).getFile();
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(mediaType)
                .contentLength(file.length())
                .body(resource);
    }
}
