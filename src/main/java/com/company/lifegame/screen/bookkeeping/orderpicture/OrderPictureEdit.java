package com.company.lifegame.screen.bookkeeping.orderpicture;

import com.company.lifegame.entity.OcrParsedResultsDTO;
import com.company.lifegame.entity.bookkeeping.OrderPicture;
import com.company.lifegame.service.bookkeeping.ReceiptOCRApiService;
import io.jmix.core.FileStorageLocator;
import io.jmix.localfs.LocalFileStorageProperties;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.FileStorageUploadField;
import io.jmix.ui.component.ResizableTextArea;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@UiController("lg_OrderPicture.edit")
@UiDescriptor("order-picture-edit.xml")
@EditedEntityContainer("orderPictureDc")
public class OrderPictureEdit extends StandardEditor<OrderPicture> {
    @Autowired
    private ReceiptOCRApiService receiptOCRApiService;

    @Autowired
    private Button doOcrBtn;

    @Autowired
    private ResizableTextArea<String> descriptionField;

    @Autowired
    private FileStorageUploadField pictureField;
    @Autowired
    private LocalFileStorageProperties localFileStorageProperties;
    @Autowired
    private FileStorageLocator fileStorageLocator;

    @Subscribe("doOcrBtn")
    public void onDoOcrBtnClick(Button.ClickEvent event) {
        if (pictureField.getValue() != null) {
            OcrParsedResultsDTO ocrParsedResultsDTO = receiptOCRApiService.doOCR(pictureField.getValue());

            List<OcrParsedResultsDTO.ParsedResult> parsedResultList = ocrParsedResultsDTO.getParsedResults();

            if (parsedResultList.size() > 0) {
                descriptionField.setValue(parsedResultList.get(0).ParsedText());
            }
        }

        doOcrBtn.setEnabled(true);
    }

}
