package io.martinoywa.documentcategorizerservice.services;

import io.martinoywa.documentcategorizerservice.entities.Text;
import io.martinoywa.documentcategorizerservice.entry.requests.TestInputRequest;
import io.martinoywa.documentcategorizerservice.entry.requests.TrainInputRequest;
import opennlp.tools.doccat.*;
import opennlp.tools.util.MarkableFileInputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Service
public class ClassifierServiceImp implements ClassifierService {
    public String train(String trainFile, String languageCode) throws IOException {
        DoccatModel model = null;
        try {
            ObjectStream<String> lineStream = new PlainTextByLineStream(new MarkableFileInputStreamFactory(new File("data/en-alot-of-things.train")), StandardCharsets.UTF_8);
            ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);
            model = DocumentCategorizerME.train(languageCode, sampleStream, TrainingParameters.defaultParams(), new DoccatFactory());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // save model
        try (OutputStream modelOut = new BufferedOutputStream(new FileOutputStream("models/model.bin"))) {
            model.serialize(modelOut);
            return "TRAINING COMPLETE";
        }
    }

    public Text getClass(TestInputRequest testInputRequest) throws IOException {
        Text text = new Text();

        InputStream inputStream = new FileInputStream("models/model.bin");
        DoccatModel model = new DoccatModel(inputStream);
        DocumentCategorizerME documentCategorizerME = new DocumentCategorizerME(model);
        double[] outcomes = documentCategorizerME.categorize(testInputRequest.getInputText());

        text.setText(testInputRequest.getInputText()[0]);
        text.setLabel(documentCategorizerME.getBestCategory(outcomes));
        text.setConfidence(documentCategorizerME.getAllResults(outcomes));
        return text;
    }
}
