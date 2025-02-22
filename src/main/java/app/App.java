package app;

import ClassesOfCharacters.Bullet;
import ClassesOfCharacters.Commander;
import ClassesOfCharacters.Soldier;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;

import managers.ObjectManager;
import start.window.MenuApp;

import static javafx.scene.input.KeyCode.LEFT;

public class App extends Application {

    public final int width = 1400;
    public final int height = 720;

    private Pane root;
    private Pane startPane;
    private Commander commander;
    private Commander commander2;

    private ObjectManager objectManager = new ObjectManager(width, height);
    private AnimationTimer mainTimer;
    private MenuApp menuApp;
    private Stage stage;
    private boolean gameStatus;

    private ScrollPane createScrollPane(Pane layout) {
        ScrollPane scroll = new ScrollPane();
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setPannable(true);
        scroll.setPrefSize(width, height);
        scroll.setContent(layout);
        return scroll;
    }

    private void addBackground(double width, double height) {
        ImageView imageView = new ImageView(new Image("https://s3.amazonaws.com/colorslive/png/248954-swJua0a0wWnCiVLL.png"));
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);

        root.getChildren().add(imageView);
    }

    @Override
    public void init() throws Exception {
        super.init();

        root = new Pane();
        root.setPrefSize(width, height);

        commander = new Commander(Color.BLUE);
        commander.setVelocity(new Point2D(1, 0));
        commander2 = new Commander(Color.GREEN);
        commander2.setVelocity(new Point2D(-1, 0));
        commander2.getView().setRotate(180);

        objectManager.setPane(root);
        objectManager.addCommander(commander, 300, 300);
        objectManager.addCommander(commander2, 1200, 300);


        mainTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                objectManager.onUpdate();
            }
        };
    }

    public static boolean clickLeft = false;
    public static boolean clickRight = false;
    public static boolean shoot = false;

    public static boolean clickLeft2 = false;
    public static boolean clickRight2 = false;
    public static boolean shoot2 = false;
    public static boolean pause = false;


    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;

        menuApp = new MenuApp(this);
        startPane = menuApp.createContent();
        stage.setScene(new Scene(startPane));
        stage.setTitle("Swarm 0.6");
        stage.show();

    }

    public void startGame() {
        mainTimer.start();
        gameStatus = true;

        stage.setScene(new Scene(root));

        root.getScene().setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case LEFT: clickLeft = true; break;
                case RIGHT: clickRight = true; break;
                case SHIFT: shoot = true; break;
                case P: pause = !pause; break;

                case A: clickLeft2 = true; break;
                case D: clickRight2 = true; break;
                case SPACE: shoot2 = true; break;
            }
        });

        root.getScene().setOnKeyReleased(e -> {
            switch (e.getCode()) {
                case LEFT: clickLeft = false; break;
                case RIGHT: clickRight = false; break;
                case SHIFT: shoot = false; break;

                case A: clickLeft2 = false; break;
                case D: clickRight2 = false; break;
                case SPACE: shoot2 = false; break;
            }
        });
        stage.setMaximized(true);
        addBackground(root.getScene().getWidth(), root.getScene().getHeight());
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}