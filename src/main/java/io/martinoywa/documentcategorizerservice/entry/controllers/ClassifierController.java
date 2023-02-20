package io.martinoywa.documentcategorizerservice.entry.controllers;

import io.martinoywa.documentcategorizerservice.entities.Text;
import io.martinoywa.documentcategorizerservice.entry.requests.TestInputRequest;
import io.martinoywa.documentcategorizerservice.entry.requests.TrainInputRequest;
import io.martinoywa.documentcategorizerservice.services.ClassifierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
public class ClassifierController {

    @Autowired
    ClassifierService classifierService;

    @GetMapping("/classify")
    public Text getClass(@RequestBody TestInputRequest testInputRequest) throws IOException {
        return classifierService.getClass(testInputRequest);
    }

    @GetMapping("/train")
    public String trainModel(@RequestBody String trainFile) throws IOException {
        return classifierService.train(trainFile, "eng");
    }
}
