package exodia.service;

import exodia.domain.models.service.DocumentServiceModel;

import java.util.List;

public interface DocumentService {
    DocumentServiceModel scheduleDocument(DocumentServiceModel documentServiceModel);

    DocumentServiceModel findById(String id);

    List<DocumentServiceModel> findAllDocument();

    boolean printDocument(String id);
}
