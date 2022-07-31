package edu.sombra.cms.controller;

import edu.sombra.cms.messages.SomethingWentWrongException;
import edu.sombra.cms.service.HomeworkUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/homework")
@RequiredArgsConstructor
public class HomeworkController {

    private final HomeworkUploadService homeworkUploadService;

    @GetMapping(value = "/file/{key}", produces = "multipart/form-data")
    public @ResponseBody byte[] getFileByKey(@PathVariable String key) throws SomethingWentWrongException {
        return homeworkUploadService.getStudentHomework(key);
    }
}
