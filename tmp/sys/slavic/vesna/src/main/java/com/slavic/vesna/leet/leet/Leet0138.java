package com.slavic.vesna.leet.leet;

import java.util.HashMap;
import java.util.Map;

public class Leet0138 {

    Map<Node,Node> map = new HashMap<>();

    public Node copyRandomList(Node head) {

        if (head == null) return null;

        if (map.containsKey(head)) {
            return map.get(head);
        }

        Node node = new Node(head.val);
        map.put(head,node);
        node.next = copyRandomList(head.next);
        node.random = copyRandomList(head.random);
        return node;
    }

    static class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

}
