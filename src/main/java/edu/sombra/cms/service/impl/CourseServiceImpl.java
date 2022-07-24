package edu.sombra.cms.service.impl;

import edu.sombra.cms.domain.dto.CourseDTO;
import edu.sombra.cms.domain.dto.LessonOverviewDTO;
import edu.sombra.cms.domain.dto.StudentOverviewDTO;
import edu.sombra.cms.domain.entity.Course;
import edu.sombra.cms.domain.entity.StudentCourse;
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
import edu.sombra.cms.service.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static edu.sombra.cms.domain.enumeration.CourseStatus.ACTIVE;
import static edu.sombra.cms.domain.enumeration.CourseStatus.INACTIVE;
import static edu.sombra.cms.domain.mapper.AbstractMapper.entitiesToIds;
import static edu.sombra.cms.messages.CourseMessage.*;
import static edu.sombra.cms.messages.StudentMessage.STUDENT_ACTIVE_COURSE_LIMIT;
import static edu.sombra.cms.util.constants.SystemSettings.STUDENT_COURSES_LIMIT;

@Service
@Validated
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final InstructorService instructorService;
    private final StudentService studentService;
    private final StudentCourseRepository studentCourseRepository;
    private final StudentLessonService studentLessonService;
    private final StudentLessonRepository studentLessonRepository;
    private final CourseMapper courseMapper;
    private final LessonRepository lessonRepository;
    private final UserService userService;
    private final LessonOverviewMapper lessonOverviewMapper;
    private final StudentOverviewMapper studentOverviewMapper;


    private static final Logger LOGGER = LoggerFactory.getLogger(CourseServiceImpl.class);

    @Override
    @Transactional(rollbackFor = SomethingWentWrongException.class)
    public Course getById(Long courseId) throws SomethingWentWrongException {
        var course = courseRepository.findById(courseId).orElseThrow(NOT_FOUND::ofException);
        userService.loggedUserHasAccess(course.getRelatedUsers());

        return course;
    }

    @Override
    @Transactional(rollbackFor = SomethingWentWrongException.class)
    public CourseDTO getDTOById(Long courseId) throws SomethingWentWrongException {
        return courseMapper.to(courseId);
    }

    @Override
    @Transactional(rollbackFor = SomethingWentWrongException.class)
    public Course getActiveById(Long courseId) throws SomethingWentWrongException {
        return courseRepository.findByIdAndStatus(courseId, ACTIVE).orElseThrow(ACTIVE_NOT_FOUND::ofException);
    }

    @Override
    @Transactional(rollbackFor = SomethingWentWrongException.class)
    public CourseDTO create(@Valid CourseData courseData) throws SomethingWentWrongException {
        Course course = new Course();

        course.setName(courseData.getName());
        course.setDescription(courseData.getDescription());
        course.setStatus(INACTIVE);

        var instructors = instructorService.getByIdList(courseData.getInstructorIds());
        course.setInstructors(new HashSet<>(instructors));

        courseRepository.save(course);

        LOGGER.info("Created course with name: {} and id: {}", course.getName(), course.getId());
        return courseMapper.to(course.getId());
    }

    @Override
    @Transactional(rollbackFor = SomethingWentWrongException.class)
    public CourseDTO update(Long courseId, @Valid CourseData courseData) throws SomethingWentWrongException {
        var course = getActiveById(courseId);

        course.setName(courseData.getName());
        course.setDescription(courseData.getDescription());

        var instructors = instructorService.getByIdList(courseData.getInstructorIds());
        course.setInstructors(new HashSet<>(instructors));

        courseRepository.save(course);

        LOGGER.info("Updated course with id: {}", course.getId());
        return courseMapper.to(course.getId());
    }

    @Override
    @Transactional(rollbackFor = SomethingWentWrongException.class)
    public Course start(Long courseId) throws SomethingWentWrongException {
        var course = Optional.of(getById(courseId)).filter(Course::canBeActivated).orElseThrow(MINIMUM_NUMBER_OF_INSTRUCTORS_AND_LESSONS::ofException);

        course.setStatus(ACTIVE);
        courseRepository.save(course);

        LOGGER.info("Started course with id: {}", course.getId());

        return course;
    }

    @Override
    @Transactional(rollbackFor = SomethingWentWrongException.class)
    public Course finish(Long courseId) throws SomethingWentWrongException {
        var course = getActiveById(courseId);

        if(existsNotFinishedLessons(courseId)){
            throw LESSONS_NOT_FINISHED.ofException();
        }

        calculateStudentCourceMarks(course);

        courseRepository.setStatus(course, CourseStatus.FINISHED);

        LOGGER.info("Finished course with id: {}", course.getId());

        return course;
    }

    private void calculateStudentCourceMarks(Course course) {
        for (StudentCourse studentCourse : course.getStudentCourses()) {
            var student = studentCourse.getStudent();

            int studentCourceMark = calculateStudentCourceMark(course, student);
            studentCourseRepository.updateMark(studentCourceMark, studentCourse);
        }
    }

    private int calculateStudentCourceMark(Course course, edu.sombra.cms.domain.entity.Student student) {
        int mark = 0;
        if(!studentLessonRepository.existsStudentLessonByStudentAndCourseAndMarkIsNull(student, course)) {
            mark = studentLessonRepository.getAvgMark(student, course);
        }
        return mark;
    }

    @Override
    @Transactional(rollbackFor = SomethingWentWrongException.class)
    public List<LessonOverviewDTO> lessonList(Long courseId) throws SomethingWentWrongException {
        var course = getById(courseId);

        return lessonOverviewMapper.toList(entitiesToIds(course.getLessons()));
    }

    @Override
    @Transactional(rollbackFor = SomethingWentWrongException.class)
    public boolean existsNotFinishedLessons(Long courseId) {
        var finishDate = LocalDate.now().minusDays(1);
        return lessonRepository.existsNotFinishedLessons(courseId, finishDate);
    }

    @Override
    @Transactional(rollbackFor = SomethingWentWrongException.class)
    public CourseDTO assignInstructor(Long courseId, Long instructorId) throws SomethingWentWrongException {
        var course = getById(courseId);

        if(course.getInstructors().stream().anyMatch(o -> o.getId().equals(instructorId))){
            throw INSTRUCTOR_IS_ALREADY_ASSIGNED.ofException();
        }

        var instructor = instructorService.getByStudentId(instructorId);
        course.addInstructor(instructor);

        courseRepository.save(course);

        LOGGER.info("Assign instructor with id: {} to course with id: {}", instructor.getId(), course.getId());
        return courseMapper.to(course.getId());
    }

    @Override
    @Transactional(rollbackFor = SomethingWentWrongException.class)
    public CourseDTO assignStudent(Long courseId, Long studentId) throws SomethingWentWrongException {
        var course = getActiveById(courseId);

        if(course.getStudents().stream().anyMatch(o -> o.getId() == studentId)){
            throw STUDENT_IS_ALREADY_ASSIGNED.ofException();
        }

        var student = studentService.getByStudentId(studentId);
        if(student.getActiveCourseList().size() >= STUDENT_COURSES_LIMIT){
            throw STUDENT_ACTIVE_COURSE_LIMIT.ofException();
        }

        studentCourseRepository.save(new StudentCourse(student, course));
        studentLessonService.saveStudentLessons(course.getLessons(), student);

        LOGGER.info("Assign student with id: {} to course with id: {}", student.getId(), course.getId());
        return courseMapper.to(course.getId());
    }

    @Override
    @Transactional(rollbackFor = SomethingWentWrongException.class)
    public List<StudentOverviewDTO> courseStudentList(Long courseId) throws SomethingWentWrongException {
        return studentOverviewMapper.toList(entitiesToIds(getById(courseId).getStudents()));
    }
}
