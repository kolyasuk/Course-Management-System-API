package edu.sombra.cms.domain.dto;

import edu.sombra.cms.domain.entity.S3File;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FileDTO {
    private String fileKey;
    private String filename;

    public static FileDTO of(S3File s3File){
        return new FileDTO(s3File.getFileKey(), s3File.getFilename());
    }
}
