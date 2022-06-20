package by.it.academy.report_service.services.api;

import by.it.academy.report_service.models.dto.IReport;

public interface IDocumentService<T> {

    /**
     * Creating document by report
     * @param report report dto
     * @return document
     */
    T create(IReport report);

    /**
     * Creating document's binary version by report
     * @param report report dto
     * @return array with bytes
     */
    byte[] createBin(IReport report);


}
