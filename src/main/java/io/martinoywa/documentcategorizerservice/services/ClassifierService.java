package io.martinoywa.documentcategorizerservice.services;

import io.martinoywa.documentcategorizerservice.entities.Text;
import io.martinoywa.documentcategorizerservice.entry.requests.TestInputRequest;
import io.martinoywa.documentcategorizerservice.entry.requests.TrainInputRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface ClassifierService {
    public String train(String trainFile, String languageCode) throws IOException;
    public Text getClass(TestInputRequest testInputRequest) throws IOException;
}
