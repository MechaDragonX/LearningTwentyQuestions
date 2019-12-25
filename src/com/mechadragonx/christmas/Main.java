package com.mechadragonx.christmas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main
{
    private static StringTree questions;
    private static InputStreamReader stream = new InputStreamReader(System.in);
    private static BufferedReader buffer = new BufferedReader(stream);

    public static void main(String[] args) throws IOException
    {
        execute();
    }
    private static void create(String question, String answer, boolean side) // true = left, false = right
    {
        questions = new StringTree(question);
        questions.addSide(answer, side, false);
    }
    private static void ask()
    {
//        if(questions.current.equals(questions.headRoot.left) || questions.current.equals(questions.headRoot.right))
//        {
//            System.out.println(questions.headRoot.value);
//        }
//        else
            System.out.println(questions.current.value);
    }
    private static boolean yesNo() throws IOException
    {
        String answer;
        while(true)
        {
            answer = buffer.readLine();
            if(answer.equals("yes"))
                return true;
            else if(answer.equals("no"))
                return false;
            else
                System.out.println("You can only type \"yes\" and \"no\"!");
        }
    }
    private static void execute() throws IOException
    {
        create("Does it fly?", "Is it a Canadian goose?", true);
        questions.addSide("Canadian goose", true, false);
        boolean exited = false;
        boolean asked = false;
        int round = 1;
        int guess = -1; // 1: Guess successful, 0: Guess failed, -1: Did not guess

        System.out.println("My name is Sylvester and I'm gonna ask you a bunch of questions to try and guess what you're thinking! \\(^ v ^)/");
        // System.out.println("Please type a command!");
        while(true)
        {
            questions.play();
            String command = buffer.readLine();
            switch(command.toLowerCase())
            {
                case "play":
                    // ask();
                    asked = true;
                    break;
                case "exit":
                    exited = true;
                    break;
                case "save":
                    questions.serialize();
                    break;
                case "load":
                    StringTree newTree = StringTree.deserialize(readPath());
                    questions = newTree;
                    break;
                default:
                    System.out.println("That command is illegal!");
                    break;
            }
            if(exited)
                break;
            if(asked)
            {
                round++;
                do
                {
                    ask();
                    if(checkAnswer())
                        guess = tryGuess();
                    else
                        guess = 0;
                }
                while(guess == -1);
                if(guess == 0)
                    addNewQuestion(round);
            }

            exited = false;
            asked = false;
            guess = 0;
        }
        System.out.println("Thanks for playing!");
    }

    private static Path readPath() throws IOException
    {
        System.out.println("Please type the path to my save file: ");
        Path path;
        while(true)
        {
            path = Paths.get(buffer.readLine());
            if(!path.toFile().exists())
                System.out.println("The specified path doesn't point to a file!");
            else if(!isTextFile(path))
                System.out.println("The specified path doesn't point to a text file!");
            else
                return path;
        }
    }
    private static boolean isTextFile(Path path)
    {
        return path.toString().substring(path.toString().length() - 3).equals("sav");
    }
    private static boolean checkAnswer() throws IOException
    {
        boolean retVal = true;
        if(yesNo())
        {
            if(questions.current.left != null)
                questions.next(true);
            else
            {
                System.out.println("What!? You stumped me! T_T");
                System.out.println("I guess you're not thinking of \"" + questions.current.left.value + "\"!");
                retVal = false;
            }
        }
        else
        {
            if(questions.current.right != null)
                questions.next(false);
            else
            {
                System.out.println("What!? You stumped me! T_T");
                System.out.println("I guess you're not thinking of \"" + questions.current.left.value + "\"!");
                retVal = false;
            }
        }
        return retVal;
    }
    private static int tryGuess() throws IOException
    {
        String[] value;
        value = questions.current.value.split("\\s");
        if(value.length <= 2)
        {
            System.out.println("My guess is \"" + questions.current.value + "\"!\nAm I right?");
        }
        else
            return -1; // Did not guess
        if(yesNo())
        {
            System.out.println("Yay! I was right! :P");
            return 1;
        }
        else
        {
            System.out.println("What!? T_T");
            return 0;
        }
    }
    private static void addNewQuestion(int round) throws IOException
    {
        String question;
        String answer;

        System.out.println("What were you thinking of?");
        answer = buffer.readLine();
        System.out.println("What is a question I can use to improve guessing algorithm?");
        question = buffer.readLine();
        if(round == 1)
            questions.addSide(question, false, true);
        else
            questions.addSide(question, false, false);
        System.out.println("Does \"" + answer + "\" satisfy that question?");
        if(yesNo())
            questions.addSide(answer, true, false);
        else
            questions.addSide(answer, false, false);
        System.out.println("Now I am smarter! :3");
    }
}
