package cn.foxkiar.support.controller;

import cn.foxkiar.support.entity.Result;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/files")
public class FilesController {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FileInfo {
        private String fileName;
        private Boolean notDirectory;
        private Long size;
        private String absolutePath;
    }

    @GetMapping
    public ResponseEntity<Result> getFileInfoList(String path) {
        File[] files = new File(path).listFiles();
        if (files == null)
            return ResponseEntity.noContent().build();
        List<FileInfo> list = Arrays.stream(files).map(file -> {
            FileInfo result = new FileInfo();
            result.setFileName(file.getName());
            result.setNotDirectory(!file.isDirectory());
            result.setSize(file.isDirectory() ? null : file.length());
            result.setAbsolutePath(file.getAbsolutePath());
            return result;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(Result.success(list));
    }
}