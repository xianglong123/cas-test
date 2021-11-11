package com.cas.encryption.rsa.demo;

import org.apache.commons.lang3.StringUtils;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2021/11/10 5:39 下午
 * @desc
 */
public class TestVerity {


    /**
     *
     Map<String, String> map3 = new HashMap<String, String>() {{
     put(PUBLIC_KEY, "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmvwBOF7x+2ihVYCKdNbpxgY9n2P8kQudkDZOdJBSnA7OOqVf0HZVjx/Sl+e+/uqPv3bGc0iO49uTGuOIXU7/O6IaPbYnOBEG04bCWyFlmTIbMsTp+lZ4zIuqdy3Usm+QPG9CeWp7gnSVOUf8+Pf46MLmLMCtT1t9UrM0XQA88J0d/E19yq5Bs3mXIHBJOWDBWSc13R6LKR6pcjCIW8ov/LWVKSXQj5mp0EUsRgvR7NhcJTG5xdjG5dir/2i850pkWn7ouSNRE3GdkXH9zKf7z4Mhdsodh374knijWALV/JV4Es6BJ3A+9bBEazDvUoD4o7ctasOz85N78+IaHyCbYwIDAQAB");
     put(PRIVATE_KEY, "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANxPy7GrqwyX24grpA0e6HKbQuEcDok/Ru1l7FSfUNuw69B2pUfU7U1vnjfjV5k+JCxGUd0Ltaz4vCkQd7owF9AgXCzAACfU+CazVYoXLr7WVi2jAIUW6LEJd69o2u0fJw7DHBtSu047rf9kBB3uU24GfF7gkQkJn7WejfdyjvW5AgMBAAECgYAgkUszQIVQn0I8pvbhWahTCNCiJlufvlLl1SNSfxqc31J8xo04SSmrQMzKxji01pLSWLLRUmHQ6BQond6VD3b1bvhlubZrzKIaJfCTWQghWBj/jSeLWxSTbaR6cHc2/fqnoAzHWkb5UkSnNMyfhuBWUnJAhuuG7pcL8pTZGZrBgQJBAP0CbY3N4A70TY3kFqYlJxtMewiT6iwwhCrGfDGSMD4qJbEQJk3OINvOnW2X1Btsj2EDBlq8wrzGY6O0PZX7RLUCQQDe6m3Ax2FFisAzUsO6uPz7qbu99mvq7sE2AEKEZlj2gbrqMB8cpaFl2F4efKm0dudzkcoC3kRXeNtYDg4JtrN1AkB2MuEtg0WultF4fVtbaTX42eUMn9WhqVTFiw7QNcSV9ii/rJTIeoj9R2xim7C60x+0qa4ZS+AKQwCfpP+0hruVAkEAkmpcaHEM/QZjs9I66Vw0HoCf5egDfi4QDg4hoqEu8crIIKY26TQJTMHPICs1tGkdIRWzICsprGKff7jLk6nDOQJAeLR13DsD+TNVFSJhcbfXU7PTbsnPkzM6MbLxyrBdVkb0LTpVI/Sw/VuZ/GJ35X5d4JsvUoyJ9OYvjENTyCRoKw==");
     }};
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        String md5Key = "9999999999999964";
        String json = "{\"name\":\"xl\"}";
        String privateKey = "MIIEogIBAAKCAQEApT/JqzFP7E0QjmIIHeIg0+Cdgptb4UPgALLiL7CdT3OWVABnfgaUjaOrgqukLiUBiiQqB7RronAOdI6LIMhCusygCpj7F+DSNLOyfaHPk2OaIoFkM7ttHiccndFdndC7kUCxlJnvF9TJN91gDJSlQDEEIv3J8E5qwCAs6EyHOvtLemWXzY6YowiQs28wcvNjuM36DSmXdIe1EwuMrOH7kobBdr18nfk7p/dUGWH3j4zwfiVWTy6eRv8f8Zz8Y2hrq2n7Q8SWH1zHQWZJkgeaLq/aPgIzCTZ1ciNok2i+l9Q1EcJpMhXUA7F0pK7upP0oFFGEpvj81ycEITV9PpgtzQIDAQABAoIBABMnLYy2DnlpBT47ho1QJFXqdtcogqG9aD0sfqLgaZtH3mimE1i8EoPIMJzYoBLpHyCPyH9UHhWZv1MrIl0OWQ2lofcYfCHsYPDS0yFUm+aNaNQPAzorwx33ko2IMr2T7+mKAgsjPF8avqfG9NcAc5bZF42mtL0xmZXjJNFX7WSchBfS1oDE+KgEUHpZbA2tFYwajzGk7JQHtIZ6Md7jJ+16lm2V1pmO71TwxI3/w6TTWGejc2w09KzmkU37q4b88kEJGM1D5/Xd07Js9L+QJHLukGsYLgdv1gnsAW3ShNNzxyGeNXGvuri1slQckZz/TO5X2C6HSlKYrKec2WD26IECgYEA0OklNLBGv90CJGLtZHW/XGuAEfXyuqzI7K4iDXPNeTVzu8DysMx3OTI9rxl1c0tamlPMjD7NOJ509xKhIpMNvWNXAcYLxFeDljC7/gtDy8lncCBPFNgNUkZtrrHrqilYjYWEd4x0hjo5BmxsaZivbmRxmShwjZdL7FuX29rvy/UCgYEAyn85KfgXZxjmL/czQfnt9GOLRI/dAWpy3gZR9hZD3eBtWL7zO3Wm6rzmfDHKwmSVpgGBQ2cMksGjsuVJcwAx7U4HDLBixVGPzbQHvqKBWHhS85BmpODAnJLE2LCwnzeYjRQVQ3qWPGHKBYxIQiFwCU2S3AvUTXvhDLVA2WiAS3kCgYBl3ysLdGIrsUQYJj2I3XpBQRKkr4B700HvWEK2uJ+b2ZrkFYTC/hGtTHiT6CBD5pz54J9ATGeaknqK+QSmbXsnvUdyT/bw+ZPrKzyg9zFKMJ5GKRsnGOv2EO3vYEeBJMRPzqn3+f/gbIfeQATlwVTayhQzEWtBQGP/j/KPfUWt+QKBgFD6T4HZFILGEkdcM8NOjzoMFMV2QosuZoEjReODQ+CWvyOeOOauRfHZWNKRluPmFXItZ+Pscbq+dRg1WQoEKfMcIPWSE+2UkE1SkUZK9i7K72E3fdSxjFUQ3GdLQVZqhTblsRsrCqla8c8GKl9Hv+X4f6I/mMbjozvRv/0xLR0BAoGAZq1AnCtm+KVWCWrit6VIZx4GrsGX5PXiyUeCY9G4yOcadJEHBlkr97XKCG9J583aewOrh69ma245VhmgbX9/UsEaTAHGLVZ5HHt1zAHfEcqpz2kPA6Kjyg+YOsbbPxPd+XVl7D+mvBD4CdfYo4EOfx5d26244CkcVA+Zx0hTpvU=";
        PrivateKey privateKeyRSA = RSAPKCS8Utils.loadPrivateKey(privateKey);
        String md5Value = Encodes.encodeHex(Digests.md5((json + md5Key).getBytes()));
        String sign = RSAPKCS8Utils.encryptByPrivate(privateKeyRSA, md5Value);


        String clientPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApT/JqzFP7E0QjmIIHeIg0+Cdgptb4UPgALLiL7CdT3OWVABnfgaUjaOrgqukLiUBiiQqB7RronAOdI6LIMhCusygCpj7F+DSNLOyfaHPk2OaIoFkM7ttHiccndFdndC7kUCxlJnvF9TJN91gDJSlQDEEIv3J8E5qwCAs6EyHOvtLemWXzY6YowiQs28wcvNjuM36DSmXdIe1EwuMrOH7kobBdr18nfk7p/dUGWH3j4zwfiVWTy6eRv8f8Zz8Y2hrq2n7Q8SWH1zHQWZJkgeaLq/aPgIzCTZ1ciNok2i+l9Q1EcJpMhXUA7F0pK7upP0oFFGEpvj81ycEITV9PpgtzQIDAQAB";
        PublicKey clientPublicKeyRSA = RSAPKCS8Utils.loadPublicKey(clientPublicKey);
        String md5 = Encodes.encodeHex(Digests.md5((json + md5Key).getBytes()));
        String encodedData = RSAPKCS8Utils.decryptByPublic(clientPublicKeyRSA, sign);
        if (StringUtils.equalsIgnoreCase(encodedData, md5)) {
            System.out.println("aaaa");
            // 验签通过
        }
    }


}
