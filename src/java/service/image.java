/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import static java.security.AccessController.getContext;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.imageio.ImageIO;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import model.Image;
import static sun.security.krb5.Confounder.bytes;

/**
 *
 * @author ASUS
 */
@WebService(serviceName = "image")
public class image {
    public File getFile(String fileName) throws IOException
    {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
         
        if (resource == null) {
            throw new IllegalArgumentException("file is not found!");
        } else {
            return new File(resource.getFile());
        }
    }
    
    @WebMethod(operationName = "getAllImage")
    public List<Image> getAll() throws IOException {
        image instance = new image();
        List<Image> listImage = new ArrayList<>();
        for(int i=1; i<=4 ;i++){
            File fnew = instance.getFile("Images/anh"+i+".jpg");
            FileInputStream fileInputStreamReader = new FileInputStream(fnew);
            byte[] bytes = new byte[(int)fnew.length()];
            fileInputStreamReader.read(bytes);
            String encodedImage ="data:image/jpg;base64,"+Base64.getEncoder().encodeToString(bytes);

            Image image = new Image(i, "name", "description", encodedImage);
            listImage.add(image);
        }       
        return listImage;
    }
    
    @WebMethod(operationName = "getImageById")
    public Image getById(int id) throws IOException {
        List<Image> list = getAll();
        Image image = list.get(id-1);
        System.out.println(image.getData());
        return image;
    }
}
