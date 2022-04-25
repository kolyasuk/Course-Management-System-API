package edu.sombra.cms.service.impl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import edu.sombra.cms.messages.SomethingWentWrongException;
import edu.sombra.cms.service.AwsClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static edu.sombra.cms.messages.StudentLessonMessage.NO_FILE_FOUND_WITH_SUCH_KEY;

@Service
@RequiredArgsConstructor
public class AwsClientImpl implements AwsClient {

    @Value("${s3.homework.bucket.accessKey}")
    private String access;
    @Value("${s3.homework.bucket.secretKey}")
    private String s3Secret;

    private AmazonS3 s3client;

    @PostConstruct
    private void postConstruct() {
        AWSCredentials credentials = new BasicAWSCredentials(access, s3Secret);

        s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.EU_CENTRAL_1)
                .build();
    }

    private static final Logger LOGGER =  LoggerFactory.getLogger(AwsClientImpl.class);

    @Override
    public void upload(final String bucketName, final String key, final File file) throws SdkClientException {
        s3client.putObject(bucketName, key, file);
    }

    @Override
    public byte[] get(final String bucketName, final String key) throws SomethingWentWrongException {
        try {
            S3Object object = s3client.getObject(bucketName, key);

            return IOUtils.toByteArray(object.getObjectContent());
        } catch (AmazonServiceException e) {
            LOGGER.error("Unable to get file", e);
        } catch (FileNotFoundException e) {
            throw NO_FILE_FOUND_WITH_SUCH_KEY.ofException();
        } catch (IOException e) {
            LOGGER.error("Unable to process file", e);
        }

        throw NO_FILE_FOUND_WITH_SUCH_KEY.ofException();
    }

}
