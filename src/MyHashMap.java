package src;

// Hashmap of <String, Integer>
class MyHashMap {
    int N; // size of the array for the hash table
    PairList<String, Integer>[] hashTable;
    MySet keys;

    public MyHashMap(int tableSize) {
        N = tableSize;
        hashTable = new PairList[N];
        keys = new MySet(tableSize);
    }

    int hashCharacter(char c) {
        // String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        return str.indexOf(c);
    }

    int hashString(String s) {
        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            res += hashCharacter(s.charAt(i));
        }
        // System.out.println(res % N);
        return res % N;
    }

    String[] keySet() {
        return keys.getKeys();
    }

    boolean put(Pair<String, Integer> p) {
        int hash = hashString(p.key);
        // no list at this location?
        if (hashTable[hash] == null) {
            hashTable[hash] = new PairList<>();
        }
        boolean res = hashTable[hash].insert(p);
        if (res) {
            keys.put(p.key);
        }
        return res;
    }

    boolean put(String key, Integer value) {
        Pair<String, Integer> mid = new Pair(key, value);
        // System.out.println(mid.key);

        return put(mid);
    }

    // Pair<String, Integer> get(String studentId) {
    // int hash = hashString(studentId);
    // if (hashTable[hash] == null) {
    // return null;
    // }
    // return hashTable[hash].get(studentId);
    // }

    int get(String studentId) {
        int hash = hashString(studentId);
        if (hashTable[hash] == null) {
            return -1;
        }
        return hashTable[hash].get(studentId).value;
    }

    boolean remove(String studentId) {
        int hash = hashString(studentId);
        if (hashTable[hash] == null) {
            return false;
        }
        boolean res = hashTable[hash].remove(studentId);
        if (res) {
            keys.remove(studentId);
        }
        return res;
    }
}

class Pair<T, E> {
    T key;
    E value;

    Pair(T key, E value) {
        this.key = key;
        this.value = value;
    }
}

class PairList<T, E> {
    PairNode<String, Integer> head;
    int size;

    public PairList() {
        head = null;
        size = 0;
    }

    // insert a student node at the end
    // because we have to check for duplicated student Id
    public boolean insert(Pair<String, Integer> pair) {
        if (size == 0) {
            head = new PairNode(pair);
            size = 1;
            return true;
        }
        PairNode<String, Integer> parent = null;
        PairNode<String, Integer> current = head;
        while (current != null) {
            if (current.data.key.equals(pair.key)) {
                current.data.value = pair.value;
                // duplicated id
                // return false;
            }
            parent = current;
            current = current.next;
        }
        parent.next = new PairNode<String, Integer>(pair);
        size++;
        return true;
    }

    // return a student with the provided id
    public Pair<String, Integer> get(String key) {
        PairNode<String, Integer> node = head;
        while (node != null) {
            if (node.data.key.equals(key)) {
                return node.data;
            }
            node = node.next;
        }
        return null;
    }

    // remove a student with a provided student id
    // return true or false if the remove is successful or not
    public boolean remove(String key) {
        if (size == 0) {
            return false;
        }
        PairNode<String, Integer> parent = null;
        PairNode<String, Integer> current = head;
        while (current != null) {
            if (current.data.key.equals(key)) {
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

class PairNode<T, E> {
    Pair<T, E> data;
    PairNode<T, E> next;

    public PairNode(Pair<T, E> s) {
        data = s;
        next = null;
    }
}
