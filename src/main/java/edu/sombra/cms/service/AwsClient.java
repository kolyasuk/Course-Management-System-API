package edu.sombra.cms.service;

import com.amazonaws.SdkClientException;
import edu.sombra.cms.messages.SomethingWentWrongException;

import java.io.File;

public interface AwsClient {

    void upload(final String bucketName, final String key, final File file) throws SdkClientException;

    byte[] get(final String bucketName, final String key) throws SomethingWentWrongException;

}
