package edu.sombra.cms.service.impl;

import com.amazonaws.SdkClientException;
import edu.sombra.cms.domain.entity.Instructor;
import edu.sombra.cms.domain.entity.S3File;
import edu.sombra.cms.domain.entity.Student;
import edu.sombra.cms.domain.entity.User;
import edu.sombra.cms.messages.SomethingWentWrongException;
import edu.sombra.cms.repository.InstructorRepository;
import edu.sombra.cms.service.AwsClient;
import edu.sombra.cms.service.HomeworkUploadService;
import edu.sombra.cms.service.S3FileService;
import edu.sombra.cms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static edu.sombra.cms.service.impl.S3FileServiceImpl.generateUniqueFileKey;
import static edu.sombra.cms.util.FileUtil.multipartToFile;

@Service
@RequiredArgsConstructor
public class HomeworkUploadServiceImpl implements HomeworkUploadService {

    private final AwsClient awsClient;
    private final S3FileService s3FileService;
    private final UserService userService;
    private final InstructorRepository instructorRepository;

    @Value("${s3.homework.bucket.name}")
    private String s3BucketName;

    private static final Logger LOGGER =  LoggerFactory.getLogger(HomeworkUploadServiceImpl.class);

    @Override
    public Optional<S3File> uploadStudentHomework(Student student, MultipartFile homeworkFile) {
        File file = null;

        try{
            file = multipartToFile(homeworkFile, homeworkFile.getOriginalFilename());
            String key = generateUniqueFileKey(student.getId(), file.getName());

            awsClient.upload(s3BucketName, key, file);

            S3File s3File = s3FileService.create(student.getUser(), key, file.getName());
            return Optional.of(s3File);
        }catch (IOException ex){
            LOGGER.error("Unable to process file", ex);
            return Optional.empty();
        }catch (SdkClientException ex){
            LOGGER.error("Unable to upload homework with for student with id: " + student.getId(), ex);
            return Optional.empty();
        }finally {
            if(file != null){
                file.delete();
            }
        }
    }

    @Override
    public byte[] getStudentHomework(String fileKey) throws SomethingWentWrongException {
        List<User> usersWithAccessToFile = getUsersWithAccessToFile(fileKey);

        userService.loggedUserHasAccess(usersWithAccessToFile);

        return awsClient.get(s3BucketName, fileKey);
    }

    private List<User> getUsersWithAccessToFile(String fileKey) throws SomethingWentWrongException {
        List<User> usersWithAccessToFile = new ArrayList<>();
        var s3File = s3FileService.getByKey(fileKey);

        usersWithAccessToFile.add(s3File.getUser());
        usersWithAccessToFile.addAll(instructorRepository.findInstructorsByHomeworkFile(s3File).stream().map(Instructor::getUser).collect(Collectors.toList()));

        return usersWithAccessToFile;
    }

}
