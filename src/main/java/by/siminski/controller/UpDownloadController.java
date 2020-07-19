package by.siminski.controller;

import by.siminski.utils.MediaTypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Controller
public class UpDownloadController {

    private static final String DEFAULT_PRICE_LIST_FILE_NAME = "prices.doc";
    private static final String DEFAULT_AGREEMENT_FILE_NAME = "agreement.doc";
    private static final String DIRECTORY_TO_UP_DOWNLOAD = "/download/";

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private MessageSource messageSource;

    @PostMapping("/upload/prices")
    public String uploadPrices(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("upload_prices_status", uploadFile(file, DEFAULT_PRICE_LIST_FILE_NAME));
        return "redirect:/settings";
    }

    @PostMapping("/upload/agreement")
    public String uploadAgreement(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("upload_agreement_status", uploadFile(file, DEFAULT_AGREEMENT_FILE_NAME));
        return "redirect:/settings";
    }

    @GetMapping("/download/pl")
    public ResponseEntity<InputStreamResource> downloadPriceList(@RequestParam(defaultValue = DEFAULT_PRICE_LIST_FILE_NAME) String fileName) {
        try {
            return buildResponseOnDownload(fileName);
        } catch (IOException e) {
            return null;
        }
    }

    @GetMapping("/download/agreement")
    public ResponseEntity<InputStreamResource> downloadAgreement(@RequestParam(defaultValue = DEFAULT_AGREEMENT_FILE_NAME) String fileName) {
        try {
            return buildResponseOnDownload(fileName);
        } catch (IOException e) {
            return null;
        }
    }

    private String uploadFile(MultipartFile file, String fileName) {
        try {
            Path path = new ClassPathResource(DIRECTORY_TO_UP_DOWNLOAD + fileName).getFile().toPath();
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            return messageSource.getMessage("Status.ok", null, LocaleContextHolder.getLocale());
        } catch (IOException e) {
            return messageSource.getMessage("Status.failed", null, LocaleContextHolder.getLocale());
        }
    }

    private ResponseEntity<InputStreamResource> buildResponseOnDownload(String fileName) throws IOException {
        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, fileName);

        File file = new ClassPathResource(DIRECTORY_TO_UP_DOWNLOAD + fileName).getFile();
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(mediaType)
                .contentLength(file.length())
                .body(resource);
    }
}
