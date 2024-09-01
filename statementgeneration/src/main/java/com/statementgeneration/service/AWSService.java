package com.statementgeneration.service;


import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.statementgeneration.exceptions.FileNotFoundInS3;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;


@Service
public class AWSService {
	
	  private final S3Client s3Client;
	    private final String bucketName;

	    //Constructor for initialization
	    public AWSService(@Value("${aws.accessKeyId}") String accessKeyId,
	                      @Value("${aws.secretAccessKey}") String secretAccessKey,
	                      @Value("${aws.s3.bucket}") String bucketName) {
	        this.bucketName = bucketName;
	        this.s3Client = S3Client.builder()
	                .region(Region.EU_NORTH_1)
	                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKeyId, secretAccessKey)))
	                .build();
	    }
	    
	    //UPLOAD FILE AFTER CREATION
	    public String uploadFile(String filePath, String fileName) {
	        File file = new File(filePath);
	        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
	                .bucket(bucketName)
	                .key(fileName)
	                .build();

	        s3Client.putObject(putObjectRequest, Paths.get(filePath));
	        return s3Client.utilities().getUrl(b -> b.bucket(bucketName).key(fileName)).toExternalForm();
	    }
	    
	    //DOWNLOAD FILE FROM AWS S3
	    public String downloadFileToLocal(String pathToDownload, String key) throws IOException {
	    	System.out.println(pathToDownload+ "\\" + key);
	        String localFilePath = pathToDownload+ "\\" + key;
	        System.out.println(localFilePath);
	        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
	                .bucket(bucketName)
	                .key(key)
	                .build();
	        ResponseInputStream<GetObjectResponse> s3Object = s3Client.getObject(getObjectRequest);
	        if(s3Object==null) {
	        	throw new FileNotFoundInS3("File not Found in cloud Storage", HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	        FileUtils.copyInputStreamToFile(s3Object, new File(localFilePath));
	        return "SUCCESS";
	    }
	    
	    
	    //CHECKING FILE EXISTANCE
	    
	    public boolean checkKeyExists(String key) {
	    	System.out.println(key);
	    	
	        ListObjectsV2Request listObjectsV2Request = ListObjectsV2Request.builder()
	                .bucket(bucketName)
	                .prefix(key)
	                .maxKeys(1) // We only need to check for one key
	                .build();

	        ListObjectsV2Response listObjectsV2Response = s3Client.listObjectsV2(listObjectsV2Request);
	        boolean keyExists = listObjectsV2Response.contents().stream()
	                .anyMatch(s3Object -> s3Object.key().equals(key));
	       
	        listObjectsV2Response.contents().stream().forEach(System.out::print);

	        if (keyExists) {
	            System.out.println("It's there");
	            return true;
	        }
	            System.out.println("It's not there");
	            return false;
	    }

}
