package edu.sombra.cms.service.impl;

import edu.sombra.cms.config.security.UserDetailsImpl;
import edu.sombra.cms.domain.entity.Owners;
import edu.sombra.cms.messages.SomethingWentWrongException;
import edu.sombra.cms.repository.*;
import edu.sombra.cms.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static edu.sombra.cms.messages.UserMessage.ADMIN_CANT_DO_THIS_ACTION;

@Service
@RequiredArgsConstructor
public class UserAccessService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;
    private final InstructorRepository instructorRepository;
    private final StudentRepository studentRepository;
    private final StudentLessonRepository studentLessonRepository;
    private final StudentCourseRepository studentCourseRepository;

    public void checkAccess(AccessEntity accessEntity, Long requestedDataId) throws SomethingWentWrongException {
        Owners owners = defineOwners(accessEntity, requestedDataId);
        owners.checkAccess(SecurityUtil.getLoggedUser().orElseThrow(() -> new RuntimeException("Unable to define content owner")));
    }

    private Owners defineOwners(AccessEntity accessEntity, Long requestedDataId) throws SomethingWentWrongException {
        if (requestedDataId != null) {
            switch (accessEntity) {
                case USER:
                    return userRepository.findById(requestedDataId).orElseThrow();
                case INSTRUCTOR:
                    return instructorRepository.findByUserId(requestedDataId).orElseThrow();
                case STUDENT:
                    return studentRepository.findById(requestedDataId).orElseThrow();
                case LESSON:
                    return lessonRepository.findById(requestedDataId).orElseThrow();
                case COURSE:
                    return courseRepository.findById(requestedDataId).orElseThrow();
                default:
                    return loggedUserOwning(accessEntity, requestedDataId);
            }
        }

        throw new RuntimeException("Unable to define content owner");
    }

    private Owners loggedUserOwning(AccessEntity accessEntity, Long requestedDataId) throws SomethingWentWrongException {
        UserDetailsImpl userDetails = SecurityUtil.getLoggedUser().filter(u -> !u.isAdmin()).orElseThrow(ADMIN_CANT_DO_THIS_ACTION::ofException);

        switch (accessEntity) {
            case STUDENT_LESSON:
                return studentLessonRepository.findStudentLessonByStudentUserIdAndLessonId(userDetails.getId(), requestedDataId).orElseThrow();
            case STUDENT_COURSE:
                return studentCourseRepository.findStudentCourseByStudentUserIdAndCourseId(SecurityUtil.getLoggedUserId(), requestedDataId).orElseThrow();
        }

        throw new RuntimeException("Unable to define content owner");
    }

    public enum AccessEntity {
        COURSE, LESSON, STUDENT, INSTRUCTOR, STUDENT_LESSON, STUDENT_COURSE, USER
    }

}
