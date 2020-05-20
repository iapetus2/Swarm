package start.window;

import app.App;
import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MenuApp {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 720;

    private Pane gamePane;
    private Stage stage;
    private App app;

    private List<Pair<String, Runnable>> menuData = Arrays.asList(
            new Pair<String, Runnable>("Start Game!", () -> {
                app.startGame();
            }),
            new Pair<String, Runnable>("Options", () -> {}),
            new Pair<String, Runnable>("Credits", () -> {

            }),
            new Pair<String, Runnable>("Exit to Desktop", Platform::exit)
    );

    public MenuApp(Pane pane) {
        gamePane = pane;
    }

    public MenuApp(Stage stage) {
        this.stage = stage;
    }

    public MenuApp(App app) {
        this.app = app;
    }

    public MenuApp(Stage stage, Pane pane) {
        this.stage = stage;
        gamePane = pane;
    }


    private Pane root = new Pane();
    private VBox menuBox = new VBox(-5);
    private Line line;

    public Pane createContent() {
        addBackground();
        addTitle();

        double lineX = WIDTH / 2 - 100;
        double lineY = HEIGHT / 3 + 50;

        addLine(lineX, lineY);
        addMenu(lineX + 5, lineY + 5);

        startAnimation();

        return root;
    }

    private void addBackground() {
        String url = "https://i.pinimg.com/originals/4a/33/25/4a33254a98b67b266c31c2f75fef2c47.jpg";
        Image img = new Image(url);
        ImageView imageView = new ImageView(img);
        imageView.setFitWidth(WIDTH);
        imageView.setFitHeight(HEIGHT);

        root.getChildren().add(imageView);
    }

    private void addTitle() {
        MenuItemTitle title = new MenuItemTitle("Swarm");
        title.setTranslateX(WIDTH / 2 - title.getTitleWidth() / 2);
        title.setTranslateY(HEIGHT / 3);

        root.getChildren().add(title);
    }

    private void addLine(double x, double y) {
        line = new Line(x, y, x, y + 170);
        line.setStrokeWidth(3);
        line.setStroke(Color.color(1, 1, 1, 0.75));
        line.setEffect(new DropShadow(5, Color.BLACK));
        line.setScaleY(0);

        root.getChildren().add(line);
    }

    private void startAnimation() {
        ScaleTransition st = new ScaleTransition(Duration.seconds(1), line);
        st.setToY(1);
        st.setOnFinished(e -> {

            for (int i = 0; i < menuBox.getChildren().size(); i++) {
                Node n = menuBox.getChildren().get(i);

                TranslateTransition tt = new TranslateTransition(Duration.seconds(1 + i * 0.15), n);
                tt.setToX(0);
                tt.setOnFinished(e2 -> n.setClip(null));
                tt.play();
            }
        });
        st.play();
    }

    private void addMenu(double x, double y) {
        menuBox.setTranslateX(x);
        menuBox.setTranslateY(y);
        menuData.forEach(data -> {
            MenuItem item = new MenuItem(data.getKey());
            item.setOnAction(data.getValue());
            item.setTranslateX(-300);

            Rectangle clip = new Rectangle(300, 30);
            clip.translateXProperty().bind(item.translateXProperty().negate());

            item.setClip(clip);

            menuBox.getChildren().addAll(item);
        });

        root.getChildren().add(menuBox);
    }

}