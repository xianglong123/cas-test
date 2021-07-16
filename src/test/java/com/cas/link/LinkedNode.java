package com.cas.link;

/**
 * 手写个单链表数据结构
 *
 * @param <T>
 */
public class LinkedNode<T> {


    @Override
    public String toString() {
        return "LinkedNode{" +
                "head=" + head +
                '}';
    }

    // 头节点
    private Node head;

    private class Node<T> {
        // 数据域
        T value;
        // 指针域
        Node next;

        /**
         * 单链表结点类
         *
         * @param value
         */
        public Node(T value) {
            this.value = value;
            next = null;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    ", next=" + next +
                    '}';
        }
    }

    /**
     * 头部插入结点
     *
     * @param value
     */
    public void addHead(T value) {
        Node newNode = new Node(value);
        // 头结点不存在，新结点成为头结点
        if (head == null) {
            head = newNode;
            return;
        }
        newNode.next = head;
        head = newNode;
    }

    /**
     * 如果链表没有头结点，新结点直接成为头结点；
     * 反之需要找到链表当前的尾结点，并将新节点插入链表尾部
     */
    public void addTail(T value) {
        Node newNode = new Node(value);
        if (head == null) {
            head = newNode;
            return;
        }

        Node last = head;
        while (last.next != null) {
            last = last.next;
        }
        last.next = newNode;
    }

    /**
     * 添加指定节点
     */
    public void addAtIndex(T value, int index) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("索引异常");
        }

        if (index == 0) {
            addHead(value);
        } else if (index == size()) {
            addTail(value);
        } else {
            Node newNode = new Node(value);
            int position = 0;
            Node cur = head; //当前节点
            Node pre = null; //记录前置节点

            while (cur != null) {
                if (position == index) {
                    newNode.next = cur;
                    pre.next = newNode;
                    return;
                }
                pre = cur;
                cur = cur.next;
                position++;
            }
        }


    }

    /**
     * 删除指定位置
     *
     * @param index
     */
    public void deleteAtIndex(int index) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("索引异常");
        }

        if (index == 0) {
            head = head.next;
            return;
        }
        //记录当前位置
        int position = 0;
        //标记当前节点
        Node cur = head;
        // 记录前置节点
        Node pre = null;

        while (cur != null) {
            if (position == index) {
                pre.next = cur.next;
                cur.next = null;
                return;
            }
            pre = cur;
            cur = cur.next;
            position++;
        }
    }

    /**
     * 反转
     */
    public void reverse() {
        //标记当前
        Node cur = head;
        //当前前一个节点
        Node pre = null;
        Node temp;

        while (cur != null) {
            temp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = temp;
        }
        head = pre;
    }

    public int size() {
        int size = 0;
        if (head == null) {
            return size;
        }
        if (head.next != null) {
            Node last = head.next;
            size = 2;
            while (last.next != null) {
                last = last.next;
                size++;
            }
        } else {
            size = 1;
        }
        return size;
    }

    public static void main(String[] args) {
        LinkedNode<Integer> list = new LinkedNode<>();
        list.addHead(1);
        list.addAtIndex(123, 1);
        list.addTail(456);
        System.out.println("toString:" + list);

        list.reverse();

        System.out.println("reverse:" + list);

        list.deleteAtIndex(2);

        System.out.println("delete:" + list);
    }


}
