package model;

import java.util.Random;

import controller.TetrisGameController;

public class TetrisBlock {
	public int posX = 0, posY = 0;
	public int[][] block = new int[4][4];
	public int c; // color.
	private TetrisGameController controller;

	public TetrisBlock(TetrisGameController controller) {
		this.controller = controller;
		Random rand = new Random();
		this.c = 1 + rand.nextInt(4);
		setBlock(rand.nextInt(6), 0);
	}

	private void setBlock(int type, int rotation) {
		if (type == 0) {
			block[0] = new int[] { c, 0, 0, 0 };
			block[1] = new int[] { c, 0, 0, 0 };
			block[2] = new int[] { c, 0, 0, 0 };
			block[3] = new int[] { c, 0, 0, 0 };
		} else if (type == 1) {
			block[0] = new int[] { 0, 0, 0, 0 };
			block[1] = new int[] { 0, 0, 0, 0 };
			block[2] = new int[] { c, 0, 0, 0 };
			block[3] = new int[] { c, c, c, 0 };
		} else if (type == 2) {
			block[0] = new int[] { 0, 0, 0, 0 };
			block[1] = new int[] { c, 0, 0, 0 };
			block[2] = new int[] { c, 0, 0, 0 };
			block[3] = new int[] { c, c, 0, 0 };
		} else if (type == 3) {
			block[0] = new int[] { 0, 0, 0, 0 };
			block[1] = new int[] { 0, 0, 0, 0 };
			block[2] = new int[] { c, c, 0, 0 };
			block[3] = new int[] { c, c, 0, 0 };
		} else if (type == 4) {
			block[0] = new int[] { 0, 0, 0, 0 };
			block[1] = new int[] { c, 0, 0, 0 };
			block[2] = new int[] { c, c, 0, 0 };
			block[3] = new int[] { c, 0, 0, 0 };
		} else if (type == 5) {
			block[0] = new int[] { 0, 0, 0, 0 };
			block[1] = new int[] { c, 0, 0, 0 };
			block[2] = new int[] { c, c, 0, 0 };
			block[3] = new int[] { 0, c, 0, 0 };
		} else if (type >= 6) {
			block[0] = new int[] { 0, 0, 0, 0 };
			block[1] = new int[] { 0, 0, 0, 0 };
			block[2] = new int[] { c, c, 0, 0 };
			block[3] = new int[] { 0, c, c, 0 };
		}
	}

	public void rotateBlock(TetrisBoard board) {
		int[][] currentBlock = block.clone();
		int[][] nextBlock = new int[4][4];

		// 회전 시킨다.
		for (int i = 0; i < block.length; i++)
			for (int j = 0; j < block[i].length; j++)
				nextBlock[i][j] = 0;
		for (int i = 0; i < block.length; i++)
			for (int j = 0; j < block[i].length; j++)
				nextBlock[i][j] = block[j][3 - i];

		block = nextBlock;
		trim(); // 일단 적용하고.
		int currPOSX = posX;
		boolean isAbleToRotate = false; // 돌릴 수 없다고 믿고.
		for (int i = 0; i < 4; i++) { // 4번 시도 해본다.
			posX = currPOSX - i; // 한칸씩 땡겨봄.
			if (isAbleToMoveAfterCrachCheck(board, 0, 0)) {// 검사해서 회전 가능 하면,
				isAbleToRotate = true; // flag를 세우고,
				break; // 루프 탈출.
			}
		}
		if (!isAbleToRotate) { // 위 루프에서 안됨이 판명되면,
			posX = currPOSX;
			block = currentBlock; // 회전상태 원상 복귀.
		}
		if (posX + getRightBorder() > TetrisBoard.x) {
			// 돌렸는데, 우측으로 삐져나간 경우.
			System.out.println("out" + ""
					+ (posX + getRightBorder() - TetrisBoard.x)); // 출력하고.
			posX = posX - (posX + getRightBorder() - TetrisBoard.x);
			// 삐져나간만큼 왼쪽으로 땡김.
		}
		trim();
	}

	public void downBlock(TetrisBoard board) {
		if (isAbleToMoveAfterCrachCheck(board, 0, 1))
			posY = posY + 1;

		trim();
	}

	public void leftBlock(TetrisBoard board) {
		if (posX + getLeftBorder() > 0) { // 좌측벽 충돌 검사.
			if (isAbleToMoveAfterCrachCheck(board, -1, 0))
				posX = posX - 1;
		}
		trim();
	}

	public void rightBlock(TetrisBoard board) {
		if (posX + getRightBorder() < TetrisBoard.x) { // 우측벽 충돌검사.
			if (isAbleToMoveAfterCrachCheck(board, 1, 0))
				posX = posX + 1;
		}
		trim();
	}

	private boolean isAbleToMoveAfterCrachCheck(TetrisBoard board, int deltaX,
			int deltaY) {
		// CrachCheck
		for (int i = 0; i < block.length; i++) { // 보드와 충돌 검사.
			for (int j = 0; j < block[i].length; j++) {
				if ((posY + deltaY + j >= TetrisBoard.y)
						|| (posX + deltaX + i >= TetrisBoard.x)) {
					// for문 유효범위 때문에 생긴 구문.
					break;
				}
				if (board.blockBoard[posY + deltaY + j][posX + deltaX + i] >= 1
						&& block[j][i] >= 1) { // 블록이 겹침.
					if (deltaY == 1)
						controller.createNewBlock();
					return false;
				}
			}
		}
		if (posY + deltaY + getDownBorder() > TetrisBoard.y) { // 바닥과 충돌 검사.
			controller.createNewBlock();
			return false;
		}
		return true;
	}

	private void trim() {
		int count = getLeftBorder();
		for (int i = 0; i < count; i++) {
			this.arrangeLeft();
		}
	}

	private void arrangeLeft() {
		for (int i = 0; i < block.length; i++)
			for (int j = 1; j < block[i].length; j++) {
				block[i][j - 1] = block[i][j];
				block[i][j] = 0;
			}
	}

	private int getLeftBorder() {
		// FIXME temp variables
		int i = 0, j = 0;
		int tmp1 = 3;
		int tmp2 = 3;

		for (j = 0; j < block.length; j++) {
			for (i = 0; i < block[j].length; i++) {
				if (block[j][i] >= 1) {
					tmp1 = i;
					break;
				}
			}
			if (tmp1 < tmp2)
				tmp2 = tmp1;
			tmp1 = 3;
		}

		return tmp2;
	}

	private int getRightBorder() {
		// FIXME temp variables
		int tmp1 = 0;
		int tmp2 = 0;

		for (int j = 0; j < block.length; j++) {
			for (int i = 0; i < block[j].length; i++) {
				if (block[j][i] >= 1)
					tmp1 = i + 1;
			}
			if (tmp1 > tmp2)
				tmp2 = tmp1;
			tmp1 = 0;
		}

		return tmp2;
	}

	private int getDownBorder() {
		// FIXME temp variables
		int tmp1 = 0;
		int tmp2 = 0;

		for (int j = 0; j < block.length; j++) {
			for (int i = 0; i < block[j].length; i++) {
				if (block[i][j] >= 1)
					tmp1 = i + 1;
			}
			if (tmp1 > tmp2)
				tmp2 = tmp1;
			tmp1 = 0;
		}

		return tmp2;
	}
}
