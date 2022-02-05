package edu.sombra.cms.service.impl;

import edu.sombra.cms.domain.dto.CourseOverviewDTO;
import edu.sombra.cms.domain.dto.InstructorDTO;
import edu.sombra.cms.domain.dto.StudentOverviewDTO;
import edu.sombra.cms.domain.entity.Instructor;
import edu.sombra.cms.domain.entity.User;
import edu.sombra.cms.domain.mapper.CourseOverviewMapper;
import edu.sombra.cms.domain.mapper.InstructorMapper;
import edu.sombra.cms.domain.mapper.StudentOverviewMapper;
import edu.sombra.cms.domain.payload.InstructorData;
import edu.sombra.cms.repository.InstructorRepository;
import edu.sombra.cms.service.CourseService;
import edu.sombra.cms.service.InstructorService;
import edu.sombra.cms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@Validated
@RequiredArgsConstructor
public class InstructorServiceImpl implements InstructorService {

    private final InstructorRepository instructorRepository;
    private final InstructorMapper instructorMapper;
    private final UserService userService;
    @Lazy
    private final CourseService courseService;
    private final CourseOverviewMapper courseOverviewMapper;
    private final StudentOverviewMapper studentOverviewMapper;

    @Override
    public Instructor getById(Long id) {
        return instructorRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Instructor not found"));
    }

    @Override
    public List<Instructor> getByIdList(List<Long> ids) {
        return Optional.of(instructorRepository.findAllById(ids)).filter(o -> !o.isEmpty())
                .orElseThrow(() -> new IllegalArgumentException("Instructors not found"));
    }

    @Override
    public InstructorDTO create(@Valid InstructorData instructorData, Long userId) {
        var user = getInstructorUser(userId);

        if(user.getInstructor() != null){
            throw new IllegalArgumentException("Instructor info is already created");
        }

        Instructor instructor = new Instructor();

        instructor.setFirstName(instructorData.getFirstName());
        instructor.setLastName(instructorData.getLastName());
        instructor.setEmail(instructorData.getEmail());
        instructor.setInfo(instructorData.getInfo());
        instructor.setUser(user);

        return instructorMapper.to(instructorRepository.save(instructor));
    }

    @Override
    public List<CourseOverviewDTO> courseList() {
        var loggedUser = userService.getLoggedUser();
        if(!loggedUser.isInstructor())
            throw new IllegalArgumentException("You are not instructor");

        return courseOverviewMapper.toList(loggedUser.getInstructor().getCourses());
    }

    @Override
    public List<StudentOverviewDTO> courseStudentList(Long courseId) {
        var course = courseService.getById(courseId);

        return studentOverviewMapper.toList(course.getStudents());
    }

    private User getInstructorUser(Long userId) {
        if(userService.getLoggedUser().isAdmin()){
            if(userId == null)
                throw new IllegalArgumentException("Unable to create Instructor, userId is null");

            return userService.findUserById(userId);
        }

        return userService.getLoggedUser();
    }
}
