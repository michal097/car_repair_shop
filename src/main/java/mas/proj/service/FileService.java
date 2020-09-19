package mas.proj.service;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import mas.proj.dao.RepairOrder;
import mas.proj.repos.RepairOrderRepository;
import net.bytebuddy.implementation.bytecode.assign.TypeCasting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class FileService {

    @Value("${upload.path}")
        @Getter@Setter
    private String uploadDir;
        @Getter@Setter
    private static String getFileName;

    public void uploadFile(MultipartFile file) throws Exception {

        try {
            Path copyLocation = Paths
                    .get(uploadDir + File.separator + StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename())));
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
            getFileName = file.getOriginalFilename();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Could not store file " + file.getOriginalFilename()
                    + ". Please try again!");
        }
    }
    @Autowired
    private RepairOrderRepository repairOrderRepository;
    @Transactional
    public void saveImageToDB(Long id, MultipartFile file) throws IOException {
        byte [] byteObj = new byte[file.getBytes().length];
        int i =0;
        RepairOrder repairOrder  = repairOrderRepository.findById(id).get();

        for(byte b: file.getBytes()){
            byteObj[i++]=b;
        }
        repairOrder.setImage(byteObj);
        repairOrderRepository.save(repairOrder);
    }


    public void writeImageToResponse(Long id, HttpServletResponse httpServletResponse){
        httpServletResponse.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        httpServletResponse.setHeader("Cache-Control", "max-age=2628000");

        byte[] imageData  = repairOrderRepository.findByIdRepairOrder(id).getImage();
        String str = Base64.getEncoder().encodeToString(imageData);

        try(OutputStream outputStream = httpServletResponse.getOutputStream()){
         outputStream.write(imageData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
