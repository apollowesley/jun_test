package com.rann.offer.problem26;

/**
 * Problem 26
 * 复杂链表的复制
 *
 * @author lemonjing
 */
public class ComplexListClone {

    public ComplexNode cloneList(ComplexNode head) {
        copyNode(head);
        processSibling(head);

        return split(head);
    }

    // 第一步 复制节点
    private void copyNode(ComplexNode head) {
        ComplexNode p = head;
        while (null != p) {
            ComplexNode clonedNode = new ComplexNode(-1);
            clonedNode.data = p.data;
            clonedNode.next = p.next;
            clonedNode.sibling = null;
            p.next = clonedNode;
            p = clonedNode.next;
        }
    }

    // 第二步 处理sibling
    private void processSibling(ComplexNode head) {
        ComplexNode p = head;
        while (null != p) {
            ComplexNode clonedNode = p.next;
            if (null != p.sibling) {
                clonedNode.sibling = p.sibling.next;
            }
            p = clonedNode.next;
        }
    }

    // 第三步 拆分
    private ComplexNode split(ComplexNode head) {
        ComplexNode p = head;
        ComplexNode cloneHead = null;
        ComplexNode q = null;
        if (null != p) {
            cloneHead = p.next;
            q = cloneHead;
        }
        while (null != p) {
            p.next = q.next;
            p = p.next;
            if (p != null) {
                q.next = p.next;
                q = q.next;
            }
        }

        return cloneHead;
    }
}
