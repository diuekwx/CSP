package yeri.csphub.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import yeri.csphub.Payload.Response.JwtResponse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class UploadService {

    @Value("${supabase.project}")
    private String projectUrl;

    @Value("${supabase.bucket}")
    private String bucket;

    @Value("${supabase.service_role_key}")
    private String serviceRoleKey;

    private final HttpClient client = HttpClient.newHttpClient();

    public void upload(MultipartFile file, String path) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(projectUrl + "/storage/v1/object/" + bucket + "/" + path))
                .header("Authorization", "Bearer " + serviceRoleKey)
                .header("Content-Type", file.getContentType())
                .PUT(HttpRequest.BodyPublishers.ofByteArray(file.getBytes()))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() >= 400){
            throw new RuntimeException("Upload failed: " + response.body());
        }

    }

    public byte[] download(String path) throws IOException, InterruptedException{
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(projectUrl + "/storage/v1/object/" + bucket + "/" + path))
                .header("Authorization", "Bearer " + serviceRoleKey)
                .GET()
                .build();

        HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

        if (response.statusCode() >= 400){
            throw new RuntimeException(("Download failed: " + response.body()));
        }
        return response.body();
    }


}
