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
import static edu.sombra.cms.util.constants.SystemSettings.MINIMUM_NUMBER_OF_COURSE_INSTRUCTORS;
import static edu.sombra.cms.util.constants.SystemSettings.MINIMUM_NUMBER_OF_COURSE_LESSONS;

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
    public Course getById(Long courseId) {
        var course = courseRepository.findById(courseId).orElseThrow(() -> new IllegalArgumentException("courseId incorrect"));
        userService.loggedUserHasAccess(course.getRelatedUsers());

        return course;
    }

    @Override
    public CourseDTO getDTOById(Long courseId) {
        return courseMapper.to(getById(courseId));
    }

    @Override
    public Course getActiveById(Long courseId) {
        return courseRepository.findByIdAndStatus(courseId, ACTIVE).orElseThrow(() -> new IllegalArgumentException("Active course not found"));
    }

    @Override
    public CourseDTO create(@Valid CourseData courseData) {
        Course course = new Course();

        course.setName(courseData.getName());
        course.setDescription(courseData.getDescription());
        course.setStatus(INACTIVE);

        var instructors = instructorService.getByIdList(courseData.getInstructorIds());
        course.setInstructors(new HashSet<>(instructors));

        return courseMapper.to(courseRepository.save(course));
    }

    @Override
    public CourseDTO update(Long courseId, @Valid CourseData courseData) {
        var course = getActiveById(courseId);

        course.setName(courseData.getName());
        course.setDescription(courseData.getDescription());

        var instructors = instructorService.getByIdList(courseData.getInstructorIds());
        course.setInstructors(new HashSet<>(instructors));

        return courseMapper.to(courseRepository.save(course));
    }

    @Override
    public void start(Long courseId) {
        var course = Optional.of(getById(courseId)).filter(Course::canBeActivated)
                .orElseThrow(() -> new IllegalArgumentException("Course should have at least " + MINIMUM_NUMBER_OF_COURSE_INSTRUCTORS + " instructor(s) and " + MINIMUM_NUMBER_OF_COURSE_LESSONS + " lessons"));

        course.setStatus(ACTIVE);
        courseRepository.save(course);
    }

    @Override
    @Transactional
    public void finish(Long courseId) {
        var course = getActiveById(courseId);

        if(existsNotFinishedLessons(courseId)){
            throw new IllegalArgumentException("Course lessons are not finished yet");
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
    public List<LessonOverviewDTO> lessonList(Long courseId) {
        var course = getById(courseId);

        return lessonOverviewMapper.toList(course.getLessons());
    }

    @Override
    public boolean existsNotFinishedLessons(Long courseId) {
        var finishDate = LocalDate.now().minusDays(1);
        return lessonRepository.existsNotFinishedLessons(courseId, finishDate);
    }

    @Override
    public CourseDTO assignInstructor(Long courseId, Long instructorId) {
        var course = getById(courseId);

        if(course.getInstructors().stream().anyMatch(o -> o.getId() == instructorId)){
            throw new IllegalArgumentException("Instructor is already assigned");
        }

        var instructor = instructorService.getById(instructorId);
        course.addInstructor(instructor);

        return courseMapper.to(courseRepository.save(course));
    }

    @Override
    public CourseDTO assignStudent(Long courseId, Long studentId) {
        var course = Optional.of(getById(courseId)).filter(Course::isActive)
                .orElseThrow(() -> new IllegalArgumentException("To assign student course should be active"));

        if(course.getStudents().stream().anyMatch(o -> o.getId() == studentId)){
            throw new IllegalArgumentException("Student is already assigned");
        }

        var student = studentService.getById(studentId);
        if(student.getStudentCourses().size() >= 5){
            throw new IllegalArgumentException("Student can't have more than 5 courses");
        }

        studentCourseRepository.save(new StudentCourse(student, course));
        studentLessonService.saveStudentLessons(course.getLessons(), student);


        return courseMapper.to(course);
    }

    @Override
    public List<StudentOverviewDTO> courseStudentList(Long courseId) {
        var course = courseService.getById(courseId);

        return studentOverviewMapper.toList(course.getStudents());
    }
}
