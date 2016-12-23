package Abstractions;

import DataStructure.Stack;

//An abstract class, the implementation will be given in the actual solver of different algorithms.
//
//2016/12/22

public abstract class Solver {
	protected boolean solvable;
	protected int moves = -1;
	protected Stack<Action> movements;
	protected int expNodes = 0;
	protected long time = 0;
		
	//Get whether a given puzzle is solvable.
	public abstract boolean isSolvable();

	//Get the number of moves that needed to solve a given puzzle.
	public abstract int moves();
	
	/**
	 *  Get the amount of expanded nodes
	 *  @return The amount of expanded nodes
	 */
	public abstract int expandedNodes();
	
	//Get the time to solve a given puzzle.
    public abstract long getRunningTime();

	//A stack of solution of a given puzzle.
    public abstract Iterable<Action> solution();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//No testing are needed in this class.
	}

}
