package edu.sombra.cms.domain.mapper;

import edu.sombra.cms.domain.dto.ICourseOverview;
import edu.sombra.cms.domain.dto.OverviewPageDTO;
import edu.sombra.cms.domain.dto.OverviewPageDTO.ComingLessonDTO;
import edu.sombra.cms.domain.dto.OverviewPageDTO.CoursesOverviewDTO;
import edu.sombra.cms.domain.dto.UserOverviewDTO;
import edu.sombra.cms.domain.entity.User;
import edu.sombra.cms.domain.enumeration.CourseOverviewStatus;
import edu.sombra.cms.messages.SomethingWentWrongException;
import edu.sombra.cms.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OverviewPageMapper {

    private final UserService userService;
    private final StudentService studentService;
    private final InstructorService instructorService;
    private final CourseService courseService;
    private final LessonService lessonService;


    public OverviewPageDTO to(Long userId) throws SomethingWentWrongException {
        User user = userService.findUserById(userId);

        var userOverviewDTO = new UserOverviewDTO(userId, user.getFullName(), user.getRole());
        var courseOverview = getCourseOverview(user);
        var comingLessons = getComingLessons(user);

        return new OverviewPageDTO(userOverviewDTO, courseOverview, comingLessons);
    }

    private List<CoursesOverviewDTO> getCourseOverview(User user) throws SomethingWentWrongException {
        List<CoursesOverviewDTO> res = new ArrayList<>();

        switch (user.getRole()){
            case ROLE_STUDENT:
                return getCoursesOverviewList(studentService.courseList());
            case ROLE_INSTRUCTOR:
                return getCoursesOverviewList(instructorService.courseList());
            case ROLE_ADMIN:
                return getCoursesOverviewList(courseService.findAll());
        }
        return res;
    }

    private List<ComingLessonDTO> getComingLessons(User user) throws SomethingWentWrongException {
        switch (user.getRole()){
            case ROLE_STUDENT:
                return studentService.comingLesson();
            case ROLE_INSTRUCTOR:
                return instructorService.comingLessons();
            case ROLE_ADMIN:
                return lessonService.comingLessons();
        }
        return Collections.emptyList();
    }

    private <T extends ICourseOverview> List<CoursesOverviewDTO> getCoursesOverviewList(List<T> tList){
        List<CoursesOverviewDTO> res = new ArrayList<>();

        var collect = tList.stream()
                .collect(Collectors.groupingBy(ICourseOverview::getCourseOverviewStatus));

        for (CourseOverviewStatus overviewStatus : collect.keySet()) {
            res.add(new CoursesOverviewDTO(overviewStatus, collect.get(overviewStatus)));
        }

        return res;
    }
}
