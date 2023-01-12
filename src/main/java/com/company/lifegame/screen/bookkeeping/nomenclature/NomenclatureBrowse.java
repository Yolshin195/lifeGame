package com.company.lifegame.screen.bookkeeping.nomenclature;

import io.jmix.ui.screen.*;
import com.company.lifegame.entity.bookkeeping.Nomenclature;

@UiController("lg_Nomenclature.browse")
@UiDescriptor("nomenclature-browse.xml")
@LookupComponent("nomenclaturesTable")
public class NomenclatureBrowse extends StandardLookup<Nomenclature> {
//    @Autowired
//    private UiComponents uiComponents;
//
//    @Install(to = "nomenclaturesTable.picture", subject = "columnGenerator")
//    private Component nomenclaturesTablePictureColumnGenerator(Nomenclature nomenclature) {
//        if (nomenclature.getPicture() != null) {
//            Image image = uiComponents.create(Image.class);
//            image.setScaleMode(Image.ScaleMode.CONTAIN);
//            image.setSource(FileStorageResource.class)
//                    .setFileReference(nomenclature.getPicture());
//            image.setWidth("30px");
//            image.setHeight("30px");
//            return image;
//        } else {
//            return null;
//        }
//    }
    
}