package com.overstar.search.export.exception;

import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

@Data
public class Tree {
    String data;
    Tree right;
    Tree left;


    public static void main(String[] args) {
        Tree pRoot = new Tree();
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        Queue<Tree> layer = new LinkedList<>();
        ArrayList<String> layerList = new ArrayList<>();
        layer.add(pRoot);
        int start = 0, end = 1;
        while(!layer.isEmpty()){
            Tree cur = layer.remove();
            layerList.add(cur.data);
            start++;
            if(cur.left!=null){
                layer.add(cur.left);
            }
            if(cur.right!=null){
                layer.add(cur.right);
            }
            if(start == end){
                end = layer.size();
                start = 0;
                result.add(layerList);
                layerList = new ArrayList<>();
            }
        }
        String collect = String.join("", layerList);
        System.out.println(collect);
    }






    public void findData(Tree tree){
        if (tree.getLeft()!=null){
            tree.getLeft().getData();
        }else {

        }
    }

    public static void main1(String[] args) {
        Tree tree = new Tree();
        String i = tree.getData();
        StringBuilder stringBuilder = new StringBuilder(i);
        boolean flag=true;


        Tree tmp = null;
        while (flag){
            Tree left = tree.getLeft();
            Tree treeRight = tree.getRight();
            if (left == null){
                Tree right = tree.getRight();

            }
        }






        while (flag){
            Tree left = tree.getLeft();
            if (left == null){
                break;
            }else {
                stringBuilder.append(left.data);
            }
        }

        flag=true;

        while (flag){
            Tree right = tree.getRight();
            if (right == null){
                flag=false;
            }else {
                stringBuilder.append(right.data);
            }
        }

        System.out.println(stringBuilder.toString());
    }
}
