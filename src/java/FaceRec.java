import java.io.File;
import java.io.FilenameFilter;
import java.nio.IntBuffer;
import static org.bytedeco.javacpp.opencv_contrib.*;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_highgui.*;

/* FaceRecognition using javacv
 * 
 * Using FaceRecognizer class..
 * It takes path to the directory containing the training
 * faces and the path to the image you want to classify.
 *
 * @author Paradox
 */
public class FaceRec {
    
    public static void main(String[] args) {
        
        String trainingDir = "C:\\Users\\Paradox\\Documents\\NetBeansProjects\\Servlet_jCV1\\Image\\train";
        Mat testImage = imread(
                            "C:\\Users\\Paradox\\Documents\\NetBeansProjects\\Servlet_jCV1\\Image\\tests.jpg",
                             CV_LOAD_IMAGE_GRAYSCALE);
        File root = new File(trainingDir);

        FilenameFilter imgFilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                name = name.toLowerCase();
                return name.endsWith(".jpg") || name.endsWith(".pgm") || name.endsWith(".png");
            }
        };

        File[] imageFiles = root.listFiles(imgFilter);

        MatVector images = new MatVector(imageFiles.length);

        Mat labels = new Mat(imageFiles.length, 1, CV_32SC1);
        IntBuffer labelsBuf = labels.getIntBuffer();

        int counter = 0;

        for (File image : imageFiles) {
            Mat img = imread(image.getAbsolutePath(), CV_LOAD_IMAGE_GRAYSCALE);

            int label = Integer.parseInt(image.getName().split("\\-")[0]);

            images.put(counter, img);

            labelsBuf.put(counter, label);

            counter++;
        }

        FaceRecognizer faceRecognizer = createFisherFaceRecognizer();
        // FaceRecognizer faceRecognizer = createEigenFaceRecognizer();
        // FaceRecognizer faceRecognizer = createLBPHFaceRecognizer()

        try
        {
            faceRecognizer.train(images, labels);
            int predictedLabel = faceRecognizer.predict(testImage);
            System.out.println("Predicted label: " + predictedLabel);
        }
        catch(Exception e)
        {
            System.out.println("Error --( " +e+" )");
        }
    }
}
