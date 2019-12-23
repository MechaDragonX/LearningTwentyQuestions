package com.mechadragonx.christmas;

public class Tree<T extends Comparable<T>>
{
    protected class TreeNode
    {
        public T value;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(T value)
        {
            this.value = value;
            left = null;
            right = null;
        }
        public TreeNode(T value, TreeNode left, TreeNode right)
        {
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

    public TreeNode headRoot;
    public TreeNode previous;
    public TreeNode current;
    // public boolean side; // true = left, false = right
    // public boolean bothSides; // true = both sides of previous covered, false = no

    public Tree(T value)
    {
        initialize(value);
    }
//    public Tree(int height)
//    {
//        headRoot = new
//    }

    public void initialize(T value)
    {
        headRoot = new TreeNode(value);
        previous = headRoot;
        current = headRoot;
    }

    public T next(boolean side) // true = left, false = right
    {
        if(side)
        {
            previous = current;
            current = current.left;
            return current.left.value;
        }
        else
        {
            previous = current;
            current = current.right;
            return current.right.value;
        }
    }

    public void add(T value)
    {
        headRoot = add(headRoot, value);
    }
    private TreeNode add(TreeNode root, T value)
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

    public void addSide(T value, boolean side, boolean toPrevious) // true = left, false = right
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
}
