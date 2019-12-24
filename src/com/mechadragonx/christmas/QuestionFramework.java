package com.mechadragonx.christmas;

public class QuestionFramework
{
    private StringTree questions;

    public QuestionFramework(String question, String answer, boolean side) // true = left, false = right
    {
        questions = new StringTree(question);
        // questions.addSide(answer, side);
    }

    public void ask()
    {
        System.out.println(questions.current.value);
    }
    public void checkAnswer(String answer)
    {
        if(answer.equals("yes"))
        {
            questions.next(true);
            ask();
        }
        else
        {
            questions.next(false);
            ask();
        }
    }
}
