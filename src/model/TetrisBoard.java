package  model;

import  controller.TetrisGameController;

public class TetrisBoard {

	public int blockBoard[][];
	private TetrisGameController gameController;
	public static final int y = 20;
	public static final int x = 10;

	public TetrisBoard(TetrisGameController gameController) {
		this.gameController = gameController;
		blockBoard = new int[y][x];
		for (int i = 0; i < x; i++)
			for (int j = 0; j < y; j++)
				blockBoard[j][i] = 0;
	}

	public void addBlock(TetrisBlock tetrisBlock) {
		for (int i = 0; i < tetrisBlock.block.length; i++) {
			for (int j = 0; j < tetrisBlock.block[i].length; j++) {
				if (tetrisBlock.posY + i >= y
						|| tetrisBlock.posX + j >= x)
					break;
				if (tetrisBlock.block[i][j] >= 1
						&& this.blockBoard[tetrisBlock.posY + i][tetrisBlock.posX + j] == 0)
					this.blockBoard[tetrisBlock.posY + i][tetrisBlock.posX + j] = tetrisBlock.block[i][j];
			}
		}
		fullLineCheck();
	}

	private void fullLineCheck() {
		for (int j = y - 1; j > 0; j--) {
			int lineChecker = 1;
			for (int i = 0; i < x; i++) {
				lineChecker = lineChecker * this.blockBoard[j][i];
			}
			if (lineChecker >= 1) {
				deleteLine(j);
				j++;
			}
			lineChecker = 1;
		}
	}

	public void deleteLine(int line) {
		for (int j = line; j > 0; j--)
			for (int i = 0; i < x; i++) {
				this.blockBoard[j][i] = this.blockBoard[j - 1][i];
			}
		gameController.getScore().scoreUp();
		
	}
}
