package src;

import java.util.Arrays;

public class MySet {
    int N; // size of the array for the hash table
    StringList[] hashTable;
    int size;

    public MySet(int tableSize) {
        N = tableSize;
        hashTable = new StringList[N];
        size = 0;
    }

    int hashCharacter(char c) {
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        return str.indexOf(c);
    }

    int hashString(String s) {
        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            res += hashCharacter(s.charAt(i));
        }
        return res % N;
    }

    boolean put(String s) {
        int hash = hashString(s);
        // no list at this location?
        if (hashTable[hash] == null) {
            hashTable[hash] = new StringList();
        }
        boolean res = hashTable[hash].insert(s);
        if (res) {
            size++;
        }
        return res;
    }

    boolean contains(String s) {
        int hash = hashString(s);
        if (hashTable[hash] == null) {
            return false;
        }
        return hashTable[hash].contains(s);
    }

    boolean remove(String s) {
        int hash = hashString(s);
        if (hashTable[hash] == null) {
            return false;
        }
        boolean res = hashTable[hash].remove(s);
        if (res) {
            size--;
        }
        return res;
    }

    String[] getKeys() {
        String[] res = new String[size];
        int idx = 0;
        for (int i = 0; i < hashTable.length; i++) {
            if (hashTable[i] == null) {
                continue;
            }
            StringNode head = hashTable[i].head;

            while (head != null) {
                res[idx] = head.data;
                head = head.next;
                idx++;
            }
        }
        return res;
    }

}

class StringList {
    StringNode head;
    int size;

    public StringList() {
        head = null;
        size = 0;
    }

    // insert a student node at the end
    // because we have to check for duplicated student Id
    public boolean insert(String s) {
        if (size == 0) {
            head = new StringNode(s);
            size = 1;
            return true;
        }
        StringNode parent = null;
        StringNode current = head;
        while (current != null) {
            if (current.data.equals(s)) {
                // duplicated id
                return false;
            }
            parent = current;
            current = current.next;
        }
        parent.next = new StringNode(s);
        size++;
        return true;
    }

    // return a student with the provided id
    public boolean contains(String s) {
        // boolean result = false;
        StringNode node = head;
        while (node != null) {
            if (node.data.equals(s)) {
                return true;
            }
            node = node.next;
        }
        return false;
    }

    // remove a student with a provided student id
    // return true or false if the remove is successful or not
    public boolean remove(String s) {
        if (size == 0) {
            return false;
        }
        StringNode parent = null;
        StringNode current = head;
        while (current != null) {
            if (current.data.equals(s)) {
                if (current == head) {
                    // remove head => need to update head
                    head = head.next;
                    size--;
                    return true;
                }
                parent.next = current.next;
                size--;
                return true;
            }
            parent = current;
            current = current.next;
        }
        return false;
    }
}

class StringNode {
    String data;
    StringNode next;

    public StringNode(String s) {
        data = s;
        next = null;
    }
}
