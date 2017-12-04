import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;

public class VisualizationWindow extends JDialog {
    private JPanel panel;

    public VisualizationWindow(String title, Tree tree) {
        panel = new JPanel(new BorderLayout());
        setTitle(title);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel, BorderLayout.CENTER);
        setModal(false);
        createUIComponents(tree);
        panel.validate();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setAlwaysOnTop(true);
    }

    private void createUIComponents(Tree tree) {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(tree.getNode());
        fillNode(rootNode, tree.getChildren());
        DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
        JTree simpleTree = new JTree(treeModel);
        simpleTree.setRootVisible(true);
        simpleTree.setVisible(true);
        JScrollPane treePane = new JScrollPane(simpleTree);
        treePane.setPreferredSize(new Dimension(440, 620));
        panel.add(treePane);
    }

    private void fillNode(DefaultMutableTreeNode rootNode, java.util.List<Tree> subTrees) {
        for (Tree tree : subTrees) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(tree.getNode());
            fillNode(node, tree.getChildren());
            rootNode.add(node);
        }
    }
}

