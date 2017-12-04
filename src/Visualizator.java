public class Visualizator {
    private Tree tree;

    public Visualizator(Tree tree) {
        this.tree = tree;
    }

    public void show(String windowTitle) {
        new VisualizationWindow(windowTitle, tree);
    }
}