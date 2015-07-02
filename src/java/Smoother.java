import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import static org.bytedeco.javacpp.opencv_highgui.*;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;

public class Smoother {
    public static void smooth(String filename) throws FrameGrabber.Exception { 
        IplImage image = cvLoadImage(filename);
        if (image != null) {
            cvSmooth(image, image);
            cvSaveImage(filename, image);
            cvReleaseImage(image);
            
            FrameGrabber grabber = FrameGrabber.createDefault(0);
            grabber.start();
            
            OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
            IplImage grabbedImage = converter.convert(grabber.grab());
            cvSaveImage("C:\\Users\\Paradox\\Documents\\NetBeansProjects\\Servlet_jCV1\\Image\\out.jpg", grabbedImage);
            cvReleaseImage(grabbedImage);
            grabber.stop();
            
            } // end of display()

        }
    }