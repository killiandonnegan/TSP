import java.util.*;
import java.io.*;
	
 class Game
{
			public static void main(String args[])
			{	
				char[] letters = Word(); //char array of letters 
				Play(letters);		//calls play method with letters array as parameter
			}
			
		
			public static char[] Word() //selects random word, returns array of its characters
			{		
				Dictionary Dictionary = new Dictionary(); //creates dictionary object
				int size = Dictionary.getSize(); 
	
				Random random = new Random(); //instance of the random class
				int wordPosition = random.nextInt(size); //gets random position/index in the dictionary
				
				String word = Dictionary.getWord(wordPosition); //gets word at that position/index
				
				char[] letters = new char[word.length()]; //char array to store letters of the randomly selected word
				for(int i = 0; i<letters.length; i++)
				{
					letters[i] = word.charAt(i); //fills array with characters
				}
				
				boolean hasLetters = false; //used to determine if the input has letters
				
				//I chose to have my game only use strings that have at least one letter,
				//A few of the words in the words.txt file have no letters which would be hard to guess
				
				while(hasLetters == false)  //while current word has no letters
				{
					for(int i = 0; i<letters.length; i++)  //checks if word has any letters
					{
						if(Character.isLetter(letters[i]))  //if it does sets boolean to true
								{
									hasLetters = true;
								}
					}
					
					if(hasLetters == false) //if it doesn't have letters,
					{
							letters = Word();   //gets a new word
					}
				}
				
				return letters; //returns this array
			}

			
			public static void Play(char[] letters) //majority of the game code
			{
				Scanner scan = new Scanner(System.in); //creates scanner 
				
				int length = letters.length; //length of word
				char[] blanks = new char[length]; //char array to compare against characters of the word 
				
				for(int i = 0; i<length-1; i++)
				{
					if(Character.isLetter(letters[i]))
					{
						blanks[i] = '_'; //fills the positions where there is letters with underscores
					}
					
					else
					{
						blanks[i] = letters[i];  //if there is not a letter in the position, fill the blanks array with whatever is in the that position
					}
				}
			
			//tells the player how the game works
			System.out.println("Welcome to my recreation of Hangman in java.");
			System.out.println(); //so the screen isn't cluttered
			System.out.println("A random word has been selected for you from the 'words.txt' file.");
			System.out.println("The word is "+length+ " characters long.");
			System.out.println("You must guess the letters you think are in the word.");
			System.out.println("For each wrong letter you guess you will lose a life.");

			int lives = 7; //number of lives the player has
			System.out.println(); //so the screen isn't cluttered
			
			boolean gameOver = false; //if game is in progress or finished
			
			while(!gameOver) //while game is in progress
			{
				System.out.println("Lives: "+lives); //before each guess display the amount of lives the player has and
				System.out.println("The word is:");  //the word including their previous correct guesses
				for(int i = 0; i<length-1; i++)
				{
					System.out.print(blanks[i]+" ");
				}

				System.out.println(); //so the screen isn't cluttered
				System.out.println(); //so the screen isn't cluttered
				System.out.println("Guess a letter.");
					
				boolean validinput = false;
	
				char input = scan.next().charAt(0); //inputted character

					while(validinput == false) //ensures the user enters a letter
					{		
						if(Character.isLetter(input))
						{
							validinput = true;
						}
						
						else //if they do not enter a letter, they are prompted to enter a letter again until they do so
						{
							System.out.println("Invalid input. Please enter a letter.");
							input = scan.next().charAt(0);
						}
					}

				int count = 0; //keeps track of how many times the guessed letter appears in the word
			
			
				 for(int i = 0; i<length-1; i++)
				{
					char inputCapital = Character.toUpperCase(input); //character the user inputted
					char lettersCapital = Character.toUpperCase(letters[i]); //char at position i in word
					
					if(lettersCapital == inputCapital) //if the inputted letter is in the word
					{
						blanks[i] = letters[i]; //put the letter in that position in blanks array
						count++; //increase count if the letter is found in word
					}
				}
		
				
				
				if(count == 1) //if letter is contained in the word once
				{
					System.out.println(" ");
					System.out.println("This letter is contained once in the word.");
					System.out.println(" ");
				}
				
				else if(count > 1) //if letter is contained in the word more than once
				{
					System.out.println(" ");
					System.out.println("This letter is contained "+count+" times in the word.");
					System.out.println(" ");
				}
				
				else  //if letter is not contained 0 times in the word
				{
					System.out.println(" "); //so the screen isn't cluttered
					System.out.println("This letter is not contained in the word.");
					System.out.println(" "); //so the screen isn't cluttered
					lives--;
				}
				
				Picture(lives); //picture method draws a hangman picture corresponding to the number of lives
				
				
				if(lives==0)  //if the player runs out of lives
				{

					System.out.println("Game Over.");
					System.out.println("The word was:"); //shows what the word was
					System.out.println(letters);
					System.out.println(" "); //so the screen isn't cluttered
					gameOver = true;
				}
				
				
				boolean same = true; //to store whether the blanks and letters array have equal values
				                     //a.k.a if the player has won
							
				for(int i = 0; i<letters.length-1; i++)
				{
					if(blanks[i] != letters[i])
					{
						same = false;  //if at any index their values are not equal set same to false
					}
				}
				
				if(same == true) //if they are the same, the user has won
				{
					System.out.println("Well done! You have successfully guessed the word:");
					System.out.println(letters);
					gameOver = true;
				}
				
				if(gameOver)  //if the game is over
				{
					System.out.println("Would you like to play again? If so enter 'Y'."); //ask do they want to play again
					char s = scan.next().charAt(0);
					
					if(s == 'Y'||s=='y') //if they want to
					{
						System.out.println(""); //so the screen isn't cluttered
						System.out.println(""); //so the screen isn't cluttered
						System.out.println(""); //so the screen isn't cluttered
						main(null);  //calls main, which calls word and play and essentially runs the program again
					}
					
					else
					{
						System.out.println("Thanks for playing."); //if they dont say yes
					}
				}
			}		
		}
	
	
				public static void Picture(int lives) //prints to the screen the stage of the hangman corresponding to how many lives you have
				{
					if(lives==6) //6 lives
					{
						System.out.println();
						System.out.println();
						System.out.println();
						System.out.println("______");
					}
					else if(lives == 5) //5 lives
					{
						System.out.println("   |  ");
						System.out.println("   |  ");
						System.out.println("   |  ");
						System.out.println("   |  ");
						System.out.println("___|___");
					}
						else if(lives==4) //4 lives
					{
						System.out.println("   -------¬");
						System.out.println("   |  ");
						System.out.println("   |  ");
						System.out.println("   |  ");
						System.out.println("___|___");
					}
					else if(lives ==3) //3 lives
					{
						System.out.println("   -------¬");
						System.out.println("   |      O ");
						System.out.println("   |  ");
						System.out.println("   |  ");
						System.out.println("___|___");
					}
					
					else if(lives ==2) //2 lives
					{
						System.out.println("   -------¬");
						System.out.println("   |      O ");
						System.out.println("   |      | ");
						System.out.println("   |  ");
						System.out.println("___|___");
					}
					
					else if(lives ==1) //1 life
					{
						System.out.println("   -------¬");
						System.out.println("   |      O ");
						System.out.println("   |     /|\\ ");
						System.out.println("   |  ");
						System.out.println("___|___");
					}
					else if(lives==0) //0 lives
					{
						
						System.out.println("   -------¬");
						System.out.println("   |      O ");
						System.out.println("   |     -|- ");
						System.out.println("   |     / \\");
						System.out.println("___|___");
					}
				}
}

 
 
	class Dictionary //given dictionary class
	{
    
    private String input[]; 

    public Dictionary()
    {
        input = load("D://words.txt");  //change to wherever the file is stored on the machine you are on
    }
    
    public int getSize()
    {
        return input.length;
    }
    
    public String getWord(int n)
    {
        return input[n];
    }
    
    public String[] load(String file)
    {
        File aFile = new File(file);     
        StringBuffer contents = new StringBuffer();
        BufferedReader input = null;
        try {
            input = new BufferedReader( new FileReader(aFile) );
            String line = null; 
            int i = 0;
            while (( line = input.readLine()) != null)
            {
                contents.append(line);
                i++;
                contents.append(System.getProperty("line.separator"));
            }
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("Can't find the file - are you sure the file is in this location: "+file);
            ex.printStackTrace();
        }
        catch (IOException ex)
        {
            System.out.println("Input output exception while processing file");
            ex.printStackTrace();
        }
        finally
        {
            try
            {
                if (input!= null)
                {
                    input.close();
                }
            }catch (IOException ex)
            {
                System.out.println("Input output exception while processing file");
                ex.printStackTrace();
            }
        }
        String[] array = contents.toString().split("\n");
        for(String s: array)
        {
            s.trim();
        }
        return array;
    }
}