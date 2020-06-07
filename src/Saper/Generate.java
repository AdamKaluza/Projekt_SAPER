package Saper;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseButton;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Generate {

    private static final int tileSize = 40;
    private int width;
    private int height;
    private int xTiles;
    private int yTiles;
    private Tile[][] field;
    public Scene scene;
    private int open = 0;
    private int bomb = 0;

    public void setALL(int h, int w) {
        height = h;
        width = w;
        yTiles = height / tileSize;
        xTiles = width / tileSize;
        field = new Tile[xTiles][yTiles];
    }

    public Parent createContent() {

        Pane mainPane = new Pane();
        mainPane.setPrefSize(width, height);

        //tworzy tablice dwuwymiarowa stackpenów

        for (int y = 0; y < yTiles; y++) {
            for (int x = 0; x < xTiles; x++) {
                //tworenie plytki i losowanie bomby

                Tile tile = new Tile(x, y, Math.random() < 0.2);
                field[x][y] = tile;
                if (tile.hasBomb) {
                    bomb++;
             //       System.out.println(1);
                }
                mainPane.getChildren().add(tile);
            }
        }

        for (int y = 0; y < yTiles; y++) {
            for (int x = 0; x < xTiles; x++) {
                Tile tile = field[x][y];

                if (tile.hasBomb) {
                    continue;
                }
                //poprzez strumień filtrujemy sasiadujace plytki i zliczamy bomby dookołą
                long bombs = getNeighbors(tile).stream().filter(t -> t.hasBomb).count();
               // System.out.println(bombs + "o");

                if (bombs > 0) {
                    tile.text.setText(String.valueOf(bombs));
                }
                //zmiana koloru fontu zależna od ilości bomb
                switch ((int) bombs) {
                    case 1: {
                        tile.text.setFill(Color.BLUE);
                    }
                    break;
                    case 2: {

                        tile.text.setFill(Color.GREEN);
                    }
                    break;
                    case 3: {

                        tile.text.setFill(Color.RED);
                    }
                    break;
                    case 4: {

                        tile.text.setFill(Color.DARKRED);
                    }
                    break;

                }

            }
        }
        return mainPane;
    }

    public List<Tile> getNeighbors(Tile tile) {
        List<Tile> neighbors = new ArrayList<>();

        //współrzędne sasiadująych kafelek
        int[][] cordinates = new int[][]{
                {-1, -1,},
                {-1, 0,},
                {-1, 1,},
                {0, -1,},
                {0, 1,},
                {1, -1,},
                {1, 0,},
                {1, 1}
        };

        for (int[] cordinate : cordinates) {
            for (int j = 0; j < cordinates[0].length; j++) {

                int dx = cordinate[j];
                int dy = cordinate[++j];

                int newX = tile.x + dx;
                int newY = tile.y + dy;


               //wprawdzamy czy sasiadujace kafelki nie wychodza poza zakres pola
                //dodajemy do listy jesli znajduja sie w polu
                if (newX >= 0 && newX < xTiles && newY >= 0 && newY < yTiles) {
                    neighbors.add(field[newX][newY]);
                }
            }
        }
        return neighbors;
    }

    //tworzenie kafelki w stackpane layout
    public class Tile extends StackPane {
        private int x, y;
        private boolean hasBomb;
        private boolean isOpen = false;
        // tworzenie kafelki
        private Rectangle plate = new Rectangle(tileSize - 2, tileSize - 2);
        private Text text = new Text();

        public Tile(int x, int y, boolean hasBomb) {
            this.x = x;
            this.y = y;
            this.hasBomb = hasBomb;

            plate.setStroke(Color.BLUE);
            //ustawianie zawartości textu
            text.setFont(Font.font(25));
            text.setText(hasBomb ? "X" : "");
            text.setVisible(false);
            //dodanie do kafelki textu i plata
            getChildren().addAll(plate, text);
            //ustawianie plate wzgledem współrzędnych
            setTranslateX(x * tileSize);
            setTranslateY(y * tileSize);

            //akcja przycisku myszka
            setOnMouseClicked(e -> {
                if (e.getButton() == MouseButton.PRIMARY) {
                    open();
                } else if (e.getButton() == MouseButton.SECONDARY) {
                    if (!isOpen)
                        plate.setFill(Color.RED);
                    if (e.getClickCount() == 2) {
                        if (!isOpen)
                            plate.setFill(Color.BLACK);
                    }
                }
            });
        }
        //funkcja otwierania kafelki
        public void open() {
            //jesli juz otwarta zakoncz
            if (isOpen) {
                return;
            }
            open++;
            System.out.println(open + "k");
            System.out.println(xTiles * yTiles);
            // komunikat o trafieniu na bombe i przeganiu rozgrywki oraz tworzenie nowej
            if (hasBomb) {
                Alert gameOver = new Alert(Alert.AlertType.ERROR);
                gameOver.setHeaderText("Game over! Press ok to play again.");
                gameOver.setTitle("YOU LOSE!");
                gameOver.showAndWait();
                System.out.println("Game Over");
                bomb = 0;
                open = 0;
                scene.setRoot(createContent());
                return;
            }
            //odkrywanie płytki
            isOpen = true;
            text.setVisible(true);
            plate.setFill(null);

            //sprawdzanie czy sasiadujace plytki sa puste jesli tak otwiranie sasiadujacych
            if (text.getText().isEmpty()) {
                getNeighbors(this).forEach(Tile::open);
            }
            //komunikat o wygranej po odkryciu wszystkich plytek
            if (open == ((xTiles * yTiles) - bomb) || bomb == 0) {
                win();
                bomb = 0;
                open = 0;
                scene.setRoot(createContent());
            }
        }
        // allert o wygranej
        public void win() {
            Alert win = new Alert(Alert.AlertType.INFORMATION);
            win.setHeaderText("YOU WON! Press ok to play again.");
            win.setTitle("Congratulations!");
            win.showAndWait();
            System.out.println("YOU WIN!");
        }
    }
}


