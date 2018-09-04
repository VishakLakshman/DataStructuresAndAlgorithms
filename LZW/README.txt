Author Info
------------
Name: Vishak Lakshman Sanjeevikani Murugesh
ID: 800985356
email: vsanjeev@uncc.edu

About the project
-----------------
The Limpel-Ziv-Welch algorithm is a lossless adapative data compression algorithm. It's major advantage is that it eradicates redudancy more effectively than other compression techiniques and it is not necessary to transmit the data dictionary table along with the code which is an asset for spatial efficiency. The code can be used to generate its own data table for the purpose of encoding and decong making this encoding tehcnique highly self reliant.

This project is the implementation of LZW encoding technique. Based on the input N bit length, the code table for the symbols are generated and used to lower the repition of characters that are transmitted. This N bit lenght is also required for decoding and can reverse engineer the data table based on the corresponding codes. In case of encoder, a SYMBOLE-CODE table is generated initially for 0-255 individual characters and as new unique sequence of characters appear, the table is expanded the max size for the table expansion is given as 2^(bit_length). As new sequences are added, the codes are incremented from 256 and so on. This codes of 65 or 97 are converted into a 16-bit representation and are stored in the output file.4

Incase of decoding, the reverse of encoding is performed. The corresponding codes are mapped to symbols in the CODE-SYMBOL table generated and expanded as the values of codes exeeds 255. The 16-bit values are converted to binary then the codes are mapped to the corresponding symbols. The datastructures used for both encoder and decoder can vary. The usage of value indexed array for decoding allows us to achieve O(1) time complexity for searching, adding and retriving values. In case of encoding, a sorted array is used which guarantees atleast O(log n) time complexity. Further improvement can be made by the usage of Hash table which can result in O(1) time complexity.

As established before, the project fixes the ability to read and write N-bit integers. Apart from that, it covers the basic functioning of a LZW ecoding system and can reduce the redudancy in the input text. One drawback is the system fails for texts involving blank spaces. For a streamlined flow of characters, this technique is the most effective.  

Development Info
----------------

Language: JAVA
Compiler Version: javac 1.8.0_112

Files
-----

There are two JAVA files Encoder.java, Decoder.java and a README.txt in this package.

Encoder.java - This file contains the source code for the LZW encoding of the input text file. 

Decoder.java - This file has the source code for LZW decoding mechanism for the encoded file.

Installation Guide (For Windows)
-------------------

1. To run this program, you would need a JAVA Development Kit(JDK). It can be downloaded for free from http://www.oracle.com/technetwork/java/javase/downloads. 

2. Once the JDK has been downloaded, run the simple installer and complete installation of JDK.

3. Set up the JAVA HOME and PATH enivronmental variables. For detailed instructions to set up the enivronmental variable, go to http://docs.oracle.com/javase/7/docs/webnotes/install/windows/jdk-installation-windows.html

4. Once JDK setup is complete, copy the Encoder.java and Decoder.java files and paste them in the "bin" directory of your JDK folder. You can open Command Prompt in the particular path by SHIFT + RIGHT MOUSE CLICK --> OPEN COMMAND PROMPT HERE or typing cmd and pressing ENTER in the address bar. Note: If the JDK is in Primary Drive you may have to open COMMAND PROMPT as an Administrator.

5. To compile the two files, use the following commands C:/jdk/bin> javac Encoder.java and C:/jdk/bin> javac Decoder.java. This will generate the corresponding class files for the two java files.

6.To run the Encoder, type the command C:/jdk/bin> java Encoder "inputfilename_with_path" Bit_length. The encoded "input_encoded.lzw" file will be generated in C drive. You can manually change the destination in the source code and make sure you compile it again after the change.

7.To run the Decoder, type the command C:/jdk/bin> java Decoder "encodedfilename_with_path" Bit_length. The decoded "input_decoded.txt" text file will be generated in C drive. You can manually change the destination in the source code and make sure you compile it again after the change.
