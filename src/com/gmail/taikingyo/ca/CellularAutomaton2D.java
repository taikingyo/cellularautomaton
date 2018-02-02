package com.gmail.taikingyo.ca;

import java.util.Arrays;
import java.util.function.ToIntFunction;

public class CellularAutomaton2D {
	public static final int N4 = 4;  //ノイマン近傍
	public static final int N8 = 8;  //ムーア近傍
	
	private final int edge;
	private final int width;
	private final int height;
	private final int neighborhood;
	private final ToIntFunction<int[][]> rule;
	
	private int[][] state;
	
	public CellularAutomaton2D(int edge, int neighborhood, ToIntFunction<int[][]> rule, int[][] init) {
		this.edge = edge;
		height = init.length;
		width = init[0].length;
		this.neighborhood = neighborhood;
		this.rule = rule;
		
		if(edge == -1) state = init.clone();
		else if(neighborhood == N4 || neighborhood == N8) {
			state = new int[height + 2][width + 2];
			
			for(int i = 0; i < state.length; i++) {
				Arrays.fill(state[i], edge);
				if(i > 0 && i < state.length - 1) System.arraycopy(init[i - 1], 0, state[i], 1, width);
			}
		}else {
			System.out.println("unknown neighborhood value");
		}
	}
	
	public int[][] next() {
		int[][] next = new int[height][width];
		int[][] neighborhoodcells;
		if(neighborhood == N4 || neighborhood == N8) {
			neighborhoodcells = new int[3][3];
			
			if(edge == -1) {
				for(int i = 0; i < height; i++) {
					for(int j = 0; j < width; j++) {
						for(int k = 0; k < 3; k++) {
							for(int l = 0; l < 3; l++) {
								int y = (i + k - 1 + height) % height;
								int x = (j + l - 1 + width) % width;
								neighborhoodcells[k][l] = state[y][x];
							}
						}
						next[i][j] = rule.applyAsInt(neighborhoodcells);
					}
				}
				
				state = next;
			}else {
				for(int i = 0; i < height; i++) {
					for(int j = 0; j < width; j++) {
						for(int k = 0; k < 3; k++) {
							for(int l = 0; l < 3; l++) {
								neighborhoodcells[k][l] = state[i + k][j + l];
							}
						}
						next[i][j] = rule.applyAsInt(neighborhoodcells);
					}
				}
				
				for(int i = 0; i < height; i++) {
					System.arraycopy(next[i], 0, state[i + 1], 1, width);
				}
			}
		}
		
		return next;
	}
	
	public int[][] getState() {
		if(edge != -1 && (neighborhood == N4 || neighborhood == N8)) {
			int[][] trim = new int[height][width];
			for(int i = 0; i < height; i++) {
				System.arraycopy(state[i + 1], 1, trim[i], 0, width);
			}
			return trim;
		}else return state;
	}
	
	//N8用ルール、ライフゲーム
	public static final ToIntFunction<int[][]> LifeGame = new ToIntFunction<int[][]>() {

		@Override
		public int applyAsInt(int[][] value) {
			// TODO Auto-generated method stub
			int n = 0;
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++) {
					if(!(i == 1 && j == 1) && value[i][j] != 0) n++;
				}
			}
			int life;
			
			if(n > 3 || n < 2) {
				life = 0;
			}else if(n == 3) {
				life = 1;
			}else life = value[1][1];
			
			return life;
		}
	};
	
}
