package com.gmail.taikingyo.ca;

import java.util.Arrays;
import java.util.function.ToIntFunction;

public class CellularAutomaton1D {
	private final int edge;	//境界を指定の値で固定、-1でループ処理
	private final int size;	//空間のサイズ
	private final int r;	//半径（近傍）
	private final ToIntFunction<int[]> rule;
	
	private int[] state;

	public CellularAutomaton1D(int edge, int r, ToIntFunction<int[]> rule, int[] init) {
		this.edge = edge;
		this.r = r;
		this.rule = rule;
		size = init.length;
		if(edge == -1) state = init.clone();
		else {
			state = new int[size + r * 2];
			Arrays.fill(state, edge);
			System.arraycopy(init, 0, state, r, size);
		}
		//printState(state);
	}
	
	public int[] next() {
		int[] next = new int[size];
		int[] neighborhood = new int[r * 2 + 1];
		
		if(edge == -1) {
			for(int i = 0; i < size; i++) {
				for(int j = 0; j < neighborhood.length; j++) {
					int n = (i - r + j + size) % size;	//mod(size)
					neighborhood[j] = state[n];
				}
				next[i] = rule.applyAsInt(neighborhood);
			}
			state = next;
		}else {
			for(int i = 0; i < size; i++) {
				for(int j = 0; j < neighborhood.length; j++) {
					neighborhood[j] = state[i + j];
				}
				next[i] = rule.applyAsInt(neighborhood);
			}
			System.arraycopy(next, 0, state, r, size);
		}
		
		//printState(state);
		return next;
	}
	
	private static void printState(int[] st) {
		System.out.print("state: ");
		for(int i : st) System.out.print(i);
		System.out.println();
	}
	
	//r=1、ルール30
	public static final ToIntFunction<int[]> Rule30 = new ToIntFunction<int[]>() {

		@Override
		public int applyAsInt(int[] value) {
			// TODO Auto-generated method stub
			if(value[0] != 0 && value[1] != 0 && value[2] != 0) {
				//state111
				return 0;
			}else if(value[0] != 0 && value[1] != 0 && value[2] == 0) {
				//state110
				return 0;
			}else if(value[0] != 0 && value[1] == 0 && value[2] != 0) {
				//state101
				return 0;
			}else if(value[0] != 0 && value[1] == 0 && value[2] == 0) {
				//state100
				return 1;
			}else if(value[0] == 0 && value[1] != 0 && value[2] != 0) {
				//state011
				return 1;
			}else if(value[0] == 0 && value[1] != 0 && value[2] == 0) {
				//state010
				return 1;
			}else if(value[0] == 0 && value[1] == 0 && value[2] != 0) {
				//state001
				return 1;
			}else {
				//state000
				return 0;
			}
		}
	};
	
	//r=1、ルール90
	public static final ToIntFunction<int[]> Rule90 = new ToIntFunction<int[]>() {

		@Override
		public int applyAsInt(int[] value) {
			// TODO Auto-generated method stub
			if(value[0] != 0 && value[1] != 0 && value[2] != 0) {
				//state111
				return 0;
			}else if(value[0] != 0 && value[1] != 0 && value[2] == 0) {
				//state110
				return 1;
			}else if(value[0] != 0 && value[1] == 0 && value[2] != 0) {
				//state101
				return 0;
			}else if(value[0] != 0 && value[1] == 0 && value[2] == 0) {
				//state100
				return 1;
			}else if(value[0] == 0 && value[1] != 0 && value[2] != 0) {
				//state011
				return 1;
			}else if(value[0] == 0 && value[1] != 0 && value[2] == 0) {
				//state010
				return 0;
			}else if(value[0] == 0 && value[1] == 0 && value[2] != 0) {
				//state001
				return 1;
			}else {
				//state000
				return 0;
			}
		}
	};
	
	//r=1、ルール110
	public static final ToIntFunction<int[]> Rule110 = new ToIntFunction<int[]>() {

		@Override
		public int applyAsInt(int[] value) {
			// TODO Auto-generated method stub
			if(value[0] != 0 && value[1] != 0 && value[2] != 0) {
				//state111
				return 0;
			}else if(value[0] != 0 && value[1] != 0 && value[2] == 0) {
				//state110
				return 1;
			}else if(value[0] != 0 && value[1] == 0 && value[2] != 0) {
				//state101
				return 1;
			}else if(value[0] != 0 && value[1] == 0 && value[2] == 0) {
				//state100
				return 0;
			}else if(value[0] == 0 && value[1] != 0 && value[2] != 0) {
				//state011
				return 1;
			}else if(value[0] == 0 && value[1] != 0 && value[2] == 0) {
				//state010
				return 1;
			}else if(value[0] == 0 && value[1] == 0 && value[2] != 0) {
				//state001
				return 1;
			}else {
				//state000
				return 0;
			}
		}
	};
}
