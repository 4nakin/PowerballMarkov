//The special ball in a lottery game, such as a Powerball, where there are no succeeding numbers.
public class SpecialBallNumber implements Comparable<SpecialBallNumber>{
	public int number;	//The number of the ball itself
	public long frequency;	//The number of times that special ball appears when the associated regular number is in the 

	public SpecialBallNumber(int n) {
		number = n;
		frequency = 1;
	}
	public boolean equals(SpecialBallNumber e) {
		return (number == e.number);
	}
	@Override
	public int compareTo(SpecialBallNumber e) {
		if(number == e.number)
			return 0;
		else if(number < e.number)
			return -1;
		else
			return 1;
	}
}
