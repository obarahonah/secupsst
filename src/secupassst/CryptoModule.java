/*
 * The MIT License
 *
 * Copyright 2020 obarahonah. https://github.com/obarahonah
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package secupassst;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.security.AlgorithmParameters;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;

/**
 *
 * @author obarahonah
 */
public class CryptoModule {

    //encrypt data and save it to file
    public static int file_encrypt(String data[][], String password, File file) {
        System.out.println("File Encrypted.");
        try {
            // encrypted file
            FileOutputStream outputFile = new FileOutputStream(file);

            //salt generation and storing
            byte[] data1 = new byte[16];
            SecureRandom secureRandom = new SecureRandom();
            secureRandom.nextBytes(data1);
            FileOutputStream saltFile = new FileOutputStream("data1");
            saltFile.write(data1);
            saltFile.close();
            //encryption configuration
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            KeySpec keySpec = new PBEKeySpec(password.toCharArray(), data1, 65536,256);//magic numbers
            SecretKey secretKey = factory.generateSecret(keySpec);
            SecretKey secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secret);
            AlgorithmParameters params = cipher.getParameters();
            // iv get and storing
            byte[] data2 = params.getParameterSpec(IvParameterSpec.class).getIV();
            FileOutputStream ivFile = new FileOutputStream("data2");
            ivFile.write(data2);
            ivFile.close();
            //converting object data to byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(data);
            oos.flush();
            byte[] input_data = bos.toByteArray();
            oos.close();
            //encrypting byte array and saving it to the file.
            byte[] output = cipher.doFinal(input_data);
            if (output != null) {
                outputFile.write(output);
            }
            outputFile.flush();
            outputFile.close();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "File encryption failed.");
        }
        return 0;
    }

    //decrypt saved file
    public static String[][] file_decrypt(String password, File file) {
        try {
            //reading the salt file
            FileInputStream saltFile = new FileInputStream("data1");
            byte[] data1 = new byte[16];
            saltFile.read(data1);
            saltFile.close();
            // reading the iv file
            FileInputStream ivFile = new FileInputStream("data2");
            byte[] data2 = new byte[16];
            ivFile.read(data2);
            ivFile.close();
            //encryption configuration
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            KeySpec keySpec = new PBEKeySpec(password.toCharArray(), data1, 65536,256);//magic numbers
            SecretKey tmp = factory.generateSecret(keySpec);
            SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");
            // Reading byte array from the file
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(data2));
            FileInputStream fis = new FileInputStream(file);
            byte[] fileContent = Files.readAllBytes(file.toPath());
            //decrypting the byte array
            byte[] output = cipher.doFinal(fileContent);
            //creating the object again from the byte array
            ByteArrayInputStream bos = new ByteArrayInputStream(output);
            ObjectInputStream ois = new ObjectInputStream(bos);
            String[][] matrix = (String[][])ois.readObject();
            fis.close();
            //sorting the matrix
            Sorting.experimentalHeapSortStringMatrix(matrix);
            return matrix;
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "File decryption failed, check for correct password.");
            System.exit(1);
            
        }
        return null;
    }
}
