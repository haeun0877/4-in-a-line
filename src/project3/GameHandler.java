package project3;

import javax.swing.JTextArea;

public class GameHandler {
	
	private final int SCREEN_WIDTH= 30;
	private final int SCREEN_HEIGHT=30;
	private final int PADDING=1;
	private int x=6;
	private int y=7;
	private int putDollNum=1;
	private String doll="●";
	private int winCountBlack=0;
	private int winCountWhite=0;
	private int previousWinCountB=0;
	private int previousWinCountW=0;
	
	private JTextArea textArea;
	private String[][] pan;
	private String[][] buffer;
	private int currentX;

	public GameHandler(JTextArea ta) {
		textArea=ta;
		pan= new String[x][y];
		buffer = new String[SCREEN_WIDTH][SCREEN_HEIGHT];
		initData();
	}
	
	public void initData() {
		currentX=SCREEN_WIDTH/4;
		doll="●";
		
		
		pan[0][0]="┌";
		for(int i=1; i<6; i++)
			pan[0][i]="─┬";
		pan[0][y-1]="─┐";
		
		for(int x=1; x<5; x++)
		{
			pan[x][0]="├";
			for(int y=1; y<6; y++)
			{
				pan[x][y]="─┼";
			}
			pan[x][y-1]="─┤";
		}
		
		pan[x-1][0]="└";
		for(int i=1; i<6; i++)
			pan[x-1][i]="─┴";
		pan[x-1][y-1]="─┘";
		
		for(int i=0; i<SCREEN_WIDTH; i++) {
			for(int j=0; j<SCREEN_HEIGHT; j++) {
				buffer[i][j]=" ";
			}
		}
		
		drawPan();
	}
	
	public void drawPan() {
		for(int i=3; i<3+x; i++) {
			for(int j=1; j<1+y; j++) {
				buffer[i][j]=pan[i-3][j-1];
			}
		}
	}
	
	public boolean win() {
		boolean result= false;
		for(int i=0; i<6; i++)
		{
			for(int j=0; j<4; j++)
			{
				if((pan[i][j]==doll||pan[i][j].equals("─"+doll))&&pan[i][j+1].equals("─"+doll)&&pan[i][j+2].equals("─"+doll)&&pan[i][j+3].equals("─"+doll))
					result=true;
			}
		}
		for(int j=0; j<7; j++)
		{
			for(int i=0; i<3; i++)
			{
				if((pan[i][j]==doll||pan[i][j].equals("─"+doll))&&(pan[i+1][j]==doll||pan[i+1][j].equals("─"+doll))&&(pan[i+2][j]==doll||pan[i+2][j].equals("─"+doll))&&(pan[i+3][j]==doll||pan[i+3][j].equals("─"+doll)))
					result=true;
			}
		}
		for(int i=5; i>=3; i--)
		{
			for(int j=0; j<4; j++)
			{
				if((pan[i][j]==doll||pan[i][j].equals("─"+doll))&&pan[i-1][j+1].equals("─"+doll)&&pan[i-2][j+2].equals("─"+doll)&&pan[i-3][j+3].equals("─"+doll))
					result=true;
			}
		}
		for(int i=0; i<3; i++)
		{
			for(int j=0; j<4; j++)
			{
				if((pan[i][j]==doll||pan[i][j].equals("─"+doll))&&pan[i+1][j+1].equals("─"+doll)&&pan[i+2][j+2].equals("─"+doll)&&pan[i+3][j+3].equals("─"+doll))
					result=true;
			}
		}
		
		return result;
	}
	
	private void drawToBuffer(int px, int py, String s) {
		buffer[px+PADDING][py]=s;
	}
	
	public void putDoll() {
		
		int first_y=SCREEN_WIDTH/4-6;
		int search_y= x-1;
		
		for(int i=0; i<7; i+=1)
		{
			if(currentX==first_y+i*2)
			{
				for(; search_y>=0; search_y--) {
					if(pan[search_y][i].equals("●")||pan[search_y][i].equals("─●")||pan[search_y][i].equals("○")||pan[search_y][i].equals("─○")) {
						continue;
					}
					else {
						if(i==0)
							pan[search_y][i]=doll;
						else
							pan[search_y][i]="─"+doll;
						break;
					}
				}
			}
		}
		dollColor();

		drawPan();
		drawAll();
		putDollNum++;
	}
	
	public void drawAll() {
		StringBuilder sb= new StringBuilder();
		
		drawToBuffer(1, currentX, doll);
		drawToBuffer(1, 16, "┌───CURRENT───┐");
		drawToBuffer(2, 10, "│  ●:"+winCountBlack+"  ○:"+winCountWhite+"   │");
		drawToBuffer(3, 10, "└─────────────┘");
		
		if(win()) {
			drawToBuffer(5,10," ");
			drawToBuffer(6,10," ");
			drawToBuffer(7,10," ");
			drawToBuffer(4,10,"╔═════════════╗");
			drawToBuffer(5,10,"║  "+doll+" WINS!    ║");
			drawToBuffer(6,10,"║             ║");
			drawToBuffer(7,10,"║AGAIN?  (Y/N)║");
			drawToBuffer(8,16,"╚═════════════╝");
			
			if(putDollNum%2==1)
				winCountWhite+=1;
			else
				winCountBlack+=1;
		}
		else {
			drawToBuffer(5, 10, "┌───PREVIOUS──┐");
			drawToBuffer(6, 10, "│  ●:"+previousWinCountB+"  ○:"+previousWinCountW+"   │");
			drawToBuffer(7, 10, "└─────────────┘");
		}
		drawToBuffer(8, 23, "by Haeun");
		
		for(int x=0; x<SCREEN_WIDTH; x++)
		{
			for(int y=0; y<SCREEN_HEIGHT; y++) {
				sb.append(buffer[x][y]);
			}
			sb.append("\n");
		}
		
		textArea.setText(sb.toString());
	}
	
	private void dollColor() {
		if(putDollNum%2==1)
			doll="○";
		else
			doll="●";
	}
	
	private boolean doesDollFitR(int dollX) {
		if(dollX>12) {
			return false;
		}
		return true;
	}
	
	private boolean doesDollFitL(int dollX) {
		if(dollX<2) {
			return false;
		}
		return true;
	}
	
	public void moveRight() {
		drawToBuffer(1, currentX, " ");
		currentX +=doesDollFitR(currentX)?2:0;
		drawAll();
	}
	
	public void moveLeft() {
		drawToBuffer(1, currentX, " ");
		currentX -=doesDollFitL(currentX)?2:0;
		drawAll();
	}

	

	public void putY() {
		initData();
		putDollNum=1;
		drawAll();
	}
	
	public void putN() {
		initData();
		putDollNum=1;
		previousWinCountB=winCountBlack;
		previousWinCountW=winCountWhite;
		winCountBlack=0;
		winCountWhite=0;
		drawAll();
	}
	
	public static void main(String[] args) {
		
	}

}
