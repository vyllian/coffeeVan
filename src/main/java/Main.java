
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Objects;

public class Main extends Application {
   public static Logger logger = LogManager.getLogger(Main.class.getName());

   public static void main(String[] args) {
      launch(args);
      logger.info("******Запуск програми******");
   }

   @Override
   public void start(Stage stage)  {
      try {
          Image icon = new Image("C:\\Users\\Lenovo\\IdeaProjects\\pp_course_work\\src\\main\\resources\\images\\icon.JPEG");
         Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("scene0.fxml")));
         Scene scene = new Scene(root);

         stage.setTitle("CAFFINE Transporting");
         stage.getIcons().add(icon);
         stage.setResizable(false);
         stage.setScene(scene);
         stage.show();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}
