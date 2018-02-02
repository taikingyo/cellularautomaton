package com.gmail.taikingyo.ca;

import java.util.Random;
import java.util.function.IntFunction;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.stage.Stage;

public class CA2DApp extends Application {
	private static final int WIDTH = 300;
	private static final int HEIGHT = 300;
	private static final int CELL_SIZE = 3;
	private static final int INTERVAL = 300;  //ミリ秒
	
	private GraphicsContext gc;

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub

		StackPane pane = new StackPane();
		Canvas canvas = new Canvas((WIDTH + 4) * CELL_SIZE, (HEIGHT + 4) * CELL_SIZE);
		pane.getChildren().add(canvas);
		arg0.setScene(new Scene(pane));
		arg0.show();
		gc = canvas.getGraphicsContext2D();
		gc.setTransform(new Affine(CELL_SIZE, 0, 0, 0, CELL_SIZE, 0));
		run();
	}
	
	void run() {
		Random rand = new Random(0);
		
		/**　ライフゲーム
		int[][] state = generate(rand, 2);
		CellularAutomaton2D ca = new CellularAutomaton2D(-1, CellularAutomaton2D.N8, CellularAutomaton2D.LifeGame, state);
		CellAnimation anim = new CellAnimation(gc, ca, Monochrome, INTERVAL);
		**/

		//BZ反応
		int n = 200;
		int[][] state = generate(rand, n);
		CellularAutomaton2D ca = new CellularAutomaton2D(-1, CellularAutomaton2D.N8, new BZReaction(2, 3, n, 70), state);
		CellAnimation anim = new CellAnimation(gc, ca, new Gradient(n, Color.RED), INTERVAL);
		
		anim.start();
	}
	
	static int[][] generate(Random rand, int n) {
		int[][] state = new int[HEIGHT][WIDTH];
		for(int i = 0; i < HEIGHT; i++) {
			for(int j = 0; j < WIDTH; j++) {
				state[i][j] = (int)rand.nextInt(n);
			}
		}
		
		return state;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		launch(args);
	}
	
	public class CellAnimation extends AnimationTimer {
		private final GraphicsContext gc;
		private final CellularAutomaton2D ca;
		private final IntFunction<Color> color;
		private final long interval;
		private long time = 0;
		
		public CellAnimation(GraphicsContext gc, CellularAutomaton2D ca, IntFunction<Color> color, int interval) {
			this.gc = gc;
			this.ca = ca;
			this.color = color;
			this.interval = interval * 1000000; //ミリ秒からナノ秒へ
			view(ca.getState());
			gc.setFill(Color.BLACK);
		}

		@Override
		public void handle(long arg0) {
			// TODO Auto-generated method stub
			if(time + interval < arg0) {
				time = arg0;
				view(ca.next());
			}
		}
		
		void view(int[][] state) {
			for(int i = 0; i < HEIGHT; i++) {
				for(int j = 0; j < WIDTH; j++) {
					gc.setFill(color.apply(state[i][j]));
					gc.fillRect(j + 2, i + 2, 1, 1);
				}
			}
		}
	}
	
	//二値CA用
	public static final IntFunction<Color> Monochrome = new IntFunction<Color>() {

		@Override
		public Color apply(int arg0) {
			// TODO Auto-generated method stub
			
			if(arg0 == 0) return Color.WHITE;
			else return Color.BLACK;
		}
	};

	//多値CA用
	public class Gradient implements IntFunction<Color> {
		private final int n;
		private final Color over;
		
		public Gradient(int n, Color over) {
			this.n = n;
			this.over = over;
		}

		@Override
		public Color apply(int arg0) {
			// TODO Auto-generated method stub
			
			if(arg0 < n) {
				return Color.gray(1.0 - (double)arg0 / (n - 1));
			}else return over;
		}
		
	}
}
