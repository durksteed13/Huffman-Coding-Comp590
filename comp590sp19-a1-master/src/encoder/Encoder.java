package encoder;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import io.InputStreamBitSource;
import io.InsufficientBitsLeftException;
import io.OutputStreamBitSink;
import decoder.CanonicalTree;
import decoder.DSymbol;

public class Encoder {
	
	public static void main(String args[]) throws IOException {
		
		HashMap <Character, Integer> frequencies = new HashMap<Character, Integer>();
		ArrayList <ESymbol> symbols = new ArrayList<ESymbol>();
		ArrayList <DSymbol> lengths = new ArrayList<DSymbol>();
		ArrayList <Character> chars = new ArrayList<Character>();
		HashMap <Character, String> codes = new HashMap<Character, String>();
		double[] probabilities = new double[256];
		
		//input source --> to be encoded
		String file = "data/decoded.txt";
		InputStream iStream = new FileInputStream(file);
		InputStreamBitSource iStreamSource = new InputStreamBitSource(iStream);
	
//		can use buffered reader instead
//		BufferedReader reader = new BufferedReader(new FileReader(file));
		
		//output source
		String outfile = "data/encoded.dat";
		OutputStream oStream = new FileOutputStream(outfile);
		OutputStreamBitSink oStreamSource = new OutputStreamBitSink(oStream);
		 
		//add all characters to map
		for(int i = 0; i < 256; i++) {
			frequencies.put((char)i, 0);
		}
		
		//read input character by character, update count in map
		int x;
		
//		can use buffered reader instead
//		while ((x = reader.read()) != -1) {
//			frequencies.put((char) x,frequencies.get((char) x) + 1);
//			chars.add((char) x);
//		}
		
		
		try {
			while ((x = iStreamSource.next(8)) != -1) {
				frequencies.put((char) x, frequencies.get((char)x)+1);
				chars.add((char) x);
			}
		} catch (InsufficientBitsLeftException e) {}
		
		//part3 questions
		//theoretical entropy
		for(int i = 0; i < 256; i++) {
			probabilities[i] = (double) frequencies.get((char) i)/chars.size();
		}
		double entropy = 0;
		for(int i = 0; i < 256; i++) {
			if(probabilities[i] != 0) {
				entropy+= (probabilities[i] * Math.log(probabilities[i]) / Math.log(2));
			}
		}
		System.out.println(Math.abs(entropy));
		
		//ArrayList of characters and their frequencies in the input
		for(Character c: frequencies.keySet()) {
			symbols.add(new ESymbol(c, frequencies.get(c)));
		}
		
		//create Huffman tree with characters and their frequencies
		OptimalMinVarianceHuffmanTree tree = new OptimalMinVarianceHuffmanTree(symbols);
		
		//discard tree and keep the lengths from the tree for each character
		lengths = tree.getLengths();
		
		//create canonical tree with lengths from Huffman tree
		CanonicalTree codeTree = new CanonicalTree(lengths);
		
		//get the Huffman codes from the canonical tree
		codes = codeTree.getCodes();
		
		//write first 256 bytes of encoded document as the lengths of each character's huffman code
		for(int i = 0; i < 256; i++) {
			oStreamSource.write(codes.get((char) i).length(), 8);
		}
		
		//write next 4 bytes of encoded document as the total number of characters in input
		oStreamSource.write(chars.size(), 32);
		
		
		//write bytes as the Huffman codes for the rest of the file
		for(int i = 0; i < chars.size(); i++) {
			oStreamSource.write(codes.get(chars.get(i)));
		}
		
		//pad remaining bits
		oStreamSource.padToWord();
		
		System.out.println("done");
	}
}
