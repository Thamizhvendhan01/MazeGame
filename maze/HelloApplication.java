package com.example.maze;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Objects;

public class HelloApplication extends Application {

    public  int currentX = 0;
    public  int currentY = 0;
    public  Pane basePane = new Pane();
    public  Scene scene = new Scene(basePane);
    public  Things[][] board;
    public  boolean pressed = false;
    private static ImageView player;
    public  GridPane maze;
    int row = 20;
    int col = 20;
    private ImageView[][] stones = new ImageView[row][col];
    public int totalEnd = 0;
    public int completed = 0;
    public Pane ground = new Pane();
    public StackPane stackPane = new StackPane();
    public int[][] ends = new int[row][col];
    public double SCREEN_WIDTH = Screen.getPrimary().getVisualBounds().getWidth();
    public double SCREEN_HEIGHT = Screen.getPrimary().getVisualBounds().getHeight();

    double cell = Math.min(SCREEN_HEIGHT,SCREEN_WIDTH)/Math.min(row,col);
    @Override
    public void start(Stage stage) {


        Image playerImage = new Image(Objects.requireNonNull(getClass().getResource("player_transparent.png").toExternalForm()));
        player = new ImageView(playerImage);
        player.setFitHeight(cell);
        player.setPreserveRatio(true);


        board = new Things[row][col];
//
//        board[4][2] = Things.PATH;
//        board[4][1] = Things.PATH;
//
//        board[4][4] = Things.PATH;
//        board[3][4] = Things.PATH;
//        board[2][4] = Things.PATH;
//        board[0][4] = Things.PATH;
//        board[1][3] = Things.PATH;
//        board[1][2] = Things.PATH;
//        board[1][1] = Things.STONE;
//        board[0][1] = Things.PATH;
//        board[3][0] = Things.PATH;
//        board[4][0] = Things.PATH;
//        board[2][0] = Things.PATH;
//        board[0][0] = Things.PATH;
//        board[1][0] = Things.PATH;
//        board[0][2] = Things.WALL;
//        board[0][3] = Things.WALL;
//        board[2][1] = Things.WALL;
//        board[3][1] = Things.WALL;
//        board[2][3] = Things.WALL;
//        board[3][3] = Things.WALL;
//        board[4][3] = Things.WALL;
//        board[2][2] = Things.PATH;
//        board[1][4] = Things.PATH;
//        board[1][3] = Things.END;
//        board[3][2] = Things.PATH;
//        int[][] blocks = new int[][]{{1,2},{1,3},{1,4},{1,5},{1,6},{0,6},{1,9},{2,9},{3,9},{4,9},{7,9},{7,9},{7,6},{9,6},{9,2},{8,2},{7,2},{7,1},{3,1},{4,1},{4,2},{5,2},{4,3},{3,3},{4,4},{3,5},{4,5},{5,5},{6,5},{7,6},{4,7}};
//        for (int i = 0; i < row; i++) {
//            for (int j = 0; j < col; j++) {
//                board[i][j] = Things.PATH;
//            }
//        }
//        for(int[] arr : blocks){
//            board[arr[0]][arr[1]]=Things.WALL;
//        }
//        board[9][0] = Things.END;
//        board[8][9] = Things.END;
//        board[0][9] = Things.END;
//
//        board[2][5] = Things.STONE;
//        board[6][4] = Things.STONE;
//        board[5][7] = Things.STONE;

        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                board[i][j] = Things.PATH;
            }
        }
        int[][] blocks = new int[][]{{1,0},{1,2},{1,5},{1,7},{1,18},{0,15},{2,0},
                {2,3},{2,9},{2,12},{2,18},{3,0},{3,2},{3,3},{3,4},{3,5},{3,6},
                {3,7},{3,9},{3,12},{3,13},{3,14},{3,15},{3,16},{3,17},{3,18},
                {4,12},{5,1},{5,6},{5,7},{5,9},{5,12},{5,13},{5,14},{6,2},{6,4},
                {6,9},{6,19},{7,1},{7,3},{7,7},{7,8},{7,9},{7,10},{7,11},{7,13},{7,14},
                {7,17},{7,18},{8,2},{8,5},{8,9},{8,14},{8,16},{9,6},{9,9},{9,12},{10,4},
                {10,9},{10,18},{11,5},{11,8},{12,1},{12,7},{12,10},{12,13},{13,3},{14,0},
                {14,5},{15,5},{15,8},{15,10},{15,14},{15,15},{16,1},{16,2},{16,4},{16,5},
                {16,8},{16,10},{16,11},{16,14},{16,16},{17,2},{17,5},{17,8},{17,11},{17,12},
                {17,14},{17,17},{18,7},{18,9},{18,18},{18,19},{19,10},{19,11},{19,13},{19,14},{4,9}};
        for(int[] arr : blocks){
            board[arr[0]][arr[1]]=Things.WALL;
        }
        board[3][11] = Things.STONE;
        board[3][1] = Things.STONE;
        board[13][1] = Things.STONE;
        board[13][13] = Things.STONE;
        board[0][19] = Things.END;
        board[10][19] = Things.END;
        board[8][8] = Things.END;
        board[19][19] = Things.END;

        maze = createGrid(board);



        scene.heightProperty().addListener((obs, oldHeight, newHeight) -> {
            maze.setPrefHeight(newHeight.doubleValue());
            stackPane.setPrefHeight(newHeight.doubleValue());
            ground.setPrefHeight(newHeight.doubleValue());
        });

        scene.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            maze.setPrefWidth(newWidth.doubleValue());
            stackPane.setPrefWidth(newWidth.doubleValue());
            ground.setPrefWidth(newWidth.doubleValue());
        });

        player.setLayoutX(0);
        player.setLayoutY(0);

        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                if(board[i][j]==Things.STONE){
                    Image image = new Image(Objects.requireNonNull(getClass().getResource("stone.png").toExternalForm()));
                    ImageView st = new ImageView(image);
                    stones[i][j] = st;
                    st.setFitHeight(cell);
                    st.setPreserveRatio(true);
                    st.setLayoutX(j*cell);
                    st.setLayoutY(i*cell);
                    ground.getChildren().add(st);
                }
            }
        }

        ground.getChildren().addAll(player);
        stackPane.getChildren().addAll(maze,ground);
        basePane.getChildren().add(stackPane);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setMaximized(true);
        stage.setTitle("Maze with Images");
        stage.show();
        setPlayerLocation(maze);
    }


    public GridPane createGrid(Things[][] inputGrid){
        GridPane gridPane = new GridPane();
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                if(inputGrid[i][j]==Things.WALL){
                    Image image = new Image(Objects.requireNonNull(getClass().getResource("wall.png").toExternalForm()));
                    ImageView imageView = new ImageView(image);
                    imageView.setFitHeight(cell);
                    imageView.setPreserveRatio(true);
                    gridPane.add(imageView,j,i);
                }else if(inputGrid[i][j]==Things.PATH || inputGrid[i][j]==Things.STONE){
                    Image image = new Image(Objects.requireNonNull(getClass().getResource("path.png").toExternalForm()));
                    ImageView imageView = new ImageView(image);
                    imageView.setFitHeight(cell);
                    imageView.setPreserveRatio(true);
                    gridPane.add(imageView,j,i);
                }else if(inputGrid[i][j]==Things.END) {
                    Image image = new Image(Objects.requireNonNull(getClass().getResource("End.png").toExternalForm()));
                    ImageView imageView = new ImageView(image);
                    imageView.setFitHeight(cell);
                    imageView.setPreserveRatio(true);
                    gridPane.add(imageView, j, i);
                    ends[i][j] = -1;
                    totalEnd++;
                }
            }
        }

        return gridPane;
    }


    public static void main(String[] args) {
        launch();
    }


    public void setPlayerLocation(GridPane maze){
        scene.addEventHandler(KeyEvent.KEY_PRESSED, action ->{
            if(!pressed){
            switch (action.getCode()) {
                case DOWN -> {
                    move(currentX+1,currentY,"DOWN",currentX+2,currentY);
                    System.out.println("total : " + totalEnd + "Completed " + completed);
                }
                case UP -> {
                    move(currentX-1,currentY,"UP",currentX-2,currentY);
                }
                case LEFT -> {
                    move(currentX,currentY-1,"LEFT",currentX,currentY-2);
                }
                case RIGHT -> {
                    move(currentX,currentY+1,"RIGHT",currentX,currentY+2);
                }
            }
            }
        });
    }



    public void move(int x,int y,String direction,int nextToStoneX,int nextToStoneY){
        KeyValue keyValueForPlayer = switch (direction) {
            case "UP" -> new KeyValue(player.translateYProperty(), player.getTranslateY() - cell, Interpolator.EASE_IN);
            case "DOWN" -> new KeyValue(player.translateYProperty(), player.getTranslateY() + cell, Interpolator.EASE_IN);
            case "LEFT" -> new KeyValue(player.translateXProperty(), player.getTranslateX() - cell, Interpolator.EASE_IN);
            default -> new KeyValue(player.translateXProperty(), player.getTranslateX() + cell, Interpolator.EASE_IN);
        };


        if(nextToStoneX>= 0 && nextToStoneX < board.length && nextToStoneY >= 0 && nextToStoneY < board.length && board[x][y]==Things.STONE){
            ImageView stoneIv = stones[x][y];
            if ((board[nextToStoneX][nextToStoneY] == Things.PATH || board[nextToStoneX][nextToStoneY]==Things.END)){

                KeyValue keyValueForStone = switch (direction) {
                    case "UP" -> new KeyValue(stoneIv.translateYProperty(), stoneIv.getTranslateY() - cell, Interpolator.EASE_IN);
                    case "DOWN" -> new KeyValue(stoneIv.translateYProperty(), stoneIv.getTranslateY() + cell, Interpolator.EASE_IN);
                    case "LEFT" -> new KeyValue(stoneIv.translateXProperty(), stoneIv.getTranslateX() - cell, Interpolator.EASE_IN);
                    default -> new KeyValue(stoneIv.translateXProperty(), stoneIv.getTranslateX() + cell, Interpolator.EASE_IN);
                };

                KeyFrame keyFrame = new KeyFrame(Duration.millis(400), keyValueForStone);
                Timeline timeline = new Timeline();
                timeline.getKeyFrames().add(keyFrame);
                timeline.setOnFinished(e->{
                    pressed = false;
                });
                timeline.play();

                board[nextToStoneX][nextToStoneY] = Things.STONE;
                board[x][y] = Things.PATH;
                stones[nextToStoneX][nextToStoneY] = stoneIv;
                stones[x][y] = null;

            }
        }


        if (x >= 0 && x < board.length && y >= 0 && y < board.length && board[x][y] == Things.PATH && !pressed) {

            currentY = y;
            currentX = x;
            pressed = true;

            KeyFrame keyFrame = new KeyFrame(Duration.millis(400), keyValueForPlayer);
            Timeline timeline = new Timeline();
            timeline.getKeyFrames().add(keyFrame);
            timeline.setOnFinished(e->{
                pressed = false;
                check();
            });
            timeline.play();

        }



    }

    public void check(){
        for(int i=0;i<row;i++){
            for (int j=0;j<col;j++){
                if(stones[i][j]!=null && ends[i][j]==-1){
                    System.out.println("hello");
                    completed++;
                }
            }
        }
        if(completed!=totalEnd) completed=0;
        System.out.println(completed +" " + totalEnd);
        if(completed==totalEnd){
            GaussianBlur g = new GaussianBlur();
            g.setRadius(0);
            maze.setEffect(g);
            ground.setEffect(g);
            Timeline t = new Timeline(new KeyFrame(Duration.millis(600.0D), new KeyValue(stackPane.opacityProperty(), 1.0D)),
                    new KeyFrame(Duration.millis(600.0D), new KeyValue(g.radiusProperty(), 15.0D)));
            t.playFromStart();

            Pane endPane = new Pane();
            Label end = new Label("GAME OVER");
            end.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 70));
            end.setTextFill(Color.CRIMSON);
            end.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.6), 15, 0.8, 4, 4);");
            end.setAlignment(Pos.CENTER);

            HBox hBox = new HBox();
            hBox.setSpacing(40);
            hBox.setAlignment(Pos.CENTER);

            Button nextLevel = new Button("Next Level");
            Button playAgain = new Button("Play Again");
            Button exit = new Button("Exit");

            String buttonStyle = "-fx-background-color: linear-gradient(to bottom, #2196F3, #0D47A1); "
                    + "-fx-text-fill: #FFFFFF; "
                    + "-fx-font-size: 10px; -fx-font-weight: bold; "
                    + "-fx-padding: 10 20; "
                    + "-fx-border-color: #1E88E5; -fx-border-width: 3px; "
                    + "-fx-border-radius: 20; -fx-background-radius: 20; "
                    + "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 10, 0, 3, 3);";

            String pressedStyle = "-fx-background-color: linear-gradient(to bottom, #0D47A1, #1565C0); "
                    + "-fx-text-fill: #FFFFFF; "
                    + "-fx-border-color: #0D47A1; -fx-border-width: 3px;";

            for (Button button : new Button[]{nextLevel, playAgain, exit}) {
                button.setStyle(buttonStyle);
                button.setOnMousePressed(e -> button.setStyle(pressedStyle));
                button.setOnMouseReleased(e -> button.setStyle(buttonStyle));
                button.setPrefWidth(100);
            }

            hBox.getChildren().addAll(nextLevel, playAgain, exit);

            endPane.widthProperty().addListener((observable, oldValue, newValue) -> {
                end.setLayoutX((newValue.doubleValue()*0.2));
                hBox.setLayoutX((newValue.doubleValue()*0.2));
            });

            endPane.heightProperty().addListener((observable, oldValue, newValue) -> {
                end.setLayoutY(newValue.doubleValue() * 0.2);
                hBox.setLayoutY(newValue.doubleValue() * 0.6);
            });

            endPane.getChildren().addAll(end, hBox);
            endPane.setStyle("-fx-background-color: linear-gradient(to bottom, #FFF8E1, #FFE082); "
                    + "-fx-border-color: #FFC107; -fx-border-width: 5px; -fx-border-radius: 20; "
                    + "-fx-background-radius: 20;");

            DropShadow dropShadow = new DropShadow();
            dropShadow.setRadius(15);
            dropShadow.setOffsetX(5);
            dropShadow.setOffsetY(5);
            dropShadow.setColor(Color.rgb(50, 50, 50, 0.5));
            endPane.setEffect(dropShadow);

            basePane.getChildren().add(endPane);

            pressed = true;
            endPane.setStyle("-fx-background-color: #EEDD82;");
            endPane.setLayoutY(SCREEN_HEIGHT*0.3);
            endPane.setLayoutX(SCREEN_WIDTH*0.25);
            endPane.setPrefWidth(basePane.getWidth()*0.5);
            endPane.setPrefHeight(basePane.getHeight()*0.5);
        }

    }

}
