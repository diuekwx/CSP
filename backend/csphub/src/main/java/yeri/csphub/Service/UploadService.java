package yeri.csphub.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import yeri.csphub.Entities.Artworks;
import yeri.csphub.Payload.Response.JwtResponse;
import yeri.csphub.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UploadService {

    @Value("${supabase.project}")
    private String projectUrl;

    @Value("${supabase.bucket}")
    private String bucket;

    @Value("${supabase.service_role_key}")
    private String serviceRoleKey;

    private final HttpClient client = HttpClient.newHttpClient();

    public String generateSignedUrl(String path, int expiresInSeconds) {
        String url = projectUrl + "/storage/v1/object/sign/" + bucket + "/" + path;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + serviceRoleKey);

        Map<String, Object> body = new HashMap<>();
        body.put("expiresIn", expiresInSeconds);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            String signedPath = (String) response.getBody().get("signedURL");
            return projectUrl + "/storage/v1" + signedPath;
        }

        throw new RuntimeException("Failed to generate signed URL from Supabase.");
    }



    public String upload(MultipartFile file, String path) throws IOException, InterruptedException {

        try {
            String encodedPath = path
                    .replace(" ", "%20")
                    .replace("(", "%28")
                    .replace(")", "%29");
            String storagePath = projectUrl + "/storage/v1/object/" + bucket + "/" + encodedPath;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(storagePath))
                    .header("Authorization", "Bearer " + serviceRoleKey)
                    .header("Content-Type", file.getContentType())
                    .PUT(HttpRequest.BodyPublishers.ofByteArray(file.getBytes()))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() >= 400) {
                throw new RuntimeException("Upload failed: " + response.body());
            }

            return storagePath;

        }
        catch (Exception e){
            throw new RuntimeException("Upload failed: " + e);
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
