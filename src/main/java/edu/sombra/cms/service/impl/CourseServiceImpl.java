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
import org.springframework.context.annotation.Lazy;
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
    @Lazy
    private final CourseService courseService;
    private final StudentOverviewMapper studentOverviewMapper;


    @Override
    public Course getById(Long courseId) throws SomethingWentWrongException {
        var course = courseRepository.findById(courseId).orElseThrow(NOT_FOUND::ofException);
        userService.loggedUserHasAccess(course.getRelatedUsers());

        return course;
    }

    @Override
    public CourseDTO getDTOById(Long courseId) throws SomethingWentWrongException {
        return courseMapper.to(getById(courseId));
    }

    @Override
    public Course getActiveById(Long courseId) throws SomethingWentWrongException {
        return courseRepository.findByIdAndStatus(courseId, ACTIVE).orElseThrow(ACTIVE_NOT_FOUND::ofException);
    }

    @Override
    public CourseDTO create(@Valid CourseData courseData) throws SomethingWentWrongException {
        Course course = new Course();

        course.setName(courseData.getName());
        course.setDescription(courseData.getDescription());
        course.setStatus(INACTIVE);

        var instructors = instructorService.getByIdList(courseData.getInstructorIds());
        course.setInstructors(new HashSet<>(instructors));

        return courseMapper.to(courseRepository.save(course));
    }

    @Override
    public CourseDTO update(Long courseId, @Valid CourseData courseData) throws SomethingWentWrongException {
        var course = getActiveById(courseId);

        course.setName(courseData.getName());
        course.setDescription(courseData.getDescription());

        var instructors = instructorService.getByIdList(courseData.getInstructorIds());
        course.setInstructors(new HashSet<>(instructors));

        return courseMapper.to(courseRepository.save(course));
    }

    @Override
    public void start(Long courseId) throws SomethingWentWrongException {
        var course = Optional.of(getById(courseId)).filter(Course::canBeActivated).orElseThrow(MINIMUM_NUMBER_OF_INSTRUCTORS_AND_LESSONS::ofException);

        course.setStatus(ACTIVE);
        courseRepository.save(course);
    }

    @Override
    @Transactional
    public void finish(Long courseId) throws SomethingWentWrongException {
        var course = getActiveById(courseId);

        if(existsNotFinishedLessons(courseId)){
            throw LESSONS_NOT_FINISHED.ofException();
        }

        for (StudentCourse studentCourse : course.getStudentCourses()) {
            var student = studentCourse.getStudent();

            int mark = 0;
            if(!studentLessonRepository.existsStudentLessonByStudentAndCourseAndMarkIsNull(student, course)) {
                mark = studentLessonRepository.getAvgMark(student, course);
            }

            studentCourseRepository.updateMark(mark, studentCourse);
        }

        courseRepository.setStatus(course, CourseStatus.FINISHED);
    }

    @Override
    public List<LessonOverviewDTO> lessonList(Long courseId) throws SomethingWentWrongException {
        var course = getById(courseId);

        return lessonOverviewMapper.toList(course.getLessons());
    }

    @Override
    public boolean existsNotFinishedLessons(Long courseId) {
        var finishDate = LocalDate.now().minusDays(1);
        return lessonRepository.existsNotFinishedLessons(courseId, finishDate);
    }

    @Override
    public CourseDTO assignInstructor(Long courseId, Long instructorId) throws SomethingWentWrongException {
        var course = getById(courseId);

        if(course.getInstructors().stream().anyMatch(o -> o.getId() == instructorId)){
            throw INSTRUCTOR_IS_ALREADY_ASSIGNED.ofException();
        }

        var instructor = instructorService.getById(instructorId);
        course.addInstructor(instructor);

        return courseMapper.to(courseRepository.save(course));
    }

    @Override
    public CourseDTO assignStudent(Long courseId, Long studentId) throws SomethingWentWrongException {
        var course = getActiveById(courseId);

        if(course.getStudents().stream().anyMatch(o -> o.getId() == studentId)){
            throw STUDENT_IS_ALREADY_ASSIGNED.ofException();
        }

        var student = studentService.getById(studentId);
        if(student.getActiveCourseList().size() >= STUDENT_COURSES_LIMIT){
            throw STUDENT_ACTIVE_COURSE_LIMIT.ofException();
        }

        studentCourseRepository.save(new StudentCourse(student, course));
        studentLessonService.saveStudentLessons(course.getLessons(), student);


        return courseMapper.to(course);
    }

    @Override
    public List<StudentOverviewDTO> courseStudentList(Long courseId) throws SomethingWentWrongException {
        var course = courseService.getById(courseId);

        return studentOverviewMapper.toList(course.getStudents());
    }
}
