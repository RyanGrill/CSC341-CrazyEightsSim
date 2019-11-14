package CrazyEights;

public interface IGameControl 
{
	void playGame();
	void runGame();
	void init();
}

interface IGameView
{
	int getInput();
	void display(String message);
}
