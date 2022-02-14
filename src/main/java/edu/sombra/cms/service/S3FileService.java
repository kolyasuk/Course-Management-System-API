package edu.sombra.cms.service;

import edu.sombra.cms.domain.entity.S3File;
import edu.sombra.cms.domain.entity.User;
import edu.sombra.cms.messages.SomethingWentWrongException;

public interface S3FileService {

    S3File getByKey(String key) throws SomethingWentWrongException;

    User getS3FileOwner(String key) throws SomethingWentWrongException;

    S3File create(User user, String key, String filename);

}
