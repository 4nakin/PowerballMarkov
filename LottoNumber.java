import java.util.TreeSet;

public class LottoNumber implements Comparable<LottoNumber>{
	public int number;	//The ball number in question
	public long frequency;	//The integer number of 
	public TreeSet<LottoNumber> numbersAfter; //The numbers the have been seen directly after this number
	public TreeSet<SpecialBallNumber> specialBall;
	
	public LottoNumber(int n) {
		number = n;
		frequency = 1;
		numbersAfter = new TreeSet<LottoNumber>();
		specialBall = new TreeSet<SpecialBallNumber>();
	}
	int getNumber() {
		return number;
	}
	public boolean equals(LottoNumber e) {
		return (number == e.number);
	}
	@Override
	public int compareTo(LottoNumber e) {
		if(number == e.number)
			return 0;
		else if(number < e.number)
			return -1;
		else
			return 1;
	}
}
