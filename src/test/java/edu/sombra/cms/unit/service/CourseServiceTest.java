package edu.sombra.cms.unit.service;

import edu.sombra.cms.domain.dto.CourseDTO;
import edu.sombra.cms.domain.entity.Course;
import edu.sombra.cms.domain.entity.Instructor;
import edu.sombra.cms.domain.entity.Lesson;
import edu.sombra.cms.domain.enumeration.CourseStatus;
import edu.sombra.cms.domain.mapper.CourseMapper;
import edu.sombra.cms.domain.mapper.LessonOverviewMapper;
import edu.sombra.cms.domain.mapper.StudentOverviewMapper;
import edu.sombra.cms.domain.payload.CourseData;
import edu.sombra.cms.messages.SomethingWentWrongException;
import edu.sombra.cms.repository.CourseRepository;
import edu.sombra.cms.repository.LessonRepository;
import edu.sombra.cms.repository.StudentCourseRepository;
import edu.sombra.cms.repository.StudentLessonRepository;
import edu.sombra.cms.service.InstructorService;
import edu.sombra.cms.service.StudentLessonService;
import edu.sombra.cms.service.StudentService;
import edu.sombra.cms.service.UserService;
import edu.sombra.cms.service.impl.CourseServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

import static edu.sombra.cms.domain.enumeration.CourseStatus.ACTIVE;
import static edu.sombra.cms.domain.enumeration.CourseStatus.INACTIVE;
import static edu.sombra.cms.util.ExceptionMatcher.containsException;
import static edu.sombra.cms.util.Generator.generateEmptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;
    @Mock
    private InstructorService instructorService;
    @Mock
    private StudentService studentService;
    @Mock
    private StudentCourseRepository studentCourseRepository;
    @Mock
    private StudentLessonService studentLessonService;
    @Mock
    private StudentLessonRepository studentLessonRepository;
    @Mock
    private CourseMapper courseMapper;
    @Mock
    private LessonRepository lessonRepository;
    @Mock
    private UserService userService;
    @Mock
    private LessonOverviewMapper lessonOverviewMapper;
    @Mock
    private StudentOverviewMapper studentOverviewMapper;

    @InjectMocks
    private CourseServiceImpl courseService;

    @Test
    void startedCourseHasActiveStatus() throws SomethingWentWrongException {
        when(courseRepository.findById(any(Long.class))).thenReturn(Optional.of(activeCourseEntityForTest()));
        Course course = courseService.start(1L);

        assertThat(course.getStatus()).isEqualTo(ACTIVE);
    }

    @Test
    void courseIsNotStartedBecauseOfInstructorNumber() {
        Course course = courseEntityForTest();
        course.setLessons(generateEmptyList(new Lesson(), 5));

        when(courseRepository.findById(any(Long.class))).thenReturn(Optional.of(course));
        SomethingWentWrongException somethingWentWrongException = assertThrows(SomethingWentWrongException.class, () -> courseService.start(1L));

        String expectedMessage = "Course should have at least";
        containsException(expectedMessage, somethingWentWrongException);
    }

    @Test
    void courseIsNotStartedBecauseOfLessonsNumber() {
        Course course = courseEntityForTest();
        course.setInstructors(Set.of(new Instructor()));

        when(courseRepository.findById(any(Long.class))).thenReturn(Optional.of(course));
        SomethingWentWrongException somethingWentWrongException = assertThrows(SomethingWentWrongException.class, () -> courseService.start(1L));

        String expectedMessage = "Course should have at least";
        containsException(expectedMessage, somethingWentWrongException);
    }

    @Test
    void finishedCourseHasInactiveStatus() throws SomethingWentWrongException {
        when(courseRepository.findByIdAndStatus(any(Long.class), any(CourseStatus.class))).thenReturn(Optional.of(inactiveCourseEntityForTest()));
        Course course = courseService.finish(1L);

        assertThat(course.getStatus()).isEqualTo(INACTIVE);
    }

    @Test
    void courseIsNotFinishedBecauseExistNotFinishedLessons() {
        when(courseRepository.findByIdAndStatus(any(Long.class), any(CourseStatus.class))).thenReturn(Optional.of(inactiveCourseEntityForTest()));
        when(lessonRepository.existsNotFinishedLessons(any(Long.class))).thenReturn(1);
        SomethingWentWrongException somethingWentWrongException = assertThrows(SomethingWentWrongException.class, () -> courseService.finish(1L));

        String expectedMessage = "Course lessons are not finished yet";
        containsException(expectedMessage, somethingWentWrongException);
    }


    private Course courseEntityForTest() {
        Course course = new Course();
        course.setId(1);
        course.setName("Test course");
        course.setDescription("Test description");

        return course;
    }

    private Course activeCourseEntityForTest() {
        Course course = courseEntityForTest();
        course.setInstructors(Set.of(new Instructor()));
        course.setLessons(generateEmptyList(new Lesson(), 5));
        course.setStatus(ACTIVE);

        return course;
    }

    private Course inactiveCourseEntityForTest() {
        Course course = courseEntityForTest();
        course.setInstructors(Set.of(new Instructor()));
        course.setStatus(INACTIVE);

        return course;
    }


    private CourseData courseDataForTest(){
        return new CourseData("Test course", "Test description", new ArrayList<>());
    }

    private CourseDTO courseDtoForTest(){
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setId(1);
        courseDTO.setName("Test course");
        courseDTO.setDescription("Test description");
        courseDTO.setStatus(INACTIVE);

        return courseDTO;
    }
}
