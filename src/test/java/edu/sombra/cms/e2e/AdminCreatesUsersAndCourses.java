package edu.sombra.cms.e2e;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.sombra.cms.config.security.JwtTokenUtil;
import edu.sombra.cms.domain.entity.*;
import edu.sombra.cms.domain.enumeration.Role;
import edu.sombra.cms.domain.payload.*;
import edu.sombra.cms.repository.*;
import edu.sombra.cms.service.impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static edu.sombra.cms.domain.enumeration.CourseStatus.ACTIVE;
import static java.lang.String.valueOf;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AdminCreatesUsersAndCourses {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private InstructorRepository instructorRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private StudentCourseRepository studentCourseRepository;
    @Autowired
    private StudentLessonRepository studentLessonRepository;


    static {
        System.setProperty("SPRING_PROFILE", "testing");
    }


    @Test
    @Order(1)
    @WithMockUser(username = "admin", roles = "ADMIN")
    void adminCreatesInstructorAndStudent() throws Exception {
        UserRegistrationData instructorRegistrationData = new UserRegistrationData("password", "Mr Instructor", "instructor1@email.com", Role.ROLE_INSTRUCTOR);

        mockMvc.perform(post("/api/admin/user")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(instructorRegistrationData)))
                .andExpect(status().isOk());

        UserRegistrationData studentRegistrationData = new UserRegistrationData("password", "Student 1", "student1@email.com", Role.ROLE_STUDENT);

        mockMvc.perform(post("/api/admin/user")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(studentRegistrationData)))
                .andExpect(status().isOk());

        String registeredInstructorUserEmail = userRepository.findByEmail(instructorRegistrationData.getEmail()).map(User::getEmail).orElseThrow();
        String registeredStudentUserEmail = userRepository.findByEmail(studentRegistrationData.getEmail()).map(User::getEmail).orElseThrow();
        assertThat(registeredInstructorUserEmail).isEqualTo(instructorRegistrationData.getEmail());
        assertThat(registeredStudentUserEmail).isEqualTo(studentRegistrationData.getEmail());
    }

    @Test
    @Order(2)
    void instructorCreatesHisInfo() throws Exception {
        User user = userRepository.findByEmail("instructor1@email.com").orElseThrow();

        InstructorData instructorData = new InstructorData("Instructor1", "Instructor1", "Mr Instructor 1337");

        mockMvc.perform(post("/api/instructor")
                .header("Authorization",  createAccessToken(user.getEmail()))
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(instructorData)))
                .andExpect(status().isOk());

        Instructor createdInstructor = instructorRepository.findByUserId(user.getId()).orElseThrow();
        assertThat(createdInstructor.getUser().getId()).isEqualTo(user.getId());
    }


    @Test
    @Order(3)
    void adminCreatesStudentInfo() throws Exception {
        StudentData studentRegistrationData = new StudentData("Student1", "Student1", "PIS2022", "Program engineering");

        User user = userRepository.findByEmail("student1@email.com").orElseThrow();
        mockMvc.perform(post("/api/student")
                .header("Authorization",  createAccessToken("admin@cms-api.com"))
                .param("userId", valueOf(user.getId()))
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(studentRegistrationData)))
                .andExpect(status().isOk());


        Student registeredStudent = studentRepository.findByUserId(user.getId()).orElseThrow();
        assertThat(registeredStudent.getFirstName()).isEqualTo(registeredStudent.getFirstName());
    }

    @Test
    @Order(4)
    @WithMockUser(username = "admin", roles = "ADMIN")
    void adminCreatesCourseWithAssignedInstructor() throws Exception {
        Long instructorId = instructorRepository.findByUsername("instructor1@email.com").map(Instructor::getId).orElseThrow();
        CourseData courseData = new CourseData("Course 1", "Course 1 description", singletonList(instructorId));

        mockMvc.perform(post("/api/admin/course")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(courseData)))
                .andExpect(status().isOk());

        Course createdCourse = courseRepository.findTopByOrderByIdDesc().orElseThrow();
        assertThat(createdCourse.getName()).isEqualTo(courseData.getName());
        assertTrue(createdCourse.getInstructors().stream().allMatch(item -> instructorId.equals(item.getId())));
    }

    @Test
    @Order(5)
    void instructorCreatesLessons() throws Exception {
        Instructor instructor = instructorRepository.findByUsername("instructor1@email.com").orElseThrow();
        Course course = courseRepository.findTopByOrderByIdDesc().orElseThrow();

        LessonData lesson1Data = new LessonData("Lesson 1", "Lesson 1 information", course.getId(), instructor.getId(),
                "Lesson 1 homework task", LocalDate.now().plusDays(1));
        LessonData lesson2Data = new LessonData("Lesson 2", "Lesson 2 information", course.getId(), instructor.getId(),
                "Lesson 2 homework task", LocalDate.now().plusDays(1));
        LessonData lesson3Data = new LessonData("Lesson 3", "Lesson 3 information", course.getId(), instructor.getId(),
                "Lesson 3 homework task", LocalDate.now().plusDays(1));
        LessonData lesson4Data = new LessonData("Lesson 4", "Lesson 4 information", course.getId(), instructor.getId(),
                "Lesson 4 homework task", LocalDate.now().plusDays(1));
        LessonData lesson5Data = new LessonData("Lesson 5", "Lesson 5 information", course.getId(), instructor.getId(),
                "Lesson 5 homework task", LocalDate.now().plusDays(1));

        mockMvc.perform(post("/api/lesson")
                .header("Authorization",  createAccessToken(instructor.getUser().getEmail()))
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(lesson1Data)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/lesson")
                .header("Authorization",  createAccessToken(instructor.getUser().getEmail()))
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(lesson2Data)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/lesson")
                .header("Authorization",  createAccessToken(instructor.getUser().getEmail()))
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(lesson3Data)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/lesson")
                .header("Authorization",  createAccessToken(instructor.getUser().getEmail()))
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(lesson4Data)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/lesson")
                .header("Authorization",  createAccessToken(instructor.getUser().getEmail()))
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(lesson5Data)))
                .andExpect(status().isOk());

        assertThat(course.getLessons().size()).isEqualTo(5);
    }

    @Test
    @Order(6)
    void instructorActivatesCourse() throws Exception {
        Course course = courseRepository.findTopByOrderByIdDesc().orElseThrow();

        mockMvc.perform(put("/api/course/{id}/start", course.getId())
                .header("Authorization",  createAccessToken("instructor1@email.com")))
                .andExpect(status().isOk());

        Course activatedCourse = courseRepository.findByIdAndStatus(course.getId(), ACTIVE).orElseThrow();
        assertThat(course.getId()).isEqualTo(activatedCourse.getId());
    }

    @Test
    @Order(7)
    @WithMockUser(username = "admin", roles = "ADMIN")
    void adminAssignsStudentToCourse() throws Exception {
        Course course = courseRepository.findTopByOrderByIdDesc().orElseThrow();
        Student student = studentRepository.findByUsername("student1@email.com").orElseThrow();

        mockMvc.perform(put("/api/admin/course/{courseId}/student/assign", course.getId())
                .param("studentId", valueOf(student.getId())))
                .andExpect(status().isOk());

        StudentCourse studentCourse = studentCourseRepository.findStudentCourseByStudentIdAndCourseId(student.getId(), course.getId()).orElseThrow();
        assertThat(studentCourse.getCourse().getId()).isEqualTo(course.getId());
        assertThat(studentCourse.getStudent().getId()).isEqualTo(student.getId());
        assertTrue(studentLessonRepository.existsStudentLessonByStudentAndCourseAndMarkIsNull(student, course));
    }

    @Test
    @Order(8)
    void studentCreatesHomework() throws Exception {
        HomeworkData homeworkData = new HomeworkData("The home task was easy");
        MockMultipartFile homeworkFile = new MockMultipartFile("homeworkFile", "homework.txt", "multipart/form-data", "Perfect homework!!!".getBytes());
        MockMultipartFile homeworkDataFile = new MockMultipartFile("homeworkData", "", "application/json", objectMapper.writeValueAsString(homeworkData).getBytes());

        for (StudentLesson studentLesson : studentLessonRepository.findAll()) {
            mockMvc.perform(multipart("/api/student/lesson/{id}/homework", studentLesson.getLesson().getId())
                    .file(homeworkFile)
                    .file(homeworkDataFile)
                    .accept(MediaType.MULTIPART_FORM_DATA)
                    .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                    .header("Authorization", createAccessToken("student1@email.com")))
                    .andExpect(status().is(200));
        }

        List<StudentLesson> all = studentLessonRepository.findAll();
        assertTrue(all.stream().allMatch(i -> "homework.txt".equals(i.getHomeworkFiles().stream().findFirst().orElseThrow().getFilename())));
    }

    @Test
    @Order(9)
    void instructorEvaluateHomework() throws Exception {

    }

    @Test
    @Order(10)
    void instructorFinishCourse() throws Exception {


        //todo: check student mark
        //todo: check status
    }

    private String createAccessToken(String username) {
        return "Bearer " + jwtTokenUtil.generateAccessToken(userDetailsService.loadUserByUsername2(username));
    }

}
