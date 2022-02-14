package edu.sombra.cms.service.impl;

import edu.sombra.cms.domain.entity.S3File;
import edu.sombra.cms.domain.entity.User;
import edu.sombra.cms.messages.SomethingWentWrongException;
import edu.sombra.cms.repository.S3FileRepository;
import edu.sombra.cms.service.S3FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static edu.sombra.cms.messages.StudentLessonMessage.NO_FILE_FOUND_WITH_SUCH_KEY;
import static edu.sombra.cms.util.FileUtil.getFileNameWithoutExtension;

@Service
@RequiredArgsConstructor
public class S3FileServiceImpl implements S3FileService {

    private final S3FileRepository s3FileRepository;

    public S3File getByKey(String key) throws SomethingWentWrongException {
        return s3FileRepository.findByFileKey(key).orElseThrow(NO_FILE_FOUND_WITH_SUCH_KEY::ofException);
    }

    @Override
    public User getS3FileOwner(String key) throws SomethingWentWrongException {
        return getByKey(key).getUser();
    }

    @Override
    public S3File create(User user, String key, String filename) {
        S3File s3File = new S3File();
        s3File.setFileKey(key);
        s3File.setFilename(filename);
        s3File.setUser(user);

        s3FileRepository.save(s3File);
        return s3File;
    }

    public static String generateUniqueFileKey(Long studentId, String fileName) {
        StringBuilder sb = new StringBuilder();
        sb.append(getFileNameWithoutExtension(fileName).toLowerCase());
        sb.append("-").append(studentId);
        sb.append(System.currentTimeMillis());

        return sb.toString();
    }
}
