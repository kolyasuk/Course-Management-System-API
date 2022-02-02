package edu.sombra.cms.service.impl;

import edu.sombra.cms.domain.dto.CourseDTO;
import edu.sombra.cms.domain.entity.Course;
import edu.sombra.cms.domain.entity.StudentCourse;
import edu.sombra.cms.domain.enumeration.CourseStatus;
import edu.sombra.cms.domain.mapper.CourseMapper;
import edu.sombra.cms.domain.payload.CourseData;
import edu.sombra.cms.repository.CourseRepository;
import edu.sombra.cms.repository.StudentCourseRepository;
import edu.sombra.cms.repository.StudentLessonRepository;
import edu.sombra.cms.service.CourseService;
import edu.sombra.cms.service.InstructorService;
import edu.sombra.cms.service.StudentLessonService;
import edu.sombra.cms.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;

import static edu.sombra.cms.domain.enumeration.CourseStatus.ACTIVE;
import static edu.sombra.cms.domain.enumeration.CourseStatus.INACTIVE;
import static edu.sombra.cms.util.constants.SystemSettings.MINIMUM_NUMBER_OF_COURSE_INSTRUCTORS;
import static edu.sombra.cms.util.constants.SystemSettings.MINIMUM_NUMBER_OF_COURSE_LESSONS;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final InstructorService instructorService;
    private final StudentService studentService;
    private final StudentCourseRepository studentCourseRepository;
    private final StudentLessonService studentLessonService;
    private final StudentLessonRepository studentLessonRepository;
    private final CourseMapper courseMapper;


    @Override
    public Course getById(Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("courseId incorrect"));
    }

    @Override
    public Course getActiveById(Long id) {
        return courseRepository.findByIdAndStatus(id, ACTIVE).orElseThrow(() -> new IllegalArgumentException("Active course not found"));
    }

    @Override
    public CourseDTO create(CourseData courseData) {
        Course course = new Course();

        course.setName(courseData.getName());
        course.setDescription(courseData.getDescription());
        course.setStatus(INACTIVE);

        var instructors = instructorService.getByIdList(courseData.getInstructorIds());
        course.setInstructors(new HashSet<>(instructors));

        return courseMapper.to(courseRepository.save(course));
    }

    @Override
    public CourseDTO update(Long id, CourseData courseData) {
        var course = getActiveById(id);

        course.setName(courseData.getName());
        course.setDescription(courseData.getDescription());

        var instructors = instructorService.getByIdList(courseData.getInstructorIds());
        course.setInstructors(new HashSet<>(instructors));

        return courseMapper.to(courseRepository.save(course));
    }

    @Override
    public void start(Long id) {
        var course = Optional.of(getById(id)).filter(Course::canBeActivated)
                .orElseThrow(() -> new IllegalArgumentException("Course should have at least " + MINIMUM_NUMBER_OF_COURSE_INSTRUCTORS + " instructor(s) and " + MINIMUM_NUMBER_OF_COURSE_LESSONS + " lessons"));

        course.setStatus(ACTIVE);

        courseRepository.save(course);
    }

    @Override
    @Transactional
    public void finish(Long courseId) {
        var course = getActiveById(courseId);

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
}
