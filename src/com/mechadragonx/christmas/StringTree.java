package com.mechadragonx.christmas;

public class StringTree
{
    protected class TreeNode
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
    // public boolean side; // true = left, false = right
    // public boolean bothSides; // true = both sides of previous covered, false = no

    public StringTree(String value)
    {
        initialize(value);
    }
//    public Tree(int height)
//    {
//        headRoot = new
//    }

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
}
