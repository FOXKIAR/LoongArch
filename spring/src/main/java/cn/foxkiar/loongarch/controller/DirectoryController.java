package cn.foxkiar.loongarch.controller;

import cn.foxkiar.loongarch.util.Result;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/directory")
public class DirectoryController {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FileInfo {
        private String name;
        private Boolean isDirectory;
        private Long size;
    }

    // 先使用 wsl 驱动器测试
    @GetMapping
    public ResponseEntity<Result<List<FileInfo>>> directory(String path) {
        File[] files = new File("//wsl.localhost/kali-linux/" + path).listFiles();
        if (files == null)
            return ResponseEntity.noContent().build();
        List<FileInfo> list = Arrays.stream(files).map(file -> {
            FileInfo result = new FileInfo();
            result.setName(file.getName());
            result.setIsDirectory(file.isDirectory());
            result.setSize(file.isDirectory() ? null : file.length());
            return result;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(Result.success(list));
    }

    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> download(String path) throws IOException {
        FileSystemResource file = new FileSystemResource("//wsl.localhost/kali-linux/" + path);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getFilename()));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(file.getInputStream()));
    }
}