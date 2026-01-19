import java.util.*;

public class GoogleSearch {
    private final TrieNode root = new TrieNode();

    private static class TrieNode {
        TrieNode[] children = new TrieNode[26];
        boolean endOfWord;
    }

    // Insert word
    public void insert(String word) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            int idx = ch - 'a';
            if (node.children[idx] == null) {
                node.children[idx] = new TrieNode();
            }
            node = node.children[idx];
        }
        node.endOfWord = true;
    }

    // Autocomplete
    public List<String> startsWith(String prefix) {
        TrieNode node = root;

        for (char ch : prefix.toCharArray()) {
            int idx = ch - 'a';
            if (node.children[idx] == null) {
                return Collections.emptyList();
            }
            node = node.children[idx];
        }

        List<String> result = new ArrayList<>();
        collectPrefix(node, new StringBuilder(prefix), result, 5);
        return result;
    }

    // DFS with backtracking
    private void collectPrefix(
            TrieNode node,
            StringBuilder path,
            List<String> result,
            int limit
    ) {
        if (node == null || result.size() == limit) return;

        if (node.endOfWord) {
            result.add(path.toString());
        }

        for (int i = 0; i < 26 && result.size() < limit; i++) {
            if (node.children[i] != null) {
                path.append((char) (i + 'a'));
                collectPrefix(node.children[i], path, result, limit);
                path.deleteCharAt(path.length() - 1); // backtrack
            }
        }
    }
}
