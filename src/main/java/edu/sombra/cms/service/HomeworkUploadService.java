package edu.sombra.cms.service;

import edu.sombra.cms.domain.entity.S3File;
import edu.sombra.cms.domain.entity.Student;
import edu.sombra.cms.messages.SomethingWentWrongException;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface HomeworkUploadService {

    Optional<S3File> uploadStudentHomework(Student student, MultipartFile homeworkFile);

    byte[] getStudentHomework(String fileKey) throws SomethingWentWrongException;

}
