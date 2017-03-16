package cn.sportsdata.webapp.youth.common.utils;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import cn.sportsdata.security.crypto.adapter.byte2Str.Base64Bytes2StrAdapter;
import cn.sportsdata.security.crypto.adapter.byte2Str.IBytes2StrAdapter;
import cn.sportsdata.security.crypto.adapter.byte2Str.UTF8Bytes2StrAdapter;
import cn.sportsdata.security.crypto.adapter.byte2Str.WebExBase64Bytes2StrAdapter;
import cn.sportsdata.security.crypto.adapter.str2byte.IStr2BytesAdapter;
import cn.sportsdata.security.crypto.adapter.str2byte.UTF8PlainStr2BytesAdapter;
import cn.sportsdata.security.crypto.adapter.str2byte.WebExBase64Str2BytesAdapter;
import cn.sportsdata.security.crypto.digests.SHA256Creator;
import cn.sportsdata.security.crypto.exceptions.WbxSecException;
import cn.sportsdata.security.crypto.generators.decryption.IDecryptionKeyGenerator;
import cn.sportsdata.security.crypto.generators.digest.DefaultDigestKG;
import cn.sportsdata.security.crypto.generators.encryption.IEncryptionKeyGenerator;
import cn.sportsdata.security.crypto.symmetric.AESDecryptionEngine;
import cn.sportsdata.security.crypto.symmetric.AESEncryptionEngine;
import cn.sportsdata.security.util.DefaultVersionMixerEncKeyGen;
import cn.sportsdata.security.util.DefaultVersionRemoverDecKeyGen;
import cn.sportsdata.webapp.youth.common.exceptions.SoccerProException;
import cn.sportsdata.webapp.youth.common.exceptions.SoccerProRuntimeException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class SecurityUtils {
    private static final int MIN_PWD_HASH_ITERATIONS = Integer.parseInt(ConfigProps.getInstance().getConfigValue("password.hash.iteration.min"));
    private static final int MAX_PWD_HASH_ITERATIONS = Integer.parseInt(ConfigProps.getInstance().getConfigValue("password.hash.iteration.max"));
    private static final int PWD_HASH_SALT_LENGTH = Integer.parseInt(ConfigProps.getInstance().getConfigValue("password.hash.salt.length"));
    private static final String SECURITY_HASH_ALGORITHM = ConfigProps.getInstance().getConfigValue("security.hash.algorithm");
    private static final String TOKEN_SECRET_KEY = ConfigProps.getInstance().getConfigValue("token.security.key");
    private static final long TOKEN_SECRET_EXPIRE = Long.parseLong(ConfigProps.getInstance().getConfigValue("token.security.mobile.expire"));
    private static Logger logger = Logger.getLogger(SecurityUtils.class);

    /**
     * Hash Password Format: HexString(salt(32 bytes) + iterations(4 bytes) + hash(32 bytes))
     *
     * @param password
     * @return
     * @throws ECommerceException
     */
    public static String generateHashPassword(String password) {
        SecureRandom random = getRandomInstance();
        int iterations = MIN_PWD_HASH_ITERATIONS + random.nextInt(MAX_PWD_HASH_ITERATIONS - MIN_PWD_HASH_ITERATIONS + 1);
        byte[] salt = new byte[PWD_HASH_SALT_LENGTH];
        random.nextBytes(salt);

        return generateHashPassword(password, iterations, salt);
    }
    
    /**
     * invite code: HexString(salt(32 bytes) hash(32 bytes)).subString(0,8)
     *
     * @param password
     * @return
     * @throws ECommerceException
     */
    public static String genrateInviteCode(String orgCode) {
        SecureRandom random = getRandomInstance();
        byte[] salt = new byte[PWD_HASH_SALT_LENGTH];
        random.nextBytes(salt);
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance(SECURITY_HASH_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            logger.error("SHA256 algorithm is not existed.", e);
            throw new SoccerProRuntimeException("SHA256 algorithm is not existed.");
        }

        ByteBuffer buffer = ByteBuffer.allocate(32);
        //the first round
        digest.update(orgCode.getBytes());
        digest.update(salt);
        buffer.put(digest.digest());
        StringBuffer fullDigest = new StringBuffer(Hex.encodeHexString(buffer.array()));
        if (fullDigest.length() < 8) {
        	return fullDigest.toString();
        } else {
        	return fullDigest.substring(0, 8);
        }
    }

    public static boolean verifyPassword(String originPwd, String hashPwd) {
        byte[] bytes;
        
        try {
            bytes = Hex.decodeHex(hashPwd.toCharArray());
        } catch (DecoderException e) {
            logger.error(hashPwd + " can not be coverted to byte array.", e);
            return false;
        }

        ByteBuffer buffer = ByteBuffer.wrap(bytes);

        byte[] salt = new byte[PWD_HASH_SALT_LENGTH];
        buffer.get(salt, 0, PWD_HASH_SALT_LENGTH);
        int iterations = buffer.getInt();
        String generatedPwd = generateHashPassword(originPwd, iterations, salt);

        return StringUtils.equals(generatedPwd, hashPwd);
    }

    /**
     * Auth Token Format: HexString(auth info json) + HexString(hash)
     * This is the version for NO adding timestamp into token, properly used for cookie case
     *
     * @param authInfo
     * @return
     */
    public static String generateAuthToken(String authInfo) {
        if (StringUtils.isBlank(authInfo)) return "";

        byte[] encodedAuthInfo = Base64.encodeBase64(authInfo.getBytes(Charset.forName("UTF-8")));
        return Hex.encodeHexString(encodedAuthInfo) + generateSignature(encodedAuthInfo, 0);
    }

    public static JsonNode vefifyAuthToken(String authToken) {
        if (StringUtils.isBlank(authToken)) return null;
        authToken = authToken.trim();
        if (authToken.length() <= 64) return null;

        String part0 = authToken.substring(0, authToken.length() - 64);
        String part1 = authToken.substring(authToken.length() - 64, authToken.length());

        byte[] bytes;
        String authInfo;
        try {
            bytes = Hex.decodeHex(part0.toCharArray());
            authInfo = new String(Base64.decodeBase64(bytes), Charset.forName("UTF-8"));
        } catch (Exception e) {
            logger.error(part0 + " is not a valid auth info.", e);
            return null;
        }

        String generatedToken = generateSignature(bytes, 0);
        if (!StringUtils.equals(generatedToken, part1)) {
            logger.error(part1 + " is not a valid signature.");
            return null;
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = null;

        try {
            rootNode = mapper.readTree(authInfo);
        } catch (Exception e) {
            logger.error(authInfo + " is not a valid json format.", e);
        }

        return rootNode;
    }

    /**
     * Auth Token Format: HexString(auth info json) | Timestamp | HexString(hash)
     * This is the version for adding timestamp into token, properly used for mobile case
     *
     * @param authInfo
     * @return
     */
    public static String generateAuthToken_ts(String authInfo) {
        if (StringUtils.isBlank(authInfo)) return "";

        byte[] encodedAuthInfo = Base64.encodeBase64(authInfo.getBytes(Charset.forName("UTF-8")));
        long timestamp = System.currentTimeMillis();

        return Hex.encodeHexString(encodedAuthInfo) + "|" + timestamp + "|" + generateSignature(encodedAuthInfo, timestamp);
    }

    public static JsonNode vefifyAuthToken_ts(String authToken) {
        if (StringUtils.isBlank(authToken)) return null;
        authToken = authToken.trim();

        String[] parts = authToken.split("\\|");
        if (parts.length != 3) return null;

        byte[] bytes;
        String authInfo;
        try {
            bytes = Hex.decodeHex(parts[0].toCharArray());
            authInfo = new String(Base64.decodeBase64(bytes), Charset.forName("UTF-8"));
        } catch (Exception e) {
            logger.error(parts[0] + " is not a valid auth info.", e);
            return null;
        }

        long timestamp;
        try {
            timestamp = Long.parseLong(parts[1]);
        } catch (Exception e) {
            logger.error(parts[1] + " is not a valid timestamp.", e);
            return null;
        }

        if (System.currentTimeMillis() - timestamp > TOKEN_SECRET_EXPIRE) {
            return null;
        }

        String generatedToken = generateSignature(bytes, timestamp);
        if (!StringUtils.equals(generatedToken, parts[2])) {
            logger.error(parts[2] + " is not a valid signature.");
            return null;
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = null;

        try {
            rootNode = mapper.readTree(authInfo);
        } catch (Exception e) {
            logger.error(authInfo + " is not a valid json format.", e);
        }

        return rootNode;
    }

    private static String generateSignature(byte[] authInfo, long timestamp) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance(SECURITY_HASH_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            logger.error("SHA256 algorithm is not existed.", e);
            throw new SoccerProRuntimeException("SHA256 algorithm is not existed.");
        }

        digest.update(TOKEN_SECRET_KEY.getBytes());
        digest.update(authInfo);

        if (timestamp != 0) {
            digest.update(String.valueOf(timestamp).getBytes());
        }

        return Hex.encodeHexString(digest.digest());
    }

    private static SecureRandom getRandomInstance() {
        SecureRandom random;

        try {
            random = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            random = new SecureRandom();
        }

        random.setSeed(random.generateSeed(16));
        return random;
    }
    

    private static String generateHashPassword(String password, int iterations, byte[] salt) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance(SECURITY_HASH_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            logger.error("SHA256 algorithm is not existed.", e);
            throw new SoccerProRuntimeException("SHA256 algorithm is not existed.");
        }

        ByteBuffer buffer = ByteBuffer.allocate(32);
        //the first round
        digest.update(password.getBytes());
        digest.update(salt);
        buffer.put(digest.digest());

        for (int i = 0; i < (iterations - 1); i++) {
            digest.reset();

            digest.update(buffer.array());
            digest.update(salt);

            buffer.clear();
            buffer.put(digest.digest());
        }

        ByteBuffer result = ByteBuffer.allocate(4 + PWD_HASH_SALT_LENGTH + 32);
        result.put(salt, 0, PWD_HASH_SALT_LENGTH);
        result.putInt(iterations);
        result.put(buffer.array(), 0, 32);

        return Hex.encodeHexString(result.array());
    }
    
    public static String encryptByAES(String text) throws SoccerProException {
		IEncryptionKeyGenerator keyGenerator = new DefaultVersionMixerEncKeyGen();
		UTF8PlainStr2BytesAdapter utf8PlainStr2BytesAdapter = new UTF8PlainStr2BytesAdapter();
		IBytes2StrAdapter base64Bytes2StrAdapter = new WebExBase64Bytes2StrAdapter();
		AESEncryptionEngine aesEngine = new AESEncryptionEngine(keyGenerator, utf8PlainStr2BytesAdapter, base64Bytes2StrAdapter);
		try {
			return aesEngine.encrypt(text);
			
		} catch (WbxSecException e) {
			logger.error("encrypt failed", e);
			throw new SoccerProException("encrypt failed", e);
		}
	}
    
    public static String getSha256Sum(String text) throws WbxSecException {
    	DefaultDigestKG dkg = new DefaultDigestKG((new String("")).getBytes());
		IStr2BytesAdapter plainStr2BytesAdapter = new UTF8PlainStr2BytesAdapter();
		IBytes2StrAdapter encryptedBytes2StrAdapter = new Base64Bytes2StrAdapter();
		SHA256Creator shaCreator = new SHA256Creator(dkg, plainStr2BytesAdapter, encryptedBytes2StrAdapter);
		return shaCreator.sum(text);
    }
    
    public static String decryptByAES(String text) throws SoccerProException {
		IDecryptionKeyGenerator keyGenerator = new DefaultVersionRemoverDecKeyGen();
		IStr2BytesAdapter str2BytesAdapter = new WebExBase64Str2BytesAdapter();
		AESDecryptionEngine decryptionEngine = new AESDecryptionEngine(keyGenerator, str2BytesAdapter, new UTF8Bytes2StrAdapter());
		try {
			return decryptionEngine.decrypt(text);
		} catch (WbxSecException e) {
			logger.error("decrypt failed", e);
			throw new SoccerProException("decrypt failed", e);
		}
	}
    
    public static void main(String[] args) throws Exception {
        String password = "pass";
        String hash = SecurityUtils.generateHashPassword(password);
        System.out.println(hash);
        System.out.println(SecurityUtils.verifyPassword(password, hash));
        System.out.println(SecurityUtils.verifyPassword("P@ss1234", hash));

        ///////////////////////////////////////////////////////////////////////////////

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("userId", "12345");
        node.put("locale", "中文（中国）");
        String info = mapper.writeValueAsString(node);
        System.out.println(info);

        String token = SecurityUtils.generateAuthToken_ts(info);
        System.out.println(token);

        JsonNode retNode = SecurityUtils.vefifyAuthToken_ts(token);
        System.out.println(retNode);
    }
}
