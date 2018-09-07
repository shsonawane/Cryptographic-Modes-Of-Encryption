

public class EncryptionMode {
	
	private byte initVector;
	private int key;
	
	public static final int ECB = 1;
	public static final int CBC = 2;
	public static final int CFB = 3;
	public static final int OF = 4;
	public static final int COUNTER = 5;
	
	public EncryptionMode(byte initVector, int key){
		this.initVector = initVector;
		this.key = key;
	}
	
	public byte[] encryptECB(byte[] pt){
		SDES sdes = new SDES(key);
		byte ct[] = new byte[pt.length+2];
		for (int i = 0; i < pt.length; i++) {
				ct[i] = sdes.encrypt((pt[i]));
		}
		ct[pt.length] = EncryptionMode.ECB;
		ct[pt.length+1] = initVector;
		return ct;
	}
	
	public byte[] decryptECB(byte[] ct){
		SDES sdes = new SDES(key);
		byte pt[] = new byte[ct.length-2];
		for (int i = 0; i < pt.length; i++) {
			pt[i] = sdes.decrypt(ct[i]);	
		}
		return pt;
	}
	
	public byte[] encryptCBC(byte[] pt){
		SDES sdes = new SDES(key);
		byte ct[] = new byte[pt.length+2];
		for (int i = 0; i < pt.length; i++) {
			if(i == 0 )
				ct[i] = sdes.encrypt((pt[i] ^ initVector));
			else
				ct[i] = sdes.encrypt((pt[i] ^ ct[i-1]));
		}
		ct[pt.length] = EncryptionMode.CBC;
		ct[pt.length+1] = initVector;
		return ct;
	}
	
	public byte[] decryptCBC(byte[] ct){
		SDES sdes = new SDES(key);
		byte pt[] = new byte[ct.length-2];
		for (int i = 0; i < pt.length; i++) {
			if(i == 0){
				pt[i] = (byte) (initVector ^ sdes.decrypt(ct[i]));				
			}else{
			    pt[i] = (byte) (ct[i-1] ^ sdes.decrypt(ct[i]));
			}
		}
		return pt;
	}
	
	public byte[] encryptCFB(byte[] pt){
		SDES sdes = new SDES(key);
		byte ct[] = new byte[pt.length+2];
		for (int i = 0; i < pt.length; i++) {
			if(i == 0 )
				ct[i] = (byte) (sdes.encrypt(initVector) ^ pt[i]);
			else
				ct[i] = (byte) (sdes.encrypt(ct[i-1]) ^ pt[i]);
		}
		ct[pt.length] = EncryptionMode.CFB;
		ct[pt.length+1] = initVector;
		return ct;
	}
	
	public byte[] decryptCFB(byte[] ct){
		SDES sdes = new SDES(key);
		byte pt[] = new byte[ct.length-2];
		for (int i = 0; i < pt.length; i++) {
			if(i == 0){
				pt[i] = (byte) (ct[i] ^ sdes.encrypt(initVector));				
			}else{
				pt[i] = (byte) (ct[i] ^ sdes.encrypt(ct[i-1]));	
			}
		}
		return pt;
	}
	
	public byte[] encryptOF(byte[] pt){
		SDES sdes = new SDES(key);
		byte ct[] = new byte[pt.length+2];
		byte nextVector = initVector;
		for (int i = 0; i < pt.length; i++) {
			nextVector = sdes.encrypt(nextVector);
			ct[i] = (byte) (nextVector ^ pt[i]);
		}
		ct[pt.length] = EncryptionMode.OF;
		ct[pt.length+1] = initVector;
		return ct;
	}
	
	public byte[] decryptOF(byte[] ct){
		SDES sdes = new SDES(key);
		byte pt[] = new byte[ct.length-2];
		byte nextVector = initVector;
		for (int i = 0; i < pt.length; i++) {
			nextVector = sdes.encrypt(nextVector);
			pt[i] = (byte) (nextVector ^ ct[i]);
		}
		return pt;
	}
	
	public byte[] encryptCounter(byte[] pt){
		SDES sdes = new SDES(key);
		byte ct[] = new byte[pt.length+2];
		byte counter = initVector;
		for (int i = 0; i < pt.length; i++) {
			ct[i] = (byte) (sdes.encrypt(counter) ^ pt[i]);
			counter++;
		}
		ct[pt.length] = EncryptionMode.COUNTER;
		ct[pt.length+1] = initVector;
		return ct;
	}
	
	public byte[] decryptCounter(byte[] ct){
		SDES sdes = new SDES(key);
		byte pt[] = new byte[ct.length-2];
		byte counter = initVector;
		for (int i = 0; i < pt.length; i++) {
			pt[i] = (byte) (sdes.encrypt(counter) ^ ct[i]);
			counter++;
		}
		return pt;
	}
	
	public static byte[] fileDecryption(byte[] ct , int k){
		int mode = ct[ct.length-2];
		byte iv = ct[ct.length-1];
		//System.out.println(mode);
		//System.out.println(iv);
		EncryptionMode em = new EncryptionMode(iv, k);
		if(mode == ECB){
			return em.decryptECB(ct);
		}else 
		if(mode == CBC){
			return em.decryptCBC(ct);
		}else
		if(mode == CFB){
			return em.decryptCFB(ct);
		}else 
		if(mode == OF){
			return em.decryptOF(ct);
		}else 
		if(mode == COUNTER){
			return em.decryptCounter(ct);
		}else{
			return null;	
		}
	}

}
