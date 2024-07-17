import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Node {
    int value;
    Node left, right;

    public Node(int item) {
        value = item;
        left = right = null;
    }
}

class BinarySearchTree {
    Node root;

    BinarySearchTree() {
        root = null;
    }

    void insert(int value) {
        root = insertRec(root, value);
    }

    Node insertRec(Node root, int value) {
        if (root == null) {
            root = new Node(value);
            return root;
        }

        if (value < root.value)
            root.left = insertRec(root.left, value);
        else if (value > root.value)
            root.right = insertRec(root.right, value);

        return root;
    }

    boolean search(int value) {
        return searchRec(root, value);
    }

    boolean searchRec(Node root, int value) {
        if (root == null)
            return false;

        if (root.value == value)
            return true;

        if (root.value > value)
            return searchRec(root.left, value);

        return searchRec(root.right, value);
    }

    void delete(int value) {
        root = deleteRec(root, value);
    }

    Node deleteRec(Node root, int value) {
        if (root == null) return root;

        if (value < root.value)
            root.left = deleteRec(root.left, value);
        else if (value > root.value)
            root.right = deleteRec(root.right, value);
        else {
            if (root.left == null)
                return root.right;
            else if (root.right == null)
                return root.left;

            root.value = minValue(root.right);

            root.right = deleteRec(root.right, root.value);
        }

        return root;
    }

    int minValue(Node root) {
        int minv = root.value;
        while (root.left != null) {
            minv = root.left.value;
            root = root.left;
        }
        return minv;
    }

    void inorder() {
        inorderRec(root);
    }

    void inorderRec(Node root) {
        if (root != null) {
            inorderRec(root.left);
            System.out.print(root.value + " ");
            inorderRec(root.right);
        }
    }

    void preorder() {
        preorderRec(root);
    }

    void preorderRec(Node root) {
        if (root != null) {
            System.out.print(root.value + " ");
            preorderRec(root.left);
            preorderRec(root.right);
        }
    }

    void postorder() {
        postorderRec(root);
    }

    void postorderRec(Node root) {
        if (root != null) {
            postorderRec(root.left);
            postorderRec(root.right);
            System.out.print(root.value + " ");
        }
    }
}

public class BSTGUI extends JFrame {
    private BinarySearchTree bst;
    private JTextField inputField;
    private JTextArea displayArea;

    public BSTGUI() {
        bst = new BinarySearchTree();
        initUI();
    }

    private void initUI() {
        setTitle("Binary Search Tree GUI");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        inputField = new JTextField();
        panel.add(inputField, BorderLayout.NORTH);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 3));

        JButton insertButton = new JButton("Insert");
        JButton searchButton = new JButton("Search");
        JButton deleteButton = new JButton("Delete");
        JButton inorderButton = new JButton("Inorder");
        JButton preorderButton = new JButton("Preorder");
        JButton postorderButton = new JButton("Postorder");

        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int value = Integer.parseInt(inputField.getText());
                    bst.insert(value);
                    displayArea.append("Inserted: " + value + "\n");
                    inputField.setText("");
                } catch (NumberFormatException ex) {
                    displayArea.append("Invalid input\n");
                }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int value = Integer.parseInt(inputField.getText());
                    boolean found = bst.search(value);
                    displayArea.append("Search " + value + ": " + (found ? "Found" : "Not Found") + "\n");
                    inputField.setText("");
                } catch (NumberFormatException ex) {
                    displayArea.append("Invalid input\n");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int value = Integer.parseInt(inputField.getText());
                    bst.delete(value);
                    displayArea.append("Deleted: " + value + "\n");
                    inputField.setText("");
                } catch (NumberFormatException ex) {
                    displayArea.append("Invalid input\n");
                }
            }
        });

        inorderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayArea.append("Inorder: ");
                bst.inorder();
                displayArea.append("\n");
            }
        });

        preorderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayArea.append("Preorder: ");
                bst.preorder();
                displayArea.append("\n");
            }
        });

        postorderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayArea.append("Postorder: ");
                bst.postorder();
                displayArea.append("\n");
            }
        });

        buttonPanel.add(insertButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(inorderButton);
        buttonPanel.add(preorderButton);
        buttonPanel.add(postorderButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                BSTGUI gui = new BSTGUI();
                gui.setVisible(true);
            }
        });
    }
}
