package com.company.lifegame.service.bookkeeping;

import com.company.lifegame.entity.OcrParsedResultsDTO;
import io.jmix.core.FileRef;
import io.jmix.core.FileStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;

@Component("lg_ReceiptOCRApiService")
public class ReceiptOCRApiService {

    @Value("${receipt.ocr.api.key}")
    private String apiKey;

    @Autowired
    private FileStorage fileStorage;

    private final RestTemplate restTemplate;

    public ReceiptOCRApiService() {
        this.restTemplate = new RestTemplate();
    }

    public OcrParsedResultsDTO doOCR(FileRef fileRef) {


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("apikey", apiKey);

        MultiValueMap<String, Object> body
                = new LinkedMultiValueMap<>();
        body.add("language", "vie");
        body.add("isTable", true);
        body.add("OCREngine", 3);
        body.add("scale", true);
        body.add("isOverlayRequired", false);
        body.add("iscreatesearchablepdf", false);
        body.add("file", new MultipartInputStreamFileResource(fileStorage.openStream(fileRef), fileRef.getFileName()));

        HttpEntity<MultiValueMap<String, Object>> requestEntity
                = new HttpEntity<>(body, headers);

        ResponseEntity<OcrParsedResultsDTO> responseEntity = restTemplate.postForEntity("https://api.ocr.space/parse/image", requestEntity, OcrParsedResultsDTO.class);
        return responseEntity.getBody();
    }

    private static class MultipartInputStreamFileResource extends InputStreamResource {

        private final String filename;

        MultipartInputStreamFileResource(InputStream inputStream, String filename) {
            super(inputStream);
            this.filename = filename;
        }

        @Override
        public String getFilename() {
            return this.filename;
        }

        @Override
        public long contentLength() throws IOException {
            return -1; // we do not want to generally read the whole stream into memory ...
        }
    }

}

