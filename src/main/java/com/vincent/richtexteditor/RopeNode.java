package com.vincent.richtexteditor;

class RopeNode {

    String text;
    RopeNode left;
    RopeNode right;
    int weight;

    RopeNode(String text, RopeNode left, RopeNode right) {
        this.text = text;
        this.left = left;
        this.right = right;
        weight = calculateWeight(left);
    }

    RopeNode(String text) {
        this(text, null, null);
    }

    boolean isLeaf() {
        return this.left==null && this.right==null;
    }

    private static int calculateWeight(RopeNode node) {
        if (node==null) return 0;
        if (node.isLeaf()) return node.text.length();
        return node.weight;
    }

}