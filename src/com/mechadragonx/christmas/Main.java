package com.mechadragonx.christmas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main
{
    private static Tree<String> questions;

    public static void main(String[] args) throws IOException
    {
        execute();
    }
    private static void create(String question, String answer, boolean side) // true = left, false = right
    {
        questions = new Tree<>(question);
        questions.addSide(answer, side, false);
    }
    private static void ask()
    {
        if(questions.current.equals(questions.headRoot.left) || questions.current.equals(questions.headRoot.right))
        {
            System.out.println(questions.headRoot.value);
        }
        else
            System.out.println(questions.current.value);
    }
    private static void checkAnswer(String answer)
    {
        if(answer.equals("yes"))
        {
            questions.next(true);
            // ask();
        }
        else
        {
            questions.next(false);
            // ask();
        }
    }
    private static void execute() throws IOException
    {
        create("Is it a boy?", "boy", true);
        InputStreamReader stream = new InputStreamReader(System.in);
        BufferedReader buffer = new BufferedReader(stream);
        boolean exited = false;
        boolean asked = false;
        boolean comFail = false;

        System.out.println("My name is Sylvester and I'm gonna ask you a bunch of questions to try and guess what you're thinking! \\(^ v ^)/");
        // System.out.println("Please type a command!");
        while(true)
        {
            questions.play();
            String command = buffer.readLine();
            switch(command.toLowerCase())
            {
                case "play":
                    ask();
                    asked = true;
                    break;
                case "exit":
                    exited = true;
                    break;
//            default:
//                System.out.println("That command is illegal!");
//                break;
            }
            if(exited)
                break;

            if(asked)
            {
                command = "";
                while(true)
                {
                    command = buffer.readLine();
                    if(command.toLowerCase().equals("yes"))
                    {
                        if(questions.current.left != null || questions.current.right != null)
                            questions.next(true);
                        break;
                    }
                    else if(command.toLowerCase().equals("no"))
                    {
                        if(questions.current.left != null || questions.current.right != null)
                            questions.next(false);
                        break;
                    }
                    else System.out.println("You can only type \"yes\" and \"no\"!");
                }
                String[] value;
                value = questions.current.value.split("\\s");
                if(value.length <= 2)
                {
                    System.out.println("My guess is \"" + questions.current.value + "\"!\nAm I right?");
                }
                command = "";
                while(true)
                {
                    command = buffer.readLine();
                    if(command.toLowerCase().equals("yes"))
                    {
                        System.out.println("Yay! I was right! :P");
                        break;
                    }
                    else if(command.toLowerCase().equals("no"))
                    {
                        System.out.println("What!? T_T");
                        comFail = true;
                        break;
                    }
                    else
                        System.out.println("You can only type \"yes\" and \"no\"!");
                }
            }

            if(comFail)
            {
                String question = "";
                String answer = "";
                boolean side = false;
                String input = "";

                System.out.println("What were you thinking of?");
                answer = buffer.readLine();
                System.out.println("What is a question I can use to improve guessing algorithm?");
                question = buffer.readLine();
                questions.addSide(question, false, true);
                System.out.println("Does \"" + answer + "\" satisfy that question?");
                while(true)
                {
                    input = buffer.readLine();
                    if(input.toLowerCase().equals("yes"))
                    {
                        questions.addSide(answer, true, false);
                        break;
                    }
                    else if(input.toLowerCase().equals("no"))
                    {
                        questions.addSide(answer, false, false);
                        break;
                    }
                    else
                        System.out.println("You can only type \"yes\" and \"no\"!");
                }
                System.out.println("Now I am smarter! :3");
            }
        }
        System.out.println("Thanks for playing!");
    }
}
