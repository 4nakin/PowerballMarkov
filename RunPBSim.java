import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.Random;

public class RunPBSim {
	public static TreeSet<LottoNumber> regularNumbers = new TreeSet<LottoNumber>();
	public static void main(String[] args) {
		//Add all the numbers to the list
		for(int i = 1; i <= 69; i++) {
			regularNumbers.add(new LottoNumber(i));
		}
		File numberFile = new File(args[0]);
		Scanner in = null;
		try {
			in = new Scanner(numberFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
			//Add in all the data to the program
			while(in.hasNextLine()) {
				String inLine = in.nextLine();
				String[] inNumsStrings = inLine.split("\t");
				int[] inNums = new int[inNumsStrings.length];
				for(int i = 0; i < inNumsStrings.length; i++) {
					inNums[i] = Integer.parseInt(inNumsStrings[i]);
				}
				ArrayList<Integer> regNums = new ArrayList<Integer>();
				for(int i = 0; i <= 4; i++) {
					regNums.add(inNums[i]);
				}
				//System.out.println(regNums);
				for(int i = 0; i <= 4; i++) {
					int a = regNums.get(i);
					for(LottoNumber l : regularNumbers) {
						if(l.number == a) {
							l.frequency++;
		
							boolean found = false;
							if(i < 4) {
								int numAfter = regNums.get(i + 1);
								for(LottoNumber ln : l.numbersAfter) {
									if(ln.number == numAfter) {
										ln.frequency++;
										found = true;
									}
								}
								if(!found){
									l.numbersAfter.add(new LottoNumber(numAfter));
								}
							}
							
							found = false;
							for(SpecialBallNumber s : l.specialBall) {
								if(s.number == inNums[5]) {
									found = true;
									s.frequency++;
								}
							}
							if(!found) {
								l.specialBall.add(new SpecialBallNumber(inNums[5]));
							}
							//End of Case to find or add 
						}
					}
				}
			}
			//Fixing the frequency issue the lazy way
			for(LottoNumber n : regularNumbers) {
				n.frequency--;
			}
			//END OF INITIALIZING LISTS
			
			
			
			///////////////////////////////////////////////////////////////////////////////////////
			///////////////////////////////////////////////////////////////////////////////////////
			///////////////////////////////////////////////////////////////////////////////////////
			
			
			//BEGIN SIMULATION
			//Generating the random ticket
			ArrayList<LottoNumber> finalNumbers = new ArrayList<LottoNumber>();
			Random rnd = new Random();
			//Finding the first number
			ArrayList<LottoNumber> allPossibleFirsts = new ArrayList<LottoNumber>();
			//Adding numbers to the array based on their frequency
			for(LottoNumber firstNumber : regularNumbers) {
				for(int i = 0; i < firstNumber.frequency; i++) {
					allPossibleFirsts.add(firstNumber);
				}
			}
			finalNumbers.add(allPossibleFirsts.get(rnd.nextInt(allPossibleFirsts.size()-1)));

			for(int i = 1; i < 5; i++) {
				ArrayList<LottoNumber> allPossibleNexts = new ArrayList<LottoNumber>();
				LottoNumber lastInList = finalNumbers.get(finalNumbers.size() - 1);
				for(LottoNumber n : lastInList.numbersAfter) {
					for(int c = 0; c < n.frequency; c++) {
						allPossibleNexts.add(n);
					}
				}
				int selectedIndex = rnd.nextInt(allPossibleNexts.size());
				LottoNumber foundRandom = allPossibleNexts.get(selectedIndex);
				for(LottoNumber a : regularNumbers) {
					if(a.number == foundRandom.number) {
						finalNumbers.add(a);
					}
				}
			}
			//All normal numbers have been added, sort them
			finalNumbers.sort(null);
			
			//Now find the most likely special ball associated with the majority of the balls
			SpecialBallNumber finalSpecialBall;
			ArrayList<SpecialBallNumber> allSpBall = new ArrayList<SpecialBallNumber>();
			for(int i = 1; i <= 29; i++) {
				allSpBall.add(new SpecialBallNumber(i));
			}
			//Totaling the frequency for all the special balls
			for(LottoNumber l : finalNumbers) {
				for(SpecialBallNumber spb : l.specialBall) {
					for(SpecialBallNumber spbCountingForFinal : allSpBall) {
						if(spb.number == spbCountingForFinal.number) {
							spbCountingForFinal.frequency++;
						}
					}
				}
			}
			//Finding the largest special ball
			finalSpecialBall = allSpBall.get(0);
			for(SpecialBallNumber n : allSpBall) {
				if(n.frequency > finalSpecialBall.frequency) {
					finalSpecialBall = n;
				}
			}
			///////////////////////////////////////////////////////////////////////////////////////
			///////////////////////////////////////////////////////////////////////////////////////
			///////////////////////////////////////////////////////////////////////////////////////
			
			//A Weighted random ticket has been generated
			//Print the ticket
			System.out.print("Weighted Random Ticket: ");
			for(LottoNumber z : finalNumbers) {
				System.out.print(z.number + " ");
			}
			System.out.print("PowerBall: " + finalSpecialBall.number + "\n");
	}
}
