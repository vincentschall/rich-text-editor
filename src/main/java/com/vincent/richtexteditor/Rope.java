package com.vincent.richtexteditor;

import java.util.Arrays;
import java.util.List;

class Rope {
    RopeNode root;
    int length;

    Rope(String initialText) {
        if (initialText == null) initialText = "";
        this.root = new RopeNode(initialText);
        this.length = initialText.length();
    }

    Rope() {
        this("");
    }

    Rope concat(Rope otherRope) {
        if (otherRope == null) return this;
        RopeNode newRoot = new RopeNode("", this.root, otherRope.root);
        newRoot.weight = this.length;
        this.root = newRoot;
        this.length += otherRope.length;
        return this;
    }

    char charAt(int index) {
        if (index < 0 || index >= this.length) {
            throw new IllegalArgumentException("Index " + index + " is out of bounds");
        }
        RopeNode node = this.root;
        while (!node.isLeaf()) {
            if (index < node.weight) {
                node = node.left;
            } else {
                index -= node.weight;
                node = node.right;
            }
        }
        return node.text.charAt(index);
    }

    @Override
    public String toString() {
        return traverse(root);
    }

    private String traverse(RopeNode node) {
        if (node == null) return "";
        if (node.isLeaf()) return node.text;
        return traverse(node.left) + traverse(node.right);
    }

    List<Rope> split(int index) {
        if (index < 0 || index > length) {
            throw new IllegalArgumentException("Index " + index + " is out of bounds");
        }
        if (index == 0) {
            return Arrays.asList(new Rope(), this);
        }
        if (index == length) {
            return Arrays.asList(this, new Rope());
        }
        List<RopeNode> leftAndRight = splitNode(root, index);
        Rope leftRope = new Rope();
        leftRope.root = leftAndRight.getFirst();
        leftRope.length = index;
        Rope rightRope = new Rope();
        rightRope.root = leftAndRight.get(1);
        rightRope.length = length - index;
        return Arrays.asList(leftRope, rightRope);
    }

    private List<RopeNode> splitNode(RopeNode node, int index) {
        if (node.isLeaf()) {
            return Arrays.asList(
                    new RopeNode(node.text.substring(0, index)),
                    new RopeNode(node.text.substring(index))
            );
        }
        if (index < node.weight) {
            List<RopeNode> leftAndRight = splitNode(node.left, index);
            RopeNode left = leftAndRight.get(0);
            RopeNode right = leftAndRight.get(1);
            return Arrays.asList(
                    left,
                    new RopeNode("", right, node.right)
            );
        } else {
            List<RopeNode> leftAndRight = splitNode(node.right, index - node.weight);
            RopeNode left = leftAndRight.get(0);
            RopeNode right = leftAndRight.get(1);
            return Arrays.asList(
                    new RopeNode("", node.left, left),
                    right
            );
        }
    }

    void insert(String text, int index) {
        if (text == null || text.isEmpty()) return;
        if (index < 0 || index > length) {
            throw new IllegalArgumentException("Index " + index + " is out of bounds");
        }
        List<Rope> leftAndRight = split(index);
        Rope left = leftAndRight.get(0);
        Rope right = leftAndRight.get(1);
        Rope textRope = new Rope(text);
        Rope newRope = left.concat(textRope).concat(right);
        this.root = newRope.root;
        this.length = newRope.length;
    }

    void delete(int start, int end) {
        if (start < 0 || end > this.length || start > end) {
            throw new IllegalArgumentException("Invalid range [" + start + ", " + end + "]");
        }
        if (start == end) return;
        Rope left = split(start).getFirst();
        Rope right = split(end).get(1);
        Rope newRope = left.concat(right);
        this.root = newRope.root;
        this.length = newRope.length;
    }
}