package net.dreamparadise.oss.service.impl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.auth.policy.Policy;
import com.amazonaws.auth.policy.Principal;
import com.amazonaws.auth.policy.Resource;
import com.amazonaws.auth.policy.Statement;
import com.amazonaws.auth.policy.actions.S3Actions;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import net.dreamparadise.common.exception.BusinessException;
import net.dreamparadise.common.result.ResponseEnum;
import net.dreamparadise.oss.service.FileService;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String upload(InputStream inputStream, String module, String fileName) {
        try {
            Regions clientRegion = Regions.US_EAST_1;
            String bucketName = "srbbucket";
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(clientRegion)
                    .withCredentials(new ClasspathPropertiesFileCredentialsProvider())
                    .build();
            if (!s3Client.doesBucketExistV2(bucketName)){
                s3Client.createBucket(bucketName);
                Policy bucket_policy= new Policy().withStatements(
                        new Statement(Statement.Effect.Allow)
                        .withPrincipals(Principal.AllUsers)
                        .withActions(S3Actions.GetObject)
                        .withResources(new Resource("arn:aws:s3:::" + bucketName + "/*")));
                String s = bucket_policy.toJson();
                s3Client.setBucketPolicy(bucketName,s);

            }
            ObjectMetadata objectMetadata = new ObjectMetadata();
            String timeFolder = new DateTime().toString("/yyyy/MM/dd/");
            fileName = UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf("."));
            String key = module + timeFolder + fileName;
            PutObjectRequest request = new PutObjectRequest(bucketName,key,inputStream,objectMetadata);
            PutObjectResult putObjectResult = s3Client.putObject(request);
            s3Client.shutdown();
            return "https://"+bucketName+".s3.amazonaws.com/"+key;
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            throw new BusinessException("aws处理异常",-103,e);
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            throw new BusinessException("aws响应异常",-103,e);
        }
        catch (Exception e){
            throw new BusinessException(ResponseEnum.UPLOAD_ERROR);
        }
    }

    @Override
    public void removeFile(String url) {
        try {
            Regions clientRegion = Regions.US_EAST_1;
            String bucketName = "srbbucket";
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(clientRegion)
                    .withCredentials(new ClasspathPropertiesFileCredentialsProvider())
                    .build();
            String host = "https://"+bucketName+".s3.amazonaws.com/";
            String key = url.substring(host.length());
            s3Client.deleteObject(new DeleteObjectRequest(bucketName,key));
            s3Client.shutdown();
        }catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            throw new BusinessException("aws处理异常",-103,e);
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            throw new BusinessException("aws响应异常",-103,e);
        }
        catch (Exception e){
            throw new BusinessException("删除文件异常",-105,e);
        }
    }
}
