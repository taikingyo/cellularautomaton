package com.gmail.taikingyo.ca;

import java.util.function.ToIntFunction;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Affine;
import javafx.stage.Stage;

public class CA1DApp extends Application {
	private static final int SPACE = 200;
	private static final int GENERATION = 200;
	private static final int CELL_SIZE = 3;
	private GraphicsContext gc;

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub

		StackPane pane = new StackPane();
		Canvas canvas = new Canvas(SPACE * CELL_SIZE + 20, GENERATION * CELL_SIZE + 20);
		pane.getChildren().add(canvas);
		arg0.setScene(new Scene(pane));
		arg0.show();
		gc = canvas.getGraphicsContext2D();
		gc.setTransform(new Affine(CELL_SIZE, 0, 0, 0, CELL_SIZE, 0));
		run();
	}
	
	private void run() {
		int[] state = generate();
		//printState(state);
		CellularAutomaton1D ca = new CellularAutomaton1D(-1, 1, CellularAutomaton1D.Rule110, state);
		for(int i = 0; i < SPACE; i++) {
			if(state[i] == 1) gc.fillRect(1 + i, 1, 1, 1);
		}
		
		for(int g = 0; g < GENERATION; g++) {
			state = ca.next();
			//printState(state);
			
			for(int i = 0; i < SPACE; i++) {
				if(state[i] == 1) gc.fillRect(1 + i, 2 + g, 1, 1);
			}
		}
	}
	
	private int[] generate() {
		int[] initState = new int[SPACE];
		for(int i = 0; i < SPACE; i++) {
			if(Math.random() > 0.5) initState[i] = 1;
		}
		return initState;
	}
	
	private static void printState(int[] state) {
		for(int i : state) {
			System.out.print(i + ", ");
		}
		System.out.println();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		launch(args);
	}
}
