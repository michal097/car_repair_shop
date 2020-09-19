package mas.proj.dao;

import java.util.HashSet;
import java.util.Set;

public class PartCatalog extends Part {

    private Set<PartCatalog> partCatalogs = new HashSet<>();

    public Set<PartCatalog> getPartCatalogs(){
        return new HashSet<>(partCatalogs);
    }

    public PartCatalog(){
        partCatalogs.add(this);
    }
}
