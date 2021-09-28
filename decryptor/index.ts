import crypto from 'crypto';
import fs from 'fs-extra'
import {compactDecrypt, DecryptOptions} from 'jose/jwe/compact/decrypt'

/*
 * Use the JOSE library to perform JWE decryption
 * https://github.com/panva/jose/blob/main/docs/functions/jwe_compact_decrypt.compactDecrypt.md
 */
class IDTokenDecryptor {

    public async decrypt(): Promise<void> {

        // Load the private key
        const buffer = await fs.readFile('../certs/client.key');
        const pem = buffer.toString();
        const privateKey = crypto.createPrivateKey(pem);

        // Decrypt the encrypted ID token
        const jwe = 'eyJraWQiOiItOTk2NzcwMjc0IiwieDV0I1MyNTYiOiJZMEg0eTllYWlpTnlvcHJNWTZ2TW00aTNMQVR1MExFQlhnU3Y3RjFpTmxVIiwiYWxnIjoiUlNBLU9BRVAiLCJlbmMiOiJBMjU2Q0JDLUhTNTEyIiwiY3R5IjoiSldUIn0.hZbCjrnvBPifz4Q4Gr7uGYWpF8W66MOXsBmOE_5PEeWcIlILH8dg-d_qNLPPEmqMk2jooNf6Kj4r10kh7Vxzmzf89bCay73bYlIY_61--v5OhSnZg6lWZJA9hUPt4GR5yQGCrjeXjVZEe6Yr92oytKCtfnoKUSuQtGYdeojcv3m718W9gB98Q4cMHyiSuBNEngZGOahNXAnHE5TeKrUldzB96iGtw8WNtZ3x3LdVv-Wm2saaBAkuDhShClQf4l_jCSEAs5_nTVn__8F4c5Z6OmNpCsNt9DVMZTjGKgV3_X7m94ABJ4441W_2Gv0Y_8RTbUfzfIOANWhrBVksHSYQEg.OumXM0TLZG3o1G7gGdEM-Q.GXecYGL9LkfLUigESl8_lYx4R34lFjfYDKktIg32yeHzyWf-8DjKPUfd9o2v9656l_KrDOVLlFECohQoJDbCTjKS6g2XuMODUv54eam1zrCnWao3Uvd-0UMYWh6sQygfFP3GosaJxej_eRW73UmBGi8rvSsJbGl9mXgrBvCebPj6ONhVY-jp-L-ZJKc1s-pPWpi_YFoivITTWFiq7dkxvXAPAbstXD9sOcw9SB3ZRP4pFWMDV0_2_J-Db7IRbMd2AqwhtUlE3paRQLI-SzYIlQexPq-M1qB9bbkfxvNqgUDNhnLBuA122CMXz3CIoCK4x1E5BdOhaM6syMcUd-pKrr_EbJQlAZvX9zTDPEJ2BeUePQL8eoVUzLqzzLS4RIrYqUfuyyQFbuTvyahcVE8HqBtcrTMj8BStXypyfja2oqrwPhFxO9SluOm4l8Rsi48zMRcfMEz-50QcdmeLhogYJd7mt5X7bqEqPLPwWEnDp0z-pzcemVxnncKvgHim8zfiSCGfb5rz6pFbv38nMUu5JQCRjQXRKJDErj5o7knDwnX7WcVC7I0PnnSoi18J_kIDl6qP_jx2kJegS4JnepRsMRRphh2qqWja5LI1-Ge7ryeOyPJ5NEHUz5tQ6PaZvhiGG6bz5Dfk4eAtyjALoeE1zJcdzV4u1ObYVAvESjegAzUjwCgU_4rKKyiRyykw1AQJtQDGfg5qn-T0KILx47fHOV1a2T2hjcUFv3Iltm5IfLusiiiRdQeeplCV525Z8QTUqTCMvRB0ZFPr7b5AJBDjo9rZU4jW45f1wTA-tUr9vGHuzy5JEdQH5Tdz6ht9mj4CPBqJeNOvZsjPToRgOa5tmipQN7MSg70HiE92004f1MZdFuNCkkloL5l75QAD6qApYSvAkJPeSaOl5fB3RyCIy5idzBEzX3gj3NMAcf2mGJvgz7V-9TQVt-JCtBiBmNwNRpE-7rbWKV5sskRiBJVDVh4RqfA7P-og-D8lAzPCiWA_7HvROUWjT1_U0Ny4r6cT4LjuX4Q5BS9H_UuN4hjETDn3b0t-W2ix5a8JVBAoOxfF2NxHE_5R2LwWV31Qiy2rzBWEYrYjnXJbDYe-b5mSTT7BBmu9a-Gb6tIF6cfHhko6qOWtwucNEh3a5ly0jUnRqkGC9jTVh_i3oACyD2I3VK5IRPiPO172ZDjtfaFSTqIuz2GvCClas_NxI69u8Pd7L2cwE8SFXZ-tX_IwBTwac9iy--DSNn31DDz0X4cNY9Qyyq46soGC3_BnMlN-T7jdIiYcwDhhEuc9j5a86ZSO5A2ExuLuE9ASmtY1rgSnE7zLg1rbur8d4kT2owNj7KZyj2begb_jlrq-vJQg-CWx2WEe5ZXbDVq8lu7D2CkAF9xZoG6-dGQS3OvojOGdPxxbfVu8prek-C8wAAoWOrb7A86OgbJyHT9yO9YL_Kv4wJgdwhpTvRCCNYhSO7XwR01pdY4ky4t5XNZH11j9aLxZug_gKBK7h2MrmNJjNJPnVdvoONV8Fk8yyXZCaJq7S79qJAxnaCNvi8ZqdbHV6AzEdacyITKddDBH0Yu9WrNOoiNKvlThk_j1FLaVROlprVe7.XAdl0bhc-jp-9j_nB_RP9wgVg1WjrD1jHLRWDJpwyRU';
        const options = {
            keyManagementAlgorithms: ['RSA-OAEP'],
            contentEncryptionAlgorithms: ['A256CBC-HS512'],
        } as DecryptOptions;
        
        // Render the JWT
        const {plaintext: decryptedData, protectedHeader} = await compactDecrypt(jwe, privateKey, options);
        const payload = new TextDecoder().decode(decryptedData);
        console.log(payload);
    }
}

(async () => {
    const decryptor = new IDTokenDecryptor();
    await decryptor.decrypt();
})();