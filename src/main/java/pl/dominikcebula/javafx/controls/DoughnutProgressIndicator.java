package pl.dominikcebula.javafx.controls;

import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.scene.canvas.*;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;

public class DoughnutProgressIndicator extends StackPane
{
   private static final int PREF_WIDTH = 200;
   private static final int PREF_HEIGHT = 200;

   private static final int MAX_PROGRESS_IN_ANGLES = 360;
   private static final int MAX_PROGRESS_IN_PERCENTAGE = 100;
   private static final double THICK_RATIO = 1.2;

   private IntegerProperty progress = new SimpleIntegerProperty();
   private StringProperty unit = new SimpleStringProperty("%");
   private StringProperty title = new SimpleStringProperty();

   public DoughnutProgressIndicator()
   {
      setPrefSize(PREF_WIDTH, PREF_HEIGHT);

      getStylesheets().add(DoughnutProgressIndicator.class.getResource("doughnut-progress-indicator.css").toExternalForm());

      progress.addListener(this::invalidated);
   }

   @SuppressWarnings("unused")
   public int getProgress()
   {
      return progress.get();
   }

   @SuppressWarnings("unused")
   public IntegerProperty progressProperty()
   {
      return progress;
   }

   @SuppressWarnings("unused")
   public void setProgress(int progress)
   {
      this.progress.set(progress);
   }

   @SuppressWarnings("unused")
   public String getTitle()
   {
      return title.get();
   }

   @SuppressWarnings("unused")
   public StringProperty titleProperty()
   {
      return title;
   }

   @SuppressWarnings("unused")
   public void setTitle(String title)
   {
      this.title.set(title);
   }

   @SuppressWarnings("unused")
   public String getUnit()
   {
      return unit.get();
   }

   @SuppressWarnings("unused")
   public StringProperty unitProperty()
   {
      return unit;
   }

   @SuppressWarnings("unused")
   public void setUnit(String unit)
   {
      this.unit.set(unit);
   }

   private void invalidated(Observable observable)
   {
      layoutChildren();
   }

   @Override
   protected void layoutChildren()
   {
      getChildren().clear();

      Canvas canvas = drawDoughnutProgressIndicatorOnCanvas();
      Label progressLabel = getProgressLabel();
      Label titleLabel = getTitleLabel();

      getChildren().addAll(canvas, progressLabel, titleLabel);

      super.layoutChildren();
   }

   private Canvas drawDoughnutProgressIndicatorOnCanvas()
   {
      Canvas canvas = new Canvas(PREF_WIDTH, PREF_HEIGHT);
      GraphicsContext gc = canvas.getGraphicsContext2D();

      drawProgressDone(gc);
      drawProgressLeft(gc);
      drawProgressBackground(gc);

      return canvas;
   }

   private void drawProgressDone(GraphicsContext gc)
   {
      gc.setFill(getProgressDoneColor());
      gc.fillArc(
         0, 0,
         gc.getCanvas().getWidth(),
         gc.getCanvas().getHeight(),
         0, MAX_PROGRESS_IN_ANGLES,
         ArcType.ROUND
      );
   }

   private void drawProgressLeft(GraphicsContext gc)
   {
      gc.setFill(getProgressLeftColor());
      gc.fillArc(
         0, 0,
         gc.getCanvas().getWidth(),
         gc.getCanvas().getHeight(),
         90, calculateProgressLeftInAngles(),
         ArcType.ROUND
      );
   }

   private void drawProgressBackground(GraphicsContext gc)
   {
      double ovalWidth = gc.getCanvas().getWidth() / THICK_RATIO;
      double ovalHeight = gc.getCanvas().getHeight() / THICK_RATIO;

      gc.setFill(getProgressBackgroundColor());
      gc.fillOval(
         gc.getCanvas().getWidth() / 2 - ovalWidth / 2,
         gc.getCanvas().getHeight() / 2 - ovalHeight / 2,
         ovalWidth,
         ovalHeight
      );
   }

   private int calculateProgressLeftInAngles()
   {
      return (MAX_PROGRESS_IN_PERCENTAGE - progress.get()) * MAX_PROGRESS_IN_ANGLES / 100;
   }

   private Label getProgressLabel()
   {
      Label label = new Label(String.format("%d%s", progress.get(), unit.get()));
      label.setId("progress");
      return label;
   }

   private Label getTitleLabel()
   {
      Label titleLabel = new Label(title.get());
      titleLabel.setId("title");
      return titleLabel;
   }

   private Paint getProgressDoneColor()
   {
      return getArcBackgroundColor("progress-done");
   }

   private Paint getProgressLeftColor()
   {
      return getArcBackgroundColor("progress-left");
   }

   private Paint getProgressBackgroundColor()
   {
      return getArcBackgroundColor("background");
   }

   private Paint getArcBackgroundColor(String id)
   {
      Arc backgroundArc = new Arc();
      backgroundArc.setId(id);
      getChildren().add(backgroundArc);
      return backgroundArc.getFill();
   }
}
