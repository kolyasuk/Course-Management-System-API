package edu.sombra.cms.controller;

import edu.sombra.cms.domain.dto.CourseOverviewDTO;
import edu.sombra.cms.domain.dto.InstructorDTO;
import edu.sombra.cms.domain.payload.InstructorData;
import edu.sombra.cms.messages.SomethingWentWrongException;
import edu.sombra.cms.service.InstructorService;
import edu.sombra.cms.service.impl.UserAccessService;
import edu.sombra.cms.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static edu.sombra.cms.service.impl.UserAccessService.AccessEntity.USER;

@RestController
@RequestMapping("/api/instructor")
@RequiredArgsConstructor
public class InstructorController {

    private final InstructorService instructorService;
    private final UserAccessService userAccessService;

    @PostMapping
    public InstructorDTO createInstructorInfo(@RequestBody @Valid InstructorData instructorData, @RequestParam(required = false) Long userId) throws SomethingWentWrongException {
        userId = Optional.ofNullable(userId).orElse(SecurityUtil.getLoggedUserId());
        userAccessService.checkAccess(USER, userId);
        return instructorService.create(instructorData, userId);
    }

    @GetMapping("/course")
    public List<CourseOverviewDTO> courseList() throws SomethingWentWrongException {
        return instructorService.courseList();
    }

}
