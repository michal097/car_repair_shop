package mas.proj.controllers;

import lombok.Getter;
import mas.proj.dao.Part;
import mas.proj.repos.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PartsOverview {
    @Getter
    private PartRepository partRepository;
@Autowired
    public PartsOverview(PartRepository partRepository){
        this.partRepository=partRepository;
    }
    @GetMapping("/parts")
public Part getAllPartsWithQuantity(){
    return partRepository.findAll().get(0);

}
}
