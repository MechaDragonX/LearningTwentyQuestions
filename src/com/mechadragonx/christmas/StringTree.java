package com.mechadragonx.christmas;

import java.io.*;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StringTree implements Serializable
{
    protected class TreeNode implements Serializable
    {
        public String value;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(String value)
        {
            this.value = value;
            left = null;
            right = null;
        }
        public TreeNode(String value, TreeNode left, TreeNode right)
        {
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

    public TreeNode headRoot;
    public TreeNode previous;
    public TreeNode current;

    public StringTree()
    {
        headRoot = null;
        previous = null;
        current = null;
    }
    public StringTree(String value)
    {
        initialize(value);
    }

    public void initialize(String value)
    {
        headRoot = new TreeNode(value);
        previous = headRoot;
        current = headRoot;
    }
    public String next(boolean side) // true = left, false = right
    {
        if(side)
        {
            previous = current;
            current = current.left;
            return current.value;
        }
        else
        {
            previous = current;
            current = current.right;
            return current.value;
        }
    }
    public void add(String value)
    {
        headRoot = add(headRoot, value);
    }
    private TreeNode add(TreeNode root, String value)
    {
        if(root == null)
            root = new TreeNode(value);
        else if(root.value.compareTo(value) > 0)
            root.left = add(root.left, value);
        else if(root.value.compareTo(value) < 0)
            root.right = add(root.right, value);
        // else a duplicate, do nothing
        return root;
    }
    public void addSide(String value, boolean side, boolean toPrevious) // true = left, false = right
    {
        TreeNode newNode;
        if(toPrevious)
        {
            if(side)
            {
                newNode = new TreeNode(value);
                previous.left = newNode;
                previous = current;
                current = newNode;
            }
            else
            {
                newNode = new TreeNode(value);
                previous.right = newNode;
                previous = current;
                current = newNode;
            }
        }
        else
        {
            if(side)
            {
                newNode = new TreeNode(value);
                current.left = newNode;
                previous = current;
                current = newNode;
            }
            else
            {
                newNode = new TreeNode(value);
                current.right = newNode;
                previous = current;
                current = newNode;
            }
        }
    }
    public void play()
    {
        previous = headRoot;
        current = headRoot;
    }
    public void serialize() throws IOException
    {
        System.out.println("Please wait while I save my brain~! ^_^");

        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
        String formatedDateTime = dateTime.format(formatter);

        FileOutputStream file = null;
        ObjectOutputStream out = null;
        try
        {
            //Saving of object in a file
            file = new FileOutputStream("sylvester-" + formatedDateTime + ".sav");
            out = new ObjectOutputStream(file);

            // Method for serialization of object
            out.writeObject(this);

            System.out.println("I saved my brain! ^ V ^");
        }
        catch(IOException ex)
        {
            System.out.println("An IOException was caught!");
        }
        finally
        {
            file.close();
            out.close();
        }
    }
    public static StringTree deserialize(Path filename) throws IOException
    {
        System.out.println("Please wait while I grab another brain~! ^_^");

        FileInputStream file = null;
        ObjectInputStream in = null;
        StringTree tree = null;
        try
        {
            // Reading the object from a file
            file = new FileInputStream(filename.toString());
            in = new ObjectInputStream(file);

            // Method for deserialization of object
            tree = (StringTree) in.readObject();

            System.out.println("The other brain has been plugged in! ^ v ^");
        }
        catch(IOException ex)
        {
            System.out.println("An IOException was caught!");
        }
        catch(ClassNotFoundException ex)
        {
            System.out.println("An ClassNotFoundException was caught!");
        }
        finally
        {
            in.close();
            file.close();
        }
        return tree;
    }


}
