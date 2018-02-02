package com.gmail.taikingyo.ca;

import java.util.function.ToIntFunction;

public class BZReaction implements ToIntFunction<int[][]> {
	private final int k1;
	private final int k2;
	private final int n;
	private final int g;
	
	public BZReaction(int k1, int k2, int n, int g) {
		this.k1 = k1;
		this.k2 = k2;
		this.n = n;
		this.g= g;
	}

	@Override
	public int applyAsInt(int[][] value) {
		// TODO Auto-generated method stub
		
		int s = 0;
		int a = 0;
		int b = 0;

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				s += value[i][j];
				
				if(!(i == 1 && j == 1)) {
					if(value[i][j] > 0 && value[i][j] < n) a++;
					if(value[i][j] >= n) b++;
				}
			}
		}
		
		if(value[1][1] == 0) {
			return a / k1 + b / k2;
		}else if(value[1][1] < n) {
			return s / (a + b + 1) + g;
		}else return 0;
	}
}
