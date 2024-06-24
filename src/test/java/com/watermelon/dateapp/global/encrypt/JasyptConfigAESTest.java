package com.watermelon.dateapp.global.encrypt;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.iv.RandomIvGenerator;
import org.junit.jupiter.api.Test;

class JasyptConfigAESTest {

	@Test
	void stringEncryptor() {
		String url = "jdbc:postgresql://database.water-melon.p-e.kr:55432/blossom?currentSchema=user-service&rewriteBatchedStatements=true";
		String username = "user-service";
		String password = "user-service@develop";

		System.out.println(jasyptEncoding(url));
		System.out.println(jasyptEncoding(username));
		System.out.println(jasyptEncoding(password));
	}

	public String jasyptEncoding(String value) {

		String key = "OUR_SECRET_KEY";
		StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
		pbeEnc.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
		pbeEnc.setPassword(key);
		pbeEnc.setIvGenerator(new RandomIvGenerator());
		return pbeEnc.encrypt(value);
	}
}