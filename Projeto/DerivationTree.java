import java.util.ArrayList;
import java.util.List;

public class DerivationTree {
    private String node;
    private List<DerivationTree> filhos;
    private int level;

    public DerivationTree(String node) {
        this.node = node;
        this.filhos =  new ArrayList<>();
        this.level = 0;
    }

    public void adiocionarFilho(DerivationTree filho){
        filho.level = this.level + 1;
        this.filhos.add(filho);
    }

    public void mostrar(){
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < level; i++) {
            indent.append("");
        }
        System.out.println(indent + " + " + node);
        for(DerivationTree filho : filhos){
            filho.mostrar();
        }
    }
    
}
