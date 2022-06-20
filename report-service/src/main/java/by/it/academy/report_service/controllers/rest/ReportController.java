package by.it.academy.report_service.controllers.rest;

import by.it.academy.report_service.exception.IncorrectInputParametersException;
import by.it.academy.report_service.models.dto.IReport;
import by.it.academy.report_service.models.dto.PageDTO;
import by.it.academy.report_service.models.enums.ReportType;
import by.it.academy.report_service.services.api.IReportService;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/report")
public class ReportController {

    private final IReportService reportService;

    public ReportController(IReportService reportService) {
        this.reportService = reportService;
    }

    @RequestMapping(value = "/{type}", method = RequestMethod.POST)
    public ResponseEntity<IReport> create(@PathVariable ReportType type, @RequestBody Map<String, Object> reportMap) {
        return new ResponseEntity<>(this.reportService.async(reportMap, type), HttpStatus.CREATED);
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<PageDTO<IReport>> getPage(@RequestParam(defaultValue = "0", required = false) int page,
                                                    @RequestParam(defaultValue = "1", required = false) int size) {
        if (size < 1) {
            throw new IncorrectInputParametersException("Page size must be more than one (1)");
        }

        if (page < 0) {
            throw new IncorrectInputParametersException("Page index must be more than zero (0)");
        }
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(this.reportService.getPage(pageable), HttpStatus.OK);
    }

    @RequestMapping(value = "/{uuid}/export", method = RequestMethod.GET)
    public RedirectView download(@PathVariable UUID uuid) {
        return new RedirectView(this.reportService.download(uuid));
    }

    @RequestMapping(value = "/{uuid}/export", method = RequestMethod.HEAD)
    public ResponseEntity<?> check(@PathVariable UUID uuid) {
        MultiValueMap<String, String> headers = new HttpHeaders();
        IReport report = this.reportService.get(uuid);
        headers.add("description", report.getDescription());
        headers.add("status", report.getStatus().toString());
        if (this.reportService.isReady(uuid)) {
            return new ResponseEntity<>(headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        }
    }
}
