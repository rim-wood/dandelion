package cn.icepear.dandelion.upm.api.utils;

import cn.icepear.dandelion.upm.api.domain.dto.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rim-wood
 * @description List集合数据转化成TreeNode结构数据工具类
 * @date Created on 2019/10/21.
 */
public class List2TreeNodeUtil {
    /**
     * 使用递归方法建树
     *
     * @param treeNodes
     * @return
     */
    public static List< ? extends TreeNode> rebuildList2Tree(List< ? extends  TreeNode > treeNodes) {
        boolean existRootNode = false;
        List<TreeNode> newTree = new ArrayList<TreeNode>();//初始化一个新的列表
        for (TreeNode treeNode : treeNodes) {
            if (isRootNode(treeNode, treeNodes)) {//选择根节点数据开始找儿子
                newTree.add(findChildren(treeNode, treeNodes));
                existRootNode = true;
            }
        }
        if(!existRootNode){//也可能大家都是根节点
            return treeNodes;
        }
        return newTree;
    }

    /**
     * 判断节点是否是根节点
     * @param checkNode
     * @param treeNodes
     * @return
     */
    public static boolean isRootNode(TreeNode checkNode, List< ? extends  TreeNode > treeNodes) {
        for (TreeNode treeNode : treeNodes) {
            if (checkNode.getParentId() == treeNode.getId()) {//判断checkNode是不是有爸爸
                return  false;
            }
        }
        return true;
    }


    /**
     * 递归查找子节点
     *
     * @param treeNodes
     * @return
     */
    public static TreeNode findChildren(TreeNode parentNode, List< ? extends  TreeNode > treeNodes) {
        List<TreeNode> children = parentNode.getChildren();
        for (TreeNode it : treeNodes) {
            if (parentNode.getId() == it.getParentId()) {//找儿子，判断parentNode是不是有儿子
                children.add(findChildren(it, treeNodes));
            }
        }
        return parentNode;
    }
}
