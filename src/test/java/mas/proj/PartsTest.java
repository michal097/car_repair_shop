package mas.proj;

import mas.proj.repos.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class PartsTest {

    @Autowired
    private static PartRepository partRepository;


    public static void main(String [] args ){
        System.out.println(partRepository.findAll().get(0));
    }

}
