package pl.dominikcebula.javafx.controls;

import javafx.application.Application;
import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class DoughnutProgressIndicatorExample extends Application
{
   @FXML
   private Slider slider1;
   @FXML
   private DoughnutProgressIndicator doughnutProgressIndicator1;

   @FXML
   private Slider slider2;
   @FXML
   private DoughnutProgressIndicator doughnutProgressIndicator2;

   @FXML
   private Slider slider3;
   @FXML
   private DoughnutProgressIndicator doughnutProgressIndicator3;

   public static void main(String[] args)
   {
      Application.launch(DoughnutProgressIndicatorExample.class, args);
   }

   public void start(Stage primaryStage) throws Exception
   {
      primaryStage.setScene(
         new Scene(
            FXMLLoader.<BorderPane>load(
               DoughnutProgressIndicatorExample.class.getResource("doughnut-progress-indicator-example.fxml")
            )
         )
      );

      primaryStage.setTitle("Doughnut Progress Indicator Example");
      primaryStage.show();
   }

   @SuppressWarnings("unsed")
   public void initialize()
   {
      doughnutProgressIndicator1.progressProperty().bind(slider1.valueProperty());
      doughnutProgressIndicator2.progressProperty().bind(slider2.valueProperty());
      doughnutProgressIndicator3.progressProperty().bind(slider3.valueProperty());
   }
}
